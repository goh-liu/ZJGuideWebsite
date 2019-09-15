// JavaScript Document
//注册页面的js文件
var xueyauns = [
	["人文学院","会计学院","设计学院","传媒学院","外国语学院","信息工程学院","经济管理学院"],
	["语言学院","做账学院","画图学院","拍视频学院","外学院","计算机学院","管理学院"]
];
var zhuanyes = [
	["汉语言文学","传播"],
	["会计学（注册会计师）","会计学（国际会计）","财务管理"],
	["风景园林","产品设计（室内与家具设计）","环境设计","视觉传达设计","服装与服饰设计"],
	["摄影","播音与主持艺术","广播电视编导"],
	["商务英语（跨境电子商务）","商务英语（国际贸易实务）","英语（教育）","英语（英西双语）","英语（英法双语）","英语（英日双语）","日语","翻译"],
	["网络工程（网络与新媒体）","网络工程（互联网金融）","计算机科学与技术（数据库工程师）","计算机科学与技术（数字媒体技术）","电气工程及其自动化","电子信息工程","电子商务"],
	["国际经济与贸易","金融工程","市场营销（商务策划与管理）","市场营销（移动互联网营销）","人力资源管理","物流管理"]
];

$(function(){
	//表单检验
	$(".bitian").after("<font style='color:red'>*</font>");
	$(".bitian > input").blur(function(){
		//首先获取用户输入的东西
		var inputText = this.value;
		//清空上一次的提示
		$(this).parent().parent().find(".formtips").remove();
		//判断当前的值是哪一项输入的值
		if($(this).is("#uname")){
			//用户名正则，2到16位（字母，数字，下划线，减号）
			var uPattern = /^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[a-zA-Z0-9_-]){2,16}$/;
			if(uPattern.test(inputText)){
				$(this).parent().parent().append("<span class='formtips'><img src='image/reg2.gif'></span>");
			}else{
				$(this).parent().parent().append("<span class='formtips'><img src='image/reg1.gif'>请输入2到16位（汉字、字母，数字，下划线，减号）</span>");
			}
		}else if($(this).is("#upassword")){
			//密码强度正则，6~16位由数字和26个英文字母混合而成的密码
			var pPattern = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
			if(pPattern.test(inputText)){
				$(this).parent().parent().append("<span class='formtips'><img src='image/reg2.gif'></span>");
			}else{
				$(this).parent().parent().append("<span class='formtips'><img src='image/reg1.gif'>6~16位非纯数字</span>");
			}
		}else if($(this).is("#Reupassword")){
			//uPass是第一次输入的密码
			var uPass = document.getElementById("upassword").value;
			if(inputText == uPass){
				$(this).parent().parent().append("<span class='formtips'><img src='image/reg2.gif'></span>");
			}else{
				$(this).parent().parent().append("<span class='formtips'><img src='image/reg1.gif'>两次密码不一样</span>");
			}
		}
	});

	//根据学校显示学院
	$("#school").change(function(){
		//alert(this.value);
		var academys = xueyauns[this.value];
		$('#academy').empty();
		$("#academy").append("<option value='-1'>--学院--</option>");
		$('#major').empty();
		$("#major").append("<option value='-1'>--专业--</option>");
		$(academys).each(function(i,n){
			$('#academy').append("<option value=\""+i+"\">"+n+"</option>");
		});
	});
	//根据学院显示专业
	$("#academy").change(function(){
		//alert(this.value);
		var majors = zhuanyes[this.value];
		$('#major').empty();
		$("#major").append("<option value='-1'>--专业--</option>");
		//alert(this.value);
		$(majors).each(function(i,n){
			$('#major').append("<option value=\""+i+"\">"+n+"</option>");
		});
	});
	
	$(".nav li").hover(function(){
		   $(this).not(".dropdown").addClass("active");
	},function(){
		$(this).removeClass("active");
	});
});

