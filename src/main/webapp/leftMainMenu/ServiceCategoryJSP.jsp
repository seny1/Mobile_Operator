<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Категории услуг</title>
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
                <th>ID категории</th>
                <th>Название категории</th>
                <th>Сложность</th>
                <th>Описание категории</th>
            </tr>
            </thead>
        </table>
        <div class="scroll-table-body">
            <table class="table1">
                <tbody>
                <c:forEach var="serviceCategory" items="${requestScope.serviceCategoryTable}">
                    <tr>
                        <td>${serviceCategory.categoryId}</td>
                        <td>${serviceCategory.name}</td>
                        <td>${serviceCategory.difficulty}</td>
                        <td>${serviceCategory.description}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Сортировать по</button>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/serviceCategoryTable?orderBy=category_id">ID категории</a>
                <a href="${pageContext.request.contextPath}/serviceCategoryTable?orderBy=name">Название категории</a>
                <a href="${pageContext.request.contextPath}/serviceCategoryTable?orderBy=difficulty">Сложность</a>
                <a href="${pageContext.request.contextPath}/serviceCategoryTable?orderBy=description">Описание категории</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>




