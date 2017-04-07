<%--
  Created by IntelliJ IDEA.
  User: user-00
  Date: 4/6/17
  Time: 6:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <form name="loginForm" method="post" action="test">
    Username: <input type="text" name="username"/> <br/>
    Password: <input type="password" name="password"/> <br/>
    <input type="submit" value="Login" />
  </form>

  <form name="uploadForm" method="post" action="upload" enctype="multipart/form-data">
      File <input type="file" name="videofile"/> <br/>
      <input type="submit" value="upload" />
  </form>
  </body>
</html>
