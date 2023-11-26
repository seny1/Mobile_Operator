<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Личный кабинет</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<%@include file="headerJSP.jsp" %>
<div class="account-container">
    <h1>Личный кабинет</h1>
    <div class="profile">
        <h2>Информация о пользователе</h2>
        <div class="profile-info">
            <p><span class="label">Имя:</span> ${sessionScope.user.firstName}</p>
            <p><span class="label">Фамилия:</span> ${sessionScope.user.lastName}</p>
            <p><span class="label">Паспортные данные:</span>
                <ul>
                    <li>Серия: ${sessionScope.user.passport.series} </li>
                    <li>Номер: ${sessionScope.user.passport.number} </li>
                    <li>Дата рождения: ${sessionScope.user.passport.birthday} </li>
                    <li>Дата выдачи: ${sessionScope.user.passport.issueDate} </li>
                    <li>Код подразделения: ${sessionScope.user.passport.placeCode}</li>
                </ul>
            </p>
            <p><span class="label">Департамент:</span> ${sessionScope.user.department.departmentName}</p>
            <p><span class="label">Должность:</span> ${sessionScope.user.post.postName}</p>
            <p><span class="label">Контакты:</span>
                <ul>
                    <li>Личный номер: <span>${sessionScope.user.contact.personalNumber}</span></li>
                    <li>Рабочий номер: <span>${sessionScope.user.contact.workNumber}</span></li>
                </ul>
            </p>
        </div>
    </div>
</div>
</body>
</html>
