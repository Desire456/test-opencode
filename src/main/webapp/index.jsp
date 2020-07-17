<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Authorization</title>
    <link rel="stylesheet" href="css/index.css">
  </head>
  <body onload="checkError()">
  <div class ="modal">
    <form id="login" action="${pageContext.request.contextPath}/main" method="POST">
      <h1>Bulls and Cows</h1>
      <input name="login" type="text" placeholder="Login" required>
      <input name="password" type="password" placeholder="Password" required>
      <input type="submit" id="submit" value="Sign in">
    </form>
    <button onclick="document.location.href = '/registration'">Sign up</button>
  </div>
  </body>
<script>
  function checkError() {
    let error = '${requestScope.error}';
    if (error !== '') alert(error);
  }
</script>
</html>