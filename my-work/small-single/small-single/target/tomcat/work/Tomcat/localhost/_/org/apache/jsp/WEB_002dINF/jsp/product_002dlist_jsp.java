/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2019-03-29 14:46:37 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class product_002dlist_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("    <title>商品列表</title>\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"lib/zTree/v3/css/zTreeStyle/zTreeStyle.css\" type=\"text/css\">\r\n");
      out.write("</head>\r\n");
      out.write("<style>\r\n");
      out.write("    .table>tbody>tr>td{\r\n");
      out.write("        text-align:center;\r\n");
      out.write("    }\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("<body class=\"pos-r\">\r\n");
      out.write("<div class=\"pos-a\" style=\"width:200px;left:0;top:0; bottom:0; height:100%; border-right:1px solid #e5e5e5; background-color:#f5f5f5; overflow:auto;\">\r\n");
      out.write("    <ul style=\"margin-top: 15px;margin-left: 20px\"><i class=\"Hui-iconfont Hui-iconfont-fenlei\"></i> 商品分类</ul>\r\n");
      out.write("    <ul id=\"treeDemo\" style=\"margin-left: 10px\" class=\"ztree\"></ul>\r\n");
      out.write("</div>\r\n");
      out.write("<div style=\"margin-left:200px;\">\r\n");
      out.write("    <nav class=\"breadcrumb\"><i class=\"Hui-iconfont\">&#xe67f;</i> 首页 <span class=\"c-gray en\">&gt;</span> 商品管理 <span class=\"c-gray en\">&gt;</span> 商品列表 <span class=\"c-gray en\">&gt;</span><span id=\"category\">所有商品</span> <a class=\"btn btn-success radius r\" style=\"line-height:1.6em;margin-top:3px\" href=\"javascript:location.replace(location.href);\" title=\"刷新\" ><i class=\"Hui-iconfont\">&#xe68f;</i></a></nav>\r\n");
      out.write("    <form id=\"form-search\" class=\"page-container\">\r\n");
      out.write("        <div class=\"text-c\"> 日期范围：\r\n");
      out.write("            <input type=\"text\" onfocus=\"WdatePicker({ maxDate:'#F{$dp.$D(\\'maxDate\\')||\\'%y-%M-%d\\'}' })\" id=\"minDate\" name=\"minDate\" class=\"input-text Wdate\" style=\"width:120px;\">\r\n");
      out.write("            -\r\n");
      out.write("            <input type=\"text\" onfocus=\"WdatePicker({ minDate:'#F{$dp.$D(\\'minDate\\')}',maxDate:'%y-%M-%d' })\" id=\"maxDate\" name=\"maxDate\" class=\"input-text Wdate\" style=\"width:120px;\">\r\n");
      out.write("            <input type=\"text\" name=\"searchKey\" id=\"searchKey\" placeholder=\" 商品名称、商品描述、价格\" style=\"width:250px\" class=\"input-text\">\r\n");
      out.write("            <button name=\"\" id=\"searchButton\" type=\"submit\" class=\"btn btn-success\"><i class=\"Hui-iconfont\">&#xe665;</i> 搜商品</button>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"cl pd-5 bg-1 bk-gray mt-20\"> <span class=\"l\"><a href=\"javascript:;\" onclick=\"datadel()\" class=\"btn btn-danger radius\"><i class=\"Hui-iconfont\">&#xe6e2;</i> 批量删除</a> <a class=\"btn btn-primary radius\" onclick=\"product_add('添加商品','product-add')\" href=\"javascript:;\"><i class=\"Hui-iconfont\">&#xe600;</i> 添加商品</a></span> <span class=\"r\">共有数据：<strong id=\"itemListCount\">0</strong> 条</span> </div>\r\n");
      out.write("        <div class=\"mt-20\">\r\n");
      out.write("            <div class=\"mt-20\" style=\"margin-bottom: 70px\">\r\n");
      out.write("                <table class=\"table table-border table-bordered table-bg table-hover table-sort\" width=\"100%\">\r\n");
      out.write("                    <thead>\r\n");
      out.write("                    <tr class=\"text-c\">\r\n");
      out.write("                        <th width=\"30\"><input name=\"\" type=\"checkbox\" value=\"\"></th>\r\n");
      out.write("                        <th width=\"40\">ID</th>\r\n");
      out.write("                        <th width=\"70\">缩略图</th>\r\n");
      out.write("                        <th width=\"150\">商品名称</th>\r\n");
      out.write("                        <th width=\"100\">描述</th>\r\n");
      out.write("                        <th width=\"60\">单价</th>\r\n");
      out.write("                        <th width=\"95\">创建日期</th>\r\n");
      out.write("                        <th width=\"95\">更新日期</th>\r\n");
      out.write("                        <th width=\"60\">发布状态</th>\r\n");
      out.write("                        <th width=\"90\">操作</th>\r\n");
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
      out.write("<script type=\"text/javascript\" src=\"lib/zTree/v3/js/jquery.ztree.all-3.5.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/My97DatePicker/4.8/WdatePicker.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/datatables/1.10.0/jquery.dataTables.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/laypage/1.2/laypage.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/datatables/dataTables.colReorder.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/jquery.validation/1.14.0/jquery.validate.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/jquery.validation/1.14.0/validate-methods.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"lib/common.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("    function imageShow(data){\r\n");
      out.write("        if(data==\"\"||data==null){\r\n");
      out.write("            return \"http://ow2h3ee9w.bkt.clouddn.com/nopic.jpg\";\r\n");
      out.write("        }\r\n");
      out.write("        var images= new Array(); //定义一数组\r\n");
      out.write("        images=data.split(\",\"); //字符分割\r\n");
      out.write("        if(images.length>0){\r\n");
      out.write("            return images[0];\r\n");
      out.write("        }else{\r\n");
      out.write("            return data;\r\n");
      out.write("        }\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /*datatables配置*/\r\n");
      out.write("    $(document).ready(function () {\r\n");
      out.write("        $('.table').DataTable({\r\n");
      out.write("            serverSide: true,//开启服务器模式\r\n");
      out.write("            \"processing\": true,//加载显示提示\r\n");
      out.write("            \"ajax\": {\r\n");
      out.write("                url:\"/item/list\",\r\n");
      out.write("                type: 'GET',\r\n");
      out.write("                data:{\r\n");
      out.write("                    \"cid\":-1\r\n");
      out.write("                }\r\n");
      out.write("            },\r\n");
      out.write("            \"columns\": [\r\n");
      out.write("                { \"data\": null,\r\n");
      out.write("                    render : function(data,type, row, meta) {\r\n");
      out.write("                        return \"<input name=\\\"checkbox\\\" value=\\\"\"+row.id+\"\\\" type=\\\"checkbox\\\" value=\\\"\\\">\";\r\n");
      out.write("                    }\r\n");
      out.write("                },\r\n");
      out.write("                { \"data\": \"id\"},\r\n");
      out.write("                { \"data\": \"image\",\r\n");
      out.write("                    render: function(data, type, row, meta) {\r\n");
      out.write("                        return '<img src=\"'+imageShow(data)+'\" style=\"width: 80px;height: 70px\" alt=\"\" />';\r\n");
      out.write("                    }\r\n");
      out.write("                },\r\n");
      out.write("                { \"data\": \"title\",\r\n");
      out.write("                    render: function(data, type, row, meta) {\r\n");
      out.write("                        if (type === 'display') {\r\n");
      out.write("                            if (data.length > 20) {\r\n");
      out.write("                                return '<span title=' + data + '>' + data.substr(0, 50) + '...</span>';\r\n");
      out.write("                            } else {\r\n");
      out.write("                                return '<span title=' + data + '>' + data + '</span>';\r\n");
      out.write("                            }\r\n");
      out.write("                        }\r\n");
      out.write("                        return data;\r\n");
      out.write("                    }\r\n");
      out.write("                },\r\n");
      out.write("                { \"data\": \"sellPoint\",\r\n");
      out.write("                    render: function(data, type, row, meta) {\r\n");
      out.write("                        if (type === 'display') {\r\n");
      out.write("                            if (data.length > 25) {\r\n");
      out.write("                                return '<span title=' + data + '>' + data.substr(0, 25) + '...</span>';\r\n");
      out.write("                            } else {\r\n");
      out.write("                                return '<span title=' + data + '>' + data + '</span>';\r\n");
      out.write("                            }\r\n");
      out.write("                        }\r\n");
      out.write("                        return data;\r\n");
      out.write("                    }\r\n");
      out.write("                },\r\n");
      out.write("                { \"data\": \"price\"},\r\n");
      out.write("                { \"data\": \"created\",\r\n");
      out.write("                    render : function(data,type, row, meta) {\r\n");
      out.write("                        return date(data);\r\n");
      out.write("                    }\r\n");
      out.write("                },\r\n");
      out.write("                { \"data\": \"updated\",\r\n");
      out.write("                    render : function(data,type, row, meta) {\r\n");
      out.write("                        return date(data);\r\n");
      out.write("                    }\r\n");
      out.write("                },\r\n");
      out.write("                { \"data\": \"status\",\r\n");
      out.write("                    render : function(data,type, row, meta) {\r\n");
      out.write("                        if(data==0){\r\n");
      out.write("                            return \"<span class=\\\"label label-defant radius td-status\\\">已下架</span>\";\r\n");
      out.write("                        }else if(data==1){\r\n");
      out.write("                            return \"<span class=\\\"label label-success radius td-status\\\">已发布</span>\";\r\n");
      out.write("                        }else{\r\n");
      out.write("                            return \"<span class=\\\"label label-warning radius td-status\\\">其它态</span>\";\r\n");
      out.write("                        }\r\n");
      out.write("                    }\r\n");
      out.write("                },\r\n");
      out.write("                {\r\n");
      out.write("                    \"data\": null,\r\n");
      out.write("                    render: function (data, type, row, meta) {\r\n");
      out.write("                        if (row.status == 1) {\r\n");
      out.write("                            return \"<a style=\\\"text-decoration:none\\\" onClick=\\\"product_stop(this,\"+row.id+\")\\\" href=\\\"javascript:;\\\" title=\\\"下架\\\"><i class=\\\"Hui-iconfont\\\">&#xe6de;</i></a> <a style=\\\"text-decoration:none\\\" class=\\\"ml-5\\\" onClick=\\\"product_edit('商品编辑','product-edit',\"+row.id+\")\\\" href=\\\"javascript:;\\\" title=\\\"编辑\\\"><i class=\\\"Hui-iconfont\\\">&#xe6df;</i></a> <a style=\\\"text-decoration:none\\\" class=\\\"ml-5\\\" onClick=\\\"product_del(this,\"+row.id+\")\\\" href=\\\"javascript:;\\\" title=\\\"删除\\\"><i class=\\\"Hui-iconfont\\\">&#xe6e2;</i></a>\";\r\n");
      out.write("                        } else {\r\n");
      out.write("                            return \"<a style=\\\"text-decoration:none\\\" onClick=\\\"product_start(this,\"+row.id+\")\\\" href=\\\"javascript:;\\\" title=\\\"发布\\\"><i class=\\\"Hui-iconfont\\\">&#xe603;</i></a> <a style=\\\"text-decoration:none\\\" class=\\\"ml-5\\\" onClick=\\\"product_edit('商品编辑','product-edit',\"+row.id+\")\\\" href=\\\"javascript:;\\\" title=\\\"编辑\\\"><i class=\\\"Hui-iconfont\\\">&#xe6df;</i></a> <a style=\\\"text-decoration:none\\\" class=\\\"ml-5\\\" onClick=\\\"product_del(this,\"+row.id+\")\\\" href=\\\"javascript:;\\\" title=\\\"删除\\\"><i class=\\\"Hui-iconfont\\\">&#xe6e2;</i></a>\";\r\n");
      out.write("                        }\r\n");
      out.write("                    }\r\n");
      out.write("                }\r\n");
      out.write("            ],\r\n");
      out.write("            \"aaSorting\": [[ 6, \"desc\" ]],//默认第几个排序\r\n");
      out.write("            \"bStateSave\": false,//状态保存\r\n");
      out.write("            \"aoColumnDefs\": [\r\n");
      out.write("                {\"orderable\":false,\"aTargets\":[0,2,9]}// 制定列不参与排序\r\n");
      out.write("            ],\r\n");
      out.write("            language: {\r\n");
      out.write("                url: '/lib/datatables/Chinese.json'\r\n");
      out.write("            },\r\n");
      out.write("            colReorder: true\r\n");
      out.write("        });\r\n");
      out.write("\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("    productCount();\r\n");
      out.write("\r\n");
      out.write("    function productCount(){\r\n");
      out.write("        $.ajax({\r\n");
      out.write("            url:\"/item/count\",\r\n");
      out.write("            type: 'GET',\r\n");
      out.write("            success:function (result) {\r\n");
      out.write("                $(\"#itemListCount\").html(result.recordsTotal);\r\n");
      out.write("            },\r\n");
      out.write("            error:function(XMLHttpRequest){\r\n");
      out.write("                if(XMLHttpRequest.status!=200){\r\n");
      out.write("                    layer.alert('数据处理失败! 错误码:'+XMLHttpRequest.status,{title: '错误信息',icon: 2});\r\n");
      out.write("                }\r\n");
      out.write("            }\r\n");
      out.write("        });\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /*初始化类别数据*/\r\n");
      out.write("    var cid=-1;\r\n");
      out.write("    /*多条件查询*/\r\n");
      out.write("    $(\"#form-search\").validate({\r\n");
      out.write("        rules:{\r\n");
      out.write("            minDate:{\r\n");
      out.write("                required:true,\r\n");
      out.write("            },\r\n");
      out.write("            maxDate:{\r\n");
      out.write("                required:true,\r\n");
      out.write("            },\r\n");
      out.write("            searchKey:{\r\n");
      out.write("                required:false,\r\n");
      out.write("            },\r\n");
      out.write("        },\r\n");
      out.write("        onkeyup:false,\r\n");
      out.write("        focusCleanup:false,\r\n");
      out.write("        success:\"valid\",\r\n");
      out.write("        submitHandler:function(form){\r\n");
      out.write("            var searchKey= $('#searchKey').val();\r\n");
      out.write("            var minDate= $('#minDate').val();\r\n");
      out.write("            var maxDate= $('#maxDate').val();\r\n");
      out.write("            var param = {\r\n");
      out.write("                \"searchKey\": searchKey,\r\n");
      out.write("                \"minDate\": minDate,\r\n");
      out.write("                \"maxDate\":maxDate,\r\n");
      out.write("                \"cid\":cid\r\n");
      out.write("            };\r\n");
      out.write("            var table = $('.table').DataTable();\r\n");
      out.write("            table.settings()[0].ajax.data = param;\r\n");
      out.write("            table.ajax.url( '/item/listSearch' ).load();\r\n");
      out.write("        }\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("    var index = layer.load(3);\r\n");
      out.write("\r\n");
      out.write("    var setting = {\r\n");
      out.write("        view: {\r\n");
      out.write("            dblClickExpand: true,\r\n");
      out.write("            showLine: false,\r\n");
      out.write("            selectedMulti: false\r\n");
      out.write("        },\r\n");
      out.write("        data: {\r\n");
      out.write("            simpleData: {\r\n");
      out.write("                enable:true,\r\n");
      out.write("                idKey: \"id\",\r\n");
      out.write("                pIdKey: \"pId\",\r\n");
      out.write("                rootPId: \"\"\r\n");
      out.write("            }\r\n");
      out.write("        },\r\n");
      out.write("        async: {\r\n");
      out.write("            enable: true,\r\n");
      out.write("            url: \"/item/cat/list\",\r\n");
      out.write("            type: \"GET\",\r\n");
      out.write("            contentType: \"application/json\",\r\n");
      out.write("            autoParam: [\"id\"],\r\n");
      out.write("        },\r\n");
      out.write("        callback: {\r\n");
      out.write("            onAsyncSuccess: function(){\r\n");
      out.write("                layer.close(index);\r\n");
      out.write("            },\r\n");
      out.write("            beforeClick: function(treeId, treeNode) {\r\n");
      out.write("                if (treeNode.isParent) {\r\n");
      out.write("                    return false;\r\n");
      out.write("                } else {\r\n");
      out.write("                    cid=treeNode.id;\r\n");
      out.write("                    $(\"#category\").html(treeNode.name);\r\n");
      out.write("                    var param = {\r\n");
      out.write("                        \"cid\": treeNode.id,\r\n");
      out.write("                    };\r\n");
      out.write("                    var table = $('.table').DataTable();\r\n");
      out.write("                    table.settings()[0].ajax.data = param;\r\n");
      out.write("                    table.ajax.reload();\r\n");
      out.write("                    return true;\r\n");
      out.write("                }\r\n");
      out.write("            }\r\n");
      out.write("        }\r\n");
      out.write("    };\r\n");
      out.write("\r\n");
      out.write("    $(document).ready(function(){\r\n");
      out.write("        var t = $(\"#treeDemo\");\r\n");
      out.write("        t = $.fn.zTree.init(t, setting);\r\n");
      out.write("        var zTree = $.fn.zTree.getZTreeObj(\"tree\");\r\n");
      out.write("    });\r\n");
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
      out.write("    /*产品-查看*/\r\n");
      out.write("    function product_show(title,url,id){\r\n");
      out.write("        var index = layer.open({\r\n");
      out.write("            type: 2,\r\n");
      out.write("            title: title,\r\n");
      out.write("            content: url\r\n");
      out.write("        });\r\n");
      out.write("        layer.full(index);\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /*产品-下架*/\r\n");
      out.write("    function product_stop(obj,id){\r\n");
      out.write("        layer.confirm('确认要下架ID为\\''+id+'\\'的商品吗？',{icon:0},function(index){\r\n");
      out.write("            var index = layer.load(3);\r\n");
      out.write("            $.ajax({\r\n");
      out.write("                type: 'PUT',\r\n");
      out.write("                url: '/item/stop/'+id,\r\n");
      out.write("                dataType: 'json',\r\n");
      out.write("                success: function(data){\r\n");
      out.write("                    layer.close(index);\r\n");
      out.write("                    if(data.success!=true){\r\n");
      out.write("                        layer.alert(data.message,{title: '错误信息',icon: 2});\r\n");
      out.write("                        return;\r\n");
      out.write("                    }\r\n");
      out.write("                    refresh();\r\n");
      out.write("                    layer.msg('已下架!',{icon: 5,time:1000});\r\n");
      out.write("                },\r\n");
      out.write("                error:function(XMLHttpRequest){\r\n");
      out.write("                    layer.close(index);\r\n");
      out.write("                    layer.alert('数据处理失败! 错误码:'+XMLHttpRequest.status,{title: '错误信息',icon: 2});\r\n");
      out.write("                }\r\n");
      out.write("            });\r\n");
      out.write("\r\n");
      out.write("        });\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /*产品-发布*/\r\n");
      out.write("    function product_start(obj,id){\r\n");
      out.write("        layer.confirm('确认要发布ID为\\''+id+'\\'的商品吗？',{icon:3},function(index){\r\n");
      out.write("            var index = layer.load(3);\r\n");
      out.write("            $.ajax({\r\n");
      out.write("                type: 'PUT',\r\n");
      out.write("                url: '/item/start/'+id,\r\n");
      out.write("                dataType: 'json',\r\n");
      out.write("                success: function(data){\r\n");
      out.write("                    layer.close(index);\r\n");
      out.write("                    if(data.success!=true){\r\n");
      out.write("                        layer.alert(data.message,{title: '错误信息',icon: 2});\r\n");
      out.write("                        return;\r\n");
      out.write("                    }\r\n");
      out.write("                    refresh();\r\n");
      out.write("                    layer.msg('已发布!',{icon: 6,time:1000});\r\n");
      out.write("                },\r\n");
      out.write("                error:function(XMLHttpRequest){\r\n");
      out.write("                    layer.close(index);\r\n");
      out.write("                    layer.alert('数据处理失败! 错误码:'+XMLHttpRequest.status,{title: '错误信息',icon: 2});\r\n");
      out.write("                }\r\n");
      out.write("            });\r\n");
      out.write("        });\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /*产品-编辑*/\r\n");
      out.write("    function product_edit(title,url,id){\r\n");
      out.write("        setId(id);\r\n");
      out.write("        var index = layer.open({\r\n");
      out.write("            type: 2,\r\n");
      out.write("            title: title,\r\n");
      out.write("            content: url\r\n");
      out.write("        });\r\n");
      out.write("        layer.full(index);\r\n");
      out.write("    }\r\n");
      out.write("    var ID=0;\r\n");
      out.write("    function setId(id){\r\n");
      out.write("        ID=id;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    function getId(){\r\n");
      out.write("        return ID;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /*产品-删除*/\r\n");
      out.write("    function product_del(obj,id){\r\n");
      out.write("        layer.confirm('确认要删除ID为\\''+id+'\\'的商品吗？',{icon:0},function(index){\r\n");
      out.write("            var index = layer.load(3);\r\n");
      out.write("            $.ajax({\r\n");
      out.write("                type: 'DELETE',\r\n");
      out.write("                url: '/item/del/'+id,\r\n");
      out.write("                dataType: 'json',\r\n");
      out.write("                success: function(data){\r\n");
      out.write("                    layer.close(index);\r\n");
      out.write("                    if(data.success!=true){\r\n");
      out.write("                        layer.alert(data.message,{title: '错误信息',icon: 2});\r\n");
      out.write("                        return;\r\n");
      out.write("                    }\r\n");
      out.write("                    productCount();\r\n");
      out.write("                    refresh();\r\n");
      out.write("                    layer.msg('已删除!',{icon:1,time:1000});\r\n");
      out.write("                },\r\n");
      out.write("                error:function(XMLHttpRequest) {\r\n");
      out.write("                    layer.close(index);\r\n");
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
      out.write("                url: '/item/del/'+ids,\r\n");
      out.write("                dataType: 'json',\r\n");
      out.write("                success:function(data){\r\n");
      out.write("                    layer.close(index);\r\n");
      out.write("                    if(data.success!=true){\r\n");
      out.write("                        layer.alert(data.message,{title: '错误信息',icon: 2});\r\n");
      out.write("                        return;\r\n");
      out.write("                    }\r\n");
      out.write("                    layer.msg('已删除!',{icon:1,time:1000});\r\n");
      out.write("                    productCount();\r\n");
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