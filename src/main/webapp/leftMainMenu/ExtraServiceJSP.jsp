<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Услуги</title>
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
        <c:forEach var="extraService" items="${requestScope.extraServiceTable}">
          <tr>
            <td>${extraService.serviceId}</td>
            <td>${extraService.serviceDescription}</td>
            <td>${extraService.price}</td>
            <td>${extraService.serviceName}</td>
            <td>${extraService.category.categoryId}</td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>
</body>
</html>
