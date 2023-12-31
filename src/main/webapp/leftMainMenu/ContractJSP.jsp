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
    <c:if test="${empty param.good}">
    <div class="scroll-table" style="width: 100%">
        <table style="width: 100%">
            <thead>
            <tr>
                <th>ID контракта</th>
                <th>ID тарифного плана</th>
                <th>ID клиента</th>
                <th>Дата заключения</th>
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
                <a href="${pageContext.request.contextPath}/contractTable?orderBy=contract_id">ID контракта</a>
                <a href="${pageContext.request.contextPath}/contractTable?orderBy=plan_id">ID тарифного плана</a>
                <a href="${pageContext.request.contextPath}/contractTable?orderBy=client_id">ID клиента</a>
                <a href="${pageContext.request.contextPath}/contractTable?orderBy=date">Дата</a>
            </div>
        </div>
        <c:if test="${sessionScope.user.role.roleId == 1 || sessionScope.user.role.roleId == 2}">
            <a href="#win1" class="dropbtn">Добавить запись</a>
            <a href="#x" class="overlay" id="win1"></a>
            <div class="popup">
                <form class="form" method="post" action="${pageContext.request.contextPath}/contractTable">
                    <div class="dropdown">
                        <label class="label" for="tariffName">Название тарифа:</label>
                        <input class="input-box" value="${param.tariffName}" type="text" id="tariffName" list="dropdown-options" name="tariffName" required>
                        <datalist id="dropdown-options">
                            <c:forEach var="planName" items="${sessionScope.planNames}">
                                <option value="${planName}">${planName}</option>
                            </c:forEach>
                        </datalist>
                    </div>

                    <label class="label" for="clientID">ID клиента:</label>
                    <input class="input" value="${param.clientID}" type="text" id="clientID" name="clientID" pattern="^\d+$" required>

                    <label class="label" for="date">Дата:</label>
                    <input class="input" value="${param.date}" type="date" id="date" name="date" required>

                    <input class="input" type="submit" value="Отправить">
                </form>
                <a class="close" title="Закрыть" href="#close"></a>
            </div>
        </c:if>

        <form class="dropdown" method="get" action="${pageContext.request.contextPath}/contractTable">
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
                        <th>ID контракта</th>
                        <th>Название тарифного плана</th>
                        <th>Имя клиента</th>
                        <th>Фамилия клиента</th>
                        <th>Номер телефона</th>
                        <th>Дата заключения</th>
                    </tr>
                    </thead>
                </table>
                <div class="scroll-table-body">
                    <table class="table1">
                        <tbody>
                        <c:forEach var="contract" items="${requestScope.contractTable}">
                            <tr>
                                <td>${contract.contractId}</td>
                                <td>${contract.plan.planName}</td>
                                <td>${contract.client.firstName}</td>
                                <td>${contract.client.lastName}</td>
                                <td>${contract.client.contact.numberOfContact}</td>
                                <td>${contract.date}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="dropdown">
                    <button class="dropbtn">Сортировать по</button>
                    <div class="dropdown-content">
                        <a href="${pageContext.request.contextPath}/contractTable?good=good&orderBy=contract_id">ID контракта</a>
                        <a href="${pageContext.request.contextPath}/contractTable?good=good&orderBy=plan_name">Название плана</a>
                        <a href="${pageContext.request.contextPath}/contractTable?good=good&orderBy=first_name">Имя клиента</a>
                        <a href="${pageContext.request.contextPath}/contractTable?good=good&orderBy=last_name">Фамилия клиента</a>
                        <a href="${pageContext.request.contextPath}/contractTable?good=good&orderBy=number_of_phone">Номер телефона</a>
                        <a href="${pageContext.request.contextPath}/contractTable?good=good&orderBy=date">Дата</a>
                    </div>
                </div>
                <a href="#win2" class="dropbtn">Фильтр</a>
                <a href="#x" class="overlay" id="win2"></a>
                <div class="popup">
                    <form class="form" method="post" action="${pageContext.request.contextPath}/contractTable?good=good&filter=filter">
                        <label class="label" for="planNameFilter">Тарифный план:</label>
                        <input class="input" type="text" id="planNameFilter" name="planNameFilter">

                        <label class="label" for="firstNameFilter">Имя клиента:</label>
                        <input class="input" type="text" id="firstNameFilter" name="firstNameFilter">

                        <label class="label" for="lastNameFilter">Фамилия клиента:</label>
                        <input class="input" type="text" id="lastNameFilter" name="lastNameFilter">

                        <label class="label" for="numberFilter">Номер телефона клиента:</label>
                        <input class="input" type="text" id="numberFilter" name="numberFilter">

                        <label class="label" for="dateUpFilter">Дата заключения после:</label>
                        <input class="input" type="date" id="dateUpFilter" name="dateUpFilter">

                        <label class="label" for="dateDownFilter">Дата заключения до:</label>
                        <input class="input" type="date" id="dateDownFilter" name="dateDownFilter">

                        <input class="input" type="submit" value="Отправить">
                    </form>
                    <a class="close" title="Закрыть" href="#close"></a>
                </div>
                <c:if test="${sessionScope.user.role.roleId == 1 || sessionScope.user.role.roleId == 2}">
                    <a href="#win3" class="dropbtn">Удалить запись</a>
                    <a href="#x" class="overlay" id="win3"></a>
                    <div class="popup">
                        <form class="form" method="post" action="${pageContext.request.contextPath}/contractTable?delete=delete&good=good">
                            <label class="label" for="contractIdDelete">ID контракта:</label>
                            <input class="input" value="${param.contractIdDelete}" type="text" id="contractIdDelete" name="contractIdDelete" required>

                            <input class="input" type="submit" value="Отправить">
                        </form>
                        <a class="close" title="Закрыть" href="#close"></a>
                    </div>
                </c:if>
                <form class="dropdown" method="get" action="${pageContext.request.contextPath}/contractTable">
                    <a href="?good" class="dropbtn" style="width: 220px; display: inline-block">Отобразить информацию с ID</a>
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
</div>
</body>
</html>
