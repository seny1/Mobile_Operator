<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Авторизация</title>
    <link rel="stylesheet" href="styles.css">
  </head>
  <body>
        <div class="login_fon">
            <form method="post" action="${pageContext.request.contextPath}/login">
                <h2 class="login_text">Login</h2>
                <label for="loginId">
                    <div class="input-form">
                        <input type="text" name="login" id="loginId" value="${param.login}" placeholder="login">
                    </div>
                </label><br>
                <label for="passwordId">
                    <div class="input-form">
                        <input type="password" name="password" id="passwordId" placeholder="password">
                    </div>
                </label><br>
                <button class="input_button" type="submit">Отправить</button>
                <c:if test="${param.error != null}">
                    <div style="color: red; text-align: center; font-size: 16px; font-weight: bold; margin: 40px">
                        <span>Неправильный логин или пароль!</span>
                    </div>
                </c:if>
            </form>
        </div>
  </body>
</html>
