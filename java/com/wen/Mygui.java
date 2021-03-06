package com.wen;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Mygui extends JFrame
{

    private JPanel mainPane;
    private JTextPane textArea;
    private JScrollPane sp;
    private StyledDocument d;
    private JButton button;
    private JButton button_1;
    private JButton button_2;
    private JButton button_3;
    private XmlProcess mainprocess;

    /**
     * Launch the application.
     */

    /**
     * Create the frame.
     */
    public void AppendMess(String mess, boolean flag)
    {
        SimpleAttributeSet attr = new SimpleAttributeSet();
        if(flag)
            StyleConstants.setForeground(attr, Color.blue);
        else
            StyleConstants.setForeground(attr, Color.red);
        try {
            d.insertString(d.getLength(),mess + "\n",attr);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    public Mygui(final XmlProcess mainpro)
    {
        mainprocess = mainpro;
        setTitle("数据库测试引擎");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(995, 708);
        setLocationRelativeTo(getOwner());
        mainPane = new JPanel();
        mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(mainPane);
        mainPane.setLayout(null);

        textArea = new JTextPane();
        textArea.setToolTipText("执行结果");
        d = textArea.getStyledDocument();
        sp = new JScrollPane(textArea);
        sp.setBounds(22, 41, 881, 560);
        mainPane.add(sp);

        JLabel label = new JLabel("执行结果：");
        label.setBounds(23, 10, 85, 15);
        mainPane.add(label);

        button = new JButton("开始");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0)
            {
                button_1.setEnabled(true);
                button_2.setEnabled(true);
                button_3.setEnabled(true);
            }
        });
        button.setBounds(22, 636, 93, 23);
        mainPane.add(button);

        button_1 = new JButton("继续");
        button_1.setBounds(146, 636, 93, 23);
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0)
            {
                if(mainpro.restart_xml())
                {
                    button_1.setEnabled(false);
                    button_2.setEnabled(true);
                }

            }
        });
        mainPane.add(button_1);

        button_2 = new JButton("暂停");
        button_2.setBounds(263, 636, 93, 23);
        button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0)
            {
                if(mainpro.pause_xml())
                {
                    button_2.setEnabled(false);
                    button_1.setEnabled(true);
                }
            }
        });
        mainPane.add(button_2);
        button_3 = new JButton("终止");
        button_3.setBounds(378, 636, 93, 23);
        button_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0)
            {
                if(mainpro.stop_xml()) {
                    button_1.setEnabled(false);
                    button_2.setEnabled(false);
                    button_3.setEnabled(false);
                    button.setEnabled(true);
                }
            }
        });
        mainPane.add(button_3);
    }
}
