/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2019-04-11 12:56:33 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("    <link rel=\"Bookmark\" href=\"favicon.ico\" >\r\n");
      out.write("    <link rel=\"Shortcut Icon\" href=\"icon/x.png\" />\r\n");
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
      out.write("    <title>SMall后台管理系统 v1.1</title>\r\n");
      out.write("    <meta name=\"keywords\" content=\"SMall后台管理系统 v1.1,SMall,SMall购物商城后台管理系统\">\r\n");
      out.write("    <meta name=\"description\" content=\"SMall后台管理系统 v1.1，是一款电商后台管理系统，适合中小型CMS后台系统。\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<header class=\"navbar-wrapper\">\r\n");
      out.write("    <div class=\"navbar navbar-fixed-top\">\r\n");
      out.write("        <div class=\"container-fluid cl\"> <a class=\"logo navbar-logo f-l mr-10 hidden-xs\" href=\"/\">SMall后台管理系统</a> <a class=\"logo navbar-logo-m f-l mr-10 visible-xs\" href=\"/\">SMall后台管理系统</a>\r\n");
      out.write("            <span class=\"logo navbar-slogan f-l mr-10 hidden-xs\">v1.1</span>\r\n");
      out.write("            <a aria-hidden=\"false\" class=\"nav-toggle Hui-iconfont visible-xs\" href=\"javascript:;\">&#xe667;</a>\r\n");
      out.write("            <nav class=\"nav navbar-nav\">\r\n");
      out.write("                <ul class=\"cl\">\r\n");
      out.write("                    <li class=\"dropDown dropDown_hover\"><a href=\"javascript:;\" class=\"dropDown_A\"><i class=\"Hui-iconfont\">&#xe600;</i> 新增 <i class=\"Hui-iconfont\">&#xe6d5;</i></a>\r\n");
      out.write("                        <ul class=\"dropDown-menu menu radius box-shadow\">\r\n");
      out.write("                            <li><a href=\"javascript:;\" onclick=\"product_add('添加商品','product-add')\"><i class=\"Hui-iconfont\">&#xe620;</i> 商品</a></li>\r\n");
      out.write("                            <li><a href=\"javascript:;\" onclick=\"member_add('添加用户','member-add','','630')\"><i class=\"Hui-iconfont\">&#xe60d;</i> 用户</a></li>\r\n");
      out.write("                        </ul>\r\n");
      out.write("                        <li class=\"navbar-levelone current\"><a href=\"javascript:;\">平台</a></li>\r\n");
      out.write("                        ");
      out.write("\r\n");
      out.write("                    </li>\r\n");
      out.write("                </ul>\r\n");
      out.write("            </nav>\r\n");
      out.write("            <nav id=\"Hui-userbar\" class=\"nav navbar-nav navbar-userbar hidden-xs\">\r\n");
      out.write("                <ul class=\"cl\">\r\n");
      out.write("                    <li style=\"right:5px\" id=\"role\"></li>\r\n");
      out.write("                    <li class=\"dropDown dropDown_hover\">\r\n");
      out.write("                        <a href=\"#\" class=\"dropDown_A\"><sapn id=\"username\"></sapn> <i class=\"Hui-iconfont\">&#xe6d5;</i></a>\r\n");
      out.write("                        <ul class=\"dropDown-menu menu radius box-shadow\">\r\n");
      out.write("                            <li><a href=\"javascript:;\" onClick=\"myselfinfo()\">个人信息</a></li>\r\n");
      out.write("                            <li><a onclick=\"logout()\">切换账户</a></li>\r\n");
      out.write("                            <li><a onclick=\"logout()\">退出</a></li>\r\n");
      out.write("                        </ul>\r\n");
      out.write("                    </li>\r\n");
      out.write("                    ");
      out.write("\r\n");
      out.write("                    <li id=\"LockScreen\"> <a href=\"lock-screen\" title=\"锁屏\"><i class=\"Hui-iconfont\" style=\"font-size:18px\">&#xe60e;</i></a> </li>\r\n");
      out.write("                   ");
      out.write("\r\n");
      out.write("                    <li id=\"Hui-skin\" class=\"dropDown right dropDown_hover\"> <a href=\"javascript:;\" class=\"dropDown_A\" title=\"换肤\"><i class=\"Hui-iconfont\" style=\"font-size:18px\">&#xe62a;</i></a>\r\n");
      out.write("                        <ul class=\"dropDown-menu menu radius box-shadow\">\r\n");
      out.write("                            <li><a href=\"javascript:;\" data-val=\"default\" title=\"默认（蓝色）\">默认（蓝色）</a></li>\r\n");
      out.write("                            <li><a href=\"javascript:;\" data-val=\"black\" title=\"黑色\">黑色</a></li>\r\n");
      out.write("                            <li><a href=\"javascript:;\" data-val=\"green\" title=\"绿色\">绿色</a></li>\r\n");
      out.write("                            <li><a href=\"javascript:;\" data-val=\"red\" title=\"红色\">红色</a></li>\r\n");
      out.write("                            <li><a href=\"javascript:;\" data-val=\"yellow\" title=\"黄色\">黄色</a></li>\r\n");
      out.write("                            <li><a href=\"javascript:;\" data-val=\"orange\" title=\"橙色\">橙色</a></li>\r\n");
      out.write("                        </ul>\r\n");
      out.write("                    </li>\r\n");
      out.write("                </ul>\r\n");
      out.write("            </nav>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</header>\r\n");
      out.write("<aside class=\"Hui-aside\">\r\n");
      out.write("    <div class=\"menu_dropdown bk_2\">\r\n");
      out.write("        <dl id=\"menu-article\">\r\n");
      out.write("            <dt><i class=\"Hui-iconfont\">&#xe616;</i> 商城内容管理<i class=\"Hui-iconfont menu_dropdown-arrow\">&#xe6d5;</i></dt>\r\n");
      out.write("            <dd>\r\n");
      out.write("                <ul>\r\n");
      out.write("                    <li><a data-href=\"content-header-list\" data-title=\"首页导航栏管理\" href=\"javascript:void(0)\">首页导航栏管理</a></li>\r\n");
      out.write("                    <li><a data-href=\"content-panel\" data-title=\"首页板块管理\" href=\"javascript:void(0)\">首页板块管理</a></li>\r\n");
      out.write("                    <li><a data-href=\"content-banner-list\" data-title=\"首页轮播图管理\" href=\"javascript:void(0)\">首页轮播图管理</a></li>\r\n");
      out.write("                    <li><a data-href=\"content-index-list\" data-title=\"首页板块内容管理\" href=\"javascript:void(0)\">首页板块内容管理</a></li>\r\n");
      out.write("                    ");
      out.write("\r\n");
      out.write("                </ul>\r\n");
      out.write("            </dd>\r\n");
      out.write("        </dl>\r\n");
      out.write("        <dl id=\"menu-picture\">\r\n");
      out.write("            <dt><i class=\"Hui-iconfont\">&#xe634;</i> 缓存管理<i class=\"Hui-iconfont menu_dropdown-arrow\">&#xe6d5;</i></dt>\r\n");
      out.write("            <dd>\r\n");
      out.write("                <ul>\r\n");
      out.write("                    <li><a data-href=\"refresh-index-redis\" data-title=\"首页缓存管理\" href=\"javascript:void(0)\">首页缓存管理</a></li>\r\n");
      out.write("                    ");
      out.write("\r\n");
      out.write("                </ul>\r\n");
      out.write("            </dd>\r\n");
      out.write("        </dl>\r\n");
      out.write("        <dl id=\"menu-product\">\r\n");
      out.write("            <dt><i class=\"Hui-iconfont\">&#xe620;</i> 商品管理<i class=\"Hui-iconfont menu_dropdown-arrow\">&#xe6d5;</i></dt>\r\n");
      out.write("            <dd>\r\n");
      out.write("                <ul>\r\n");
      out.write("                    <li><a data-href=\"product-category\" data-title=\"分类管理\" href=\"javascript:void(0)\">分类管理</a></li>\r\n");
      out.write("                    <li><a data-href=\"product-list\" data-title=\"商品列表\" href=\"javascript:void(0)\">商品列表</a></li>\r\n");
      out.write("                </ul>\r\n");
      out.write("            </dd>\r\n");
      out.write("        </dl>\r\n");
      out.write("        <dl id=\"menu-order\">\r\n");
      out.write("            <dt><i class=\"Hui-iconfont\">&#xe627;</i> 订单管理<i class=\"Hui-iconfont menu_dropdown-arrow\">&#xe6d5;</i></dt>\r\n");
      out.write("            <dd>\r\n");
      out.write("                <ul>\r\n");
      out.write("                    <li><a data-href=\"order-list\" data-title=\"订单列表\" href=\"javascript:void(0)\">订单列表</a></li>\r\n");
      out.write("                </ul>\r\n");
      out.write("            </dd>\r\n");
      out.write("        </dl>\r\n");
      out.write("        <dl id=\"menu-search\">\r\n");
      out.write("            <dt><i class=\"Hui-iconfont\">&#xe665;</i> 搜索管理<i class=\"Hui-iconfont menu_dropdown-arrow\">&#xe6d5;</i></dt>\r\n");
      out.write("            <dd>\r\n");
      out.write("                <ul>\r\n");
      out.write("                    <li><a data-href=\"refresh-index\" data-title=\"同步索引\" href=\"javascript:void(0)\">同步索引</a></li>\r\n");
      out.write("                    <li><a data-href=\"dict-list\" data-title=\"词典库管理\" href=\"javascript:void(0)\">词典库管理</a></li>\r\n");
      out.write("                </ul>\r\n");
      out.write("            </dd>\r\n");
      out.write("        </dl>\r\n");
      out.write("        ");
      out.write("\r\n");
      out.write("        <dl id=\"menu-member\">\r\n");
      out.write("            <dt><i class=\"Hui-iconfont\">&#xe60d;</i> 会员管理<i class=\"Hui-iconfont menu_dropdown-arrow\">&#xe6d5;</i></dt>\r\n");
      out.write("            <dd>\r\n");
      out.write("                <ul>\r\n");
      out.write("                    <li><a data-href=\"member-list\" data-title=\"会员列表\" href=\"javascript:;\">会员列表</a></li>\r\n");
      out.write("                    <li><a data-href=\"member-del\" data-title=\"删除的会员\" href=\"javascript:;\">删除的会员</a></li>\r\n");
      out.write("                </ul>\r\n");
      out.write("            </dd>\r\n");
      out.write("        </dl>\r\n");
      out.write("        <dl id=\"menu-admin\">\r\n");
      out.write("            <dt><i class=\"Hui-iconfont\">&#xe62d;</i> 管理员管理<i class=\"Hui-iconfont menu_dropdown-arrow\">&#xe6d5;</i></dt>\r\n");
      out.write("            <dd>\r\n");
      out.write("                <ul>\r\n");
      out.write("                    <li><a data-href=\"admin-role\" data-title=\"角色管理\" href=\"javascript:void(0)\">角色管理</a></li>\r\n");
      out.write("                    <li><a data-href=\"admin-permission\" data-title=\"权限管理\" href=\"javascript:void(0)\">权限管理</a></li>\r\n");
      out.write("                    <li><a data-href=\"admin-list\" data-title=\"管理员列表\" href=\"javascript:void(0)\">管理员列表</a></li>\r\n");
      out.write("                </ul>\r\n");
      out.write("            </dd>\r\n");
      out.write("        </dl>\r\n");
      out.write("       ");
      out.write("\r\n");
      out.write("        <dl id=\"menu-system\">\r\n");
      out.write("            <dt><i class=\"Hui-iconfont\">&#xe62e;</i> 系统管理<i class=\"Hui-iconfont menu_dropdown-arrow\">&#xe6d5;</i></dt>\r\n");
      out.write("            <dd>\r\n");
      out.write("                <ul>\r\n");
      out.write("                    <li><a data-href=\"system-base\" data-title=\"基本设置\" href=\"javascript:void(0)\">基本设置</a></li>\r\n");
      out.write("                    <li><a data-href=\"system-express\" data-title=\"快递管理\" href=\"javascript:void(0)\">快递管理</a></li>\r\n");
      out.write("                    <li><a data-href=\"system-shiro\" data-title=\"权限配置\" href=\"javascript:void(0)\">权限配置</a></li>\r\n");
      out.write("                    <li><a data-href=\"system-log\" data-title=\"系统日志\" href=\"javascript:void(0)\">系统日志</a></li>\r\n");
      out.write("                </ul>\r\n");
      out.write("            </dd>\r\n");
      out.write("        </dl>\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("</aside>\r\n");
      out.write("<div class=\"dislpayArrow hidden-xs\"><a class=\"pngfix\" href=\"javascript:void(0);\" onClick=\"displaynavbar(this)\"></a></div>\r\n");
      out.write("<section class=\"Hui-article-box\">\r\n");
      out.write("    <div id=\"Hui-tabNav\" class=\"Hui-tabNav hidden-xs\">\r\n");
      out.write("        <div class=\"Hui-tabNav-wp\">\r\n");
      out.write("            <ul id=\"min_title_list\" class=\"acrossTab cl\">\r\n");
      out.write("                <li class=\"active\">\r\n");
      out.write("                    <span title=\"我的首页\" data-href=\"welcome\">我的首页</span>\r\n");
      out.write("                    <em></em></li>\r\n");
      out.write("            </ul>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"Hui-tabNav-more btn-group\"><a id=\"js-tabNav-prev\" class=\"btn radius btn-default size-S\" href=\"javascript:;\"><i class=\"Hui-iconfont\">&#xe6d4;</i></a><a id=\"js-tabNav-next\" class=\"btn radius btn-default size-S\" href=\"javascript:;\"><i class=\"Hui-iconfont\">&#xe6d7;</i></a></div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div id=\"iframe_box\" class=\"Hui-article\">\r\n");
      out.write("        <div class=\"show_iframe\">\r\n");
      out.write("            <div style=\"display:none\" class=\"loading\"></div>\r\n");
      out.write("            <iframe scrolling=\"yes\" frameborder=\"0\" src=\"welcome\"></iframe>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</section>\r\n");
      out.write("\r\n");
      out.write("<div class=\"contextMenu\" id=\"Huiadminmenu\">\r\n");
      out.write("    <ul>\r\n");
      out.write("        <li id=\"closethis\">关闭当前 </li>\r\n");
      out.write("        <li id=\"closeall\">关闭全部 </li>\r\n");
      out.write("    </ul>\r\n");
      out.write("</div>\r\n");
      out.write("<!--_footer 作为公共模版分离出去-->\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/jquery/1.9.1/jquery.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/layer/2.4/layer.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"static/h-ui/js/H-ui.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"static/h-ui.admin/js/H-ui.admin.js\"></script> <!--/_footer 作为公共模版分离出去-->\r\n");
      out.write("\r\n");
      out.write("<!--请在下方写此页面业务相关的脚本-->\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/jquery.contextmenu/jquery.contextmenu.r2.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("    $(function(){\r\n");
      out.write("        $(\"body\").Huitab({\r\n");
      out.write("            tabBar:\".navbar-wrapper .navbar-levelone\",\r\n");
      out.write("            tabCon:\".Hui-aside .menu_dropdown\",\r\n");
      out.write("            className:\"current\",\r\n");
      out.write("            index:0\r\n");
      out.write("        });\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("    /*个人信息*/\r\n");
      out.write("    function myselfinfo(){\r\n");
      out.write("        layer_show('管理员信息','admin-show',360,400);\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /*产品-添加*/\r\n");
      out.write("    function product_add(title,url){\r\n");
      out.write("        var index = layer.open({\r\n");
      out.write("            type: 2,\r\n");
      out.write("            title: title,\r\n");
      out.write("            content: url\r\n");
      out.write("        });\r\n");
      out.write("        layer.full(index);\r\n");
      out.write("    }\r\n");
      out.write("    /*用户-添加*/\r\n");
      out.write("    function member_add(title,url,w,h){\r\n");
      out.write("        layer_show(title,url,w,h);\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    var username=\"\",description=\"\",sex=\"\",phone=\"\",email=\"\",address=\"\",created=\"\",file=\"\";\r\n");
      out.write("    $.ajax({\r\n");
      out.write("        type: 'GET',\r\n");
      out.write("        url: '/user/userInfo',\r\n");
      out.write("        success:function (data) {\r\n");
      out.write("            if(data.success==true){\r\n");
      out.write("                $(\"#role\").html(data.result.description);\r\n");
      out.write("                $(\"#username\").html(data.result.username);\r\n");
      out.write("                username=data.result.username;\r\n");
      out.write("                description=data.result.description;\r\n");
      out.write("                sex=data.result.sex;\r\n");
      out.write("                phone=data.result.phone;\r\n");
      out.write("                email=data.result.email;\r\n");
      out.write("                address=data.result.address;\r\n");
      out.write("                created=data.result.created;\r\n");
      out.write("                file=data.result.file;\r\n");
      out.write("            }else {\r\n");
      out.write("                layer.alert(data.message,{title: '错误信息',icon: 2});\r\n");
      out.write("            }\r\n");
      out.write("        },\r\n");
      out.write("        error:function(XMLHttpRequest){\r\n");
      out.write("            layer.alert('数据处理失败! 错误码:'+XMLHttpRequest.status+' 错误信息:'+JSON.parse(XMLHttpRequest.responseText).message,{title: '错误信息',icon: 2});\r\n");
      out.write("        }\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("    function logout() {\r\n");
      out.write("        $.ajax({\r\n");
      out.write("            type: 'GET',\r\n");
      out.write("            url: '/user/logout',\r\n");
      out.write("            success:function (data) {\r\n");
      out.write("                window.location.href=\"/login\";\r\n");
      out.write("            },\r\n");
      out.write("            error:function(XMLHttpRequest){\r\n");
      out.write("                layer.alert('数据处理失败! 错误码:'+XMLHttpRequest.status+' 错误信息:'+JSON.parse(XMLHttpRequest.responseText).message,{title: '错误信息',icon: 2});\r\n");
      out.write("            }\r\n");
      out.write("        });\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    function thanks(){\r\n");
      out.write("        layer.open({\r\n");
      out.write("            title: '捐赠',\r\n");
      out.write("            type: 2,\r\n");
      out.write("            skin: 'layui-layer-rim', //加上边框\r\n");
      out.write("            area: ['525px', '300px'], //宽高\r\n");
      out.write("            content: ['thanks-pic','no']\r\n");
      out.write("        });\r\n");
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
