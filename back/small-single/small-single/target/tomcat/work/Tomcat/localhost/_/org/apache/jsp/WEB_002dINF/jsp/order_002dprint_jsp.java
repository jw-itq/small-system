/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2019-03-31 09:04:55 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class order_002dprint_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("    <title>订单信息</title>\r\n");
      out.write("</head>\r\n");
      out.write("<style>\r\n");
      out.write("    .title{\r\n");
      out.write("        margin-bottom: 15px;\r\n");
      out.write("    }\r\n");
      out.write("    .item{\r\n");
      out.write("        width: 24%;\r\n");
      out.write("        text-align: left;\r\n");
      out.write("        display: inline-block;\r\n");
      out.write("        line-height: 30px;\r\n");
      out.write("    }\r\n");
      out.write("    .itemAdress {\r\n");
      out.write("        width: 50%;\r\n");
      out.write("        text-align: left;\r\n");
      out.write("        display: inline-block;\r\n");
      out.write("        line-height: 30px;\r\n");
      out.write("    }\r\n");
      out.write("</style>\r\n");
      out.write("<body>\r\n");
      out.write("<div class=\"page-container\">\r\n");
      out.write("    <div id=\"print\">\r\n");
      out.write("        <form class=\"form form-horizontal\">\r\n");
      out.write("            <div>\r\n");
      out.write("                <h3 class=\"text-c title\">XMall商城订单信息</h3>\r\n");
      out.write("                <div class=\"item\">会员名称：<span id=\"customerName\"></span></div>\r\n");
      out.write("                <div class=\"item\">下单时间：<span id=\"createTime\"></span></div>\r\n");
      out.write("                <div class=\"item\">订单编号：<span id=\"orderCode\"></span></div>\r\n");
      out.write("                <div class=\"item\">支付方式：<span id=\"payType\"></span></div>\r\n");
      out.write("                <div class=\"item\">付款时间：<span id=\"payTime\"></span></div>\r\n");
      out.write("                <div class=\"item\">发货时间：<span id=\"deliverTime\"></span></div>\r\n");
      out.write("                <div class=\"item\">发货单号：<span id=\"expressId\"></span></div>\r\n");
      out.write("                <div class=\"item\">收货人：<span id=\"receiver\"></span></div>\r\n");
      out.write("                <div class=\"item\">手机：<span id=\"telephone\"></span></div>\r\n");
      out.write("                <div class=\"itemAdress\">收货地址：<span id=\"expressAdress\"></span></div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </form>\r\n");
      out.write("\r\n");
      out.write("        <div class=\"mt-20\">\r\n");
      out.write("            <table class=\"table table-border table-bg table-bordered table-striped table-hover\">\r\n");
      out.write("                <thead>\r\n");
      out.write("                    <tr class=\"text-c\">\r\n");
      out.write("                        <th width=\"40\">商品名称</th>\r\n");
      out.write("                        <th width=\"40\">商品ID号</th>\r\n");
      out.write("                        <th width=\"20\">价格(￥)</th>\r\n");
      out.write("                        <th width=\"10\">数量(件)</th>\r\n");
      out.write("                        <th width=\"20\">小计(￥)</th>\r\n");
      out.write("                    </tr>\r\n");
      out.write("                </thead>\r\n");
      out.write("                <tbody id=\"products\">\r\n");
      out.write("\r\n");
      out.write("                </tbody>\r\n");
      out.write("\r\n");
      out.write("            </table>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div class=\"text-r\"  style=\"margin-top: 1vw\">\r\n");
      out.write("            运费：￥<span id=\"expressCost\"></span> + 支付费用：￥<span id=\"orderPrice\"></span> - 订单折扣：￥0\r\n");
      out.write("            = 订单总金额：￥<span id=\"orderPriceAll\"></span>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"text-r\">= 应付款金额：￥<span id=\"orderPriceAll2\"></div>\r\n");
      out.write("        <div class=\"text-l\">订单备注：<span id=\"remark\"></div>\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("    <div class=\"row cl\" style=\"margin-top: 1vw\">\r\n");
      out.write("        <div class=\"text-c\">\r\n");
      out.write("            <input id=\"saveButton\" class=\"btn btn-primary radius\" onclick=\"print()\" value=\"打印\">\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
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
      out.write("<script type=\"text/javascript\" src=\"lib/jQuery.print.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("    function print() {\r\n");
      out.write("        jQuery('#print').print();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    //加载数据\r\n");
      out.write("    var index = layer.load(3);\r\n");
      out.write("    $.ajax({\r\n");
      out.write("        type: 'GET',\r\n");
      out.write("        url: \"/order/detail/\"+parent.orderId,\r\n");
      out.write("        dataType: 'json',\r\n");
      out.write("        success: function(data){\r\n");
      out.write("            layer.close(index);\r\n");
      out.write("            if(data.success!=true){\r\n");
      out.write("                layer.alert(data.message,{title: '错误信息',icon: 2},function () {\r\n");
      out.write("                    var index = parent.layer.getFrameIndex(window.name);\r\n");
      out.write("                    parent.layer.close(index);\r\n");
      out.write("                });\r\n");
      out.write("                return;\r\n");
      out.write("            }\r\n");
      out.write("            $(\"#customerName\").html(data.result.tbOrder.buyerNick);\r\n");
      out.write("            $(\"#createTime\").html(date(data.result.tbOrder.createTime));\r\n");
      out.write("            $(\"#orderCode\").html(data.result.tbOrder.orderId);\r\n");
      out.write("            $(\"#payType\").html(\"在线支付\");\r\n");
      out.write("            $(\"#deliverTime\").html(date(data.result.tbOrder.consignTime));\r\n");
      out.write("            $(\"#expressId\").html(data.result.tbOrder.shippingCode);\r\n");
      out.write("            $(\"#expressAdress\").html(data.result.tbOrderShipping.receiverAddress);\r\n");
      out.write("            $(\"#payTime\").html(date(data.result.tbOrder.paymentTime));\r\n");
      out.write("            $(\"#receiver\").html(data.result.tbOrderShipping.receiverName);\r\n");
      out.write("            $(\"#telephone\").html(data.result.tbOrderShipping.receiverPhone);\r\n");
      out.write("\r\n");
      out.write("            var products=data.result.tbOrderItem;\r\n");
      out.write("            var i=0;\r\n");
      out.write("            for(i=0;i<products.length;i++){\r\n");
      out.write("                $(\"#products\").append(\"<tr class='text-c'><td>\"+products[i].title+\"</td>\" +\r\n");
      out.write("                    \"<td>\"+products[i].itemId+\"</td><td>\"+products[i].price+\"</td><td>\"+products[i].num+\"</td>\" +\r\n");
      out.write("                    \"<td>\"+products[i].totalFee+\"</td></tr>\");\r\n");
      out.write("            }\r\n");
      out.write("            if(data.result.tbOrder.postFee==null||data.result.tbOrder.postFee==\"\"){\r\n");
      out.write("                data.result.tbOrder.postFee=0;\r\n");
      out.write("            }\r\n");
      out.write("            $(\"#expressCost\").html(data.result.tbOrder.postFee);\r\n");
      out.write("            $(\"#orderPrice\").html(data.result.tbOrder.payment);\r\n");
      out.write("            $(\"#orderPriceAll\").html(data.result.tbOrder.payment);\r\n");
      out.write("            $(\"#orderPriceAll2\").html(data.result.tbOrder.payment);\r\n");
      out.write("            $(\"#remark\").html(data.result.tbOrder.buyerMessage);\r\n");
      out.write("        },\r\n");
      out.write("        error:function(XMLHttpRequest){\r\n");
      out.write("            layer.close(index);\r\n");
      out.write("            layer.alert('数据处理失败! 错误码:'+XMLHttpRequest.status,{title: '错误信息',icon: 2});\r\n");
      out.write("        }\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("<!--/请在上方写此页面业务相关的脚本-->\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
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