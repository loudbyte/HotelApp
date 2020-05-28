<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="imageEncoder" class="com.epam.hotel.util.ImageEncoder"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title>Show room by class</title>

</head>
<body>

<div class="container marketing">

    <jsp:include page="header.jsp"/>

    <hr class="featurette-divider">
    <c:forEach items="${requestScope.room_list}" var="room">
    <c:if test="${room.availability}">
    <div class="row featurette">
        <div class="col-md-7">
            <h2 class="featurette-heading">${room.roomClassRu} номер </h2>
            <p class="lead">
                Вместимость: ${room.capacity} человек </br>
                Цена в сутки: ${room.price}$ </br>
                Номер комнаты №${room.roomNumber} </br>
            </p>
            <form action="${pageContext.request.contextPath}/controller/create_item_button" method="post">
                <input type="hidden" name="room_id" value="${room.id}">
                <div class="button-bottom"><p><button type="submit" class="btn btn-secondary" role="button">Забронировать &raquo;</button></p></div>
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
