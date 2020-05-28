<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="roomListDAO" class="com.epam.hotel.dao.impl.RoomDAOImpl"/>
<jsp:useBean id="roomImageDAO" class="com.epam.hotel.dao.impl.RoomImageDAOImpl"/>
<jsp:useBean id="imageEncoder" class="com.epam.hotel.util.ImageEncoder"/>
<jsp:useBean id="room" class="com.epam.hotel.entity.Room"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title>Edit room</title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>

    <c:set var="room" value="${room = roomListDAO.getOneById(sessionScope.get('id'))}"/>
    <c:set var="roomImages" value="${roomImages = roomImageDAO.geAllByRoomId(room.id)}"/>

    <form action="${pageContext.request.contextPath}/upload" method="post"
          enctype="multipart/form-data">
        <div class="row">

            <div class="col-md-6">
                <p class="featurette-heading">${room.roomClassRu} номер
                    ID: ${room.id}</br>
                    Номер комнаты №${room.price} </br>
                </p>
                Добавить фото
                <div class="input-group mb-6">
                    <div class="custom-file">
                        <input type="file" name="file" class="">
                    </div>
                </div>
                <br/>
                <div class="form-row">
                    <button type="submit" value="upload" class="btn btn-primary">Загрузить</button>
                </div>
            </div>
            <div class="col-md-6">
                <c:if test="${room.imageList.size() > 0 && room.imageList.get(0).image != null}">
                    <ul>
                        <li>
                            <p>
                                Отметьте чтобы загрузить еще одно фото
                                <input type="radio" value="0" name="room_image_id_radio">
                            </p>
                        </li>
                        <c:forEach items="${room.imageList}" var="image">
                            <li>
                                <p>
                                    Фото ID: ${image.id}<br/>
                                    Отметьте чтобы заменить фото
                                    <input type="radio" value="${image.id}" name="room_image_id_radio"><br/>
                                    <input type="hidden" name="room_image_radio" value="${image.id}">
                                <img width="200" height="200" src="${imageEncoder.encode(image.image)}"/><br/>
                                    <a href="${pageContext.request.contextPath}/controller/delete_room_image?image_id=${image.id}" type="button" style="width: 200px" class="btn btn-warning">Удалить фото</a>
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