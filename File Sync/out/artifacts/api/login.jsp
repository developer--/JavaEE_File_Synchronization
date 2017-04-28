<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 4/27/17
  Time: 1:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign in</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container" style="width: 300px">
    <h2>Authorization</h2>
   <form>
       <div class="form-group">
           <label for="usr">Name:</label>
           <input type="text" name="username" class="form-control" id="usr">
       </div>
       <div class="form-group">
           <label for="pwd">Password:</label>
           <input type="password" name="password" class="form-control" id="pwd">
       </div>
       <button type="submit" formmethod="post" class="btn btn-default">Sign in</button>
   </form>
</div>
</body>
</html>
