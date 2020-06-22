<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="roomListDAO" class="com.epam.hotel.dao.impl.RoomDAOImpl"/>
<jsp:useBean id="roomImageDAO" class="com.epam.hotel.dao.impl.RoomImageDAOImpl"/>
<jsp:useBean id="imageEncoder" class="com.epam.hotel.util.ImageEncoder"/>
<jsp:useBean id="room" class="com.epam.hotel.entity.Room"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="edit"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>

    <c:set var="room" value="${room = roomListDAO.getOneById(sessionScope.get('room_id_for_upload'))}"/>
    <c:set var="roomImages" value="${roomImages = roomImageDAO.geAllByRoomId(room.id)}"/>

    <form action="${pageContext.request.contextPath}/upload" method="post"
          enctype="multipart/form-data">
        <div class="row">

            <div class="col-md-6">
                <p class="featurette-heading">
                    <c:choose>
                        <c:when test="${room.roomClass == 1}"><fmt:message key="deluxe"/></c:when>
                        <c:when test="${room.roomClass == 2}"><fmt:message key="suite"/></c:when>
                        <c:when test="${room.roomClass == 3}"><fmt:message key="standard"/></c:when>
                    </c:choose>
                        <fmt:message key="number"/>
                    ID: ${room.id}</br>
                    <fmt:message key="room_number"/> №${room.price} </br>
                </p>
                <fmt:message key="add_photo"/>
                <div class="input-group mb-6">
                    <div class="custom-file">
                        <input type="file" name="file" class="">
                    </div>
                </div>
                <br/>
                <div class="form-row">
                    <button type="submit" value="upload" class="btn btn-primary"><fmt:message key="upload"/></button>
                </div>
            </div>
            <div class="col-md-6">
                <c:if test="${room.imageList.size() > 0 && room.imageList.get(0).image != null}">
                    <ul>
                        <li>
                            <p>
                                <label for="check_upload_photo"><fmt:message key="check_upload_photo"/></label>
                                <input id="check_upload_photo" type="radio" value="0" name="room_image_id_radio">
                            </p>
                        </li>
                        <c:forEach items="${room.imageList}" var="image">
                            <li>
                                <p>
                                    <fmt:message key="photo"/> ID: ${image.id}<br/>
                                    <label for="${image.id}"><fmt:message key="check_replace"/></label>
                                    <input id="${image.id}" type="radio" value="${image.id}" name="room_image_id_radio"><br/>
                                    <input type="hidden" name="room_image_radio" value="${image.id}">
                                <img width="200" height="200" src="${imageEncoder.encode(image.image)}"/><br/>
                                    <a href="${pageContext.request.contextPath}/controller/delete_room_image?image_id=${image.id}" type="button" style="width: 200px" class="btn btn-warning"><fmt:message key="delete"/></a>
                                </p>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
            </div>
        </div>
    </form>
</div>
</body>
</html>