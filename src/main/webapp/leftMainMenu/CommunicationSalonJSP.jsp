<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Салоны связи</title>
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
            <table>
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
        <div class="dropdown">
            <button class="dropbtn">Сортировать по</button>
            <div class="dropdown-content">
                <c:forEach var="column" items="${requestScope.columnNames}">
                    <a href="${pageContext.request.contextPath}/communicationSalonTable?orderBy=${column}">${column}</a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>
