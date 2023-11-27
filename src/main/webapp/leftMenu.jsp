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
        <c:if test="${sessionScope.user.role.roleId == 1 || sessionScope.user.role.roleId == 2}">
            <form method="get" action="${pageContext.request.contextPath}/callTable">
                <button class="button_scroll">Звонки</button>
            </form>
        </c:if>
        <form method="get" action="${pageContext.request.contextPath}/checkTable">
            <button class="button_scroll">Чеки</button>
        </form>
        <c:if test="${sessionScope.user.role.roleId == 1}">
            <form method="get" action="${pageContext.request.contextPath}/clientContactTable">
                <button class="button_scroll">Номера клиентов</button>
            </form>
        </c:if>
        <c:if test="${sessionScope.user.role.roleId == 1}">
            <form method="get" action="${pageContext.request.contextPath}/clientDeviceTable">
                <button class="button_scroll">Устройства клиентов</button>
            </form>
        </c:if>
        <c:if test="${sessionScope.user.role.roleId == 1}">
            <form method="get" action="${pageContext.request.contextPath}/clientPassportTable">
                <button class="button_scroll">Паспорта клиентов</button>
            </form>
        </c:if>
        <form method="get" action="${pageContext.request.contextPath}/communicationSalonTable">
            <button class="button_scroll">Салоны связи</button>
        </form>
        <c:if test="${sessionScope.user.role.roleId == 1 || sessionScope.user.role.roleId == 2}">
            <form method="get" action="${pageContext.request.contextPath}/contractTable">
                <button class="button_scroll">Контракты</button>
            </form>
        </c:if>
        <form method="get" action="${pageContext.request.contextPath}/departmentTable">
            <button class="button_scroll">Департаменты</button>
        </form>
        <c:if test="${sessionScope.user.role.roleId == 1 || sessionScope.user.role.roleId == 2}">
            <form method="get" action="${pageContext.request.contextPath}/employeeTable">
                <button class="button_scroll">Сотрудники</button>
            </form>
        </c:if>
        <c:if test="${sessionScope.user.role.roleId == 1 || sessionScope.user.role.roleId == 2}">
            <form method="get" action="${pageContext.request.contextPath}/employeeContactTable">
                <button class="button_scroll">Контакты сотрудников</button>
            </form>
        </c:if>
        <c:if test="${sessionScope.user.role.roleId == 1 || sessionScope.user.role.roleId == 2}">
            <form method="get" action="${pageContext.request.contextPath}/employeePassportTable">
                <button class="button_scroll">Паспорта сотрудников</button>
            </form>
        </c:if>
        <form method="get" action="${pageContext.request.contextPath}/extraServiceTable">
            <button class="button_scroll">Услуги</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/orderTable">
            <button class="button_scroll">Заказы</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/postTable">
            <button class="button_scroll">Должности</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/productTable">
            <button class="button_scroll">Продукты</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/productCategoryTable">
            <button class="button_scroll">Категории продуктов</button>
        </form>
        <c:if test="${sessionScope.user.role.roleId == 1}">
            <form method="get" action="${pageContext.request.contextPath}/roleTable">
                <button class="button_scroll">Роли</button>
            </form>
        </c:if>
        <form method="get" action="${pageContext.request.contextPath}/serviceCategoryTable">
            <button class="button_scroll">Категории услуг</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/statusTable">
            <button class="button_scroll">Статусы работы</button>
        </form>
        <form method="get" action="${pageContext.request.contextPath}/tariffPlanTable">
            <button class="button_scroll">Тарифные планы</button>
        </form>
    </div>
    <div class="cover-bar"></div>
</div>

