<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Салоны связи</title>
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
            <c:forEach var="communicationSalon" items="${requestScope.communicationSalonTable}">
                <tr>
                    <td>${communicationSalon.salonId}</td>
                    <td>${communicationSalon.address}</td>
                    <td>${communicationSalon.employeeNumber}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
