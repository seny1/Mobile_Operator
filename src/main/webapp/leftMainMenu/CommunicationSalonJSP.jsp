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
                <th>ID салона</th>
                <th>Адрес</th>
                <th>Кол-во сотрудников</th>
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
                <a href="${pageContext.request.contextPath}/communicationSalonTable?orderBy=salon_id">ID салона</a>
                <a href="${pageContext.request.contextPath}/communicationSalonTable?orderBy=address">Адрес</a>
                <a href="${pageContext.request.contextPath}/communicationSalonTable?orderBy=employee_number">Кол-во сотрудников</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
