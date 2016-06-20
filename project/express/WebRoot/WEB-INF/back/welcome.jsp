<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="resources/lib/html5.js"></script>
<script type="text/javascript" src="resources/lib/respond.min.js"></script>
<script type="text/javascript" src="resources/lib/PIE_IE678.js"></script>
<![endif]-->
<link href="resources/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="resources/css/H-ui.admin.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/369-main.css" rel="stylesheet" type="text/css" />
<link href="resources/lib/font-awesome/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<!--[if IE 7]>
<link href="resources/lib/font-awesome/font-awesome-ie7.min.css" rel="stylesheet" type="text/css" />
<![endif]-->
<link href="resources/lib/iconfont/iconfont.css" rel="stylesheet"
	type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="resources/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<script type="text/javascript" src="resources/lib/jquery.min.js"></script>
<script type="text/javascript" src="resources/js/H-ui.js"></script>
<script type="text/javascript" src="resources/js/H-ui.admin.doc.js"></script>
<!-- --------------------------------------------------------------自定义---------------------------------------------------------------->
<link href="resources/whaozl/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/whaozl/app.js"></script>
<script type="text/javascript" src="resources/whaozl/whaozl.js"></script>
<!-- --------------------------------------------------------------自定义---------------------------------------------------------------->
<script type="text/javascript">
	$(function() {
		welcome();
	});

	function welcome() {
		var data = {};
		$.ajax({
			url : "back/welcome",
			type : "post",
			//contentType : "application/json",
			dataType : "json",
			data : data,//JSON.stringify(data),
			success : function(o) {
				o=eval(o);
				if(!isNull(o)){
					$(".DivInfo1").append(o.memberInfo);
					$(".DivInfo2").append(o.accountInfo);
				}
			},
			error : function(o) {
				FriendlyReminder("服务器连接失败", 1500);
			}
		});
	}
</script>
</head>
<body>
	<div class="pd-20" style="padding-top:20px;">
		<p class="f-20 text-success">369EXPRESS后台管理系统</p>
		<table width="778" border="0" cellspacing="0" cellpadding="0"
			id="table_line" style="margin-top:10px">
			<tr>
				<td><table width="100%" border="0" cellspacing="0"
						cellpadding="0">
						<tr>
							<td width="17%"><img src="resources/images/start28.jpg" width="80"
								height="80" hspace="30" /></td>
							<td width="1%" id="tdlineRight">&nbsp;</td>
							<td width="82%" align="center"><table width="98%" border="0"
									cellspacing="0" cellpadding="0" style="margin-bottom:20px">
									<tr>
										<td height="20" align="left"></td>
									</tr>
									<tr>
										<td><table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td align="left" valign="top" id="tdline">
														<div id="DivInfo" class="DivInfo1">
															<h1>
																<img src="resources/images/start29.jpg" width="19" height="23" />
																会员信息
															</h1>
															<!--
															<li>会员账号：max</li>
															<li>会员昵称：马克</li>
															<li>上次登入IP：117.40.139.40</li>
															<li>上次登入时间：2015-07-16 22:18:03</li>
															-->
														</div>
													</td>
													<td width="15">&nbsp;</td>
													<td align="left" valign="top" id="tdline">
														<table width="100%" border="0" cellspacing="0" cellpadding="0">
															<tr>
																<td valign="top">
																	<div id="DivInfo" class="DivInfo2">
																		<h1>
																			<img src="resources/images/start26.jpg" width="20" height="14" />
																			帐户信息
																		</h1>
																		<!-- 
																		<li>上次核算后余额： <span class="forange"><font
																				color='green'>3.00</font></span>
																		</li>
																		<li>最近走货费用：<span class="forange"> 1012.02</span>
																		</li>
																		<li>最近走货票数：<span class="forange">（2710）</span></li>
																		<li>可用发货款余额：<span class="forange"><font
																				color='red'>-1009.02</font></span></li>
																		<li>概算后实际应付款：<span class="forange"><font
																				color='red'>-1009.02</font></span></li>
																				 -->
																	</div>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table></td>
									</tr>
								</table></td>
						</tr>
					</table></td>
			</tr>
		</table>
		<table width="778" border="0" cellspacing="0" cellpadding="0"
			style="margin-left:15px; margin-top:10px">
			<tr>
				<td width="50%" id="table_line"><table width="98%" border="0"
						cellspacing="0" cellpadding="0">
						<tr>
							<td width="183" align="left"><img src="resources/images/start35.jpg"
								width="183" height="236" /></td>
							<td width="20" align="left"></td>
							<td width="261" align="center">
								<table width="260" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="260" height="27"><table width="260"
												height="27" border="0" cellspacing="0" cellpadding="0"
												background="resources/images/start46.jpg">
												<tr>
													<td width="236" height="27" align="left" valign="top"><div
															id="txtKind">会员信息</div></td>
												</tr>
											</table></td>
									</tr>
									<tr>
										<td>
											<table width="260" border="0" cellspacing="0" cellpadding="0"
												style="margin-top:5px">
												<tr>
													<td width="236" height="180" align="left" valign="top"><img
														src="resources/images/start48.jpg"></img>信息</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
							<td width="100" align="left"></td>
						</tr>
					</table></td>
				<td width="20">&nbsp;</td>
				<td valign="top" id="table_line"><table width="100%" border="0"
						cellspacing="0" cellpadding="0">
						<tr>
							<td width="183" align="left"><img src="resources/images/start38.jpg"
								width="183" height="236" /></td>
							<td width="261" align="center"><table width="260" border="0"
									cellspacing="0" cellpadding="0">
									<tr>
										<td><table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td height="27" width="261" valign="top"
														background="resources/images/start46.jpg"><div id="txtKind">价格调整通知</div></td>
												</tr>
											</table></td>
									</tr>
									<tr>
										<td align="center"><table width="260" border="0"
												cellspacing="0" cellpadding="0" style="margin-top:5px">
												<tr>
													<td width="236" height="180" align="left" valign="top"
														id="MemInfo2"><img src="resources/images/start48.jpg"></img>信息</td>
												</tr>
											</table></td>
										<td width="60" align="left"></td>
									</tr>
								</table></td>
						</tr>
					</table></td>
			</tr>
		</table>
	</div>
	<div class="footer">
		<p>
			Copyright &copy;2015 西萨克网络科技有限公司 All Rights Reserved.<br>
			本管理系统后台由<a href="#" target="_blank" title="西萨克网络科技有限公司">西萨克网络科技有限公司</a>提供前端技术支持
		</p>
	</div>
	<script>
		var _hmt = _hmt || [];
		(function() {
			var hm = document.createElement("script");
			hm.src = "//hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
			var s = document.getElementsByTagName("script")[0];
			s.parentNode.insertBefore(hm, s);
		})();
		var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://"
				: " http://");
		document
				.write(unescape("%3Cscript src='"
						+ _bdhmProtocol
						+ "hm.baidu.com/h.js%3F080836300300be57b7f34f4b3e97d911' type='text/javascript'%3E%3C/script%3E"));
	</script>
</body>
</html>