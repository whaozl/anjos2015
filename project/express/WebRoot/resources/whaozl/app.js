var App = function () {
    var alpha_num_chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz'.split('');

    return {

        Config: {
            ConsoleServiceURL: "http://localhost:1687/",
        },

        Namespace: function () {
            var o, d;
            $.each(arguments, function (index, v) {
                d = v.split(".");
                o = window[d[0]] = window[d[0]] || {};
                $.each(d.slice(1), function (index, v2) {
                    o = o[v2] = o[v2] || {};
                });
            });
            return o;
        },

        RandomString: function (length) {

            if (!length) {
                length = Math.floor(Math.random() * alpha_num_chars.length);
            }
            var str = '';
            for (var i = 0; i < length; i++) {
                str += alpha_num_chars[Math.floor(Math.random() * alpha_num_chars.length)];
            }
            return str;
        },

        EncodeUtf8: function (s1) {
            var s = escape(s1);
            var sa = s.split("%");
            var retV = "";
            if (sa[0] != "") {
                retV = sa[0];
            }
            for (var i = 1; i < sa.length; i++) {
                if (sa[i].substring(0, 1) == "u") {
                    retV += App.Hex2Utf8(App.Str2Hex(sa[i].substring(1, 5)));

                }
                else retV += "%" + sa[i];
            }

            return retV;
        },

        Str2Hex: function (s) {
            var c = "";
            var n;
            var ss = "0123456789ABCDEF";
            var digS = "";
            for (var i = 0; i < s.length; i++) {
                c = s.charAt(i);
                n = ss.indexOf(c);
                digS += App.Dec2Dig(eval(n));

            }
            //return value;
            return digS;
        },

        Dec2Dig: function (n1) {
            var s = "";
            var n2 = 0;
            for (var i = 0; i < 4; i++) {
                n2 = Math.pow(2, 3 - i);
                if (n1 >= n2) {
                    s += '1';
                    n1 = n1 - n2;
                }
                else
                    s += '0';

            }
            return s;

        },

        Dig2Dec: function (s) {
            var retV = 0;
            if (s.length == 4) {
                for (var i = 0; i < 4; i++) {
                    retV += eval(s.charAt(i)) * Math.pow(2, 3 - i);
                }
                return retV;
            }
            return -1;
        },

        Hex2Utf8: function (s) {
            var retS = "";
            var tempS = "";
            var ss = "";
            if (s.length == 16) {
                tempS = "1110" + s.substring(0, 4);
                tempS += "10" + s.substring(4, 10);
                tempS += "10" + s.substring(10, 16);
                var sss = "0123456789ABCDEF";
                for (var i = 0; i < 3; i++) {
                    retS += "%";
                    ss = tempS.substring(i * 8, (eval(i) + 1) * 8);



                    retS += sss.charAt(App.Dig2Dec(ss.substring(0, 4)));
                    retS += sss.charAt(App.Dig2Dec(ss.substring(4, 8)));
                }
                return retS;
            }
            return "";
        },

        //获取QueryString的数组 
        GetQueryString: function (query) {
            if (query)
                query = "?" + query;
            else
                query = location.search;
            var result = query.match(new RegExp("[\?\&][^\?\&]+=[^\?\&]+", "g"));
            for (var i = 0; i < result.length; i++) {
                result[i] = result[i].substring(1);
            }
            return result;
        },

        //根据QueryString参数名称获取值 
        GetQueryStringByName: function (name, query) {
            if (query)
                query = "?" + query;
            else
                query = location.search;
            var result = query.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
            if (result == null || result.length < 1) {
                return "";
            }
            return result[1];
        },

        //根据QueryString参数索引获取值 
        GetQueryStringByIndex: function (index, query) {
            if (index == null) {
                return "";
            }
            var queryStringList = App.GetQueryString(query);
            if (index >= queryStringList.length) {
                return "";
            }
            var result = queryStringList[index];
            var startIndex = result.indexOf("=") + 1;
            result = result.substring(startIndex);
            return result;
        },

        ///设置cookie   
        //参数:三个变量用来设置新的cookie:   
        //cookie的名称,存储的Cookie值,   
        // 以及Cookie过期的时间.   
        // 这几行是把天数转换为合法的日期  
        SetCookie: function (NameOfCookie, value, expiredays) {
            var ExpireDate = new Date();
            ExpireDate.setTime(ExpireDate.getTime() + (expiredays * 24 * 3600 * 1000));

            // 下面这行是用来存储cookie的,只需简单的为"document.cookie"赋值即可.   
            // 注意日期通过toGMTstring()函数被转换成了GMT时间。   
            document.cookie = NameOfCookie + "=" + escape(value) +
            ((expiredays == null) ? ";path=/" : "; path=/;expires=" + ExpireDate.toGMTString());

        },

        ///获取cookie值   
        GetCookie: function (NameOfCookie) {

            // 首先我们检查下cookie是否存在.   
            // 如果不存在则document.cookie的长度为0   

            if (document.cookie.length > 0) {

                // 接着我们检查下cookie的名字是否存在于document.cookie   

                // 因为不止一个cookie值存储,所以即使document.cookie的长度不为0也不能保证我们想要的名字的cookie存在   
                //所以我们需要这一步看看是否有我们想要的cookie   
                //如果begin的变量值得到的是-1那么说明不存在   
                var begin, end;
                begin = document.cookie.indexOf(NameOfCookie + "=");
                if (begin != -1) {

                    // 说明存在我们的cookie.   

                    begin += NameOfCookie.length + 1; //cookie值的初始位置   
                    end = document.cookie.indexOf(";", begin); //结束位置   
                    if (end == -1) end = document.cookie.length; //没有;则end为字符串结束位置   
                    return unescape(document.cookie.substring(begin, end));
                }
            }

            return null;

            // cookie不存在返回null   
        }
    };

}();