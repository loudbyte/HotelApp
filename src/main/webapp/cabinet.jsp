<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title>Cabinet</title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <div class="row">
        <div class="col">

        </div>
        <div class="col-6">
            <h3> Кабинет ${sessionScope.person.firstName} ${sessionScope.person.email} </h3>
        </div>
        <div class="col">
            <c:choose>
                <c:when test="${sessionScope.role == 'ADMIN'}">
                    <jsp:include page="admin_right_panel.jsp"/>
                </c:when>
                <c:otherwise>
                    <p>
                    <div class="btn-group" role="group" aria-label="Basic example">
                        <a href="${requestScope.pageContext.request.contextPath}/controller/show_my_orders" type="button"
                           class="btn btn-secondary">Мои заказы</a>
                    </div>
                    </p>
                    <p>
                    <div class="btn-group" role="group" aria-label="Basic example">
                        <a href="${pageContext.request.contextPath}/controller/show_rooms" type="button" class="btn btn-secondary">Посмотреть комнаты</a>
                    </div>
                    </p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>