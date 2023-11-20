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
                    <c:forEach var="column" items="${requestScope.columnNames}">
                        <a href="${pageContext.request.contextPath}/employeeTable?orderBy=${column}">${column}</a>
                    </c:forEach>
                </div>
            </div>
        </div>
        <a href="#win1" class="dropbtn">Добавить запись</a>
        <a href="#x" class="overlay" id="win1"></a>
        <div class="popup">
            <div class="scroll scroll1">
                <form class="form" method="post" action="${pageContext.request.contextPath}/employeeTable">
                    <label class="label" for="firstName">Имя сотрудника:</label>
                    <input class="input" type="text" id="firstName" name="firstName" required>

                    <label class="label" for="lastName">Фамилия сотрудника:</label>
                    <input class="input" type="text" id="lastName" name="lastName" required>

                    <label class="label" for="series">Серия паспорта:</label>
                    <input class="input" type="text" id="series" name="series" pattern="\d{4}" required>

                    <label class="label" for="numberOfPassport">Номер паспорта:</label>
                    <input class="input" type="text" id="numberOfPassport" name="numberOfPassport" pattern="\d{6}"
                           required>

                    <label class="label" for="birthday">Дата рождения:</label>
                    <input class="input" type="date" id="birthday" name="birthday" required>

                    <label class="label" for="issueDate">Дата выдачи паспорта:</label>
                    <input class="input" type="date" id="issueDate" name="issueDate" required>

                    <label class="label" for="placeCode">Код подразделения:</label>
                    <input class="input" type="text" id="placeCode" name="placeCode" pattern="^\d{3}\-\d{3}$" required>

                    <label class="label" for="personalNumber">Личный номер телефона:</label>
                    <input class="input" type="text" id="personalNumber" name="personalNumber" pattern="^\+7\d{10}$"
                           required>

                    <label class="label" for="workNumber">Рабочий номер телефона:</label>
                    <input class="input" type="text" id="workNumber" name="workNumber" pattern="^\+7\d{10}$" required>

                    <label class="label" for="login">Логин:</label>
                    <input class="input" type="text" id="login" name="login" required>

                    <label class="label" for="password">Пароль:</label>
                    <input class="input" type="text" id="password" name="password" required>

                    <div class="dropdown">
                        <label class="label" for="department">Департамент:</label>
                        <input class="input-box" type="text" id="department" list="dropdown-options1" name="department"
                               required>
                        <datalist id="dropdown-options1">
                            <c:forEach var="department" items="${sessionScope.departments}">
                                <option value="${department}">${department}</option>
                            </c:forEach>
                        </datalist>
                    </div>
                    <br>
                    <div class="dropdown">
                        <label class="label" for="salon">Салон:</label>
                        <input class="input-box" type="text" id="salon" list="dropdown-options2" name="salon" required>
                        <datalist id="dropdown-options2">
                            <c:forEach var="salon" items="${sessionScope.salons}">
                                <option value="${salon}">${salon}</option>
                            </c:forEach>
                        </datalist>
                    </div>
                    <br>
                    <div class="dropdown">
                        <label class="label" for="post">Должность:</label>
                        <input class="input-box" type="text" id="post" list="dropdown-options3" name="post" required>
                        <datalist id="dropdown-options3">
                            <c:forEach var="post" items="${sessionScope.posts}">
                                <option value="${post}">${post}</option>
                            </c:forEach>
                        </datalist>
                    </div>
                    <br>
                    <div class="dropdown">
                        <label class="label" for="role">Роль:</label>
                        <input class="input-box" type="text" id="role" list="dropdown-options4" name="role" required>
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
    </div>
</div>
</body>
</html>
