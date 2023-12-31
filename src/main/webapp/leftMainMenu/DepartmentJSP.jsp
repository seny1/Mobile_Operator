<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Департаменты</title>
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
                <th>ID департамента</th>
                <th>Название департамента</th>
                <th>Время начала рабочего дня</th>
                <th>Время конца рабочего дня</th>
            </tr>
            </thead>
        </table>
        <div class="scroll-table-body">
            <table class="table1">
                <tbody>
                <c:forEach var="department" items="${requestScope.departmentTable}">
                    <tr>
                        <td>${department.departmentId}</td>
                        <td>${department.departmentName}</td>
                        <td>${department.startTime}</td>
                        <td>${department.endTime}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Сортировать по</button>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/departmentTable?orderBy=department_id">ID департамента</a>
                <a href="${pageContext.request.contextPath}/departmentTable?orderBy=department_name">Название департамента</a>
                <a href="${pageContext.request.contextPath}/departmentTable?orderBy=start_time">Начало рабочего дня</a>
                <a href="${pageContext.request.contextPath}/departmentTable?orderBy=end_time">Конец рабочего дня</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
