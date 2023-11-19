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
                <input class="input" type="text" id="numberOfTypes" name="numberOfTypes" pattern="^\d+$" required>

                <input class="input" type="submit" value="Отправить">
            </form>
            <a class="close" title="Закрыть" href="#close"></a>
        </div>
        <a href="#win2" class="dropbtn"></a>
        <a href="#x" class="overlay" id="win2"></a>
        <div class="popup">
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
                    <input class="input" type="text" id="productCount${i}" name="productCount${i}" required>

                    <br>
                </c:forEach>
                <input class="input" type="submit" value="Отправить">
            </form>
            <a class="close" title="Закрыть" href="#close"></a>
        </div>
    </div>
</div>
</body>
</html>
