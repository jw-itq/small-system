/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2019-04-12 09:34:44 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class admin_002drole_002dedit_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!--_meta 作为公共模版分离出去-->\r\n");
      out.write("<!DOCTYPE HTML>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <meta charset=\"utf-8\">\r\n");
      out.write("    <meta name=\"renderer\" content=\"webkit|ie-comp|ie-stand\">\r\n");
      out.write("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\r\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no\" />\r\n");
      out.write("    <meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />\r\n");
      out.write("    <link rel=\"Bookmark\" href=\"/favicon.ico\" >\r\n");
      out.write("    <link rel=\"Shortcut Icon\" href=\"/favicon.ico\" />\r\n");
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
      out.write("    <!--/meta 作为公共模版分离出去-->\r\n");
      out.write("\r\n");
      out.write("    <title>新建网站角色 - 管理员管理 - H-ui.admin v3.1</title>\r\n");
      out.write("    <meta name=\"keywords\" content=\"H-ui.admin v3.1,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载\">\r\n");
      out.write("    <meta name=\"description\" content=\"H-ui.admin v3.1，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。\">\r\n");
      out.write("</head>\r\n");
      out.write("<style>\r\n");
      out.write("    .permission-list > dd > dl > dd {\r\n");
      out.write("        margin-left: 0px;\r\n");
      out.write("    }\r\n");
      out.write("</style>\r\n");
      out.write("<body>\r\n");
      out.write("<article class=\"page-container\">\r\n");
      out.write("    <form action=\"\" method=\"post\" class=\"form form-horizontal\" id=\"form-admin-role-add\">\r\n");
      out.write("        <input type=\"text\" hidden value=\"\" placeholder=\"\" id=\"id\" name=\"id\">\r\n");
      out.write("        <div class=\"row cl\">\r\n");
      out.write("            <label class=\"form-label col-xs-4 col-sm-3\"><span class=\"c-red\">*</span>角色名称：</label>\r\n");
      out.write("            <div class=\"formControls col-xs-8 col-sm-9\">\r\n");
      out.write("                <input type=\"text\" class=\"input-text\" value=\"\" placeholder=\"\" id=\"name\" name=\"name\">\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"row cl\">\r\n");
      out.write("            <label class=\"form-label col-xs-4 col-sm-3\">描述：</label>\r\n");
      out.write("            <div class=\"formControls col-xs-8 col-sm-9\">\r\n");
      out.write("                <input type=\"text\" class=\"input-text\" value=\"\" placeholder=\"\" id=\"description\" name=\"description\">\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"row cl\">\r\n");
      out.write("            <label class=\"form-label col-xs-4 col-sm-3\">拥有权限：</label>\r\n");
      out.write("            <div class=\"formControls col-xs-8 col-sm-9\">\r\n");
      out.write("                <dl class=\"permission-list\">\r\n");
      out.write("                    <dt>\r\n");
      out.write("                        <label>\r\n");
      out.write("                            <input type=\"checkbox\" value=\"\" name=\"checkAll\" id=\"checkAll\"> 全选\r\n");
      out.write("                        </label>\r\n");
      out.write("                    </dt>\r\n");
      out.write("                    <dd>\r\n");
      out.write("                        <dl class=\"cl permission-list1\">\r\n");
      out.write("                            <dd id=\"permissions\"></dd>\r\n");
      out.write("                        </dl>\r\n");
      out.write("                    </dd>\r\n");
      out.write("                </dl>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"row cl\">\r\n");
      out.write("            <div class=\"col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3\">\r\n");
      out.write("                <button id=\"saveButton\" type=\"submit\" class=\"btn btn-success radius\" id=\"admin-role-save\" name=\"admin-role-save\"><i class=\"icon-ok\"></i> 确定</button>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </form>\r\n");
      out.write("</article>\r\n");
      out.write("\r\n");
      out.write("<!--_footer 作为公共模版分离出去-->\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/jquery/1.9.1/jquery.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/layer/2.4/layer.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"static/h-ui/js/H-ui.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"static/h-ui.admin/js/H-ui.admin.js\"></script> <!--/_footer 作为公共模版分离出去-->\r\n");
      out.write("\r\n");
      out.write("<!--请在下方写此页面业务相关的脚本-->\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/jquery.validation/1.14.0/jquery.validate.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/jquery.validation/1.14.0/validate-methods.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/jquery.validation/1.14.0/messages_zh.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("    $(\"#id\").val(parent.roleId);\r\n");
      out.write("    $(\"#name\").val(parent.name);\r\n");
      out.write("    $(\"#description\").val(parent.description);\r\n");
      out.write("    var permissions=parent.permissions;\r\n");
      out.write("    var array= new Array();\r\n");
      out.write("    array=permissions.split(\"|\");\r\n");
      out.write("    var index = layer.load(3);\r\n");
      out.write("    $.ajax({\r\n");
      out.write("        url:\"/user/permissionList\",\r\n");
      out.write("        type: 'GET',\r\n");
      out.write("        success:function (data) {\r\n");
      out.write("            layer.close(index);\r\n");
      out.write("            if(data.success==true){\r\n");
      out.write("                var size=data.data.length;\r\n");
      out.write("                for(var i=0;i<size;i++){\r\n");
      out.write("                    var flag=false;\r\n");
      out.write("                    for (var j=0;j<array.length;j++)\r\n");
      out.write("                    {\r\n");
      out.write("                        if(array[j]==data.data[i].name){\r\n");
      out.write("                            flag=true;\r\n");
      out.write("                            break;\r\n");
      out.write("                        }\r\n");
      out.write("                    }\r\n");
      out.write("                    if(flag){\r\n");
      out.write("                        $(\"#permissions\").append(\"<label><input type='checkbox' checked value=\"+data.data[i].id+\" name='roles' id='roles'> \"+data.data[i].name+\"</label>\");\r\n");
      out.write("                    }else {\r\n");
      out.write("                        $(\"#permissions\").append(\"<label><input type='checkbox' value=\"+data.data[i].id+\" name='roles' id='roles'> \"+data.data[i].name+\"</label>\");\r\n");
      out.write("                    }\r\n");
      out.write("                }\r\n");
      out.write("            }else{\r\n");
      out.write("                layer.alert(data.message, {title: '错误信息',icon: 2});\r\n");
      out.write("            }\r\n");
      out.write("        },\r\n");
      out.write("        error:function(XMLHttpRequest){\r\n");
      out.write("            layer.close(index);\r\n");
      out.write("            layer.alert('数据处理失败! 错误码:'+XMLHttpRequest.status,{title: '错误信息',icon: 2});\r\n");
      out.write("        }\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("    $(function () {\r\n");
      out.write("        $(\"#checkAll\").click(function () {         //全选/取消全选\r\n");
      out.write("            $(\":checkbox\").prop(\"checked\", this.checked);\r\n");
      out.write("        });\r\n");
      out.write("        $(\":checkbox\").click(function () {          //当选中某个子复选框时，checkAll取消选中\r\n");
      out.write("            if (!this.checked) {\r\n");
      out.write("                $(\"#checkAll\").prop(\"checked\", false);\r\n");
      out.write("            }\r\n");
      out.write("        });\r\n");
      out.write("        $(\":checkbox\").click(function () {\r\n");
      out.write("            var chsub = $(\"input[name='roles']\").length;\r\n");
      out.write("            var checkedsub = $(\"input[name='roles']:checked\").length;\r\n");
      out.write("            if (checkedsub == chsub) {\r\n");
      out.write("                $(\"#checkAll\").prop(\"checked\", true);\r\n");
      out.write("            }\r\n");
      out.write("        });\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("    $(\"#form-admin-role-add\").validate({\r\n");
      out.write("        rules:{\r\n");
      out.write("            name:{\r\n");
      out.write("                required:true,\r\n");
      out.write("                minlength:1,\r\n");
      out.write("                maxlength:20,\r\n");
      out.write("                //remote: \"/user/edit/roleName/\"+parent.roleId\r\n");
      out.write("                //remote: \"/user/roleName\"\r\n");
      out.write("            },\r\n");
      out.write("        },\r\n");
      out.write("        messages: {\r\n");
      out.write("            /*name: {\r\n");
      out.write("                remote: \"该角色名已被使用\"\r\n");
      out.write("            }*/\r\n");
      out.write("        },\r\n");
      out.write("        onkeyup:false,\r\n");
      out.write("        focusCleanup:false,\r\n");
      out.write("        success:\"valid\",\r\n");
      out.write("        submitHandler:function(form){\r\n");
      out.write("            var index = layer.load(3);\r\n");
      out.write("            $(form).ajaxSubmit({\r\n");
      out.write("                url: \"/user/updateRole\",\r\n");
      out.write("                type: \"POST\",\r\n");
      out.write("                success: function(data) {\r\n");
      out.write("                    layer.close(index);\r\n");
      out.write("                    if(data.success==true){\r\n");
      out.write("                        parent.roleCount();\r\n");
      out.write("                        parent.refresh();\r\n");
      out.write("                        parent.msgSuccess(\"编辑成功!\");\r\n");
      out.write("                        var index = parent.layer.getFrameIndex(window.name);\r\n");
      out.write("                        parent.layer.close(index);\r\n");
      out.write("                    }else{\r\n");
      out.write("                        layer.alert(data.message, {title: '错误信息',icon: 2});\r\n");
      out.write("                    }\r\n");
      out.write("                },\r\n");
      out.write("                error:function(XMLHttpRequest) {\r\n");
      out.write("                    layer.close(index);\r\n");
      out.write("                    layer.alert('数据处理失败! 错误码:'+XMLHttpRequest.status+' 错误信息:'+JSON.parse(XMLHttpRequest.responseText).message,{title: '错误信息',icon: 2});\r\n");
      out.write("                }\r\n");
      out.write("            });\r\n");
      out.write("        }\r\n");
      out.write("    });\r\n");
      out.write("</script>\r\n");
      out.write("<!--/请在上方写此页面业务相关的脚本-->\r\n");
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
