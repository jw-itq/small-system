/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2019-03-30 14:27:02 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class dict_002dlist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE HTML>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <meta charset=\"utf-8\">\r\n");
      out.write("    <meta name=\"renderer\" content=\"webkit|ie-comp|ie-stand\">\r\n");
      out.write("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\r\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no\" />\r\n");
      out.write("    <meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />\r\n");
      out.write("    <!--[if lt IE 9]>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"lib/html5shiv.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"lib/respond.min.js\"></script>\r\n");
      out.write("    <![endif]-->\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"static/h-ui/css/H-ui.min.css\" />\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"static/h-ui.admin/css/H-ui.admin.css\" />\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"lib/Hui-iconfont/1.0.8/iconfont.css\" />\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"static/h-ui.admin/skin/default/skin.css\" id=\"skin\" />\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"static/h-ui.admin/css/style.css\" />\r\n");
      out.write("    <!--[if IE 6]>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"lib/DD_belatedPNG_0.0.8a-min.js\" ></script>\r\n");
      out.write("    <script>DD_belatedPNG.fix('*');</script>\r\n");
      out.write("    <![endif]-->\r\n");
      out.write("    <title>词典库管理</title>\r\n");
      out.write("</head>\r\n");
      out.write("<style>\r\n");
      out.write("    .table>tbody>tr>td{\r\n");
      out.write("        text-align:center;\r\n");
      out.write("    }\r\n");
      out.write("</style>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("<div>\r\n");
      out.write("    <nav class=\"breadcrumb\"><i class=\"Hui-iconfont\">&#xe67f;</i> 首页 <span class=\"c-gray en\">&gt;</span> 搜索管理 <span class=\"c-gray en\">&gt;</span> 词典库管理 <a class=\"btn btn-success radius r\" style=\"line-height:1.6em;margin-top:3px\" href=\"javascript:location.replace(location.href);\" title=\"刷新\" ><i class=\"Hui-iconfont\">&#xe68f;</i></a></nav>\r\n");
      out.write("    <form class=\"page-container\">\r\n");
      out.write("        <div class=\"cl pd-5 bg-1 bk-gray mt-20\">\r\n");
      out.write("            <span class=\"l\">\r\n");
      out.write("                <a href=\"javascript:;\" onclick=\"datadel()\" class=\"btn btn-danger radius\"><i class=\"Hui-iconfont\">&#xe6e2;</i> 批量删除</a>\r\n");
      out.write("                <a class=\"btn btn-primary radius\" onclick=\"add('添加词典','dict-add',500,250)\" href=\"javascript:;\"><i class=\"Hui-iconfont\">&#xe600;</i> 添加词典</a>\r\n");
      out.write("                <select class=\"select-box\" style=\"width: 100px\" id=\"type\" onchange=\"changeType()\">\r\n");
      out.write("                    <option value=\"1\" selected>扩展词库</option>\r\n");
      out.write("                    <option value=\"0\">停用词库</option>\r\n");
      out.write("                </select>\r\n");
      out.write("            </span>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"mt-20\">\r\n");
      out.write("            <div class=\"mt-20\" style=\"margin-bottom: 70px\">\r\n");
      out.write("                <table class=\"table table-border table-bordered table-bg table-hover table-sort\" width=\"100%\">\r\n");
      out.write("                    <thead>\r\n");
      out.write("                    <tr class=\"text-c\">\r\n");
      out.write("                        <th width=\"15\"><input type=\"checkbox\" value=\"\" name=\"\"></th>\r\n");
      out.write("                        <th width=\"30\">ID</th>\r\n");
      out.write("                        <th width=\"80\">词典名称</th>\r\n");
      out.write("                        <th width=\"20\">类型</th>\r\n");
      out.write("                        <th width=\"50\">操作</th>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                    </thead>\r\n");
      out.write("                </table>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </form>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<!--_footer 作为公共模版分离出去-->\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/jquery/1.9.1/jquery.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/layer/2.4/layer.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"static/h-ui/js/H-ui.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"static/h-ui.admin/js/H-ui.admin.js\"></script> <!--/_footer 作为公共模版分离出去-->\r\n");
      out.write("\r\n");
      out.write("<!--请在下方写此页面业务相关的脚本-->\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/datatables/1.10.0/jquery.dataTables.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/datatables/dataTables.colReorder.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/laypage/1.2/laypage.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/common.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("    /*datatables配置*/\r\n");
      out.write("    $(document).ready(function () {\r\n");
      out.write("        $('.table').DataTable({\r\n");
      out.write("            \"processing\": true,//加载显示提示\r\n");
      out.write("            \"ajax\": {\r\n");
      out.write("                url:\"/dict/list\",\r\n");
      out.write("                type: 'GET'\r\n");
      out.write("            },\r\n");
      out.write("            \"columns\": [\r\n");
      out.write("                { \"data\": null,\r\n");
      out.write("                    render : function(data,type, row, meta) {\r\n");
      out.write("                        return \"<input name=\\\"checkbox\\\" value=\\\"\"+row.id+\"\\\" type=\\\"checkbox\\\" value=\\\"\\\">\";\r\n");
      out.write("                    }\r\n");
      out.write("                },\r\n");
      out.write("                { \"data\": \"id\"},\r\n");
      out.write("                { \"data\": \"dict\"},\r\n");
      out.write("                { \"data\": \"type\",\r\n");
      out.write("                    render: function (data, type, row, meta) {\r\n");
      out.write("                        if(data==0){\r\n");
      out.write("                            return \"<span class=\\\"label label-defant radius td-status\\\">停用词库</span>\";\r\n");
      out.write("                        }else if(data==1){\r\n");
      out.write("                            return \"<span class=\\\"label label-success radius td-status\\\">扩展词库</span>\";\r\n");
      out.write("                        }\r\n");
      out.write("                    }\r\n");
      out.write("                },\r\n");
      out.write("                {\r\n");
      out.write("                    \"data\": null,\r\n");
      out.write("                    render: function (data, type, row, meta) {\r\n");
      out.write("                        return \"<a style=\\\"text-decoration:none\\\" class=\\\"ml-5\\\" onClick=\\\"edit('编辑','dict-edit',500,250)\\\" href=\\\"javascript:;\\\" title=\\\"编辑\\\"><i class=\\\"Hui-iconfont\\\">&#xe6df;</i></a> \" +\r\n");
      out.write("                            \"<a style=\\\"text-decoration:none\\\" class=\\\"ml-5\\\" onClick=\\\"del(\"+row.id+\")\\\" href=\\\"javascript:;\\\" title=\\\"删除\\\"><i class=\\\"Hui-iconfont\\\">&#xe6e2;</i></a>\";\r\n");
      out.write("                    }\r\n");
      out.write("                }\r\n");
      out.write("            ],\r\n");
      out.write("            \"aaSorting\": [[ 1, \"desc\" ]],//默认第几个排序\r\n");
      out.write("            \"bStateSave\": false,//状态保存\r\n");
      out.write("            \"aoColumnDefs\": [\r\n");
      out.write("                {\"orderable\":false,\"aTargets\":[0,4]}// 制定列不参与排序\r\n");
      out.write("            ],\r\n");
      out.write("            language: {\r\n");
      out.write("                url: '/lib/datatables/Chinese.json'\r\n");
      out.write("            },\r\n");
      out.write("            colReorder: true\r\n");
      out.write("        });\r\n");
      out.write("\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("    function add(title,url,w,h){\r\n");
      out.write("        layer_show(title,url,w,h);\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    function changeType() {\r\n");
      out.write("        var v = $(\"#type\").val();\r\n");
      out.write("        var table = $('.table').DataTable();\r\n");
      out.write("        if(v==\"0\"){\r\n");
      out.write("            table.ajax.url( '/dict/stop/list' ).load();\r\n");
      out.write("        }else{\r\n");
      out.write("            table.ajax.url( '/dict/list' ).load();\r\n");
      out.write("        }\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    var id,dict,type;\r\n");
      out.write("    function edit(title,url,w,h){\r\n");
      out.write("        var table = $('.table').DataTable();\r\n");
      out.write("        $('.table tbody').on( 'click', 'tr', function () {\r\n");
      out.write("            id = table.row(this).data().id;\r\n");
      out.write("            dict = table.row(this).data().dict;\r\n");
      out.write("            type = table.row(this).data().type;\r\n");
      out.write("        });\r\n");
      out.write("        layer_show(title,url,w,h);\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    function del(id){\r\n");
      out.write("        layer.confirm('确认要删除ID为\\''+id+'\\'的数据吗？',{icon:0},function(index){\r\n");
      out.write("            $.ajax({\r\n");
      out.write("                type: 'DELETE',\r\n");
      out.write("                url: '/dict/del/'+id,\r\n");
      out.write("                dataType: 'json',\r\n");
      out.write("                success: function(data){\r\n");
      out.write("                    if(data.success!=true){\r\n");
      out.write("                        layer.alert(data.message,{title: '错误信息',icon: 2});\r\n");
      out.write("                        return;\r\n");
      out.write("                    }\r\n");
      out.write("                    refresh();\r\n");
      out.write("                    layer.msg('已删除!',{icon:1,time:1000});\r\n");
      out.write("                },\r\n");
      out.write("                error:function(XMLHttpRequest) {\r\n");
      out.write("                    layer.alert('数据处理失败! 错误码:'+XMLHttpRequest.status,{title: '错误信息',icon: 2});\r\n");
      out.write("                },\r\n");
      out.write("            });\r\n");
      out.write("        });\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /*批量删除*/\r\n");
      out.write("    function datadel() {\r\n");
      out.write("        var cks=document.getElementsByName(\"checkbox\");\r\n");
      out.write("        var count=0,ids=\"\";\r\n");
      out.write("        for(var i=0;i<cks.length;i++){\r\n");
      out.write("            if(cks[i].checked){\r\n");
      out.write("                count++;\r\n");
      out.write("                ids+=cks[i].value+\",\";\r\n");
      out.write("            }\r\n");
      out.write("        }\r\n");
      out.write("        if(count==0){\r\n");
      out.write("            layer.msg('您还未勾选任何数据!',{icon:5,time:3000});\r\n");
      out.write("            return;\r\n");
      out.write("        }\r\n");
      out.write("        /*去除末尾逗号*/\r\n");
      out.write("        if(ids.length>0){\r\n");
      out.write("            ids=ids.substring(0,ids.length-1);\r\n");
      out.write("        }\r\n");
      out.write("        layer.confirm('确认要删除所选的'+count+'条数据吗？',{icon:0},function(index){\r\n");
      out.write("            var index = layer.load(3);\r\n");
      out.write("            $.ajax({\r\n");
      out.write("                type: 'DELETE',\r\n");
      out.write("                url: '/dict/del/'+ids,\r\n");
      out.write("                dataType: 'json',\r\n");
      out.write("                success:function(data){\r\n");
      out.write("                    layer.close(index);\r\n");
      out.write("                    if(data.success!=true){\r\n");
      out.write("                        layer.alert(data.message,{title: '错误信息',icon: 2});\r\n");
      out.write("                        return;\r\n");
      out.write("                    }\r\n");
      out.write("                    layer.msg('已删除!',{icon:1,time:1000});\r\n");
      out.write("                    refresh();\r\n");
      out.write("                },\r\n");
      out.write("                error:function(XMLHttpRequest){\r\n");
      out.write("                    layer.close(index);\r\n");
      out.write("                    layer.alert('数据处理失败! 错误码:'+XMLHttpRequest.status,{title: '错误信息',icon: 2});\r\n");
      out.write("                }\r\n");
      out.write("            });\r\n");
      out.write("        });\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    function msgSuccess(content){\r\n");
      out.write("        layer.msg(content, {icon: 1,time:3000});\r\n");
      out.write("    }\r\n");
      out.write("</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
