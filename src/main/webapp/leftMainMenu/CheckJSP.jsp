<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Чеки</title>
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
            <c:forEach var="check" items="${requestScope.checkTable}">
                <tr>
                    <td>${check.product.productId}</td>
                    <td>${check.productCount}</td>
                    <td>${check.checkId}</td>
                    <td>${check.client.clientId}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
