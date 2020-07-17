<%--
  Created by IntelliJ IDEA.
  User: fame1
  Date: 07.07.2020
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="../css/registration.css">
</head>
<body onload="checkError()">
<div class="modal">
    <h1>Registration</h1>
    <form action="${pageContext.request.contextPath}/registration" method="POST" onsubmit="return checkPass()">
        <label for="username">Create your login</label>
        <input id="username" type="text" name="login" required>
        <label for="password">Create your password</label>
        <input id="password" type="password" name="password" required>
        <label for="passCh">Retype your password</label>
        <input id="passCh" type="password" name="passCh" required>
        <input type="submit" value="Sign up">
        <input type="button" value="Return to sign in page" onclick="document.location.href = '../../../../web'">
    </form>
</div>
</body>
<script>
    function checkError() {
        let error = '${requestScope.error}';
        if (error !== '') alert(error);
    }

    function checkPass() {
        let password = document.getElementById('password').value;
        let passwordCheck = document.getElementById('passCh').value;
        if (password !== passwordCheck) alert('Passwords must match');
        return password === passwordCheck;
    }
</script>
</html>
