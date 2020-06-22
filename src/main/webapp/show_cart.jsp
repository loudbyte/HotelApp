<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="calculatePrice" class="com.epam.hotel.businesslogic.CalculatePrice"/>
<jsp:useBean id="roomDAO" class="com.epam.hotel.dao.impl.RoomDAOImpl"/>
<jsp:useBean id="packageDAO" class="com.epam.hotel.dao.impl.FacilityPackageDAOImpl"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="cart"/></title>
    <jsp:include page="style.jsp"/>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <div class="row">
        <div class="col-9">
            <c:choose>
                <c:when test="${sessionScope.cart.orderRoomDetailMap.size() != 0}">
                    <table class="table table-sm">
                        <thead>
                        <tr>
                            <th scope="col"><fmt:message key="room_number"/></th>
                            <th scope="col"><fmt:message key="facility_package"/></th>
                            <th scope="col"><fmt:message key="start_date"/></th>
                            <th scope="col"><fmt:message key="end_date"/></th>
                            <th scope="col"><fmt:message key="price"/></th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="orderDetail" items="${sessionScope.cart.orderRoomDetailMap.values()}">
                            <tr>
                                <td>${roomDAO.getOneById(orderDetail.roomId).roomNumber}</td>
                                <td>${packageDAO.getFacilityPackageNameMapByFacilityPackageId(orderDetail.facilityPackageId).get(sessionScope.locale)}</td>
                                <td>${orderDetail.startDate}</td>
                                <td>${orderDetail.endDate}</td>
                                <td>${calculatePrice.calculateOrderRoomDetail(orderDetail)}$</td>
                                <td>
                                     <form action="${pageContext.request.contextPath}/controller/delete_item" method="post">
                                         <input type="hidden" name="order_detail_id" value="${orderDetail.id}">
                                             <button type="submit"
                                                 class="btn btn-sm  btn-danger"><fmt:message key="delete"/></button>
                                     </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                        <h2><fmt:message key="empty_cart"/></h2>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col">
            <p>
            <div class="btn-group" role="group" aria-label="Basic example">
                <a href="${pageContext.request.contextPath}/controller/show_rooms" type="button" class="btn btn-secondary"><fmt:message key="show_rooms"/></a>
            </div>
            </p>
            <c:if test="${sessionScope.role == 'ADMIN'}">
                <jsp:include page="admin_right_panel.jsp" />
            </c:if>
            <c:if test="${sessionScope.cart.orderRoomDetailMap.size() != 0}">
                <form action="${pageContext.request.contextPath}/controller/create_order" method="post">
                    <button type="submit" class="btn btn-lg  btn-info"><fmt:message key="create_order"/></button>
                </form>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>