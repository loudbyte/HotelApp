<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="cabinet"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <div class="row">

        <div class="col-9">
            <h2><fmt:message key="user_info"/></h2>
            <div class="table-responsive">
            <table class="table">
                <tr>
                    <td><fmt:message key="cabinet"/></td>
                    <td>${sessionScope.person.firstName} </td>
                </tr>
                <tr>
                    <td><fmt:message key="email"/></td>
                    <td>${sessionScope.person.email}</td>
                </tr>
            </table>
            </div>
        </div>
        <div class="col">
            <c:choose>
                <c:when test="${sessionScope.role == 'ADMIN'}">
                    <jsp:include page="admin_right_panel.jsp"/>
                    <p>
                    <div class="btn-group" role="group" aria-label="Basic example">
                        <a href="${requestScope.pageContext.request.contextPath}/controller/show_my_orders" type="button"
                           class="btn btn-secondary"><fmt:message key="my_orders"/> </a>
                    </div>
                    </p>
                </c:when>
                <c:otherwise>
                    <p>
                    <div class="btn-group" role="group" aria-label="Basic example">
                        <a href="${requestScope.pageContext.request.contextPath}/controller/show_my_orders" type="button"
                           class="btn btn-secondary"><fmt:message key="my_orders"/> </a>
                    </div>
                    </p>
                    <p>
                    <div class="btn-group" role="group" aria-label="Basic example">
                        <a href="${pageContext.request.contextPath}/controller/show_rooms" type="button" class="btn btn-secondary"><fmt:message key="show_rooms"/></a>
                    </div>
                    </p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>