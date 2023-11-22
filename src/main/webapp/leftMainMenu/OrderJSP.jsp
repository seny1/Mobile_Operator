<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Заказы услуг</title>
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
                <c:forEach var="order" items="${requestScope.orderTable}">
                    <tr>
                        <td>${order.service.serviceId}</td>
                        <td>${order.employee.employeeId}</td>
                        <td>${order.client.clientId}</td>
                        <td>${order.orderId}</td>
                        <td>${order.status.statusId}</td>
                        <td>${order.device.deviceId}</td>
                        <td>${order.comment}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Сортировать по</button>
            <div class="dropdown-content">
                <c:forEach var="column" items="${requestScope.columnNames}">
                    <a href="${pageContext.request.contextPath}/orderTable?orderBy=${column}">${column}</a>
                </c:forEach>
            </div>
        </div>

        <a href="#win1" class="dropbtn">Добавить запись</a>
        <a href="#x" class="overlay" id="win1"></a>
        <div class="popup">
            <form class="form" method="post" action="${pageContext.request.contextPath}/orderTable">
                <div class="dropdown">
                    <label class="label" for="choose">Клиент уже есть в базе?</label>
                    <input class="input-box" type="text" id="choose" list="dropdown-options" name="choose" required>
                    <datalist id="dropdown-options">
                        <option value="Да">Да</option>
                        <option value="Нет">Нет</option>
                    </datalist>
                </div>

                <input class="input" type="submit" value="Отправить">
            </form>
            <a class="close" title="Закрыть" href="#close"></a>
        </div>

        <a href="#win2" class="dropbtn"></a>
        <a href="#x" class="overlay" id="win2"></a>
        <div class="popup">
            <form class="form" method="post" action="${pageContext.request.contextPath}/orderTable">
                <label class="label" for="firstName">Имя:</label>
                <input class="input" type="text" id="firstName" name="firstName" required>

                <label class="label" for="lastName">Фамилия:</label>
                <input class="input" type="text" id="lastName" name="lastName" required>

                <label class="label" for="birthday">Дата рождения:</label>
                <input class="input" type="date" id="birthday" name="birthday" required>

                <label class="label" for="series">Серия паспорта:</label>
                <input class="input" type="text" id="series" name="series" pattern="^\d{4}$" required>

                <label class="label" for="numberOfPassport">Номер паспорта:</label>
                <input class="input" type="text" id="numberOfPassport" name="numberOfPassport" pattern="^\d{6}$" required>

                <label class="label" for="numberOfContact">Номер телефона:</label>
                <input class="input" type="tel" id="numberOfContact" name="numberOfContact" pattern="^\+7\d{10}$" required>

                <label class="label" for="type">Тип телефона:</label>
                <select id="type" name="type">
                    <option value="mobile">Мобильный</option>
                    <option value="home">Домашний</option>
                    <option value="work">Рабочий</option>
                </select>
                <input class="input" type="submit" value="Отправить">
            </form>
            <a class="close" title="Закрыть" href="#close"></a>
        </div>

        <a href="#win3" class="dropbtn"></a>
        <a href="#x" class="overlay" id="win3"></a>
        <div class="popup">
            <form class="form" method="post" action="${pageContext.request.contextPath}/orderTable?ready=ready">
                <div class="dropdown">
                    <label class="label" for="serviceName">Услуга:</label>
                    <input class="input-box" type="text" id="serviceName" list="dropdown-options2" name="serviceName" required>
                    <datalist id="dropdown-options2">
                        <c:forEach var="service" items="${sessionScope.services}">
                            <option value="${service}">${service}</option>
                        </c:forEach>
                    </datalist>
                </div>

                <label class="label" for="employeeId">ID сотрудника:</label>
                <input class="input-box" type="text" id="employeeId" name="employeeId" pattern="^\d+$" required>

                <label class="label" for="clientId">ID клиента:</label>
                <input class="input-box" type="text" id="clientId" name="clientId" value="${sessionScope.clientId}" pattern="^\d+$" required>

                <label class="label" for="model">Модель устройства:</label>
                <input class="input-box" type="text" id="model" name="model" required>

                <label class="label" for="clientProblem">Проблема клиента:</label>
                <input class="input-box" type="text" id="clientProblem" name="clientProblem" required>

                <label class="label" for="comment">Комментарий:</label>
                <input class="input-box" type="text" id="comment" name="comment" required>

                <input class="input" type="submit" value="Отправить">
            </form>
            <a class="close" title="Закрыть" href="#close"></a>
        </div>
        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <c:forEach var="error" items="${requestScope.errors}">
                    <span>${error.message}</span>
                    <br>
                </c:forEach>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>

