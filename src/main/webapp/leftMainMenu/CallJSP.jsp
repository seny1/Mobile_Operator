<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Звонки</title>
</head>
<body>
    <header>
        <%@include file="../headerJSP.jsp" %>
    </header>
    <div class="content">
        <%@include file="../leftMenu.jsp" %>
        <div class="tables-container">
            <table class="table1">
                <thead>
                <tr>
                    <c:forEach var="column" items="${requestScope.columnNames}">
                        <th>${column}</th>
                    </c:forEach>
                </tr>
                </thead>
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
    </div>
</body>
</html>
