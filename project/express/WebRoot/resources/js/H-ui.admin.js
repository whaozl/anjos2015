/*H-ui.admin.js v2.2 date:15:42 2015-04-29 by:guojunhui*/
/*获取顶部选项卡总长度*/
function tabNavallwidth(){
	var taballwidth=0,
		$tabNav = $(".acrossTab"),
		$tabNavWp = $(".Hui-tabNav-wp"),
		$tabNavitem = $(".acrossTab li"),
		$tabNavmore =$(".Hui-tabNav-more");
	if (!$tabNav[0]){return;}
	$tabNavitem.each(function(index, element) {
        taballwidth+=Number(parseFloat($(this).width()+60));
    });
	$tabNav.width(taballwidth+25);
	var w = $tabNavWp.width();
	if(taballwidth+25>w){
		$tabNavmore.show();
	}
	else{
		$tabNavmore.hide();
		$tabNav.css({left:0});
	}
}

/*批量删除*/
function datadel(){
	layer.confirm('确认要删除吗？',function(index){
		alert("测试");
		layer.msg('已删除!',1);
	});
}

/*弹出层*/
function layer_show(w,h,title,url){
	if (w == null || w == '') {
		w=800;
	};
	if (h == null || h == '') {
		h=($(window).height() - 50);
	};
	if (title == null || title == '') {
		title=false;
	};
	if (url == null || url == '') {
		url="404.html";
	};
	$.layer({
    	type: 2,
    	shadeClose: true,
    	title: title,
		maxmin:false,
		shadeClose: true,
    	closeBtn: [0, true],
    	shade: [0.8, '#000'],
    	border: [0],
    	offset: ['20px',''],
    	area: [w+'px', h +'px'],
    	iframe: {src: url}
	});
}
/*左侧菜单响应式*/
function Huiasidedisplay(){
	if($(window).width()>=768){
		$(".Hui-aside").show();
	} 
}
$(function(){
	Huiasidedisplay();
	var resizeID;
	$(window).resize(function(){
		clearTimeout(resizeID);
		resizeID = setTimeout(function(){
			Huiasidedisplay();
		},500);
	}); 
	/*菜单处于当前状态*/
    var webSite ="";
    var loc=location.href;var url = loc.replace(webSite,"");
    $(".menu_dropdown ul li").each(function(){
		var current = $(this).find("a");
		$(this).removeClass("current");
		if(url == $(current[0]).attr("href")){
			$(this).addClass("current");
		}
	});
	
	$(".Hui-nav-toggle").click(function(){
		$(".Hui-aside").slideToggle();	
	});	
	$(".menu_dropdown dd li a").click(function(){
		if($(window).width()<768){
			$(".Hui-aside").slideToggle();
		}
	});
		/*左侧菜单*/
	$.Huifold(".menu_dropdown dl dt",".menu_dropdown dl dd","fast",1,"click");
	/*选项卡导航*/
	var topWindow=$(window.parent.document);
	$(".Hui-aside .menu_dropdown a").click(function(){
		var bStop=false;
		var bStopIndex=0;
		var _href=$(this).attr('_href');
		var _titleName=$(this).html();
		var show_navLi=topWindow.find("#min_title_list li");
		show_navLi.each(function() {
			if($(this).find('span').attr("data-href")==_href)
			{
				bStop=true;
				bStopIndex=show_navLi.index($(this));
				return false;	
			}
		});
		if(!bStop){
			creatIframe(_href,_titleName);
			min_titleList();	
		}
		else{
			show_navLi.removeClass("active").eq(bStopIndex).addClass("active");
			var iframe_box=topWindow.find("#iframe_box");
			iframe_box.find(".show_iframe").hide().eq(bStopIndex).show();	
		}
	});
	min_titleList()
	function min_titleList(){
		var show_nav=topWindow.find("#min_title_list");
		var aLi=show_nav.find("li");
	};
	function creatIframe(href,titleName){
		var show_nav=topWindow.find('#min_title_list');
		show_nav.find('li').removeClass("active")
		var iframe_box=topWindow.find('#iframe_box');
		show_nav.append('<li class="active"><span data-href="'+href+'">'+titleName+'</span><i></i><em></em></li>');
		tabNavallwidth();
		var iframeBox=iframe_box.find('.show_iframe')
		iframeBox.hide();
		iframe_box.append('<div class="show_iframe"><div class="loading"></div><iframe frameborder="0" src="'+href+'"></iframe></div>');
		var showBox=iframe_box.find('.show_iframe:visible')
		showBox.find('iframe').hide().load(function(){
			showBox.find('.loading').hide();	
			$(this).show()
		});
	}

	var num=0;
	var oUl=$("#min_title_list");
	var hide_nav=$("#Hui-tabNav");
	$(document).on("click","#min_title_list li",function(){
		var bStopIndex=$(this).index();
		var iframe_box=$("#iframe_box");
		$("#min_title_list li").removeClass("active").eq(bStopIndex).addClass("active");
		iframe_box.find(".show_iframe").hide().eq(bStopIndex).show();		
	});
	$(document).on("click","#min_title_list li i",function(){
		var aCloseIndex=$(this).parents("li").index();
		$(this).parent().remove();
		$('#iframe_box').find('.show_iframe').eq(aCloseIndex).remove();	
		num==0?num=0:num--;
		tabNavallwidth();
	});
	$(document).on("dblclick","#min_title_list li",function(){
		var aCloseIndex=$(this).index();
		var iframe_box=$("#iframe_box");
		if(aCloseIndex>0){
			$(this).remove();
			$('#iframe_box').find('.show_iframe').eq(aCloseIndex).remove();	
			num==0?num=0:num--;
			$("#min_title_list li").removeClass("active").eq(aCloseIndex-1).addClass("active");
			iframe_box.find(".show_iframe").hide().eq(aCloseIndex-1).show();
			tabNavallwidth();
		}else{
			return false;
		}
	});
	tabNavallwidth();
	
	$('#js-tabNav-next').click(function(){
		num==oUl.find('li').length-1?num=oUl.find('li').length-1:num++;
		toNavPos()
	});
	$('#js-tabNav-prev').click(function(){
		num==0?num=0:num--;
		toNavPos();
	});
	
	function toNavPos(){
		oUl.stop().animate({'left':-num*100},100)
	}
 
}); 