package com.wen.test;

import com.wen.XmlProcess;

import java.io.*;
/**
 * Created by wen on 2017/2/21.
 */
class RunnableDemo extends Thread {
    private XmlProcess mainxmlprocess;

    RunnableDemo( XmlProcess e) {
        mainxmlprocess = e;
    }
    @Override
    public void run() {
        mainxmlprocess.run_xml();
    }


}
public class test_xml {
    public static String readFileByLines(String fileName) {
        FileInputStream file = null;
        BufferedReader reader = null;
        InputStreamReader inputFileReader = null;
        String content = "";
        String tempString = null;
        try {
            file = new FileInputStream(fileName);
            inputFileReader = new InputStreamReader(file, "utf-8");
            reader = new BufferedReader(inputFileReader);
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                content += tempString;
                //content += "\n";
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return content;
    }
    public static void main(String[] args)
    {
        XmlProcess xmltest = new XmlProcess();
        String[] files = {
                "C:\\Users\\wen\\Desktop\\testexample\\demo.xml"

        };
        Thread demo = new RunnableDemo(xmltest);
        demo.start();
        for(int i = 0; i < files.length; i++)
        {
            xmltest.push_xml(files[i],readFileByLines(files[i]));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

}
