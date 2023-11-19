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
            <a href="#win1" class="dropbtn">Добавить запись</a>
            <a href="#x" class="overlay" id="win1"></a>
            <div class="popup">
                <form class="form" method="post" action="${pageContext.request.contextPath}/callTable">
                    <label class="label" for="clientID">ID клиента:</label>
                    <input class="input" type="text" id="clientID" name="clientID" pattern="^\d+$" required>

                    <label class="label" for="subscriberNumber">Номер абонента:</label>
                    <input class="input" type="text" id="subscriberNumber" name="subscriberNumber" pattern="^\+7\d{10}$" required>

                    <label class="label" for="callDuration">Продолжительность звонка:</label>
                    <input class="input" type="text" id="callDuration" name="callDuration" pattern="^\d+$" required>

                    <input class="input" type="submit" value="Отправить">
                </form>
                <a class="close" title="Закрыть" href="#close"></a>
            </div>
        </div>
    </div>
</body>
</html>
