<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="imageEncoder" class="com.epam.hotel.util.ImageEncoder"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="show.rooms"/></title>

</head>
<body>

<div class="container marketing">

    <jsp:include page="header.jsp"/>

    <hr class="featurette-divider">
    <c:forEach items="${requestScope.room_list}" var="room">
    <c:if test="${room.availability}">
    <div class="row featurette">
        <div class="col-md-7">
            <h2 class="featurette-heading">
                <c:choose>
                    <c:when test="${room.roomClassId == 1}">
                        <fmt:message key="deluxe"/>
                    </c:when>
                    <c:when test="${room.roomClassId == 2}">
                        <fmt:message key="suite"/>
                    </c:when>
                    <c:when test="${room.roomClassId == 3}">
                        <fmt:message key="standard"/>
                    </c:when>
                </c:choose>
                    <fmt:message key="room"/>
            </h2>
            <p class="lead">
                <fmt:message key="capacity.people"/>: ${room.capacity}</br>
                <fmt:message key="room.price"/>: ${room.price}$</br>
                <fmt:message key="room.number"/>: №${room.roomNumber}</br>
            </p>
            <form action="${pageContext.request.contextPath}/controller/create_item_button" method="post">
                <input type="hidden" name="room_id" value="${room.id}">
                <div class="button-bottom"><p><button type="submit" class="btn btn-secondary" role="button"><fmt:message key="reservation"/> &raquo;</button></p></div>
            </form>
        </div>
        <div class="col-md-5">
            <c:if test="${room.imageList.size() > 0 && room.imageList.get(0).image != null}">
                <img class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto" src="${imageEncoder.encode(room.imageList.get(0).image)}"/>
            </c:if>
        </div>
    </div>
    </c:if>
    </c:forEach>

</div>
</body>
</html>