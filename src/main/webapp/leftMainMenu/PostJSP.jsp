<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Должности</title>
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
                <th>ID должности</th>
                <th>Название должности</th>
                <th>Описание должности</th>
            </tr>
            </thead>
        </table>
        <div class="scroll-table-body">
            <table class="table1">
                <tbody>
                <c:forEach var="post" items="${requestScope.postTable}">
                    <tr>
                        <td>${post.postId}</td>
                        <td>${post.postName}</td>
                        <td>${post.postDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Сортировать по</button>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/postTable?orderBy=post_id">ID должности</a>
                <a href="${pageContext.request.contextPath}/postTable?orderBy=post_name">Название должности</a>
                <a href="${pageContext.request.contextPath}/postTable?orderBy=post_description">Описание должности</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>



