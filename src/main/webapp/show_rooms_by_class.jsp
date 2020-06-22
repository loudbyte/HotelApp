<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="imageEncoder" class="com.epam.hotel.util.ImageEncoder"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="show_rooms"/></title>
</head>
<body>
<div class="container">

    <jsp:include page="header.jsp"/>

    <c:forEach items="${requestScope.room_list}" var="room">
    <c:if test="${room.availability}">
    <div class="row">
        <div class="col-md-7">
            <h2 class="heading">
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
            <div class="button-bottom"><p><a href="${pageContext.request.contextPath}/show_room_images.jsp?room_id=${room.id}" type="button" class="btn btn-secondary" role="button"><fmt:message key="show_photo"/> &raquo;</a></p></div>
            <form action="${pageContext.request.contextPath}/controller/create_item_button" method="post">
                <input type="hidden" name="room_id" value="${room.id}">
                <div class="button-bottom"><p><button type="submit" class="btn btn-info" role="button"><fmt:message key="reservation"/> &raquo;</button></p></div>
            </form>
        </div>
        <div class="col-md-5">
            <c:if test="${room.imageList.size() > 0 && room.imageList.get(0).image != null}">
                <img class="bd-placeholder-img bd-placeholder-img-lg img-fluid mx-auto" src="${imageEncoder.encode(room.imageList.get(0).image)}" hspace="10" vspace="10"/>
            </c:if>
        </div>
    </div>
    </c:if>
    </c:forEach>
</div>
</body>
</html>