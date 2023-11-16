<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <link rel="stylesheet" href="styles.css">
</head>
<div class="scroll-bar-wrap">
    <div class="buttons-on-left">
        <form method="get" action="${pageContext.request.contextPath}/clientTable">
            <button class="button_scroll">Клиенты</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/callTable">
            <button class="button_scroll">Звонки</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/checkTable">
            <button class="button_scroll">Чеки</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/clientContactTable">
            <button class="button_scroll">Номера клиентов</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/clientDeviceTable">
            <button class="button_scroll">Устройства клиентов</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/clientPassportTable">
            <button class="button_scroll">Паспорта клиентов</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/communicationSalonTable">
            <button class="button_scroll">Салоны связи</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/contractTable">
            <button class="button_scroll">Контракты</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/departmentTable">
            <button class="button_scroll">Департаменты</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/employeeTable">
            <button class="button_scroll">Сотрудники</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/employeeContactTable">
            <button class="button_scroll">Контакты сотрудников</button>
        </form>
    </div>
    <div class="cover-bar"></div>
</div>

