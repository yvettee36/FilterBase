<%--
  Created by IntelliJ IDEA.
  User: yvettee
  Date: 2017/10/24
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
欢迎你：${user.userName}

<form action="${pageContext.request.contextPath}/htmlEscapeServlet" method="post">
    <input type="text" name="userName"><br/>
    <textarea rows="5" cols="50" name="resume"></textarea><br/>

    <input type="submit" value="提交">
</form>
</body>
</html>
