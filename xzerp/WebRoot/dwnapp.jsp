<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%!
// \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),字符串在编译时会被转码一次,所以是 "\\b"
// \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
 
String androidReg = "\\bandroid|Nexus\\b";
String iosReg = "ip(hone|od|ad)";
 
Pattern androidPat = Pattern.compile(androidReg, Pattern.CASE_INSENSITIVE);
Pattern iosPat = Pattern.compile(iosReg, Pattern.CASE_INSENSITIVE);
 
public boolean likeAndroid(String userAgent){
    if(null == userAgent){
        userAgent = "";
    }
    // 匹配
    Matcher matcherAndroid = androidPat.matcher(userAgent);
    if(matcherAndroid.find()){
        return true;
    } else {
        return false;
    }
}
public boolean likeIOS(String userAgent){
    if(null == userAgent){
        userAgent = "";
    }
    // 匹配
    Matcher matcherIOS = iosPat.matcher(userAgent);
    if(matcherIOS.find()){
        return true;
    } else {
        return false;
    }
}
 
%>
<%
String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
//
String userAgent = request.getHeader( "USER-AGENT" ).toLowerCase();
System.out.println("userAgent: "+userAgent);
if(null == userAgent){
    userAgent = "";
}
 out.println("<SCRIPT LANGUAGE='JavaScript'> alert('test!') </SCRIPT>");
	
if(likeAndroid(userAgent)){
    System.out.println("likeAndroid: "+true);
    response.sendRedirect("download.jsp?platform=android");
    return;
    //request.getRequestDispatcher("/download.html").forward(request,response);
} else if(likeIOS(userAgent)){
     //System.out.println("likeIOS: "+true);
     //System.out.println("alert('舾装-移动客户端暂不支持IOS!')");
     out.println("<SCRIPT LANGUAGE='JavaScript'>");
	 out.println("<!--");
	 out.println("alert('舾装-移动客户端暂不支持IOS!')");
	 out.println("//-->");
	 out.println("</SCRIPT>");
     //response.sendRedirect("http://itunes.apple.com/us/app/id714751061");
    return;
    //request.getRequestDispatcher("/index.html").forward(request,response);
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no" />
<title>舾装-移动客户端 下载</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
 
<body>
<div class="p_down">
    <div>
        <img src="images/logo_xz.jpg" height="100" width="100" align="middle" class="p_logo" />
    </div> 
    <br/>
    <br/>
    <br/>
    <span>舾装-移动客户端</span>
    <br/>
        <!-- 
        <a href="itms-services://?action=download-manifest&url=http://m.jb51.net/upload/client/yhjyios.plist" class="apple download"><img src="images/p_down_apple.png" /></a>
        -->
        <a href="download.jsp?platform=android" class="download"><img src="images/down_and.png" height="45" width="120" align="middle" /></a>
         
</div>

</body>
</html>