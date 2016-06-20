/**
**判断是否null
*@param data
*/
function isNull(data) {
    if (parseInt(data) == 0) {//0不为空
        return false;
    }
    return (data == "" || data == undefined || data == null);
}

/**
判断是否为空，若为空则返回""
*/
function threeFlag(data) {
    return isNull(data) ? "" : data;
}

/**
*字符串截取长度
*/
function ellipsis(str, n) {
    var b = arguments[1] || 20;
    var result = "";
    if (str.length > b) {
        result = str.substring(0, b) + "。。。";
    }else {
        result = str;
    }
    return result;
}

/**
友情提示调用函数
@param o——为文本内容
@param times——友情提示停留时间

*/
function FriendlyReminder(o, times) {
    var b = arguments[1] || 1000;
    $('.FriendlyReminder .FriendlyReminder_title').text(o);
    //$(".FriendlyReminder").fadeIn(0);
    $(".FriendlyReminder").css("display","block");
    setTimeout(function(){
    	$(".FriendlyReminder").css("display","none");
    },b)
    //$(".FriendlyReminder").fadeOut(b);
}

/**
*两种调用方式
*var template1="我是{0}，今年{1}了";
*var template2="我是{name}，今年{age}了";
*var result1=template1.format("loogn",22);
*var result2=template2.format({name:"loogn",age:22});
*两个结果都是"我是loogn，今年22了"
*/
String.prototype.format = function (args) {
    var result = this;
    if (arguments.length > 0) {
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                if (args[key] != undefined) {
                    var reg = new RegExp("({" + key + "})", "g");
                    result = result.replace(reg, args[key]);
                }
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] != undefined) {
                    //var reg = new RegExp("({[" + i + "]})", "g");//这个在索引大于9时会有问题，谢谢何以笙箫的指出
                    var reg = new RegExp("({)" + i + "(})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
    }
    return result;
}

function SetAjax() {
    $.ajaxSetup({
        beforeSend: function (e, xhr, o) {
            if (xhr.url != "" && xhr.url.indexOf("/Check") >= 0) {
            }
        },
        complete: function (e, xhr, o) {
        },
        //contentType : 'application/json; charset=utf-8',
        //dataType : "json",
    });
}

$(function () {
    SetAjax();
})