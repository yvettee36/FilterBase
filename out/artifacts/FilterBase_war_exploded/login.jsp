<%--
  Created by IntelliJ IDEA.
  User: yvettee
  Date: 2017/10/24
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<form action="${pageContext.request.contextPath }/loginServlet" method="post">
    用户名：<input type="text" name="userName"><br/>
    密码：<input type="password" name="passWord"><br/>
    有效期：
    1分钟<input type="radio" name="time" value="${1*60 }">
    5分钟<input type="radio" name="time" value="${5*60 }">
    10分钟<input type="radio" name="time" value="${10*60 }">
    <br/>
    <input type="submit" value="登陆">
</form>
</body>
</html>
