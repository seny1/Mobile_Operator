<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Авторизация</title>
    <link rel="stylesheet" href="styles.css">
  </head>
  <body>
        <div class="main-container">
            <form method="post" action="${pageContext.request.contextPath}/login">
                <label for="loginId">Логин:
                    <input type="text" name="login" id="loginId" value="${param.login}">
                </label><br>
                <label for="passwordId">Пароль:
                    <input type="password" name="password" id="passwordId">
                </label><br>
                <button type="submit">Отправить</button>
                <c:if test="${param.error != null}">
                    <div style="color: red">
                        <span>Неправильный логин или пароль!</span>
                    </div>
                </c:if>
            </form>
        </div>
  </body>
</html>
