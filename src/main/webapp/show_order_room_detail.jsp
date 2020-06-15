<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="calculatePrice" class="com.epam.hotel.businesslogic.CalculatePrice"/>
<jsp:useBean id="roomDAO" class="com.epam.hotel.dao.impl.RoomDAOImpl"/>
<jsp:useBean id="orderFacilityDetailDAO" class="com.epam.hotel.dao.impl.FacilityPackageDAOImpl"/>
<jsp:useBean id="orderMainDAO" class="com.epam.hotel.dao.impl.OrderMainDAOImpl"/>
<c:set var="orderMain" value="${orderMainDAO.getOneById(requestScope.order_main_id)}"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="language"/>

<jsp:useBean id="orderRoomDetailDAO" class="com.epam.hotel.dao.impl.OrderRoomDetailDAOImpl"/>
<c:set var="orderRoomDetailList" value="${orderRoomDetailDAO.getAllByOrderId(requestScope.order_main_id)}"/>
<html>
<head>
    <title>Order detail</title>
    <jsp:include page="style.jsp"/>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <div class="row">
        <div class="col-9">
            <table class="table table-sm">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col"><fmt:message key="room.number"/></th>
                    <th scope="col"><fmt:message key="package"/></th>
                    <th scope="col"><fmt:message key="start.date"/></th>
                    <th scope="col"><fmt:message key="end.date"/></th>
                    <th scope="col"><fmt:message key="price"/></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="orderRoomDetail" items="${orderRoomDetailList}">
                        <tr>
                            <td>${orderRoomDetail.id}</td>
                            <td>${roomDAO.getOneById(orderRoomDetail.roomId).roomNumber}</td>
                            <td>${orderFacilityDetailDAO.getOneById(orderRoomDetail.facilityPackageId).facilityPackageName}</td>
                            <td>${orderRoomDetail.startDate}</td>
                            <td>${orderRoomDetail.endDate}</td>
                            <td>${calculatePrice.calculateOrderRoomDetail(orderRoomDetail)}$</td>
                            <td>
                                <c:choose>
                                    <c:when test="${sessionScope.role == 'ADMIN'}">
                                        <form action="${pageContext.request.contextPath}/controller/edit_order_room_detail_button" method="post">
                                            <input type="hidden" name="order_room_detail_id" value="${orderRoomDetail.id}">
                                            <input type="hidden" name="start_date" value="${orderRoomDetail.startDate}">
                                            <input type="hidden" name="end_date" value="${orderRoomDetail.endDate}">
                                            <input type="hidden" name="room_id" value="${orderRoomDetail.roomId}">
                                            <input type="hidden" name="order_main_id" value="${orderRoomDetail.orderMainId}">
                                            <input type="hidden" name="facility_package_id" value="${orderRoomDetail.facilityPackageId}">
                                            <button type="submit"
                                                    class="btn btn-sm  btn-warning"><fmt:message key="edit"/></button>
                                        </form>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${orderMain.statusId == 1}">
                                        <form action="${pageContext.request.contextPath}/controller/delete_order_room_detail" method="post">
                                            <input type="hidden" name="order_room_detail_id" value="${orderRoomDetail.id}">
                                            <input type="hidden" name="order_main_id" value="${requestScope.order_main_id}">
                                            <input type="hidden" name="room_id" value="${orderRoomDetail.roomId}">
                                            <button type="submit"
                                                    class="btn btn-sm  btn-danger"><fmt:message key="delete"/></button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <button title='<fmt:message key="cannot.delete.order"/>' type="button" class="btn btn-sm btn-dark"><fmt:message key="delete"/></button>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col">
            <c:if test="${sessionScope.role == 'ADMIN'}">
                <jsp:include page="admin_right_panel.jsp" />
            </c:if>
        </div>
    </div>
</div>
</body>
</html>