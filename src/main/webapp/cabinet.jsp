<%--suppress Annotator --%>
<%--suppress Annotator --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="title.cabinet"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <div class="row">

        <div class="col-9">
            <h2><fmt:message key="page.user_info"/></h2>
            <div class="table-responsive">
            <table class="table">
                <tr>
                    <td><fmt:message key="page.cabinet"/></td>
                    <td>${sessionScope.person.firstName} </td>
                </tr>
                <tr>
                    <td><fmt:message key="page.email"/></td>
                    <td>${sessionScope.person.email}</td>
                </tr>
            </table>
            </div>
        </div>
<%--suppress Annotator --%>
        <div class="col">
            <c:choose>
                <c:when test="${sessionScope.role == 'ADMIN'}">
                    <jsp:include page="admin_right_panel.jsp"/>
                    <p>
                    <div class="btn-group" role="group" aria-label="Basic example">
                        <a href="${pageContext.request.contextPath}/controller/show_my_orders" type="button"
                           class="btn btn-secondary"><fmt:message key="page.my_orders"/> </a>
                    </div>
                    </p>
                </c:when>
                <c:otherwise>
                    <p>
                    <div class="btn-group" role="group" aria-label="Basic example">
                        <a href="${pageContext.request.contextPath}/controller/show_my_orders" type="button"
                           class="btn btn-secondary"><fmt:message key="page.my_orders"/> </a>
                    </div>
                    </p>
                    <p>
                    <div class="btn-group" role="group" aria-label="Basic example">
                        <a href="${pageContext.request.contextPath}/controller/show_rooms" type="button" class="btn btn-secondary"><fmt:message key="page.show_rooms"/></a>
                    </div>
                    </p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>