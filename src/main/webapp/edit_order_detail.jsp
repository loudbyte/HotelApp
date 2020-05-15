<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="orderRoomFacilityDetailDAO" class="com.epam.hotel.dao.impl.OrderFacilityDetailDAOImpl"/>
<c:set var="packageList" value="${orderRoomFacilityDetailDAO.all}"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title>Edit order detail</title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <h3>Редактирование деталей заказа - ${requestScope.order_detail_id}</h3>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/edit_order_detail" method="post">
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="room_id">ID комнаты</label>
                        <input type="text" class="form-control" id="room_id" name="room_id" value="${requestScope.room_id}">
                    </div>
                    <div class="form-group col-md-5">
                        <label for="order_facility_detail_id">ID пакета услуг</label>
                        <input type="text" class="form-control" id="order_facility_detail_id" name="order_facility_detail_id" value="${requestScope.order_facility_detail_id}"
                               placeholder="<c:forEach var="packageItem" items="${packageList}">${packageItem.id} - ${packageItem.facilityPackageName}. </c:forEach>">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="start_date">Дата начала аренды</label>
                        <input type="text" class="form-control" id="start_date" name="start_date" value="${requestScope.start_date}">
                    </div>
                    <div class="form-group col-md-5">
                        <label for="end_date">Дата конца аренды</label>
                        <input type="text" class="form-control" id="end_date" name="end_date" value="${requestScope.end_date}">
                    </div>
                </div>
                <br/>
                <input type="hidden" name="order_main_id" value="${requestScope.order_main_id}" >
                <input type="hidden" name="order_detail_id" value="${requestScope.order_detail_id}" >
                <div class="form-row">
                    <button type="submit" style="width: 100px" class="btn btn-primary">Сохранить</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
