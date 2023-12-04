<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Чеки</title>
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
                    <c:forEach var="check" items="${requestScope.checkTable}">
                        <tr>
                            <td>${check.product.productId}</td>
                            <td>${check.productCount}</td>
                            <td>${check.checkId}</td>
                            <td>${check.client.clientId}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="dropdown">
                <button class="dropbtn">Сортировать по</button>
                <div class="dropdown-content">
                    <c:forEach var="column" items="${requestScope.columnNames}">
                        <a href="${pageContext.request.contextPath}/checkTable?orderBy=${column}">${column}</a>
                    </c:forEach>
                </div>
            </div>
            <a href="#win1" class="dropbtn">Добавить запись</a>
            <a href="#x" class="overlay" id="win1"></a>
            <div class="popup">
                <form class="form" method="post" action="${pageContext.request.contextPath}/checkTable">
                    <label class="label" for="numberOfTypes">Количество товаров разных типов:</label>
                    <input class="input" value="${param.numberOfTypes}" type="text" id="numberOfTypes" name="numberOfTypes" pattern="^\d+$" required>
                    <input class="input" type="submit" value="Отправить">
                </form>
                <a class="close" title="Закрыть" href="#close"></a>
            </div>
            <a href="#win2" class="dropbtn" style="display: contents"></a>
            <a href="#x" class="overlay" id="win2"></a>
            <div class="popup">
                <div class="scroll scroll1">
                    <form class="form" method="post" action="${pageContext.request.contextPath}/checkTable?i=${param.numberOfTypes}">
                        <label class="label" for="clientID">ID клиента: </label>
                        <input class="input" type="text" id="clientID" name="clientID" pattern="^\d+$" required>
                        <c:forEach var="i" begin="1" end="${param.numberOfTypes}">
                            <div class="dropdown">
                                <label class="label" for="productName${i}">Название товара:</label>
                                <input class="input-box" type="text" id="productName${i}" list="dropdown-options" name="productName${i}" required>
                                <datalist id="dropdown-options">
                                    <c:forEach var="product" items="${sessionScope.products}">
                                        <option value="${product}">${product}</option>
                                    </c:forEach>
                                </datalist>
                            </div>

                            <label class="label" for="productCount${i}">Количество товара:</label>
                            <input class="input" type="text" id="productCount${i}" name="productCount${i}" pattern="^\d+$" required>
                        </c:forEach>
                        <input class="input" type="submit" value="Отправить">
                    </form>
                </div>
                <a class="close" title="Закрыть" href="#close"></a>
            </div>
            <form class="dropdown" method="get" action="${pageContext.request.contextPath}/clientTable">
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
                    <c:forEach var="check" items="${requestScope.checkTable}">
                        <tr>
                            <td>${check.checkId}</td>
                            <td>${check.product.productName}</td>
                            <td>${check.productCount}</td>
                            <td>${check.client.firstName}</td>
                            <td>${check.client.lastName}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="dropdown">
                <button class="dropbtn">Сортировать по</button>
                <div class="dropdown-content">
                    <c:forEach var="column" items="${requestScope.goodColumnNames}">
                        <a href="${pageContext.request.contextPath}/checkTable?good=good&orderBy=${column}">${column}</a>
                    </c:forEach>
                </div>
            </div>
            <a href="#win3" class="dropbtn">Фильтр</a>
            <a href="#x" class="overlay" id="win3"></a>
            <div class="popup">
                <form class="form" method="post" action="${pageContext.request.contextPath}/checkTable?filter=filter&good=good">
                    <label class="label" for="productNameFilter">Имя продукта:</label>
                    <input class="input" type="text" id="productNameFilter" name="productNameFilter">

                    <label class="label" for="productCountUpFilter">Количество товара больше чем:</label>
                    <input class="input" type="text" id="productCountUpFilter" name="productCountUpFilter" pattern="^\d+$">

                    <label class="label" for="productCountDownFilter">Количество товара меньше чем:</label>
                    <input class="input" type="text" id="productCountDownFilter" name="productCountDownFilter" pattern="^\d+$">

                    <label class="label" for="firstNameFilter">Имя клиента:</label>
                    <input class="input" type="text" id="firstNameFilter" name="firstNameFilter">

                    <label class="label" for="lastNameFilter">Фамилия клиента:</label>
                    <input class="input" type="text" id="lastNameFilter" name="lastNameFilter">

                    <input class="input" type="submit" value="Отправить">
                </form>
                <a class="close" title="Закрыть" href="#close"></a>
            </div>
            <form class="dropdown" method="get" action="${pageContext.request.contextPath}/clientTable">
                <a href="?good" class="dropbtn" style="width: 220px; display: inline-block">Отобразить информацию с ID</a>
            </form>
        </div>
    </c:if>
</div>
</body>
</html>
