<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Паспорта клиентов</title>
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
            <c:forEach var="clientPassport" items="${requestScope.clientPassportTable}">
                <tr>
                    <td>${clientPassport.passportId}</td>
                    <td>${clientPassport.series}</td>
                    <td>${clientPassport.number}</td>
                    <td>${clientPassport.birthday}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
