<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<title>舾装信息平台 下载</title>
</head>
<body>
	<style type="text/css">
	*{margin:0; padding:0;}
	img{max-width: 100%; height: auto;}
	.test{height: 600px; max-width: 600px; font-size: 40px;}
	
	#weixin-tip{display:none;position:fixed;left:0;top:0;background:rgba(0,0,0,0.8);filter:alpha(opacity=80);width:100%;height:100%;z-index:100;}
    #weixin-tip p{text-align:center;margin-top:10%;padding:0 5%;position:relative;}
    #weixin-tip .close{color:#fff;padding:5px;font:bold 20px/24px simsun;text-shadow:0 1px 0 #ddd;position:absolute;top:0;left:5%;}

	</style>
	
	<div class="test">
	    <div style="text-align:center"><br/><img src="images/logo_xz.png" width="20%" height="20%" /></div>
		<div id="app-tip" style="text-align:center"><p><br/><span style="font-size:18px">舾装信息平台</span><br/></p></div> 
		<div style="text-align:center"><a id="J_weixin" class="android-btn" href="xzapp.apk"><img src="images/down_and.png" width="36%" height="36%" alt="安卓版下载" /></a></div>
		<div id="ios-tip" style="text-align:center;display:none"><p><span style="font-size:16px">暂不支持IOS系统!</span></p></div>
		<div id="weixin-tip"><p><img src="images/live_weixin.png" alt="微信打开"/><span id="close" title="关闭" class="close">ⅹ</span></p></div>
	</div>
	
	<script type="text/javascript">
	    var is_ios = (function(){return !!navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)})();
	    var is_weixin = (function(){return navigator.userAgent.toLowerCase().indexOf('micromessenger') !== -1})();
	    var is_qq = (function(){return navigator.userAgent.toLowerCase().indexOf('qq') !== -1})();
		window.onload = function() {
			var winHeight = typeof window.innerHeight != 'undefined' ? window.innerHeight : document.documentElement.clientHeight; //兼容IOS，不需要的可以去掉
			var btn = document.getElementById('J_weixin');
			var tip = document.getElementById('weixin-tip');
			var close = document.getElementById('close');
			var ios = document.getElementById('ios-tip');
			
			if (is_ios) {
				//ios.style.display = 'block';
				//btn.style.display = 'none';
			}

			//if (is_weixin || is_qq) {
			if (is_weixin) {
				//btn.onclick = function(e) {
				//	tip.style.height = winHeight + 'px'; //兼容IOS，不需要的可以去掉
				//	tip.style.display = 'block';
				//	return false;
				//}
				//close.onclick = function() {
				//	tip.style.display = 'none';
				//}
			}
		}
		
	</script>
</body>
</html>