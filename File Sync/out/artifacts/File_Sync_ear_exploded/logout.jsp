<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 5/1/17
  Time: 1:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="false" %>
<html>
<head>
    <title>logout</title>
</head>
<body>
<%
    request.getSession(false).invalidate();
    response.sendRedirect("/api/login");
%>
</body>
</html>
