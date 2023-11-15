<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <link rel="stylesheet" href="styles.css">
</head>
<div>
    <div class="buttons-on-right">
        <form method="get" action="${pageContext.request.contextPath}/main" class="buttons-in-main-menu">
            <c:if test="${not empty sessionScope.user}">
                <div class="buttons-in-main-menu">
                    <button class="button1" type="submit">Главная</button>
                </div>
            </c:if>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/account" class="buttons-in-main-menu">
            <c:if test="${not empty sessionScope.user}">
                <div class="buttons-in-main-menu">
                    <button type="submit">Личный кабинет</button>
                </div>
            </c:if>
        </form>
        <form method="post" action="${pageContext.request.contextPath}/logout" class="buttons-in-main-menu">
            <c:if test="${not empty sessionScope.user}">
                <div class="buttons-in-main-menu">
                    <button type="submit">Выйти</button>
                </div>
            </c:if>
        </form>
    </div>
</div>

