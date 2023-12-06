<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Сотрудники</title>
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
                    <th>ID сотрудника</th>
                    <th>ID департамента</th>
                    <th>ID салона</th>
                    <th>Имя</th>
                    <th>Фамилия</th>
                    <th>ID должности</th>
                    <th>ID паспорта</th>
                    <th>ID контакта</th>
                    <th>Логин</th>
                    <th>Пароль</th>
                    <th>ID роли</th>
                </tr>
                </thead>
            </table>
            <div class="scroll-table-body">
                <table class="table1">
                    <tbody>
                    <c:forEach var="employee" items="${requestScope.employeeTable}">
                        <tr>
                            <td>${employee.employeeId}</td>
                            <td>${employee.department.departmentId}</td>
                            <td>${employee.salon.salonId}</td>
                            <td>${employee.firstName}</td>
                            <td>${employee.lastName}</td>
                            <td>${employee.post.postId}</td>
                            <td>${employee.passport.passportId}</td>
                            <td>${employee.contact.contactId}</td>
                            <td>${employee.login}</td>
                            <td>${employee.password}</td>
                            <td>${employee.role.roleId}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="dropdown">
                <button class="dropbtn">Сортировать по</button>
                <div class="dropdown-content">
                    <div class="scroll-table-body">
                        <a href="${pageContext.request.contextPath}/employeeTable?orderBy=employee_id">ID сотрудника</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?orderBy=department_id">ID департамента</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?orderBy=salon_id">ID салона</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?orderBy=first_name">Имя</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?orderBy=last_name">Фамилия</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?orderBy=post_id">ID должности</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?orderBy=passport_id">ID паспорта</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?orderBy=contact_id">ID контакта</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?orderBy=login">Логин</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?orderBy=password">Пароль</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?orderBy=role_id">ID роли</a>
                    </div>
                </div>
            </div>
            <c:if test="${sessionScope.user.role.roleId == 1 || sessionScope.user.role.roleId == 2}">
                <a href="#win1" class="dropbtn">Добавить запись</a>
                <a href="#x" class="overlay" id="win1"></a>
                <div class="popup">
                    <div class="scroll scroll1">
                        <form class="form" method="post" action="${pageContext.request.contextPath}/employeeTable">
                            <label class="label" for="firstName">Имя сотрудника:</label>
                            <input class="input" value="${param.firstName}" type="text" id="firstName" name="firstName" required>

                            <label class="label" for="lastName">Фамилия сотрудника:</label>
                            <input class="input" value="${param.lastName}" type="text" id="lastName" name="lastName" required>

                            <label class="label" for="series">Серия паспорта:</label>
                            <input class="input" value="${param.series}" type="text" id="series" name="series" pattern="\d{4}" placeholder="xxxx" required>

                            <label class="label" for="numberOfPassport">Номер паспорта:</label>
                            <input class="input" value="${param.numberOfPassport}" type="text" id="numberOfPassport" name="numberOfPassport" pattern="\d{6}" placeholder="xxxxxx" required>

                            <label class="label" for="birthday">Дата рождения:</label>
                            <input class="input" value="${param.birthday}" type="date" id="birthday" name="birthday" required>

                            <label class="label" for="issueDate">Дата выдачи паспорта:</label>
                            <input class="input" value="${param.issueDate}" type="date" id="issueDate" name="issueDate" required>

                            <label class="label" for="placeCode">Код подразделения:</label>
                            <input class="input" value="${param.placeCode}" type="text" id="placeCode" name="placeCode" pattern="^\d{3}\-\d{3}$" placeholder="xxx-xxx" required>

                            <label class="label" for="personalNumber">Личный номер телефона:</label>
                            <input class="input" value="${param.personalNumber}" type="text" id="personalNumber" name="personalNumber" placeholder="+7xxxxxxxxxx" pattern="^\+7\d{10}$"
                                   required>

                            <label class="label" for="workNumber">Рабочий номер телефона:</label>
                            <input class="input" value="${param.workNumber}" type="text" id="workNumber" name="workNumber" placeholder="+7xxxxxxxxxx" pattern="^\+7\d{10}$" required>

                            <label class="label" for="login">Логин:</label>
                            <input class="input" value="${param.login}" type="text" id="login" name="login" required>

                            <label class="label" for="password">Пароль:</label>
                            <input class="input" value="${param.password}" type="text" id="password" name="password" required>

                            <div class="dropdown">
                                <label class="label" for="department">Департамент:</label>
                                <input class="input-box" value="${param.department}" type="text" id="department" list="dropdown-options1" name="department" required>
                                <datalist id="dropdown-options1">
                                    <c:forEach var="department" items="${sessionScope.departments}">
                                        <option value="${department}">${department}</option>
                                    </c:forEach>
                                </datalist>
                            </div>
                            <br>
                            <div class="dropdown">
                                <label class="label" for="salon">Салон:</label>
                                <input class="input-box" value="${param.salon}" type="text" id="salon" list="dropdown-options2" name="salon" required>
                                <datalist id="dropdown-options2">
                                    <c:forEach var="salon" items="${sessionScope.salons}">
                                        <option value="${salon}">${salon}</option>
                                    </c:forEach>
                                </datalist>
                            </div>
                            <br>
                            <div class="dropdown">
                                <label class="label" for="post">Должность:</label>
                                <input class="input-box" value="${param.post}" type="text" id="post" list="dropdown-options3" name="post" required>
                                <datalist id="dropdown-options3">
                                    <c:forEach var="post" items="${sessionScope.posts}">
                                        <option value="${post}">${post}</option>
                                    </c:forEach>
                                </datalist>
                            </div>
                            <br>
                            <div class="dropdown">
                                <label class="label" for="role">Роль:</label>
                                <input class="input-box" value="${param.role}" type="text" id="role" list="dropdown-options4" name="role" required>
                                <datalist id="dropdown-options4">
                                    <c:forEach var="role" items="${sessionScope.roles}">
                                        <option value="${role}">${role}</option>
                                    </c:forEach>
                                </datalist>
                            </div>

                            <input class="input" type="submit" value="Отправить">
                        </form>
                    </div>
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
            <div class="big-table">
                <div class="scroll-table-body" style="overflow-x: hidden">
                    <table class="table1">
                        <thead>
                        <tr>
                            <th>Имя</th>
                            <th>Фамилия</th>
                            <th>Департамент</th>
                            <th>Салон</th>
                            <th>Должность</th>
                            <th>Серия паспорта</th>
                            <th>Номер паспорта</th>
                            <th>Дата рождения</th>
                            <th>Дата выдачи</th>
                            <th>Код подразделения</th>
                            <th>Рабочий номер</th>
                            <th>Личный номер</th>
                            <th>Логин</th>
                            <th>Пароль</th>
                            <th>Роль</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="employee" items="${requestScope.employeeTable}">
                            <tr>
                                <td>${employee.firstName}</td>
                                <td>${employee.lastName}</td>
                                <td>${employee.department.departmentName}</td>
                                <td>${employee.salon.address}</td>
                                <td>${employee.post.postName}</td>
                                <td>${employee.passport.series}</td>
                                <td>${employee.passport.number}</td>
                                <td>${employee.passport.birthday}</td>
                                <td>${employee.passport.issueDate}</td>
                                <td>${employee.passport.placeCode}</td>
                                <td>${employee.contact.workNumber}</td>
                                <td>${employee.contact.personalNumber}</td>
                                <td>${employee.login}</td>
                                <td>${employee.password}</td>
                                <td>${employee.role.roleName}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="dropdown">
                <button class="dropbtn">Сортировать по</button>
                <div class="dropdown-content">
                    <div class="scroll-table-body">
                        <a href="${pageContext.request.contextPath}/employeeTable?good=good&orderBy=first_name">Имя</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?good=good&orderBy=last_name">Фамилия</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?good=good&orderBy=department_name">Департамент</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?good=good&orderBy=address">Адрес салона</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?good=good&orderBy=post_name">Должность</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?good=good&orderBy=series">Серия паспорта</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?good=good&orderBy=birthday">Дата рождения</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?good=good&orderBy=issue_date">Дата выдачи паспорта</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?good=good&orderBy=place_code">Код подразделения</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?good=good&orderBy=work_number">Рабочий номер</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?good=good&orderBy=personal_number">Личный номер</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?good=good&orderBy=login">Логин</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?good=good&orderBy=password">Пароль</a>
                        <a href="${pageContext.request.contextPath}/employeeTable?good=good&orderBy=role_name">Роль</a>
                    </div>
                </div>
            </div>
            <a href="#win2" class="dropbtn">Фильтр</a>
            <a href="#x" class="overlay" id="win2"></a>
            <div class="popup">
                <form class="form" method="post" action="${pageContext.request.contextPath}/employeeTable?filter=filter&good=good">
                    <label class="label" for="firstNameFilter">Имя сотрудника:</label>
                    <input class="input" type="text" id="firstNameFilter" name="firstNameFilter">

                    <label class="label" for="lastNameFilter">Фамилия сотрудника:</label>
                    <input class="input" type="text" id="lastNameFilter" name="lastNameFilter">

                    <div class="dropdown">
                        <label class="label" for="department">Департамент:</label>
                        <input class="input-box" type="text" id="departmentFilter" list="dropdown-options6" name="departmentFilter">
                        <datalist id="dropdown-options6">
                            <c:forEach var="department" items="${sessionScope.departments}">
                                <option value="${department}">${department}</option>
                            </c:forEach>
                        </datalist>
                    </div>
                    <br>
                    <div class="dropdown">
                        <label class="label" for="salonFilter">Салон:</label>
                        <input class="input-box" type="text" id="salonFilter" list="dropdown-options7" name="salonFilter">
                        <datalist id="dropdown-options7">
                            <c:forEach var="salon" items="${sessionScope.salons}">
                                <option value="${salon}">${salon}</option>
                            </c:forEach>
                        </datalist>
                    </div>
                    <br>
                    <div class="dropdown">
                        <label class="label" for="postFilter">Должность:</label>
                        <input class="input-box" type="text" id="postFilter" list="dropdown-options7" name="postFilter">
                        <datalist id="dropdown-options8">
                            <c:forEach var="post" items="${sessionScope.posts}">
                                <option value="${post}">${post}</option>
                            </c:forEach>
                        </datalist>
                    </div>
                    <br>

                    <label class="label" for="seriesFilter">Серия паспорта:</label>
                    <input class="input" type="text" id="seriesFilter" name="seriesFilter">

                    <label class="label" for="numberOfPassportFilter">Номер паспорта:</label>
                    <input class="input" type="text" id="numberOfPassportFilter" name="numberOfPassportFilter">

                    <label class="label" for="birthdayUpFilter">Дата рождения до:</label>
                    <input class="input" type="date" id="birthdayUpFilter" name="birthdayUpFilter">

                    <label class="label" for="birthdayDownFilter">Дата рождения после:</label>
                    <input class="input" type="date" id="birthdayDownFilter" name="birthdayDownFilter">

                    <input class="input" type="submit" value="Отправить">
                </form>
                <a class="close" title="Закрыть" href="#close"></a>
            </div>
            <c:if test="${sessionScope.user.role.roleId == 1 || sessionScope.user.role.roleId == 2}">
                <a href="#win3" class="dropbtn">Удалить запись</a>
                <a href="#x" class="overlay" id="win3"></a>
                <div class="popup">
                    <form class="form" method="post" action="${pageContext.request.contextPath}/employeeTable?delete=delete&good=good">
                        <label class="label" for="loginDelete">Логин сотрудника:</label>
                        <input class="input" value="${param.loginDelete}" type="text" id="loginDelete" name="loginDelete" required>

                        <input class="input" type="submit" value="Отправить">
                    </form>
                    <a class="close" title="Закрыть" href="#close"></a>
                </div>
            </c:if>
            <form class="dropdown" method="get" action="${pageContext.request.contextPath}/clientTable">
                <a href="?good" class="dropbtn" style="width: 220px; display: inline-block">Отобразить информацию c ID</a>
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
