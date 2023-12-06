<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Категории продуктов</title>
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
                <th>Описание категории</th>
            </tr>
            </thead>
        </table>
        <div class="scroll-table-body">
            <table class="table1">
                <tbody>
                <c:forEach var="productCategory" items="${requestScope.productCategoryTable}">
                    <tr>
                        <td>${productCategory.categoryId}</td>
                        <td>${productCategory.name}</td>
                        <td>${productCategory.description}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Сортировать по</button>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/productCategoryTable?orderBy=category_id">ID категории</a>
                <a href="${pageContext.request.contextPath}/productCategoryTable?orderBy=name">Название категории</a>
                <a href="${pageContext.request.contextPath}/productCategoryTable?orderBy=description">Описание категории</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>