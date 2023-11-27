<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Звонки</title>
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
                    <c:forEach var="call" items="${requestScope.callTable}">
                        <tr>
                            <td>${call.callId}</td>
                            <td>${call.client.clientId}</td>
                            <td>${call.subscriberNumber}</td>
                            <td>${call.callDuration}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="dropdown">
                <button class="dropbtn">Сортировать по</button>
                <div class="dropdown-content">
                    <c:forEach var="column" items="${requestScope.columnNames}">
                        <a href="${pageContext.request.contextPath}/callTable?orderBy=${column}">${column}</a>
                    </c:forEach>
                </div>
            </div>
            <c:if test="${sessionScope.user.role.roleId == 1}">
                <a href="#win1" class="dropbtn">Добавить запись</a>
                <a href="#x" class="overlay" id="win1"></a>
                <div class="popup">
                    <form class="form" method="post" action="${pageContext.request.contextPath}/callTable">
                        <label class="label" for="clientID">ID клиента:</label>
                        <input class="input" type="text" id="clientID" name="clientID" pattern="^\d+$" required>

                        <label class="label" for="subscriberNumber">Номер абонента:</label>
                        <input class="input" type="text" id="subscriberNumber" name="subscriberNumber" placeholder="+7xxxxxxxxxx" pattern="^\+7\d{10}$" required>

                        <label class="label" for="callDuration">Продолжительность звонка:</label>
                        <input class="input" type="text" id="callDuration" name="callDuration" pattern="^\d+\.\d+$" placeholder="xx.xx" required>

                        <input class="input" type="submit" value="Отправить">
                    </form>
                    <a class="close" title="Закрыть" href="#close"></a>
                </div>
            </c:if>

            <form class="dropdown" method="get" action="${pageContext.request.contextPath}/callTable">
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
                    <c:forEach var="call" items="${requestScope.callTable}">
                        <tr>
                            <td>${call.callId}</td>
                            <td>${call.subscriberNumber}</td>
                            <td>${call.callDuration}</td>
                            <td>${call.client.firstName}</td>
                            <td>${call.client.lastName}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="dropdown">
                <button class="dropbtn">Сортировать по</button>
                <div class="dropdown-content">
                    <c:forEach var="column" items="${requestScope.goodColumnNames}">
                        <a href="${pageContext.request.contextPath}/callTable?good=good&orderBy=${column}">${column}</a>
                    </c:forEach>
                </div>
            </div>
            <a href="#win2" class="dropbtn">Фильтр</a>
            <a href="#x" class="overlay" id="win2"></a>
            <div class="popup">
                <form class="form" method="post" action="${pageContext.request.contextPath}/callTable?filter=filter&good=good">
                    <label class="label" for="subscriberNumberFilter">Номер абонента:</label>
                    <input class="input" type="text" id="subscriberNumberFilter" name="subscriberNumberFilter" placeholder="+7xxxxxxxxxx" pattern="^\+7\d{10}$">

                    <label class="label" for="callDurationUpFilter">Продолжительность звонка больше:</label>
                    <input class="input" type="text" id="callDurationUpFilter" name="callDurationUpFilter" placeholder="xx.xx" pattern="^\d+\.\d+$">

                    <label class="label" for="callDurationDownFilter">Продолжительность звонка меньше:</label>
                    <input class="input" type="text" id="callDurationDownFilter" name="callDurationDownFilter" placeholder="xx.xx" pattern="^\d+\.\d+$">

                    <label class="label" for="firstNameFilter">Имя клиента:</label>
                    <input class="input" type="text" id="firstNameFilter" name="firstNameFilter">

                    <label class="label" for="lastNameFilter">Фамилия клиента:</label>
                    <input class="input" type="text" id="lastNameFilter" name="lastNameFilter">

                    <input class="input" type="submit" value="Отправить">
                </form>
                <a class="close" title="Закрыть" href="#close"></a>
            </div>
            <form class="dropdown" method="get" action="${pageContext.request.contextPath}/callTable">
                <a href="?good" class="dropbtn" style="width: 220px; display: inline-block">Отобразить информацию с ID</a>
            </form>
        </div>
    </c:if>
</div>
</body>
</html>
