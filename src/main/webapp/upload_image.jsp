<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
    <title><fmt:message key="title.edit"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>

    <c:set var="room" value="${room = roomListDAO.getOneById(sessionScope.get('room_id_for_upload'))}"/>

    <form action="${pageContext.request.contextPath}/upload" method="post"
          enctype="multipart/form-data">
        <div class="row">

            <div class="col-md-6">
                <p>
                        <fmt:message key="page.number"/>
                    ID: ${room.id}<br/>
                    <fmt:message key="page.room_number"/> №${room.price} <br/>
                </p>
                <p><strong><fmt:message key="page.format_jpg"/><br/>
                <fmt:message key="page.size_no_more_2mb"/>!</strong></p>
                <div class="input-group mb-6">
                    <div class="custom-file">
                        <input type="file" name="image_file">
                    </div>
                </div>
                <br/>
                <div class="form-row">
                    <button type="submit" value="upload" class="btn btn-primary"><fmt:message key="page.upload"/></button>
                </div>
            </div>
            <div class="col-md-6">
                <c:if test="${room.imageList.size() > 0 && room.imageList.get(0).image != null}">
                    <ul>
                        <li>
                            <p>
                                <label for="check_upload_photo"><fmt:message key="page.check_upload_photo"/></label>
                                <input id="check_upload_photo" type="radio" value="0" name="room_image_id_radio">
                            </p>
                        </li>
                        <c:forEach items="${room.imageList}" var="image">
                            <li>
                                <p>
                                    <fmt:message key="page.photo"/> ID: ${image.id}<br/>
                                    <label for="${image.id}"><fmt:message key="page.check_replace"/></label>
                                    <input id="${image.id}" type="radio" value="${image.id}" name="room_image_id_radio"><br/>
                                    <input type="hidden" name="room_image_radio" value="${image.id}">
                                <img width="200" height="200" src="${imageEncoder.encode(image.image)}" alt=""/><br/>
                                    <a href="${pageContext.request.contextPath}/controller/delete_room_image?image_id=${image.id}" type="button" style="width: 200px" class="btn btn-warning"><fmt:message key="page.delete"/></a>
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