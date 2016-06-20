<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>369EXPRESS后台登录 - 西萨克网络科技有限公司</title>
<meta name="keywords" content="369EXPRESS后台管理系统">
<meta name="description" content="369EXPRESS后台管理系统">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="resources/lib/html5.js"></script>
<script type="text/javascript" src="resources/lib/respond.min.js"></script>
<script type="text/javascript" src="resources/lib/PIE_IE678.js"></script>
<![endif]-->
<link href="resources/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="resources/css/H-ui.admin.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/lib/iconfont/iconfont.css" rel="stylesheet"
	type="text/css" />
<link href="resources/lib/font-awesome/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<!--[if IE 7]>
<link href="resources/lib/font-awesome/font-awesome-ie7.min.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript" src="resources/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<script type="text/javascript" src="resources/lib/jquery.min.js"></script>
<script type="text/javascript" src="resources/lib/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="resources/lib/layer1.8/layer.min.js"></script>
<script type="text/javascript" src="resources/js/H-ui.js"></script>
<script type="text/javascript" src="resources/js/H-ui.admin.js"></script>
<script type="text/javascript" src="resources/js/H-ui.admin.doc.js"></script>
</head>
<body>
	<header class="Hui-header cl"> <a class="Hui-logo l"
		title="369EXPRESS后台登录" href="/">369EXPRESS</a>
	<span class="Hui-subtitle l">后台管理系统</span> <span class="Hui-userbox"><span
		class="c-white">用户员：${username} </span> <a
		class="btn btn-danger radius ml-10" href="back/exit" title="退出"><i
			class="icon-off"></i> 退出</a></span> <a aria-hidden="false"
		class="Hui-nav-toggle" href="#"></a> </header>
	<aside class="Hui-aside"> <input runat="server"
		id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
		<dl id="menu-product">
			<dt>
				<i class="icon-beaker"></i> 创建订单<i
					class="iconfont menu_dropdown-arrow">&#xf02a9;</i>
			</dt>
			<dd>
				<ul>
					<li><a _href="back/order_one">单票件录入</a></li>
					<li><a _href="back/order_some">批量上传录单</a></li>
				</ul>
			</dd>
		</dl>
		<!--  
		<dl id="menu-page">
			<dt>
				<i class="icon-paste"></i> 订单查询与打印<i
					class="iconfont menu_dropdown-arrow">&#xf02a9;</i>
			</dt>
			<dd>
				<ul>
					<li><a _href="printlist.html">订单查询与打印</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-order">
			<dt>
				<i class="icon-credit-card"></i> 快件业务跟踪查询<i
					class="iconfont menu_dropdown-arrow">&#xf02a9;</i>
			</dt>
			<dd>
				<ul>
					<li><a _href="EMSlist.html">快件业务跟踪查询</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-tongji">
			<dt>
				<i class="icon-bar-chart"></i> 运费报价查询<i
					class="iconfont menu_dropdown-arrow">&#xf02a9;</i>
			</dt>
			<dd>
				<ul>
					<li><a _href="Emsprice.html">运费报价查询</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-admin">
			<dt>
				<i class="icon-key"></i> 账单查询<i class="iconfont menu_dropdown-arrow">&#xf02a9;</i>
			</dt>
			<dd>
				<ul>
					<li><a _href="moneyInput.html">在线充值</a></li>
					<li><a _href="moneyList.html">交款记录查询</a></li>
					<li><a _href="moneyListform.html">账单查询</a></li>
				</ul>
			</dd>
		</dl>
		-->
		<dl id="menu-system">
			<dt>
				<i class="icon-cogs"></i> 个人设置<i
					class="iconfont menu_dropdown-arrow">&#xf02a9;</i>
			</dt>
			<dd>
				<ul>
					<li><a _href="back/404">密码修改</a></li>
				</ul>
			</dd>
		</dl>
	</div>
	</aside>
	<div class="dislpayArrow">
		<a class="pngfix" href="javascript:void(0);"
			onClick="displaynavbar(this)"></a>
	</div>
	<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active"><span title="我的桌面" data-href="back/welcome">我的桌面</span><em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group">
			<a id="js-tabNav-prev" class="btn radius btn-default size-S"
				href="javascript:;"><i class="icon-step-backward"></i></a><a
				id="js-tabNav-next" class="btn radius btn-default size-S"
				href="javascript:;"><i class="icon-step-forward"></i></a>
		</div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="back/welcome"></iframe>
		</div>
	</div>
	</section>
</body>
</html>