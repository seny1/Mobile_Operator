<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Услуги</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<header>
    <%@include file="../headerJSP.jsp" %>
</header>
<div class="content">
    <%@include file="../leftMenu.jsp" %>
    <c:if test="${empty param.good}">
    <div class="scroll-table" style="width: 100%">
        <table style="width: 100%">
            <thead>
            <tr>
                <c:forEach var="column" items="${requestScope.columnNames}">
                    <th>${column}</th>
                </c:forEach>
            </tr>
            </thead>
        </table>
        <div class="scroll-table-body">
            <table class="table1">
                <tbody>
                <c:forEach var="extraService" items="${requestScope.extraServiceTable}">
                    <tr>
                        <td>${extraService.serviceId}</td>
                        <td>${extraService.serviceDescription}</td>
                        <td>${extraService.price}</td>
                        <td>${extraService.serviceName}</td>
                        <td>${extraService.category.categoryId}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Сортировать по</button>
            <div class="dropdown-content">
                <c:forEach var="column" items="${requestScope.columnNames}">
                    <a href="${pageContext.request.contextPath}/extraServiceTable?orderBy=${column}">${column}</a>
                </c:forEach>
            </div>
        </div>
        <c:if test="${sessionScope.user.role.roleId == 1 || sessionScope.user.role.roleId == 2}">
            <a href="#win1" class="dropbtn">Добавить запись</a>
            <a href="#x" class="overlay" id="win1"></a>
            <div class="popup">
                <form class="form" method="post" action="${pageContext.request.contextPath}/extraServiceTable">
                    <label class="label" for="serviceName">Название услуги:</label>
                    <input class="input" value="${param.serviceName}" type="text" id="serviceName" name="serviceName" required>

                    <label class="label" for="price">Стоимость услуги:</label>
                    <input class="input" value="${param.price}" type="text" id="price" name="price" pattern="^\d+$" required>

                    <div class="dropdown">
                        <label class="label" for="serviceCategory">Категория услуги:</label>
                        <input class="input-box" value="${param.serviceCategory}" type="text" id="serviceCategory" list="dropdown-options" name="serviceCategory" required>
                        <datalist id="dropdown-options">
                            <c:forEach var="serviceCategory" items="${sessionScope.serviceCategories}">
                                <option value="${serviceCategory}">${serviceCategory}</option>
                            </c:forEach>
                        </datalist>
                    </div>

                    <label class="label" for="description">Описание услуги:</label>
                    <input class="input" value="${param.description}" type="text" id="description" name="description" required>

                    <input class="input" type="submit" value="Отправить">
                </form>
                <a class="close" title="Закрыть" href="#close"></a>
            </div>
        </c:if>
        <form class="dropdown" method="get" action="${pageContext.request.contextPath}/extraServiceTablet">
            <a href="?good=good" class="dropbtn" style="width: 220px; display: inline-block">Отобразить всю информацию</a>
        </form>
        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <c:forEach var="error" items="${requestScope.errors}">
                    <span>${error.message}</span>
                    <br>
                </c:forEach>
            </div>
        </c:if>
    </div>
    </c:if>
    <c:if test="${not empty param.good}">
        <div class="scroll-table" style="width: 100%">
            <table style="width: 100%">
                <thead>
                <tr>
                    <c:forEach var="column" items="${requestScope.goodColumnNames}">
                        <th>${column}</th>
                    </c:forEach>
                </tr>
                </thead>
            </table>
            <div class="scroll-table-body">
                <table class="table1">
                    <tbody>
                    <c:forEach var="extraService" items="${requestScope.extraServiceTable}">
                        <tr>
                            <td>${extraService.serviceId}</td>
                            <td>${extraService.serviceDescription}</td>
                            <td>${extraService.price}</td>
                            <td>${extraService.serviceName}</td>
                            <td>${extraService.category.name}</td>
                            <td>${extraService.category.difficulty}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="dropdown">
                <button class="dropbtn">Сортировать по</button>
                <div class="dropdown-content">
                    <c:forEach var="column" items="${requestScope.goodColumnNames}">
                        <a href="${pageContext.request.contextPath}/extraServiceTable?good=good&orderBy=${column}">${column}</a>
                    </c:forEach>
                </div>
            </div>
            <a href="#win3" class="dropbtn">Фильтр</a>
            <a href="#x" class="overlay" id="win3"></a>
            <div class="popup">
                <form class="form" method="post" action="${pageContext.request.contextPath}/extraServiceTable?filter=filter&good=good">
                    <label class="label" for="serviceNameFilter">Название услуги:</label>
                    <input class="input" type="text" id="serviceNameFilter" name="serviceNameFilter">

                    <label class="label" for="priceUpFilter">Цена больше чем:</label>
                    <input class="input" type="text" id="priceUpFilter" name="priceUpFilter">

                    <label class="label" for="priceDownFilter">Цена меньше чем:</label>
                    <input class="input" type="text" id="priceDownFilter" name="priceDownFilter">

                    <label class="label" for="categoryNameFilter">Название категории:</label>
                    <input class="input" type="text" id="categoryNameFilter" name="categoryNameFilter">

                    <input class="input" type="submit" value="Отправить">
                </form>
                <a class="close" title="Закрыть" href="#close"></a>
            </div>
            <form class="dropdown" method="get" action="${pageContext.request.contextPath}/extraServiceTable">
                <a href="?good" class="dropbtn" style="width: 220px; display: inline-block">Отобразить информацию с ID</a>
            </form>
        </div>
    </c:if>
</div>
</body>
</html>
