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
        <a href="#win1" class="dropbtn">Добавить запись</a>
        <a href="#x" class="overlay" id="win1"></a>
        <div class="popup">
            <form class="form" method="post" action="${pageContext.request.contextPath}/extraServiceTable">
                <label class="label" for="serviceName">Название услуги:</label>
                <input class="input" type="text" id="serviceName" name="serviceName" required>

                <label class="label" for="price">Стоимость услуги:</label>
                <input class="input" type="text" id="price" name="price" required>

                <div class="dropdown">
                    <label class="label" for="serviceCategory">Категория услуги:</label>
                    <input class="input-box" type="text" id="serviceCategory" list="dropdown-options" name="serviceCategory" required>
                    <datalist id="dropdown-options">
                        <c:forEach var="serviceCategory" items="${sessionScope.serviceCategories}">
                            <option value="${serviceCategory}">${serviceCategory}</option>
                        </c:forEach>
                    </datalist>
                </div>

                <label class="label" for="description">Описание услуги:</label>
                <input class="input" type="text" id="description" name="description" required>

                <input class="input" type="submit" value="Отправить">
            </form>
            <a class="close" title="Закрыть" href="#close"></a>
        </div>
    </div>
</div>
</body>
</html>
