/**
 * jQuery :  城市联动插件
 * @author   xupp
 * @example  $("#test").ProvinceCity();
 * @params   暂无
 */
$.fn.ProvinceCity = function(options){
	var job_domain = domain_job || 'http://job.zcool.com.cn';
	var _self = this;
	//定义3个默认值
	_self.data("province",["请选择", "请选择"]);
	_self.data("city1",["请选择", "请选择"]);
	_self.data("city2",["请选择", "请选择"]);
	
	//分别获取3个下拉框
	var $sel1 = _self.find("select").eq(0);
	var $sel2 = _self.find("select").eq(1);
	var $sel3 = _self.find("select").eq(2);
	//默认省级下拉
	if(_self.data("province")){
		$sel1.append("<option value='"+_self.data("province")[1]+"'>"+_self.data("province")[0]+"</option>");
	}
	/*$.each( GP , function(index,data){
		$sel1.append("<option value='"+data+"'>"+data+"</option>");
	});*/
	$.ajax({
		   type: "POST",
		   url: job_domain+"/city/getCity.do",
		   data: "parentid=0",
		   dataType:'json',
		   success: function(msg){
		     //alert( "Data Saved: " + msg.confcitylist.length );
		     $.each( msg.confcitylist , function(index,data){
		 		$sel1.append("<option value='"+data.id+"'>"+data.name+"</option>");
		 	});
		     //$sel1.trigger("change","22"); 
		     if(options.province!=0&&options.province!=""){
		     $sel1.find("option[value="+options.province+"]").attr("selected",true).trigger("change");
		     }//---------------------------------------
		   }
		});
	//默认的1级城市下拉
	if(_self.data("city1")){
		$sel2.append("<option value='"+_self.data("city1")[1]+"'>"+_self.data("city1")[0]+"</option>");
	}
	//默认的2级城市下拉
	if(_self.data("city2")){
		$sel3.append("<option value='"+_self.data("city2")[1]+"'>"+_self.data("city2")[0]+"</option>");
	}
	//省级联动 控制
	var index1 = "" ;
	$sel1.change(function(){
		//清空其它2个下拉框
		$sel2[0].options.length=0;
		$sel3[0].options.length=0;
		index1 = this.selectedIndex;
		if(index1==0){	//当选择的为 “请选择” 时
			if(_self.data("city1")){
				$sel2.append("<option value='"+_self.data("city1")[1]+"'>"+_self.data("city1")[0]+"</option>");
			}
			if(_self.data("city2")){
				$sel3.append("<option value='"+_self.data("city2")[1]+"'>"+_self.data("city2")[0]+"</option>");
			}
		}else{
			/*$.each( GT[index1-1] , function(index,data){
				$sel2.append("<option value='"+data[0]+"'>"+data[1]+"</option>");
			});*/
			var initthreeid=0;
			$.ajax({
				   type: "POST",
				   url: job_domain+"/city/getCity.do",
				   data: "parentid="+this.value,
				   dataType:'json',
				   success: function(msg){
				     //alert( "Data Saved: " + msg.confcitylist.length );
				     $.each( msg.confcitylist , function(index,data){
				    	 if(index==0){
				    		 initthreeid=data.id;
				    	 }
				    	 $sel2.append("<option value='"+data.id+"'>"+data.name+"</option>");
				 	});
				     if(options.city1!=0&&options.city1!=""){
					     $sel2.find("option[value="+options.city1+"]").attr("selected",true);
					     }
				     //点击二级后级联三级
				     $.ajax({
						   type: "POST",
						   url: job_domain+"/city/getCity.do",
						   data: "parentid="+initthreeid,
						   dataType:'json',
						   success: function(msg){
						     //alert( "Data Saved: " + msg.confcitylist.length );
						     $.each( msg.confcitylist , function(index,data){
						    	 $sel3.append("<option value='"+data.id+"'>"+data.name+"</option>");
						 	});
						     if(options.city2!=0&&options.city2!=""){
							     $sel3.find("option[value="+options.city2+"]").attr("selected",true);
							     }
						   }
						});
				     
				   }
				});
			/*$.each( GC[index1-1][0] , function(index,data){
				$sel3.append("<option value='"+data+"'>"+data+"</option>");
			})*/
			
		}
	}).change();
	//1级城市联动 控制
	var index2 = "" ;
	$sel2.change(function(){
		/*$sel3[0].options.length=0;
		index2 = this.selectedIndex;
		$.each( GC[index1-1][index2] , function(index,data){
			$sel3.append("<option value='"+data+"'>"+data+"</option>");
		})*/
		$.ajax({
			   type: "POST",
			   url: job_domain+"/city/getCity.do",
			   data: "parentid="+this.value,
			   dataType:'json',
			   success: function(msg){
			     //alert( "Data Saved: " + msg.confcitylist.length );
			      $sel3[0].options.length=0;
			     $.each( msg.confcitylist , function(index,data){
			    	 $sel3.append("<option value='"+data.id+"'>"+data.name+"</option>");
			 	});
			   }
			});
	});
	return _self;
};