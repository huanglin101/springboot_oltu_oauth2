function getQueryString(name) {		    
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

$(function(){
	
	var token=getQueryString("token");
	alert(token);
	$("#token").val(token);
	
	$("#btnGetResource").click(function(){
		
		$.ajax({
					
					"url":"http://localhost:9191/gateway/api1/test",
					"type":"post",
					 data: {access_token:token, strname:"123"},
		             dataType: "json",
		             success: function(data){
		                   alert(data);
		             },
		             error:function(error){
		            	 console.log(error);
		             }
				})
	})
})