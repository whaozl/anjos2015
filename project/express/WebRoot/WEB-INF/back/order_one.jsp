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
<title>客户业务单录入</title>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="resources/css/autocomplete.css" rel="stylesheet" type="text/css" />
<link href="resources/css/order_one.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/css/order_one.css" />
<!-----------------------------------------------------------------自定义---------------------------------------------------------------->
<script type="text/javascript" src="resources/lib/jquery.min.js"></script> 
<link href="resources/whaozl/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/whaozl/app.js"></script>
<script type="text/javascript" src="resources/whaozl/whaozl.js"></script>
<!-----------------------------------------------------------------自定义---------------------------------------------------------------->
<script language='javascript' type='text/javascript'>
	var basePath="<%=basePath%>";
	var express;//本页面所携带解析信息

	//添加多件物品信息
	var iGoodsItem=0;
	var acGoods=new Array();
	var acCodeA=new Array();
	var aiQuant=new Array();
	var afPrice=new Array();
	
	var acCodeB=new Array();
	var acCodeC=new Array();
	var acAlias=new Array();
	var afCustom=new Array();
	var afRate=new Array();
	
	var bLast = false;
	
	var strPayDir="PP";
	var iLanguage=0;
	var strSender="";
	var strNum="";
	var strRNo="";
	var iItemType=1;
	var iPayWay=0;
	var strEmsKind="";
	var strDes="";
	var iItem=1;
	var fWeight=0;
	var iLong=0;
	var iWidth=0;
	var iHeight=0;
	var strGoods="";
	var strCodea="";
	var iQuantity=1;
	var fPrice=0;
	var strMoney="USD";
	var strPack="";
	var strTransNote="";
	var fDValue=0;
	var fIValue=0;
	var fGoods=0;
	var fGoodsC=0;
	var strReceiver="";
	var strUnitName="";
	var strAddr="";
	var strPhone="";
	var strPostCode="";
	var strCountry="";
	var strProvince="";
	var strCity="";
	var strMemo="";
	var strSUnitName="";
	var strSAddr="";
	var strSCity="";
	var strSProvince="";
	var strSCountry="";
	var strSPostCode="";
	var strSPhone="";
	var strSEMail="";
	var strSSms="";
	var strREMail="";
	var strRSms="";
	
	$(function() {
		OrderOne();//解析初始化
		IsMod();//加载相应数据
	});
	
	//提交前的行为
	function pre_submit(){
		var html="请更正如下资料后再提交：\r\n-------------------\r\n";
		var bOk=true;
		var og = $("[name='info_g']");
		var oca =$("[name='info_ca']");
		var oq = $("[name='info_q']");
		var op = $("[name='info_p']");
		if (og == null || oq == null || op == null || oca == null) return;
		$("[name=cgoods]").eq(0).val( og.eq(0).val() );
		$("[name=ccodea]").eq(0).val( oca.eq(0).val() );
		$("[name=iquantity]").eq(0).val( oq.eq(0).val() );
		$("[name=fprice]").eq(0).val( op.eq(0).val() );
		if(iGoodsItem>1&& $("[name=info_g]").eq(1).val()==""){
			for(i=1;i<iGoodsItem;i++){ 
				$("[name=info_g]").eq(i).val("");
				$("[name=info_ca]").eq(i).val("");
				$("[name=info_q]").eq(i).val("");
				$("[name=info_p]").eq(i).val("");
			}
		}
		if($("[name=cemskind]").val()==''){
			html+="没有选择网络\r\n";
			bOk=false;
		}
		if($("[name=cdes]").val().length<1){
			html+="没有填写目的地\r\n";
			bOk=false;
		}
		if($("[name=cgoods]").val().length<1){
			html+="没有填写物品描述\r\n";
			bOk=false;
		}
		if($("[name=caddr]").val().length<1){
			html+="没有填写收件人详细地址\r\n";
			bOk=false;
		}
		if($("[name=creceiver]").val().length<1){
			html+="没有填写收件人姓名\r\n";
			bOk=false;
		}
		if($("[name=ccountry]").val().length<1){
			html+="没有填写收件人国家\r\n";
			bOk=false;
		}
		if(!bOk) alert(html);
		return bOk;
	}
	
	//获取本页面信息
	function OrderOne(){
		var data={};
		$.ajax({
			url : "back/OrderOne",
			type : "post",
			async: false,
			data : data,
			success : function(o) {
				express=eval(o);//保存页面信息
			},
			error : function(o) {
				FriendlyReminder("服务器连接失败", 1500);
			}
		});
	}
	
	//提交表单
	function RecPreInputSet(PrintFlag) {
		if(! pre_submit() ) return;//验证
		var data = $("#theForm").serialize();
		$.ajax({
			url : "back/RecPreInputSet",
			type : "post",
			data : {param : data},
			success : function(o) {
				if(PrintFlag==true){
					window.open( basePath+"back/EmsPrintTab?cnum="+$("[name=cnum]").val() );
				}
				$("#theNote").contents().find("html").html(o);
				//FriendlyReminder(o, 1500);
			},
			error : function(o) {
				FriendlyReminder("服务器连接失败", 1500);
			}
		});
	}
	
	
	//查询表单信息
	function QueryNum(){
		$("#btnQueryNum").html("");
		$("#theNum").html("<font color=red>正在查询运单...</font>");
		var data={cnum : $("[name=cnum]").val()};
		$.ajax({
			url : "back/QueryNum",
			type : "post",
			data : data,
			success : function(o) {
				$("#btnQueryNum").html("<button type='button' value='查询运单...' class='btn' onclick='QueryNum();'>查询运单...</button>");
				if ( isNull(o) ) return;
				o=o+"";
				switch(o.charAt(0)){
					case '-': $("#theNum").html("没有可用信息"); return;
					case '0': $("#theNum").html("新运单"); return;
					case '1': $("#theNum").html("运单重复,请检查是否正确"); $("[name=cnum]").focus(); return;
					default: $("#theNum").html("记录修改");break;
				}
				QueryNumOK(o.split('|'));
			},
			error : function(o) {
				$("#theNum").html("查询失败");
				FriendlyReminder("服务器连接失败", 1500);
			},
			complete: function(o){
				$("#btnQueryNum").html("<button type='button' value='查询运单...' class='btn' onclick='QueryNum();'>查询运单...</button>");
			}
		});
	}
	
	//重新输入
	function PartClear(bAdd){
			bLast=bAdd;
			if($("#btnDelete").length>0){
				$("#btnDelete").html("");
			}
			$("#btnLast").html(bLast ? "<button type='button' value='上条数据'  class='btn' onclick='LastValue()'>上条数据</button>" : "");
			
			strPayDir=$("[name=cpaydir]").val();
			iLanguage=$("[name=ilanguage]").val();
			strSender=$("[name=csender]").val();
			strNum=$("[name=cnum]").val();
			strRNo=$("[name=crno]").val();
			iItemType=$("[name=iitemtype]").val();
			iPayWay=$("[name=ipayway]").val();
			strEmsKind=$("[name=cemskind]").val();
			strDes=$("[name=cdes]").val();
			iItem=$("[name=iitem]").val();
			fWeight=$("[name=fweight]").val();
			iLong=$("[name=ilong]").val();
			iWidth=$("[name=iwidth]").val();
			iHeight=$("[name=iheight]").val();
			strGoods=$("[name=cgoods]").val();
			strCodea=$("[name=ccodea]").val();
	
			iQuantity=$("[name=iquantity]").val();
			fPrice=$("[name=fprice]").val();
			strMoney=$("[name=cmoney]").val();
			strPack=$("[name=cpack]").val();
			strTransNote=$("[name=ctransnote]").val();
			fDValue=$("[name=fdvalue]").val();
			fIValue=$("[name=fivalue]").val();
			fGoods=$("[name=fgoods]").val();
			fGoodsC=$("[name=fgoodsc]").val();
			strReceiver=$("[name=creceiver]").val();
			strUnitName=$("[name=cunitname]").val();
			strAddr=$("[name=caddr]").val();
			strPhone=$("[name=cphone]").val();
			strPostCode=$("[name=cpostcode]").val();
			strCountry=$("[name=ccountry]").val();
			strProvince=$("[name=cprovince]").val();
			strCity=$("[name=ccity]").val();
			strMemo=$("[name=cmemo]").val();
			strSEMail=$("[name=csemail]").val();
			strSSms=$("[name=cssms]").val();
			strREMail=$("[name=cremail]").val();
			strRSms=$("[name=crsms]").val();
			strSUnitName=$("[name=csunitname]").val();
			strSAddr=$("[name=csaddr]").val();
			strSPhone=$("[name=csphone]").val();
			strSPostCode=$("[name=cspostcode]").val();
			strSCountry=$("[name=cscountry]").val();
			strSProvince=$("[name=csprovince]").val();
			strSCity=$("[name=cscity]").val();
			
			$("[name=ilanguage]").val(0);
			$("[name=cpaydir]").val("PP");
			$("[name=iid]").val(0);
			$("[name=iitem]").val(1);
			$("[name=iquantity]").val(1);
			$("[name=ilong]").val("");
			$("[name=iwidth]").val("");
			$("[name=iheight]").val("");
			$("[name=fweight]").val("");
			$("[name=famount]").val("");
			$("[name=fprice]").val("");
			$("[name=fgoods]").val("");
			$("[name=fgoodsc]").val("");
			$("[name=fdvalue]").val("");
			$("[name=fivalue]").val("");
			$("[name=caddr]").val("");
			$("[name=ccity]").val("");
			$("[name=ccountry]").val("");
			$("[name=cdes]").val("");
			$("[name=cgoods]").val("");
			$("[name=ccodea]").val("");
			$("[name=cmemo]").val("");
			$("[name=cpack]").val("");
			$("[name=cphone]").val("");
			$("[name=cpostcode]").val("");
			$("[name=cprovince]").val("");
			$("[name=csender]").val("");
			$("[name=ctransnote]").val("");
			$("[name=cunitname]").val("");
			//$("[name=cnum]").val("");
			$("[name=crno]").val("");
			$("[name=creceiver]").val("");
			$("[name=csemail]").val("");
			$("[name=cssms]").val("");
			$("[name=cremail]").val("");
			$("[name=crsms]").val("");
			$("[name=csunitname]").val("");
			$("[name=csaddr]").val("");
			$("[name=cscity]").val("");
			$("[name=cscountry]").val("");
			$("[name=csprovince]").val("");
			$("[name=csphone]").val("");
			$("[name=cspostcode]").val("");
			$("#theTitle").html("【**mk 21-369**】客户业务单录入");
			$("#thePrice").html("");
			$("#theNum").html("");
			iGoodsItem=1;
			//$("#theGoodsInfo").html("");
		}
	
	//记录修改则补充表单信息
	function QueryNumOK(o){
		$("[name=iid]").val( parseInt(o[1],10) );
		$("[name=iitem]").val( parseInt(o[2],10) );
		$("[name=iitemtype]").val( parseInt(o[3],10) );
		$("[name=ipayway]").val( parseInt(o[4],10) );
		$("[name=iquantity]").val( parseInt(o[5],10) );
		$("[name=ilong]").val( parseInt(o[6],10) );
		$("[name=iwidth]").val( parseInt(o[7],10) );
		$("[name=iheight]").val( parseInt(o[8],10) );
		$("[name=fweight]").val( o[9] );
		$("[name=fprice]").val( o[10] );
		if(iGoodsItem==1) {
			$("[name=info_g]").val( o[20].replace(/&#124;/g,'|') );
			$("[name=info_ca]").val( o[60].replace(/&#124;/g,'|') );
			$("[name=info_q]").val( parseInt(o[5],10) );
			$("[name=info_p]").val( o[10] );
		}
		$("[name=fgoods]").val( o[11] );
		$("[name=fgoodsc]").val( o[12] );
		$("[name=fdvalue]").val( o[13] );
		$("[name=fivalue]").val( o[14] );
		$("[name=caddr]").val( o[15].replace(/&#124;/g,'|') );
		$("[name=ccity]").val( o[16].replace(/&#124;/g,'|') );
		$("[name=ccountry]").val( o[17].replace(/&#124;/g,'|') );
		$("[name=cdes]").val( o[18].replace(/&#124;/g,'|') );
		$("[name=cemskind]").val( o[19].replace(/&#124;/g,'|') );
		$("[name=cgoods]").val( o[20].replace(/&#124;/g,'|') );
		$("[name=ccodea]").val( o[60].replace(/&#124;/g,'|') );
		$("[name=cmemo]").val( o[21].replace(/&#124;/g,'|') );
		$("[name=cpack]").val( o[22].replace(/&#124;/g,'|') );
		$("[name=cphone]").val( o[23].replace(/&#124;/g,'|') );
		$("[name=cpostcode]").val( o[24].replace(/&#124;/g,'|') );
		$("[name=cprovince]").val( o[25].replace(/&#124;/g,'|') );
		$("[name=csender]").val( o[26].replace(/&#124;/g,'|') );
		$("[name=ctransnote]").val( o[27].replace(/&#124;/g,'|') );
		$("[name=cunitname]").val( o[28].replace(/&#124;/g,'|') );
		$("[name=cmoney]").val( o[29] );
		$("[name=creceiver]").val( o[30].replace(/&#124;/g,'|') );
		$("[name=csunitname]").val( o[31].replace(/&#124;/g,'|') );
		$("[name=csaddr]").val( o[35].replace(/&#124;/g,'|')+o[34].replace(/&#124;/g,'|')+o[33].replace(/&#124;/g,'|')+o[32].replace(/&#124;/g,'|') );
		$("[name=cscity]").val( o[33].replace(/&#124;/g,'|') );
		$("[name=csprovince]").val( o[34].replace(/&#124;/g,'|') );
		$("[name=cscountry]").val( o[35].replace(/&#124;/g,'|') );
		$("[name=csphone]").val( o[36].replace(/&#124;/g,'|') );
		$("[name=cspostcode]").val( o[37].replace(/&#124;/g,'|') );
		$("[name=ilanguage]").val( parseInt(o[38],10) );
		$("[name=cpaydir]").val( o[39] );
		$("[name=famount]").val( o[40] );
		$("[name=crno]").val( o[41] );
		$("[name=cssms]").val( o[42].replace(/&#124;/g,'|') );
		$("[name=csemail]").val( o[43].replace(/&#124;/g,'|') );
		$("[name=cremail]").val( o[44].replace(/&#124;/g,'|') );
		$("[name=crsms]").val( o[45].replace(/&#124;/g,'|') );
		eval(o[65].replace(/&#124;/g,"|"));
	}
	
	//更新输入的物品信息
	function UpdateGoodsInfo(){
		var html="<table border=0 width=100% cellspacing=1 bgcolor=#E8F0F9>";
		html += "<tr><td align=left><span class='fred'>* </span>物品描述</td><td width=100 align=left>HS编码</td><td width=100 align=left><span class='fred'>* </span>物品数量</td>";
		html += "<td width=100 align=left><span class='fred'>* </span>单价</td><td width=40 align=left> </td></tr>";
		for(var i=0; i<iGoodsItem; i++){
			html+="<tr><td><input type=text name=info_g size=35 value='{acGoods}'/></td>";
			html+="<td align=left><input type=text name=info_ca  size=10 value='{acCodeA}'/></td>";
			html+="<td align=left><input type=text name=info_q  size=10 value='{aiQuant}'/></td>";
			html+="<td align=left><input type=text name=info_p  size=10 value='{afPrice}'/></td>";
			html+="<td align='left' class=sblue {onclick} </td></tr>";
			html=html.format({
				acGoods : acGoods[i],
				acCodeA: acCodeA[i],
				aiQuant : aiQuant[i],
				afPrice : afPrice[i],
				onclick : ( i>0 ? ("onclick=GoodsItemDel("+ i +")>删除") : ">")
			});
		}
		html+="</table>";
		$("#theGoodsInfo").html(html);
	}

	/*
	 *焦点下移动
	 */
	function GO(o) {
		if (event.keyCode == 13) {
			event.keyCode = 9;
			o.focus();
		}
	}
	
	//添加物品信息
	function GoodsItemAdd(cg, cca, iq, fp) {
		if (iGoodsItem > 0) {
			var og = $("[name='info_g']");
			var oca =$("[name='info_ca']");
			var oq = $("[name='info_q']");
			var op = $("[name='info_p']");
			if (og == null || oq == null || op == null || oca == null) return;
			var n = 0;
			for (var i = 0; i < iGoodsItem; i++) {
				acGoods[n] = $(og).eq(i).val();
				acCodeA[n] = $(oca).eq(i).val();
				aiQuant[n] = $(oq).eq(i).val();
				afPrice[n] = $(op).eq(i).val();
				n++;
			}
		}
		acGoods[iGoodsItem] = cg;
		acCodeA[iGoodsItem] = cca;
		aiQuant[iGoodsItem] = iq;
		afPrice[iGoodsItem] = fp;
		iGoodsItem++;
		UpdateGoodsInfo();
	}
	
	//删除物品信息
	function GoodsItemDel(ipos){
		var og = $("[name='info_g']");
		var oca =$("[name='info_ca']");
		var oq = $("[name='info_q']");
		var op = $("[name='info_p']");
		if(og == null || oq == null ||op == null||oca == null) return;
		var n=0;
		for(var i=0; i<iGoodsItem; i++){
			if(i==ipos) continue;
			acGoods[n] = $(og).eq(i).val();
			acCodeA[n] = $(oca).eq(i).val();
			aiQuant[n] = $(oq).eq(i).val();
			afPrice[n] = $(op).eq(i).val();
			n++;
		}
		iGoodsItem--;
		UpdateGoodsInfo();
	}
	
	//用自己信息补充发件人信息
	function FillSender(){
		$("[name=csphone]").val(express.csphone);
		$("[name=cspostcode]").val(express.cspostcode);
		$("[name=csemail]").val(express.csemail);
		$("[name=cssms]").val(express.cssms);
		if(express.iLanguage<2){
			$("[name=csender]").val(express.csender);
			$("[name=csaddr]").val(express.csaddr);
			$("[name=csunitname]").val(express.csunitname);
			$("[name=csprovince]").val(express.csprovince);
			$("[name=cscountry]").val(express.cscountry);
			$("[name=cscity]").val(express.cscity);
		}else{
			$("[name=csender]").val(express.csenderE);
			$("[name=csaddr]").val(express.csaddrE);
			$("[name=csunitname]").val(express.csunitnameE);
			$("[name=csprovince]").val(express.csprovinceE);
			$("[name=cscountry]").val(express.cscountryE);
			$("[name=cscity]").val(express.cscityE);
		}
	}
	
	//补充服务方式选项框
	function FillEmskind(){	
		if(express.cemskind.length<=0) return;
		var html="<select size=1 name='cemskind'><option value='' selected>--请选择--</option>";
		for(i=0;i<express.cemskind.length;i++){
			html+="<option value='{cemskind}' >{cemskindd}</option>";
			html=html.format({
				cemskind : express.cemskind[i],
				cemskindd: express.cemskindd[i]
			});
		}
		html+="</select>";
		$("#oemskind").html(html);
	 }
	
	//页面加载
	function IsMod() {
		var o = $("#iid");
		if (o.length <= 0)
			return;
		if ($(o).val() == 0) {
			GoodsItemAdd('', '', 1, 1.00);
			PartClear(false);
			FillSender();
			FillEmskind();
		}
	}
</script>
</head>
<body>
	<br/>
	<form id='theForm' name='theForm'>
		<input type='hidden' name='w' value='369express'>
		<input type='hidden' name='iidnum' value=0 id="iidnum">
		<input type='hidden' name='iid' value=0 id="iid">
		<input type='hidden' name='ilanguage' value=0>
		<input type='hidden' name='cmark' id="cmark">
		<input type='hidden' name='cdepart' id="cdepart">
		<input type='hidden' name='cno' id="cno">
		<input type='hidden' name='crno' id="crno">
		<input type='hidden' name='ccno' id="ccno">
		<input type='hidden' name='creserve' id="creserve">
		<input type='hidden' name='ireserve' id="ireserve">
		<input type='hidden' name='cunit' id="cunit">
		<input type='hidden' name='pscBy1' id="pscBy1">
		<input type='hidden' name='pscBy2' id="pscBy2">
		<input type='hidden' name='pscBy3' id="pscBy3">
		<input type='hidden' name='pscBy4' id="pscBy4">
		<input type='hidden' name='pscBy5' id="pscBy5">
		<input type='hidden' name='calias' id="calias">
		<input type='hidden' name='ccodeb' id="ccodeb">
		<input type='hidden' name='ccodec' id="ccodec">
		<input type='hidden' name='fcustom' id="fcustom">
		<input type='hidden' name='frate' id="frate">
<!-----------------------------------------------------整个表格------------------------------------------------------------------->
		<table width="780" border="0" align="left" cellpadding="5" cellspacing="1" bgcolor="#E8F0F9">
<!-----------------------------------------------------导航标题------------------------------------------------------------------->
			<tr><td align="left" bgcolor="#E8F0F9"><img src="resources/images/input01.gif" width="18" height="18" hspace="5" align="absmiddle"><span class="f12B">当前位置：</span>会员首页 &gt;单票件录入</td></tr>
			<tr>
				<td bgcolor="#E8F0F9">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td bgcolor="#E8F0F9">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="526" align="left" valign="top">
											<table width='100%' border='0' cellpadding="0" cellspacing='2' id="tbBG">
<!-----------------------------------------------------选择方式标题------------------------------------------------------------------->
												<tr>
													<td colspan="2" align="left">
														<div id="txtKind">
															<strong><span id='theTitle'>客户业务单录入：您希望如何发运？</span></strong>
															<a href="#" class="f12blue2"></a>
														</div>
													</td>
												</tr>
<!-----------------------------------------------------服务方式------------------------------------------------------------------->
												<tr>
													<td width="74" align='right'><span class="fred">*</span>服务方式：</td>
													<td width="429" id="oemskind">
														<select size=1 name='cemskind'>
															<!--<option value='EMS（国际）'>EMS（国际）</option>-->
														</select>
													</td>
												</tr>
<!-----------------------------------------------------运单号码------------------------------------------------------------------->
												<tr>
													<td align='right'><span class="fred">* </span>运单号码：</td>
													<td>
														<input type='text' name='cnum' onkeypress='GO(eval(cdes));' class="myinput4">&nbsp;
														<span id='btnQueryNum'>
															<button type='button' value='查询运单...' class='btn' onclick='QueryNum();'>查询运单...</button>
														</span>
														<span id='theNum'></span>
														<input type='hidden' name='crno' size=30>
													</td>
												</tr>
<!-----------------------------------------------------目的地------------------------------------------------------------------->
												<tr>
													<td align='right'><span class="fred">*</span> 目的地：</td>
													<td>
														<input name='cdes' id="cdes" onblur='QueryDes();' onkeypress='GO(eval(fweight));' class="myinput1" />
														<span class="f12blue3">用来计算运输费用</span>
													</td>
												</tr>
<!-----------------------------------------------------重量------------------------------------------------------------------->
												<tr>
													<td align='right'>重量：</td>
													<td>
														<input type='text' name='fweight' class="input2" value="1"> KG 体积：长
														<input type='text' name='ilong' class="myinput3">厘米*宽
														<input type='text' name='iwidth' class="myinput3">厘米*高
														<input type='text' name='iheight' class="myinput3">厘米
														<input type='hidden' name='famount' size=8 class="myinput4">
													</td>
												</tr>
<!-----------------------------------------------------查询报价------------------------------------------------------------------->
												<tr>
													<td align='right'></td>
													<td>
														<span id='btnQueryPrice' style="display:block">
															<button type='button' value='查询报价...' class='btn' onclick='QueryPrice();' style="display:none">查询报价...</button>
														</span>
														<span id='thePrice' style="display:none"></span>
													</td>
												</tr>
											</table>
										</td>
<!-----------------------------------------------------友情提示------------------------------------------------------------------->
										<td width="242" align="left" valign="top">
											<div id="txtGray">
												Please Note: The chargeable weight of the shipment we also need check its Dimensions weight （(L x W x H)cm÷5000）请注意：快件的结算重量也取决于包裹的体积重量（(长X宽X高)cm÷5000）,如果算出体积重量大于实际重量，就需按体积重计算运费，请自己记录下每件包裹的尺寸便于以后和我司核对，特殊情况请联系我司。
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table width="768" border="0" cellpadding="0" cellspacing="0" bgcolor="#E8F0F9" style="margin-top:5px">
									<tr>
										<td width="380" valign="top">
<!-----------------------------------------------------发件人信息------------------------------------------------------------------->
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td>
														<div id="txtKind">
															<strong><span id='theTitle'>发件人信息：</span></strong>
															<a href="#" class="f12blue2"></a>
														</div>
													</td>
												</tr>
												<tr>
													<td>
														<div id='sndDiv' style='display:block'>
															<table width='100%' border='0' cellpadding="0" cellspacing='2' id="tbBG">
																<tr>
																	<td height="30" align='right'></td>
																	<td height="40" align="left" id='sndTag'>
																		<button type="button" class="btn" onclick='FillSender();'>用我的默认信息填充！</button>
																	</td>
																</tr>
																<tr>
																	<td align='right' width='85'>发件人姓名：</td>
																	<td align="left"><input name='csender' type='text' class="input1" onblur='QuerySender()' onkeypress='GO(eval(csemail));'></td>
																</tr>
																<tr>
																	<td align='right'>电子邮件：</td>
																	<td align="left"><input name='csemail' type='text' class="input1" onkeypress='GO(eval(cssms));'>&nbsp; &nbsp;</td>
																</tr>
																<tr>
																	<td align='right'>短信：</td>
																	<td align="left"><input name='cssms' type='text' class="input1" onkeypress='GO(eval(csunitname));' /></td>
																</tr>
																<tr>
																	<td align='right'>单位名称：</td>
																	<td align="left"><input name='csunitname' type='text' class="input1" onkeypress='GO(eval(csaddr));'></td>
																</tr>
																<tr>
																	<td align='right' valign='top'>详细地址：</td>
																	<td align="left"><textarea name='csaddr' rows='4' class="mytextarea"></textarea></td>
																</tr>
																<tr>
																	<td align='right'>联系电话：</td>
																	<td align="left">
																		<input name='csphone' type='text' class="input3" onkeypress='GO(eval(cspostcode));'>&nbsp; &nbsp;邮编：
																		<input name='cspostcode' type='text' class="input3" onkeypress='GO(eval(csprovince));'></td>
																</tr>
																<tr>
																	<td align='right'>省/州：</td>
																	<td align="left"><input name='csprovince' type='text' class="input3" onkeypress='GO(eval(cscity));' /> &nbsp; &nbsp;城市：<input name='cscity' type='text' class="input3" onkeypress='GO(eval(cscountry));' /></td>
																</tr>
																<tr>
																	<td align='right'>国家：</td>
																	<td align="left"><input name='cscountry' type='text' class="input1"></td>
																</tr>
															</table>
														</div>
													</td>
												</tr>
											</table>
										</td>
										<td width="5" valign="top" bgcolor="#E8F0F9"></td>
<!-----------------------------------------------------收件人信息------------------------------------------------------------------->
										<td valign="top">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td>
														<div id="txtKind">
															<strong><span id='theTitle'>收件人信息：</span></strong>
															<a href="#" class="f12blue2"></a>
														</div>
													</td>
												</tr>
												<tr>
													<td>
														<table width="100%" border="0" cellspacing="0"cellpadding="0">
															<tr>
																<td align="center">
																	<table width="95%" border="0" cellpadding="4" cellspacing="0" id="table1">
																		<tr><td height="30" align="left"><a href="javascript:QueryAddrList();" class="f12blue2">显示常用地址：</a></td></tr>
																		<tr><td align="left"><div id="addrList"></div></td></tr>
																	</table>
																</td>
															</tr>
														</table>
														<table width='100%' border='0' cellpadding="0" cellspacing='2' id="tbBG">
															<tr>
																<td width="85" align='right'><font color=red>*</font>收件人姓名：</td>
																<td width="292" align="left"><input type='text' name='creceiver' onkeypress='GO(eval(cunitname));' onblur='QueryReceiver()' class="myinput2"></td>
															</tr>
															<tr>
																<td align='right' valign='top'>收件公司：</td>
																<td align="left"><input type='text' name='cunitname' size=80 class="myinput2" onkeypress='GO(eval(caddr));'></td>
															</tr>
															<tr>
																<td align='right' valign='top'><font color="red">*</font>详细地址：</td>
																<td align="left"><textarea name="caddr" cols="20" rows="4" class="mytextarea" onkeypress='GO(eval(cpostcode));'></textarea></td>
															</tr>
															<tr>
																<td align='right' valign='top'><font color="red">*</font>邮编：</td>
																<td align="left"><input name='cpostcode' type='text' class="myinput2" onKeyPress='GO(eval(cprovince));'></td>
															</tr>
															<tr>
																<td align='right' valign='top'>省/州：</td>
																<td align="left">
																	<input type='text' name='cprovince' class="input3" onkeypress='GO(eval(ccity));' /> &nbsp; &nbsp;
																	<font color="red">*</font> 城市: 
																	<input type='text' name='ccity' class="input3" onkeypress='GO(eval(ccountry));' />
																	<input type='hidden' name='cremail' />
																	<input type='hidden' name='crsms' size="20" />
																</td>
															</tr>
															<tr>
																<td align='right' valign='top'><font color="red">*</font>国家：</td>
																<td align="left"><input type='text' name='ccountry' class="myinput2" onKeyPress='GO(eval(cphone));'></td>
															</tr>
															<tr>
																<td align='right'><span class="fred">*</span>收件人电话：</td>
																<td align="left"><input type='text' name='cphone' class="myinput2">&nbsp; &nbsp;</td>
															</tr>
															<tr>
																<td height="30" align='right'>&nbsp;</td>
																<td align="left"><a href="javascript:addNewAddress();" class="f12blue2">[添加到常用地址]</a>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#E8F0F9" id="tbBG" style="margin-top:5px">
<!-----------------------------------------------------您要发送什么?------------------------------------------------------------------->
									<tr>
										<td>
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="17%"><div id="txtKind"><strong>您要发运什么？</strong></div></td>
													<td width="83%" height="50" align="left">
														<table width="98%" border="0" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF" style="margin-top:5px">
															<tr>
																<td width="14%" align="center"><span class="fred">*</span>快件类型：</td>
																<td width="56%">
																	<input name='iitemtype' type='radio' value=1 checked> 包裹
																	<span class="f12gray"> Non-Documents</span>
																	<input name='iitemtype' type='radio' value=0> 文件
																	<span class="f12gray"> Documents</span>
																</td>
																<td width="21%"><span class="fred">*</span>此运单的包裹件数：</td>
																<td width="9%"><input name='iitem' type='text' class="myinput3" onkeypress='GO(eval(cdes));' value=1></td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
<!-----------------------------------------------------添加多件物品信息------------------------------------------------------------------->
									<tr>
										<td height="60" align="center">
											<table width="705" border="0" cellspacing="0" cellpadding="0">
												<tr><td height="35" align="left" id='theGoodsInfo'>&nbsp;</td></tr>
												<tr>
													<td height="35" align="left">
														<button type="button" class="btn" onClick="GoodsItemAdd('','',1,1.00)">添加多件物品信息</button>
														<input type='hidden' name='fgoodsc' size=8>
														<input type='hidden' name='fgoods' size=8>
														<input type='hidden' name='fivalue' size=8>
														<input type='hidden' name='ctransnote' size=30>
														<input name='cgoods' id="cgoods" type='hidden'>
														<input name='ccodea' id="ccodea" type='hidden'>
														<input name='iquantity' type='hidden' id="iquantity" value=1>
														<input name="fprice" type='hidden' id="fprice" value="1" />
														<input name='cpack' type='hidden' class="input1" size=30>
														<select size=1 name='cmoney' style="display:none">
															<option value='USD'>USD</option>
														</select>
													</td>
												</tr>
											</table>
											<table width="100%" border="0" cellspacing="2" cellpadding="0">
												<tr>
													<td width="12%" align="right">声明价：</td>
													<td width="88%" height="10" align="left"><input name='fdvalue' type='text' class="input3" size=10 /></td>
												</tr>
												<tr>
													<td align='right' valign='top'>附加备注：</td>
													<td align="left"><textarea name='cmemo' rows='2' class="myMemo"></textarea></td>
												</tr>
												<tr>
													<td align='right'></td>
													<td align="left"><span class="f12blue3">不要超过254字符（127汉字）</span></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						
<!-----------------------------------------------------其他事件------------------------------------------------------------------->
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#E8F0F9" id="tbBG">
									<tr>
										<td>
											<table width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none">
												<tr>
													<td width="23%"><div id="txtKind"><strong>您希望如何付款 ？</strong></div></td>
													<td width="77%">
														<input type='radio' name='ipayway' value="0" />
														<font color='brown'>月结</font>
														<input name='ipayway' type='radio' value="1" checked="checked" />
														<font color='brown'>现付</font>
														<input type='radio' name='ipayway' value="2" />
														<font color='brown'>到付</font>&nbsp;┇
														<input type='radio' name='cpaydir' checked="checked" value='PP' />
														<font color='purple'>PP</font>
														<input type='radio' name='cpaydir' value='CC' />
														<font color='purple'>CC</font>
														<input type='radio' name='cpaydir' value='COD' />
														<font color='purple'>COD</font>
														<input type='radio' name='cpaydir' value='DP' />
														<font color='purple'>DP</font>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table width="100%" border="0" cellspacing="2" cellpadding="0">
												<tr>
													<td width="23%">&nbsp;</td>
													<td width="77%" align="left">
														<IFRAME frameBorder=0 allowtransparency="1" height=20 marginHeight=0 marginWidth=0 width=200 name='theNote' id='theNote'  SCROLLING=NO>
															
														</IFRAME>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
<!-----------------------------------------------------协议声明------------------------------------------------------------------->
						<tr>
							<td height="40" align="left" valign="top" class="f12gray">
								By clicking on the button below, you agree to our Terms and
								Conditions 在点击以下按钮以前请阅读 承运条款 ，一旦您生成了此运单就表示您已阅读过此承运条款，并同意条款的所有内容。</td>
						</tr>
<!-----------------------------------------------------按钮相应------------------------------------------------------------------->
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="2" cellpadding="0">
									<tr>
										<!--
										<td align="left"><button type='button' value='生成运单并预览运单' class='btn' id="prtTab" onClick="PrintTab();">生成并打印运单</button></td>
										<td align="left"><button type='button' value='生成标签并预览标签' class='btn' id="prtTab" onClick="PrintLab();">生成并打印标签</button></td>
										<td align="left"><button type='button' value='生成运单并预览形式发票' class='btn' onClick="PrintInv();">生成并打印形式发票</button></td>
										 -->
										 <td align="left"><button type='button' class="btn" onclick="RecPreInputSet(true);">保存并打印面单</button></td>
										<td align="left"><button type='button' class="btn" onclick="RecPreInputSet(false);">直接保存运单</button></td>
										<td align="left"><button type='button' onclick='PartClear();' value='重新输入'class='btn'>重新输入</button>
										</td>
									</tr>
									<tr>
										<!--
										<td align="left"><span id='btnLast'><button type='button' value='上条数据' onclick='LastValue()' class='btn'>上条数据</button></span></td>
										<td align="left"><span id='btnDelete'> <button type='button' value='删除本条' onclick='DeleteIt()' class='btn'>删除本条</button></span></td>
										<td align="left"></td>
										<td align="left">&nbsp;</td>
										<td align="left">&nbsp;</td>
										 -->
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<input type='hidden' name='fin' value='e'>
	</form>
	
<!-----------------------------------------友情提示开始--------------------------------------------->
<div class="FriendlyReminder">
    <div class='FriendlyReminder_info'></div>
    <div class='FriendlyReminder_title'>您好</div>
</div>
<!-----------------------------------------友情提示结束--------------------------------------------->
</body>
</html>