<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>批量导入</title>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" href="resources/css/order_some.css" />
</head>
<body>
<br />
<table width="780" border="0" cellpadding="5" cellspacing="1" bgcolor="#BDBCBD">
	<tr><td bgcolor="#F5F5F5"><img src="resources/images/input01.gif" width="18" height="18" hspace="5" align="absmiddle" /><span class="f12B">当前位置：</span>会员首页 > 批量上传录单</td></tr>
  <tr>
    <td bgcolor="#F5F5F5">
    	<table width="760" border="0" cellspacing="5" cellpadding="0">
<!-----------------------------------------------------下载模板------------------------------------------------------------------->
      		<tr>
	        	<td bgcolor="#e3e3e3">
	        		<table width="100%" border="0" cellspacing="0" cellpadding="5">
		          		<tr>
		            		<td width="50%" align="center" valign="top">
		            			<table width="80%" border="0" cellspacing="5" cellpadding="0">
		            				<tr><td align="left"><div id="txtKind">第一步：下载上传模板</div></td></tr>
		            				<tr><td align="center">&nbsp;</td></tr>
		            				<tr>
		            					<td align="center">
		            					<a href="back/InputModelxls">
		            						<button type="button" class="btn">下载EXCEL格式模板</button>
		            					</a>
		            					</td>
		            				</tr>
		            				<tr>
			            				<td align="center">
			            					<a href="back/InputModelcsv">
			            					<button type="button" class="btn">下载CSV格式模板</button>
			            					</a>
			            				</td>
		            				</tr>
		            			</table>
		            		</td>
		            		<td width="50%" class="f12gray">1.请选择系统提供的下载模板，我们提供的是EXCEL格式模板和CSV格式模板；<br />
					              说明：如何将Excel文档保存为CSV格式？<br />
					              打开EXCEL文件后，选择菜单栏上的“文件”-“另存为”，在弹出的框最下方“保存类型”中直接选为“CSV”格式就可以了。<br />
					              2.因为网络速度和服务器负载等因素，建议您一次导入的业务记录不要超过50条。
				              </td>
		          		</tr>
	        		</table>
	        	</td>
      		</tr>
<!-----------------------------------------------------上传数据------------------------------------------------------------------->
      		<tr>
	        	<td bgcolor="#e3e3e3">
	        		<form method='POST' action="back/UploadModel" target='theNote' enctype='multipart/form-data' id='theForm'>
	        			<!--<input TYPE='hidden' NAME='w' VALUE='369express'> -->
							<table width="100%" border="0" cellspacing="0" cellpadding="5">
	          					<tr>
		            				<td width="50%" align="center" valign="top">
		            					<table width="80%" border="0" cellspacing="5" cellpadding="0">
							                <tr><td align="left"><div id="txtKind">第二步：数据文件上传导入</div></td></tr>
							                <tr><td height="22" align="left"><span class="fred">*</span>选择整理好的EXCEL或者CSV数据文件：</td></tr>
							                <tr><td height="30" align="left"><input name=file1 type=file class="myinput2" size=70></td></tr>
							                <tr><td height="30" align="left"><button  type="submit" class="btn" onclick=''>上 传</button> </td></tr>
							                <tr><td align="left">&nbsp;</td></tr>
							            </table>
							          </td>
	            				 	  <td width="50%" valign="top" class="f12gray"><IFRAME frameBorder=0 height=400 marginHeight=0 marginWidth=0  width=360 name='theNote' id='theNote' class='contc' SCROLLING=YES></IFRAME></td>
	          					</tr>
	        				</table>
					</form>
				</td>
      		</tr>
    	</table>
    </td>
  </tr>
</table>
</body>
</html>
