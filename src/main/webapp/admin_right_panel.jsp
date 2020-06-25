<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
</head>
<body>
<p>
<div class="btn-group-vertical " role="group" aria-label="Basic example">
    <a href="${pageContext.request.contextPath}/controller/show_person_admin_list" type="button"
       class="btn btn-secondary"><fmt:message key="page.users"/></a>
    <a href="${pageContext.request.contextPath}/controller/show_room_admin_list" type="button"
        class="btn btn-secondary"><fmt:message key="page.rooms"/></a>
    <a href="${pageContext.request.contextPath}/controller/show_order_admin_list" type="button"
        class="btn btn-secondary"><fmt:message key="page.orders"/></a>
    <a href="${pageContext.request.contextPath}/show_facility_package_admin_list.jsp" type="button"
        class="btn btn-secondary"><fmt:message key="page.facility_packages"/></a>
    <a href="${pageContext.request.contextPath}/show_facility_admin_list.jsp" type="button"
        class="btn btn-secondary"><fmt:message key="page.facilities"/></a>
    <a href="${pageContext.request.contextPath}/show_room_class_admin_list.jsp" type="button"
        class="btn btn-secondary"><fmt:message key="page.room_classes"/></a>
    <a href="${pageContext.request.contextPath}/show_order_status_admin_list.jsp" type="button"
        class="btn btn-secondary"><fmt:message key="page.order_statuses"/></a>
</div>
</p>
</body>
</html>