/**
 * Created by xq on 2017/2/9.
 */
var index = 0;
var EtreeTG={};
var textBeforeEdit="";// 保存修改之前的文件名
var parent,children; // 保存重命名过程中 父 子节点
var once=1;
var tag=0;//用户判断是否重名
// var length=0;
//监听点击  如果增加了tab 就将panel的id与tab的index做对应
function del(){
  var t=$("#etree").etree("getSelected");
  var p=$("#etree").etree("getParent",t.target);
  // $('#etree').etree('destroy');

  // var c=$("#etree").etree("getChildren",p.target);
// alert(JSON.stringify(c)) ;
//   $("#etree").etree("reload",t.target);
//    $("#etree").etree("reload",p.target);

  $('#etree').tree('expandTo', p.target).tree('select', p.target).tree("reload",p.target);



  // $("#etree").etree("reload",p.target);
  //
  //
  //
  // $("#etree").tree("expandTo",p.target);


  // $("#etree").etree("reload",t.target);
  // alert("reload");

};
$(document).ready(function(){
  $("#tt").tabs({
    onAdd:function(title,index){
      // alert(index);
      // alert(node);
      var tar=$("#tt").tabs("getTab",index).panel('options');
      // alert(tar.id);
      EtreeTG[tar.id]=index;
      // alert("新加tabs的index是"+index+" 对应的id是"+tar.id);

      // node[id]=index;
    },
    //关闭tab后触发  在对应关系中删除
    onClose:function(title,index){
      var last;
      for (var i in EtreeTG){
        last=i;
      }
      $("#tt").tabs("select",index)
    }
  })
})
// $(".tabs-close").click(function(){
//   alert("test");
// })
//j监听etree 的编辑

// node.test='test';
function addPanel(url,title,id) {



  //  alert(node.test);
  //
  // var nodes=eval(node);

  // var nodes=$('#'+id);
  // alert(nodes);
  // var myindex;
  // for(var tab in nodes)
  // {
  //   alert("tab :"+tab+"value in nodes[tab]"+nodes[tab]);
  //   if(nodes[tab].panel("options").id==id)
  //       index=tab.index;
  //
  // }
  // var tar=nodes.tabs("getTab",id);
  // alert(tar);

  // var showtitle=title.substring(2,title.length);
// alert("id in addPanel:"+id);
  //通过node判断该id是否有对应的tab
  var judge=EtreeTG[id]==null;
  if (judge) {
    // node=
    $('#tt')
        .tabs(
            'add',
            {
              id:id,
              title: title,
              content: '<iframe src="'
              + url
              + '" frameBorder="0" border="0" id="'+id+'" style="width: 100%; height: 99%; scrolling="no";"/>',
              closable: true,

            });
  } else {
    $('#tt').tabs('select', EtreeTG[id]);
  }
}
function removePanel() {
  var tab = $('#tt').tabs('getSelected');
  if (tab) {
    var index = $('#tt').tabs('getTabIndex', tab);
    $('#tt').tabs('close', index);
    // EtreeTG[tab.panel("options").id]=null;
  }
}

//删除Tabs
function closeTab(menu, type) {
  // var allTabs = $("#tt").tabs('tabs');
  // var allTabtitle = [];
  // $.each(allTabs, function (i, n) {
  //   var opt = $(n).panel('options');
  //   if (opt.closable)
  //     allTabtitle.push(opt.title);
  // });
  // var curTabTitle = $(menu).data("tabTitle");
  var curTab=$("#tt").tabs("getSelected");
  var curId=curTab.panel("options").id;
  var curTabIndex =EtreeTG[curId];
  var len=0;
  for (var i in EtreeTG){
    len++;
  }

  var key=Object.keys((EtreeTG));
//   alert(EtreeTG)
//    alert(EtreeTG[0]);
//   alert("EtreGrid [key]"+EtreeTG[key[0]]);

  switch (type) {
    case 1: //delete this tab
      // delete EtreeTG[curId];
      $("#tt").tabs("close", curTabIndex);

      return false;
      break;
    case 2: //delete all tabs
            // alert("test");
            // closeAllTag=1;
      for(var i in EtreeTG){
        $('#tt').tabs('close', EtreeTG[i]);

      }

      break;
    case 3: //delete other tabs

      for (var i = 0; i < len; i++) {
        if (curId != key[i])
          $('#tt').tabs('close', EtreeTG[key[i]]);
        // EtreeTG[key[i]]=null;
      }
      $('#tt').tabs('select', curTabIndex);
      break;
    case 4: // delete right
      for (var i = curTabIndex; i <len; i++) {
        $('#tt').tabs('close', EtreeTG[key[i]]);
        // EtreeTG[key[i]]=null;
      }
      // $('#tt').tabs('select', curTabIndex);
      break;
    case 5: //delete left
      for (var i = 0; i < curTabIndex - 1; i++) {
        $('#tt').tabs('close', EtreeTG[key[i]]);
        // EtreeTG[key[i]]=null;
      }
      // $('#tt').tabs('select', curTabIndex);
      break;
    case 6: //刷新
      var panel = $("#tt").tabs("getTab", curTabIndex).panel("refresh");
      break;
  }

}

function removeAll(){
//        $('#kzt1').remove();
//        $('#kztbody',$('#kzt1iframe').contentWindow.document).remove();
//        document.getElementById('#kzt1iframe').contentWindow.document.getElementById('#kztbody').remove();
  var tab = $('#kzts').tabs('getSelected');
  if (tab) {
    var index = $('#kzts').tabs('getTabIndex', tab);
//            $('#tt').tabs('close', index);
    console.log(index);
  }
  var id = "kzt"+(1+index)+"iframe";

  document.getElementById(id).contentWindow.location.reload(true);
}

$(document).ready(function () {
  //监听右键事件，创建右键菜单
  $('#tt').tabs({
    onContextMenu: function (e, title, index) {
      e.preventDefault();
      if (index > 0) {
        $('#mm').menu('show', {
          left: e.pageX,
          top: e.pageY
        }).data("tabTitle", title);
      }
    },
  });
//tabs关闭之前 删除对应关系  注意删除的时候需要将所有的后方节点的index-1
  $("#tt").tabs({
    onBeforeClose:function(title,index){

      var node=$("#tt").tabs("getTab",index).panel('options');
      // alert("删除之前的整个关系表是"+JSON.stringify(EtreeTG))
      // alert("需要删除的id"+node.id);
      // alert("需要被删除的tab的index是"+index);
      delete EtreeTG[node.id];
      // if(!closeAllTag){
      for(var i in EtreeTG)
      {
        if(EtreeTG[i]>index){
          EtreeTG[i]--;
        }
      }
      // }

      // alert("after delete"+JSON.stringify(EtreeTG));
      // alert(EtreeTG);
    }
  });
  //监听tabs的左键点击
  $('#tt').tabs({
    onSelect: function (title,index) {
      // var node=$("#tt").tabs("getTab",index).panel('options');
      //  var pp = $('#etree').tree('find', node.id);
      // $('#etree').tree('expandTo', pp.target).tree('select', pp.target);
      for(var i in EtreeTG){
        if(EtreeTG[i]==index){
          var pp = $('#etree').tree('find', i);
          $('#etree').tree('expandTo', pp.target).tree('select', pp.target);
        }

      }
    }
  });
  $('#kzts').tabs({
    onContextMenu: function (e, title, index) {
      e.preventDefault();
      if (index >= 0) {
        $('#kzt1menu').menu('show', {
          left: e.pageX,
          top: e.pageY
        });//.data("tabTitle", title);
      }
    }
  });
  //右键菜单click
  $("#mm").menu({
    onClick: function (item) {
      closeTab(this, item.name);
    }
  });
//监听etree的右键  同时选中相应的tabs
  $("#etree").etree({
    onContextMenu: function (e, node) {
      e.preventDefault();
      var p=$("#tt").tabs("select",EtreeTG[node.id]);
      //查找节点
      $('#etree').etree('select',node.target);
      //显示快捷菜单
      if (node.type == "file"){
        console.log("file");
        $('#fmenu').menu('show',{
          left: e.pageX,
          top: e.pageY
        });
      }else if (node.type == "directory"){
        console.log("directory");
        $('#dmenu').menu('show',{
          left: e.pageX,
          top: e.pageY
        });
      }
    },
    onBeforeEdit:function(node){
      if(once==1){
        textBeforeEdit=node.text;
        parent=$("#etree").etree("getParent",node.target);
        children=$("#etree").etree("getChildren",parent.target);
         // children=JSON.stringify(children);
         // alert("all children"+JSON.stringify(children)+"to  string1");
        once=0;
      }
    
    
    },
    onAfterEdit:function(node){
      // var tab = $('#stree').tree('getSelected');
      // alert(tab.title);
      // 获取到点击的节点的父节点 并获得所有的兄弟节点  判断修改的节点是否在兄弟节点中重名
      //  alert(parent.id+children);
      //
      //  alert(textBeforeEdit);
      // children.pop(node.id);
      // children=JSON.stringify(children);
      // alert(children+"to  string1");
      var tab=$("#tt").tabs("getTab",EtreeTG[node.id]);
      // $("#tt").tabs("update",{
      //   tab:tab,
      //   options:{title:node.text}
      // });
      tag=0;
      for(var i in children){
        // alert("try to find");
        // alert("i text"+children[i].text);
          if(children[i].text==node.text&&children[i].level==node.level&&children[i].id!=node.id)
          {
            tag=1;
            // alert("find ");
            break;
          }
      }
      // var index=children.indexOf(node.text);
       // alert(node.text+"find "+index);
      // alert(children+"to  string2");
      
      if(tag==0){
        tag=1;
        $("#tt").tabs("update",{
          tab:tab,
          options:{title:node.text}
        });
         // alert("c编辑生效");


      }else if (node.text!="新建文件"&&node.text!="新建文件夹"&&node.text!=textBeforeEdit){
        alert("名字重复请重新输入");
        tag=0;
        // node.text=textBeforeEdit;
        $("#etree").etree("update",{
          target:node.target,
          text:textBeforeEdit
      });
        // alert("textBeforeEdit:"+textBeforeEdit)
        // alert("c编辑生效");

      }
      parent=children="";
      once=1;
      // alert("test"+node.id+node.text);
    }
  });
  var setting = {
    view: {
      selectedMulti: false
    },
    callback: {
      onClick: addPanel
    }
  };

//        $('#kzt_if').window.scroll(0,999999);
});