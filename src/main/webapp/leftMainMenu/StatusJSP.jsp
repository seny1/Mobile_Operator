<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Статусы заказов</title>
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
                <th>ID статуса</th>
                <th>Название статуса</th>
                <th>Описание статуса</th>
            </tr>
            </thead>
        </table>
        <div class="scroll-table-body">
            <table class="table1">
                <tbody>
                <c:forEach var="status" items="${requestScope.statusTable}">
                    <tr>
                        <td>${status.statusId}</td>
                        <td>${status.name}</td>
                        <td>${status.description}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Сортировать по</button>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/statusTable?orderBy=status_id">ID статуса</a>
                <a href="${pageContext.request.contextPath}/statusTable?orderBy=name">Название статуса</a>
                <a href="${pageContext.request.contextPath}/statusTable?orderBy=description">Описание статуса</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>




