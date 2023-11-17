<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Сотрудники</title>
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
                <c:forEach var="employee" items="${requestScope.employeeTable}">
                    <tr>
                        <td>${employee.employeeId}</td>
                        <td>${employee.department.departmentId}</td>
                        <td>${employee.salon.salonId}</td>
                        <td>${employee.firstName}</td>
                        <td>${employee.lastName}</td>
                        <td>${employee.post.postId}</td>
                        <td>${employee.passport.passportId}</td>
                        <td>${employee.contact.contactId}</td>
                        <td>${employee.login}</td>
                        <td>${employee.password}</td>
                        <td>${employee.role.roleId}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Сортировать по</button>
            <div class="dropdown-content">
                <div class="scroll-table-body">
                    <c:forEach var="column" items="${requestScope.columnNames}">
                        <a href="${pageContext.request.contextPath}/employeeTable?orderBy=${column}">${column}</a>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
