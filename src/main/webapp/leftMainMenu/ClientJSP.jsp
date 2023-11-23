<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Клиенты</title>
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
                        <c:forEach var="client" items="${requestScope.clientTable}">
                            <tr>
                                <td>${client.clientId}</td>
                                <td>${client.passport.passportId}</td>
                                <td>${client.firstName}</td>
                                <td>${client.lastName}</td>
                                <td>${client.contact.contactId}</td>
                                <td>${client.remainMinutes}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="dropdown">
                    <button class="dropbtn">Сортировать по</button>
                    <div class="dropdown-content">
                        <c:forEach var="column" items="${requestScope.columnNames}">
                            <a href="${pageContext.request.contextPath}/clientTable?orderBy=${column}">${column}</a>
                        </c:forEach>
                    </div>
                </div>
                <a href="#win1" class="dropbtn">Добавить запись</a>
                <a href="#x" class="overlay" id="win1"></a>
                <div class="popup">
                    <form class="form" method="post" action="${pageContext.request.contextPath}/clientTable">
                        <label class="label" for="firstName">Имя:</label>
                        <input class="input" type="text" id="firstName" name="firstName" required>

                        <label class="label" for="lastName">Фамилия:</label>
                        <input class="input" type="text" id="lastName" name="lastName" required>

                        <label class="label" for="birthday">Дата рождения:</label>
                        <input class="input" type="date" id="birthday" name="birthday" required>

                        <label class="label" for="series">Серия паспорта:</label>
                        <input class="input" type="text" id="series" name="series" pattern="^\d{4}$" placeholder="xxxx" required>

                        <label class="label" for="numberOfPassport">Номер паспорта:</label>
                        <input class="input" type="text" id="numberOfPassport" name="numberOfPassport" pattern="^\d{6}$" placeholder="xxxxxx" required>

                        <label class="label" for="numberOfContact">Номер телефона:</label>
                        <input class="input" type="text" id="numberOfContact" name="numberOfContact" pattern="^\+7\d{10}$" placeholder="+7xxxxxxxxxx" required>

                        <label class="label" for="type">Тип телефона:</label>
                        <select id="type" name="type">
                            <option value="mobile">Мобильный</option>
                            <option value="home">Домашний</option>
                            <option value="work">Рабочий</option>
                        </select>
                        <input class="input" type="submit" value="Отправить">
                    </form>
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
                        <c:forEach var="client" items="${requestScope.clientTable}">
                            <tr>
                                <td>${client.firstName}</td>
                                <td>${client.lastName}</td>
                                <td>${client.passport.series}</td>
                                <td>${client.passport.numberOfPassport}</td>
                                <td>${client.passport.birthday}</td>
                                <td>${client.contact.numberOfContact}</td>
                                <td>${client.contact.type}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="dropdown">
                    <button class="dropbtn">Сортировать по</button>
                    <div class="dropdown-content">
                        <c:forEach var="column" items="${requestScope.goodColumnNames}">
                            <a href="${pageContext.request.contextPath}/clientTable?good=good&orderBy=${column}">${column}</a>
                        </c:forEach>
                    </div>
                </div>
                <form class="dropdown" method="get" action="${pageContext.request.contextPath}/clientTable">
                    <a href="?good" class="dropbtn" style="width: 220px; display: inline-block">Отобразить информацию с ID</a>
                </form>
            </div>
        </c:if>
    </div>
</body>
</html>
