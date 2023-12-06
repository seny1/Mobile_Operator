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
                        <th>ID клиента</th>
                        <th>ID паспорта</th>
                        <th>Имя</th>
                        <th>Фамилия</th>
                        <th>ID контакта</th>
                        <th>Оставшиеся минуты</th>
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
                        <a href="${pageContext.request.contextPath}/clientTable?orderBy=client_id">ID клиента</a>
                        <a href="${pageContext.request.contextPath}/clientTable?orderBy=passport_id">ID паспорта</a>
                        <a href="${pageContext.request.contextPath}/clientTable?orderBy=first_name">Имя</a>
                        <a href="${pageContext.request.contextPath}/clientTable?orderBy=last_name">Фамилия</a>
                        <a href="${pageContext.request.contextPath}/clientTable?orderBy=contact_id">ID контакта</a>
                        <a href="${pageContext.request.contextPath}/clientTable?orderBy=remain_minutes">Оставшиеся минуты</a>
                    </div>
                </div>
                <c:if test="${sessionScope.user.role.roleId == 1}">
                    <a href="#win1" class="dropbtn">Добавить запись</a>
                    <a href="#x" class="overlay" id="win1"></a>
                    <div class="popup">
                        <form class="form" method="post" action="${pageContext.request.contextPath}/clientTable">
                            <label class="label" for="firstName">Имя:</label>
                            <input class="input" value="${param.firstName}" type="text" id="firstName" name="firstName" required>

                            <label class="label" for="lastName">Фамилия:</label>
                            <input class="input" value="${param.lastName}" type="text" id="lastName" name="lastName" required>

                            <label class="label" for="birthday">Дата рождения:</label>
                            <input class="input" value="${param.birthday}" type="date" id="birthday" name="birthday" required>

                            <label class="label" for="series">Серия паспорта:</label>
                            <input class="input" value="${param.series}" type="text" id="series" name="series" pattern="^\d{4}$" placeholder="xxxx" required>

                            <label class="label" for="numberOfPassport">Номер паспорта:</label>
                            <input class="input" value="${param.numberOfPassport}" type="text" id="numberOfPassport" name="numberOfPassport" pattern="^\d{6}$" placeholder="xxxxxx" required>

                            <label class="label" for="numberOfContact">Номер телефона:</label>
                            <input class="input" value="${param.numberOfContact}" type="text" id="numberOfContact" name="numberOfContact" pattern="^\+7\d{10}$" placeholder="+7xxxxxxxxxx" required>

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
                </c:if>

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
                        <th>Имя</th>
                        <th>Фамилия</th>
                        <th>Серия паспорта</th>
                        <th>Номер паспорта</th>
                        <th>Дата рождения</th>
                        <th>Номер телефона</th>
                        <th>Тип телефона</th>
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
                        <a href="${pageContext.request.contextPath}/clientTable?good=good&orderBy=first_name">Имя</a>
                        <a href="${pageContext.request.contextPath}/clientTable?good=good&orderBy=last_name">Фамилия</a>
                        <a href="${pageContext.request.contextPath}/clientTable?good=good&orderBy=series">Серия паспорта</a>
                        <a href="${pageContext.request.contextPath}/clientTable?good=good&orderBy=number_of_passport">Номер паспорта</a>
                        <a href="${pageContext.request.contextPath}/clientTable?good=good&orderBy=birthday">Дата рождения</a>
                        <a href="${pageContext.request.contextPath}/clientTable?good=good&orderBy=number_of_contact">Номер телефона</a>
                        <a href="${pageContext.request.contextPath}/clientTable?good=good&orderBy=type">Тип телефона</a>
                    </div>
                </div>
                <a href="#win2" class="dropbtn">Фильтр</a>
                <a href="#x" class="overlay" id="win2"></a>
                <div class="popup">
                    <form class="form" method="post" action="${pageContext.request.contextPath}/clientTable?filter=filter&good=good">
                        <label class="label" for="firstName">Имя:</label>
                        <input class="input" type="text" id="firstNameFilter" name="firstNameFilter">

                        <label class="label" for="lastName">Фамилия:</label>
                        <input class="input" type="text" id="lastNameFilter" name="lastNameFilter">

                        <label class="label" for="birthday">Кто родился после:</label>
                        <input class="input" type="date" id="birthdayUpFilter" name="birthdayUpFilter">

                        <label class="label" for="birthday">Кто родился до:</label>
                        <input class="input" type="date" id="birthdayDownFilter" name="birthdayDownFilter">

                        <label class="label" for="series">Серия паспорта:</label>
                        <input class="input" type="text" id="seriesFilter" name="seriesFilter">

                        <label class="label" for="numberOfPassport">Номер паспорта:</label>
                        <input class="input" type="text" id="numberOfPassportFilter" name="numberOfPassportFilter">

                        <label class="label" for="numberOfContact">Номер телефона:</label>
                        <input class="input" type="text" id="numberOfContactFilter" name="numberOfContactFilter">

                        <label class="label" for="type">Тип телефона:</label>
                        <input class="input" type="text" id="typeFilter" name="typeFilter">

                        <input class="input" type="submit" value="Отправить">
                    </form>
                    <a class="close" title="Закрыть" href="#close"></a>
                </div>
                <c:if test="${sessionScope.user.role.roleId == 1 || sessionScope.user.role.roleId == 2}">
                    <a href="#win3" class="dropbtn">Удалить запись</a>
                    <a href="#x" class="overlay" id="win3"></a>
                    <div class="popup">
                        <form class="form" method="post" action="${pageContext.request.contextPath}/clientTable?delete=delete&good=good">
                            <label class="label" for="seriesDelete">Серия паспорта:</label>
                            <input class="input" value="${param.seriesDelete}" type="text" id="seriesDelete" name="seriesDelete" required>

                            <label class="label" for="numberOfPassportDelete">Номер паспорта:</label>
                            <input class="input" value="${param.numberOfPassportDelete}" type="text" id="numberOfPassportDelete" name="numberOfPassportDelete" required>

                            <input class="input" type="submit" value="Отправить">
                        </form>
                        <a class="close" title="Закрыть" href="#close"></a>
                    </div>
                </c:if>
                <form class="dropdown" method="get" action="${pageContext.request.contextPath}/clientTable">
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
