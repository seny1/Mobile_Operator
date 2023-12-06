<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Паспорта сотрудников</title>
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
                <th>ID паспорта</th>
                <th>Серия</th>
                <th>Номер</th>
                <th>День рождения</th>
                <th>Дата выдачи</th>
                <th>Код подразделения</th>
            </tr>
            </thead>
        </table>
        <div class="scroll-table-body">
            <table class="table1">
                <tbody>
                <c:forEach var="employeePassport" items="${requestScope.employeePassportTable}">
                    <tr>
                        <td>${employeePassport.passportId}</td>
                        <td>${employeePassport.series}</td>
                        <td>${employeePassport.number}</td>
                        <td>${employeePassport.birthday}</td>
                        <td>${employeePassport.issueDate}</td>
                        <td>${employeePassport.placeCode}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Сортировать по</button>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/employeePassportTable?orderBy=passport_id">ID паспорта</a>
                <a href="${pageContext.request.contextPath}/employeePassportTable?orderBy=series">Серия</a>
                <a href="${pageContext.request.contextPath}/employeePassportTable?orderBy=number">Номер</a>
                <a href="${pageContext.request.contextPath}/employeePassportTable?orderBy=birthday">День рождения</a>
                <a href="${pageContext.request.contextPath}/employeePassportTable?orderBy=issue_date">Дата выдачи</a>
                <a href="${pageContext.request.contextPath}/employeePassportTable?orderBy=place_code">Код подразделения</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>