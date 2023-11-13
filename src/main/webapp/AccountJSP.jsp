<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Личный кабинет</title>
</head>
<body>
<%@include file="headerJSP.jsp"%>
    <div>
        <div class="background-righter" style="height: 300px; width: 100px"></div>
        <div class="info-container">
            <h1>Основная информация</h1>
            <div>Имя: <span>${sessionScope.user.firstName}</span></div>
            <div>Фамилия: <span>${sessionScope.user.lastName}</span></div>
            <div>Паспорт:
                <ul>
                    <li>Серия: ${sessionScope.user.passport.series} </li>
                    <li>Номер: ${sessionScope.user.passport.number} </li>
                    <li>Дата рождения: ${sessionScope.user.passport.birthday} </li>
                    <li>Дата выдачи: ${sessionScope.user.passport.issueDate} </li>
                    <li>Код подразделения: ${sessionScope.user.passport.placeCode}</li>
                </ul>
            </div>
            <div>Департамент: <span>${sessionScope.user.department.departmentName}</span></div>
            <div>Должность: <span>${sessionScope.user.post.postName}</span></div>
            <div>Контакты:
                <ul>
                    <li>Личный номер: <span>${sessionScope.user.contact.personalNumber}</span></li>
                    <li>Рабочий номер: <span>${sessionScope.user.contact.workNumber}</span></li>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>
