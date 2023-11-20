<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Контракт</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<header>
    <%@include file="../headerJSP.jsp" %>
    <link rel="stylesheet" href="styles.css">
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
                <c:forEach var="contract" items="${requestScope.contractTable}">
                    <tr>
                        <td>${contract.contractId}</td>
                        <td>${contract.plan.planId}</td>
                        <td>${contract.client.clientId}</td>
                        <td>${contract.date}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Сортировать по</button>
            <div class="dropdown-content">
                <c:forEach var="column" items="${requestScope.columnNames}">
                    <a href="${pageContext.request.contextPath}/contractTable?orderBy=${column}">${column}</a>
                </c:forEach>
            </div>
        </div>
        <a href="#win1" class="dropbtn">Добавить запись</a>
        <a href="#x" class="overlay" id="win1"></a>
        <div class="popup">
            <form class="form" method="post" action="${pageContext.request.contextPath}/contractTable">
                <div class="dropdown">
                    <label class="label" for="tariffName">Название тарифа:</label>
                    <input class="input-box" type="text" id="tariffName" list="dropdown-options" name="tariffName" required>
                    <datalist id="dropdown-options">
                        <c:forEach var="planName" items="${sessionScope.planNames}">
                            <option value="${planName}">${planName}</option>
                        </c:forEach>
                    </datalist>
                </div>

                <label class="label" for="clientID">ID клиента:</label>
                <input class="input" type="text" id="clientID" name="clientID" pattern="^\d+$" required>

                <label class="label" for="date">Дата:</label>
                <input class="input" type="date" id="date" name="date" required>

                <input class="input" type="submit" value="Отправить">
            </form>
            <a class="close" title="Закрыть" href="#close"></a>
        </div>
    </div>
</div>
</body>
</html>
