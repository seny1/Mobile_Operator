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
        <c:if test="${sessionScope.user.role.roleId == 1 || sessionScope.user.role.roleId == 2}">
            <a href="#win1" class="dropbtn">Добавить запись</a>
            <a href="#x" class="overlay" id="win1"></a>
            <div class="popup">
                <form class="form" method="post" action="${pageContext.request.contextPath}/tariffPlanTable">
                    <label class="label" for="planName">Имя плана:</label>
                    <input class="input" value="${param.planName}" type="text" id="planName" name="planName"  required>

                    <label class="label" for="callMinutes">Минуты:</label>
                    <input class="input" value="${param.callMinutes}" type="text" id="callMinutes" name="callMinutes" pattern="^\d+" required>

                    <label class="label" for="internetGb">Гигабайты:</label>
                    <input class="input" value="${param.internetGb}" type="text" id="internetGb" name="internetGb" pattern="^\d+" required>

                    <label class="label" for="smsNumber">Сообщения:</label>
                    <input class="input" value="${param.smsNumber}" type="text" id="smsNumber" name="smsNumber" pattern="^\d+" required>

                    <label class="label" for="price">Стоимость:</label>
                    <input class="input" value="${param.price}" type="text" id="price" name="price" pattern="^\d+" required>

                    <input class="input" type="submit" value="Отправить">
                </form>
                <a class="close" title="Закрыть" href="#close"></a>
            </div>
        </c:if>

        <c:if test="${sessionScope.user.role.roleId == 1 || sessionScope.user.role.roleId == 2}">
            <a href="#win4" class="dropbtn">Удалить запись</a>
            <a href="#x" class="overlay" id="win4"></a>
            <div class="popup">
                <form class="form" method="post" action="${pageContext.request.contextPath}/tariffPlanTable?delete=delete&good=good">
                    <div class="dropdown">
                        <label class="label" for="nameDelete">Название тарифа:</label>
                        <input class="input-box" type="text" id="nameDelete" list="dropdown-options-name" name="nameDelete" value="${param.nameDelete}" required>
                        <datalist id="dropdown-options-name">
                            <c:forEach var="nameDelete" items="${sessionScope.names}">
                                <option value="${nameDelete}">${nameDelete}</option>
                            </c:forEach>
                        </datalist>
                    </div>
                    <input class="input" type="submit" value="Отправить">
                </form>
                <a class="close" title="Закрыть" href="#close"></a>
            </div>
        </c:if>
        <a href="#win3" class="dropbtn">Фильтр</a>
        <a href="#x" class="overlay" id="win3"></a>
        <div class="popup">
            <form class="form" method="post" action="${pageContext.request.contextPath}/tariffPlanTable?filter=filter">
                <label class="label" for="planNameFilter">Имя тарифа:</label>
                <input class="input" type="text" id="planNameFilter" name="planNameFilter">

                <label class="label" for="callMinutesUpFilter">Минут разговоров больше чем:</label>
                <input class="input" type="text" id="callMinutesUpFilter" name="callMinutesUpFilter">

                <label class="label" for="callMinutesDownFilter">Минут разговоров меньше чем:</label>
                <input class="input" type="text" id="callMinutesDownFilter" name="callMinutesDownFilter">

                <label class="label" for="internetGbUpFilter">Гигабайт больше чем:</label>
                <input class="input" type="text" id="internetGbUpFilter" name="internetGbUpFilter">

                <label class="label" for="internetGbDownFilter">Гигабайт меньше чем:</label>
                <input class="input" type="text" id="internetGbDownFilter" name="internetGbDownFilter">

                <label class="label" for="smsNumberUpFilter">СМС больше чем:</label>
                <input class="input" type="text" id="smsNumberUpFilter" name="smsNumberUpFilter">

                <label class="label" for="smsNumberDownFilter">СМС меньше чем:</label>
                <input class="input" type="text" id="smsNumberDownFilter" name="smsNumberDownFilter">

                <label class="label" for="priceUpFilter">Цена больше чем:</label>
                <input class="input" type="text" id="priceUpFilter" name="priceUpFilter">

                <label class="label" for="priceDownFilter">Цена меньше чем:</label>
                <input class="input" type="text" id="priceDownFilter" name="priceDownFilter">

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




