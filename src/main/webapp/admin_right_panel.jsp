<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="language"/>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
</head>
<body>
<p>
<div class="btn-group-vertical " role="group" aria-label="Basic example">
    <a href="${requestScope.pageContext.request.contextPath}/controller/show_person_admin_list" type="button"
       class="btn btn-secondary"><fmt:message key="users"/></a>
    <a href="${requestScope.pageContext.request.contextPath}/controller/show_room_admin_list" type="button"
        class="btn btn-secondary"><fmt:message key="rooms"/></a>
    <a href="${requestScope.pageContext.request.contextPath}/controller/show_order_admin_list" type="button"
        class="btn btn-secondary"><fmt:message key="orders"/></a>
    <a href="${requestScope.pageContext.request.contextPath}/show_package_admin_list.jsp" type="button"
        class="btn btn-secondary"><fmt:message key="packages"/></a>
    <a href="${requestScope.pageContext.request.contextPath}/show_facility_admin_list.jsp" type="button"
        class="btn btn-secondary"><fmt:message key="facilities"/></a>
</div>
</p>
</body>
</html>