<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Контракт</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<header>
    <%@include file="../headerJSP.jsp" %>
    <link rel="stylesheet" href="styles.css">
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
                <c:forEach var="contract" items="${requestScope.contractTable}">
                    <tr>
                        <td>${contract.contractId}</td>
                        <td>${contract.plan.planId}</td>
                        <td>${contract.client.clientId}</td>
                        <td>${contract.date}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>