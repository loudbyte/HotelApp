<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="facilityPackageDAO" class="com.epam.hotel.dao.impl.FacilityPackageDAOImpl"/>
<c:set var="packageList" value="${facilityPackageDAO.all}"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="edit"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <h3><fmt:message key="edit"/> <fmt:message key="order.number"/> - ${requestScope.order_room_detail_id}</h3>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/edit_order_room_detail" method="post">
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="room_id"><fmt:message key="room"/> ID</label>
                        <input type="text" class="form-control" id="room_id" name="room_id" value="${requestScope.room_id}">
                    </div>
                    <div class="form-group col-md-5">
                        <label for="facility_package"><fmt:message key="package"/> ID</label>
                        <input type="text" class="form-control" id="facility_package" name="facility_package_id" value="${requestScope.facility_package_id}"
                               placeholder="<c:forEach var="packageItem" items="${packageList}">${packageItem.id} - ${packageItem.facilityPackageName}. </c:forEach>">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="start_date"><fmt:message key="start.date"/></label>
                        <input type="text" class="form-control" id="start_date" name="start_date" value="${requestScope.start_date}">
                    </div>
                    <div class="form-group col-md-5">
                        <label for="end_date"><fmt:message key="end.date"/></label>
                        <input type="text" class="form-control" id="end_date" name="end_date" value="${requestScope.end_date}">
                    </div>
                </div>
                <br/>
                <input type="hidden" name="order_main_id" value="${requestScope.order_main_id}" >
                <input type="hidden" name="order_room_detail_id" value="${requestScope.order_room_detail_id}" >
                <div class="form-row">
                    <button type="submit" style="width: 100px" class="btn btn-primary"><fmt:message key="save"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>