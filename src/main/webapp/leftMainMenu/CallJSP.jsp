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
        </div>
    </div>
</body>
</html>
