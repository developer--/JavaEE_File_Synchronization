<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 5/1/17
  Time: 12:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="http://vjs.zencdn.net/5.19.2/video-js.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div style="width: 100%; float: left;">
    <button class="btn btn-primary" style="float: right; margin: 50px" onclick="window.location.href='/api/logout.jsp'">Log out</button>
</div>
<script>

//    $(document).ready(function () {
//        $(".delete_btn").on("click", function (event) {
//            var id = $(event.target).closest("div").children("p").eq(0).text();
//            var dataObj = JSON.stringify({
//                "fileId": id
//            });
//            $.ajax({
//               type: 'POST',
//                contentType: 'application/json',
//                url: "videos/delete",
//                data: id,
//                success: function (data) {
//                    alert("success" + data)
//                }
////                error: function (error) {
////                   console.log(error);
////                    alert("error "+ dataObj.toString() + error.toString())
////                }
//            });
//        });
//    })
</script>
</body>
</html>
