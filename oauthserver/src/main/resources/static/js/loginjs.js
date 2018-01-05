function getQueryString(name) {		    
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

		
			
$(function(){
	
	var url="http://192.168.25.143:8901/login";
	var clientId=getQueryString("client_id");
	var responsetype=getQueryString("response_type");
	var redirect_uri=getQueryString("redirect_uri");
	$("#client_id").val(clientId);
	$("#response_type").val(responsetype);
	$("#redirect_uri").val(redirect_uri);

	//判读是否登陆
	var token = getCookie("token");
	if(token==null && token!='')//已经登录
	{
		$("#logindiv").show();
		$("#oauthdiv").hide();
		$("#token").val(token);
	}
	else
	{		
		$("#logindiv").hide();
		$("#oauthdiv").show();
	}
	
	$("#loginAndOauth").click(function(){
		var username=$("#username").val();
		var pword=$("#pword").val();
			
		var requestUrl=url+"?";
		$.ajax({
			
			"url":requestUrl,
			"type":"post",
			 data: {username:username, pword:pword},
             dataType: "json",
             success: function(data){
                    if(data.result=="true")
                	{
                    	//写缓存                    	
                    	setCookie("token",data.msg,1000000);
                    	$("#token").val(data.msg);
                    	$("#authform").submit();
                	}
                    else{
                    	alert(data.msg);
                    }
             },
             error:function(error){
            	 console.log(error);
             }
		})
	});
});
	

function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)"); //正则匹配
    if(arr=document.cookie.match(reg)){
      return unescape(arr[2]);
    }
    else{
     return null;
    }
} 
//设置自定义过期时间cookie
function setCookie(name,value,time)
{
    var msec = 1000000;//getMsec(time); //获取毫秒
    var exp = new Date();
    exp.setTime(exp.getTime() + msec*1);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

//将字符串时间转换为毫秒,1秒=1000毫秒
function getMsec(str)
{
   var timeNum=str.substring(0,str.length-1)*1; //时间数量
   var timeStr=str.substring(str.length-1,str.length); //时间单位前缀，如h表示小时
   
   if (timeStr=="s") //20s表示20秒
   {
        return timeNum*1000;
   }
   else if (timeStr=="h") //12h表示12小时
   {
       return timeNum*60*60*1000;
   }
   else if (timeStr=="d")
   {
       return timeNum*24*60*60*1000; //30d表示30天
   }
}