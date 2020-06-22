<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="imageEncoder" class="com.epam.hotel.util.ImageEncoder"/>
<jsp:useBean id="roomDAO" class="com.epam.hotel.dao.impl.RoomDAOImpl"/>
<c:set var="room" value="${roomDAO.getOneById(param.get('room_id'))}"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="show_photo"/></title>
</head>
<body>
<div class="container marketing">

    <jsp:include page="header.jsp"/>

        <c:if test="${room.availability}">
            <div class="row">
                <div class="col-md-7">
                    <h2 class="featurette-heading">
                        <c:choose>
                            <c:when test="${room.roomClass == 1}"><fmt:message key="deluxe"/></c:when>
                            <c:when test="${room.roomClass == 2}"><fmt:message key="suite"/></c:when>
                            <c:when test="${room.roomClass == 3}"><fmt:message key="standard"/></c:when>
                        </c:choose>
                        <fmt:message key="room"/>
                    </h2>
                    <p class="lead">
                        <fmt:message key="capacity.people"/>: ${room.capacity}</br>
                        <fmt:message key="room_price"/>: ${room.price}$</br>
                        <fmt:message key="room_number"/>: №${room.roomNumber}</br>
                    </p>
                    <form action="${pageContext.request.contextPath}/controller/create_item_button" method="post">
                        <input type="hidden" name="room_id" value="${room.id}">
                        <div class="button-bottom"><p><button type="submit" class="btn btn-info" role="button"><fmt:message key="reservation"/> &raquo;</button></p></div>
                    </form>
                </div>
            </div>
            <div class="row">
                <c:if test="${room.imageList.size() > 0 && room.imageList.get(0).image != null}">
                    <c:forEach var="image" items="${room.imageList}">
                        <img src="${imageEncoder.encode(image.image)}" height="400" width="400" hspace="10" vspace="10"/>
                    </c:forEach>
                </c:if>
            </div>
        </c:if>
</div>
</body>
</html>