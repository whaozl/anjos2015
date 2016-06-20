<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="resources/lib/html5.js"></script>
<script type="text/javascript" src="resources/lib/respond.min.js"></script>
<script type="text/javascript" src="resources/lib/PIE_IE678.js"></script>
<![endif]-->
<link href="resources/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="resources/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/lib/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css" />
<!--[if IE 7]>
<link href="resources/lib/font-awesome/font-awesome-ie7.min.css" rel="stylesheet" type="text/css" />
<![endif]-->
<link href="resources/lib/iconfont/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="resources/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<!-----------------------------------------------------------------自定义---------------------------------------------------------------->
<script type="text/javascript" src="resources/lib/jquery.min.js"></script> 
<link href="resources/whaozl/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/whaozl/app.js"></script>
<script type="text/javascript" src="resources/whaozl/whaozl.js"></script>
<!-----------------------------------------------------------------自定义---------------------------------------------------------------->
<script type="text/javascript" src="resources/js/H-ui.js"></script>
<script type="text/javascript">
	$(function(){
	});

	function login(){
		var data={
			username : $("#username").val(),
			password : $("#password").val(),
			checkcode: $("#checkcode").val(),
			RememberMe : $("#online").attr("checked")=="checked" ? 1 : 0
		};
		$.ajax({
			url: "back/login",
			type:"post",
			//contentType : "application/json",
			//dataType : "json",
			data: data,//JSON.stringify(data),
			success: function(o){
				if(o=="success"){
					window.location.href="back/index";
				}else{
					FriendlyReminder(o, 1500);
				}
			},
			error: function (o){
				FriendlyReminder("服务器连接失败", 1500);
			}
		});
	}
	
	function reset(){
		$("username").val("");
		$("#password").val("");
		$("#online").attr("checked", false);
	}
	
	function checkcodeChange(){
		$("#checkcodeImage").attr("src", "back/checkcode?"+Math.random());
	}
</script>
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="header"></div>
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form class="form form-horizontal">
      <div class="row cl">
        <label class="form-label col-3"><i class="iconfont">&#xf00ec;</i></label>
        <div class="formControls col-8">
          <input id="username" name="username" type="text" placeholder="账户" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-3"><i class="iconfont">&#xf00c9;</i></label>
        <div class="formControls col-8">
          <input id="password" name="password" type="password" placeholder="密码" class="input-text size-L">
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-8 col-offset-3">
          <input class="input-text size-L" id="checkcode" name="checkcode" type="text" placeholder="验证码" style="width:150px;">
          <img src="back/checkcode" id="checkcodeImage" onclick="checkcodeChange();"> <a id="kanbuq"  onclick="checkcodeChange();">看不清，换一张</a> </div>
      </div>
      <div class="row">
        <div class="formControls col-8 col-offset-3">
          <label for="online">
            <input type="checkbox" name="online" id="online" value="">
            使我保持登录状态</label>
        </div>
      </div>
      <div class="row">
        <div class="formControls col-8 col-offset-3">
          <input name="" type="button" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;" onclick="login();">
          <input name="" type="button" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;" onclick="reset();">
        </div>
      </div>
    </form>
  </div>
</div>
<div class="footer">Copyright 版权所有：西萨克网络科技有限公司</div>
<!-----------------------------------------友情提示开始--------------------------------------------->
<div class="FriendlyReminder">
    <div class='FriendlyReminder_info'></div>
    <div class='FriendlyReminder_title'>您好</div>
</div>
<!-----------------------------------------友情提示结束--------------------------------------------->
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F080836300300be57b7f34f4b3e97d911' type='text/javascript'%3E%3C/script%3E"));
</script>
</body>
</html>