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
<c:if test="${empty param.good}">
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
    <c:if test="${sessionScope.user.role.roleId == 1 || sessionScope.user.role.roleId == 2}">
        <a href="#win1" class="dropbtn">Добавить запись</a>
        <a href="#x" class="overlay" id="win1"></a>
        <div class="popup">
            <form class="form" method="post" action="${pageContext.request.contextPath}/productTable">
                <label class="label" for="price">Стоимость</label>
                <input class="input" type="text" id="price" name="price" pattern="^\d+(\.\d+)*$" value="${param.price}" required>

                <label class="label" for="productDescription">Описание товара:</label>
                <input class="input" type="text" id="productDescription" name="productDescription" value="${param.productDescription}" required>

                <label class="label" for="productName">Название товара:</label>
                <input class="input" type="text" id="productName" name="productName" value="${param.productName}"  required>

                <div class="dropdown">
                    <label class="label" for="categoryName">Категория товара:</label>
                    <input class="input-box" type="text" id="categoryName" list="dropdown-options" name="categoryName" value="${param.categoryName}" required>
                    <datalist id="dropdown-options">
                        <c:forEach var="category" items="${sessionScope.categories}">
                            <option value="${category}">${category}</option>
                        </c:forEach>
                    </datalist>
                </div>

                <label class="label" for="productCount">Количество товара:</label>
                <input class="input" type="text" id="productCount" name="productCount" pattern="^\d+$" value="${param.productCount}" required>

                <input class="input" type="submit" value="Отправить">
            </form>
            <a class="close" title="Закрыть" href="#close"></a>
        </div>
    </c:if>

        <form class="dropdown" method="get" action="${pageContext.request.contextPath}/produtTable">
            <a href="?good=good" class="dropbtn" style="width: 220px; display: inline-block">Отобразить всю информацию</a>
        </form>
        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <c:forEach var="error" items="${requestScope.errors}">
                    <span>${error.message}</span>
                    <br>
                </c:forEach>
            </div>
        </c:if>
    </div>
</c:if>

    <c:if test="${not empty param.good}">
        <div class="scroll-table" style="width: 100%">
            <table style="width: 100%">
                <thead>
                <tr>
                    <c:forEach var="column" items="${requestScope.goodColumnNames}">
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
                            <td>${product.category.name}</td>
                            <td>${product.count}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="dropdown">
                <button class="dropbtn">Сортировать по</button>
                <div class="dropdown-content">
                    <c:forEach var="column" items="${requestScope.goodColumnNames}">
                        <a href="${pageContext.request.contextPath}/productTable?good=good&orderBy=${column}">${column}</a>
                    </c:forEach>
                </div>
            </div>
            <a href="#win3" class="dropbtn">Фильтр</a>
            <a href="#x" class="overlay" id="win3"></a>
            <div class="popup">
                <form class="form" method="post" action="${pageContext.request.contextPath}/productTable?filter=filter&good=good">
                    <label class="label" for="productNameFilter">Имя продукта:</label>
                    <input class="input" type="text" id="productNameFilter" name="productNameFilter">

                    <label class="label" for="priceUpFilter">Цена больше чем:</label>
                    <input class="input" type="text" id="priceUpFilter" name="priceUpFilter">

                    <label class="label" for="priceDownFilter">Цена меньше чем:</label>
                    <input class="input" type="text" id="priceDownFilter" name="priceDownFilter">

                    <label class="label" for="countUpFilter">Количество товара больше чем:</label>
                    <input class="input" type="text" id="countUpFilter" name="countUpFilter">

                    <label class="label" for="countDownFilter">Количество товара меньше чем:</label>
                    <input class="input" type="text" id="countDownFilter" name="countDownFilter">

                    <input class="input" type="submit" value="Отправить">
                </form>
                <a class="close" title="Закрыть" href="#close"></a>
            </div>
            <form class="dropdown" method="get" action="${pageContext.request.contextPath}/productTable">
                <a href="?good" class="dropbtn" style="width: 220px; display: inline-block">Отобразить информацию с ID</a>
            </form>
        </div>
    </c:if>
</div>
</body>
</html>