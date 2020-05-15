<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
</head>
<body>
<p>
<div class="btn-group-vertical " role="group" aria-label="Basic example">
    <a href="${requestScope.pageContext.request.contextPath}/controller/show_person_list" type="button"
       class="btn btn-secondary">Список пользователей</a>

    <a href="${requestScope.pageContext.request.contextPath}/controller/show_room_admin_list" type="button"
        class="btn btn-secondary">Список комнат</a>

    <a href="${requestScope.pageContext.request.contextPath}/controller/show_order_admin_list" type="button"
        class="btn btn-secondary">Список заказов</a>
    <a href="${requestScope.pageContext.request.contextPath}/show_package_admin_list.jsp" type="button"
        class="btn btn-secondary">Пакеты услуг</a>
    <a href="${requestScope.pageContext.request.contextPath}/show_facility_admin_list.jsp" type="button"
        class="btn btn-secondary">Список услуг</a>
</div>
</p>
</body>

</html>
