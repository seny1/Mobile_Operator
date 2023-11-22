<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Тарифные планы</title>
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
                <c:forEach var="tariffPlan" items="${requestScope.tariffPlanTable}">
                    <tr>
                        <td>${tariffPlan.planId}</td>
                        <td>${tariffPlan.planName}</td>
                        <td>${tariffPlan.callMinutes}</td>
                        <td>${tariffPlan.internetGb}</td>
                        <td>${tariffPlan.smsNumber}</td>
                        <td>${tariffPlan.price}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="dropdown">
            <button class="dropbtn">Сортировать по</button>
            <div class="dropdown-content">
                <c:forEach var="column" items="${requestScope.columnNames}">
                    <a href="${pageContext.request.contextPath}/tariffPlanTable?orderBy=${column}">${column}</a>
                </c:forEach>
            </div>
        </div>
        <a href="#win1" class="dropbtn">Добавить запись</a>
        <a href="#x" class="overlay" id="win1"></a>
        <div class="popup">
            <form class="form" method="post" action="${pageContext.request.contextPath}/tariffPlanTable">
                <label class="label" for="planName">Имя плана:</label>
                <input class="input" type="text" id="planName" name="planName"  required>

                <label class="label" for="callMinutes">Минуты:</label>
                <input class="input" type="text" id="callMinutes" name="callMinutes" pattern="^\d+" required>

                <label class="label" for="internetGb">Гигабайты:</label>
                <input class="input" type="text" id="internetGb" name="internetGb" pattern="^\d+" required>

                <label class="label" for="smsNumber">Сообщения:</label>
                <input class="input" type="text" id="smsNumber" name="smsNumber" pattern="^\d+" required>

                <label class="label" for="price">Стоимость:</label>
                <input class="input" type="text" id="price" name="price" pattern="^\d+" required>

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




