<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Устройства клиентов</title>
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
                <th>ID устройства</th>
                <th>Модель</th>
                <th>Проблема клиента</th>
            </tr>
            </thead>
        </table>
        <div class="scroll-table-body">
            <table>
                <tbody>
                <c:forEach var="clientDevice" items="${requestScope.clientDeviceTable}">
                    <tr>
                        <td>${clientDevice.deviceId}</td>
                        <td>${clientDevice.model}</td>
                        <td>${clientDevice.clientProblem}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Сортировать по</button>
            <div class="dropdown-content">
                <c:forEach var="column" items="${requestScope.columnNames}">
                    <a href="${pageContext.request.contextPath}/clientDeviceTable?orderBy=${column}">${column}</a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>
