<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Роли</title>
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
                <th>ID роли</th>
                <th>Название роли</th>
                <th>Описание роли</th>
            </tr>
            </thead>
        </table>
        <div class="scroll-table-body">
            <table class="table1">
                <tbody>
                <c:forEach var="role" items="${requestScope.roleTable}">
                    <tr>
                        <td>${role.roleId}</td>
                        <td>${role.roleName}</td>
                        <td>${role.description}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Сортировать по</button>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/roleTable?orderBy=role_id">ID роли</a>
                <a href="${pageContext.request.contextPath}/roleTable?orderBy=role_name">Название роли</a>
                <a href="${pageContext.request.contextPath}/roleTable?orderBy=description">Описание роли</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>




