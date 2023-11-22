<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Продукты</title>
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
                <c:forEach var="product" items="${requestScope.productTable}">
                    <tr>
                        <td>${product.productId}</td>
                        <td>${product.price}</td>
                        <td>${product.productDescription}</td>
                        <td>${product.productName}</td>
                        <td>${product.category.categoryId}</td>
                        <td>${product.count}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Сортировать по</button>
            <div class="dropdown-content">
                <c:forEach var="column" items="${requestScope.columnNames}">
                    <a href="${pageContext.request.contextPath}/productTable?orderBy=${column}">${column}</a>
                </c:forEach>
            </div>
        </div>
        <a href="#win1" class="dropbtn">Добавить запись</a>
        <a href="#x" class="overlay" id="win1"></a>
        <div class="popup">
            <form class="form" method="post" action="${pageContext.request.contextPath}/productTable">
                <label class="label" for="price">Стоимость</label>
                <input class="input" type="text" id="price" name="price" pattern="^\d+(\.\d+)*$" required>

                <label class="label" for="productDescription">Описание товара:</label>
                <input class="input" type="text" id="productDescription" name="productDescription" required>

                <label class="label" for="productName">Название товара:</label>
                <input class="input" type="text" id="productName" name="productName" required>

                <div class="dropdown">
                    <label class="label" for="categoryName">Категория товара:</label>
                    <input class="input-box" type="text" id="categoryName" list="dropdown-options" name="categoryName" required>
                    <datalist id="dropdown-options">
                        <c:forEach var="category" items="${sessionScope.categories}">
                            <option value="${category}">${category}</option>
                        </c:forEach>
                    </datalist>
                </div>

                <label class="label" for="productCount">Количество товара:</label>
                <input class="input" type="text" id="productCount" name="productCount" pattern="^\d+$" required>

                <input class="input" type="submit" value="Отправить">
            </form>
            <a class="close" title="Закрыть" href="#close"></a>
        </div>
        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <c:forEach var="error" items="${requestScope.errors}">
                    <span>${error.message}</span>
                    <br>
                </c:forEach>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>




