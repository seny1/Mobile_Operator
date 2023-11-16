<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Номера клиентов</title>
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
            <c:forEach var="clientContact" items="${requestScope.clientContactTable}">
                <tr>
                    <td>${clientContact.contactId}</td>
                    <td>${clientContact.number}</td>
                    <td>${clientContact.type}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</div>
</body>
</html>