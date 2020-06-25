<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="imageEncoder" class="com.epam.hotel.util.ImageEncoder"/>
<jsp:useBean id="roomDAO" class="com.epam.hotel.dao.impl.RoomDAOImpl"/>
<jsp:useBean id="roomClassDAO" class="com.epam.hotel.dao.impl.RoomClassDAOImpl"/>
<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>

<c:set var="room" value="${roomDAO.getOneById(param.get('room_id'))}"/>
<c:set var="roomClass" value="${roomClassDAO.getOneById(room.roomClassId)}"/>
<c:set var="languageMap" value="${languageDAO.languageMap}"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="title.show_photo"/></title>
</head>
<body>
<div class="container marketing">

    <jsp:include page="header.jsp"/>

    <c:choose>
        <c:when test="${room == null}">
            <h3><fmt:message key="page.try_again"/></h3>
            <div class="btn-group" role="group" aria-label="Basic example" style="float: right">
                <a href="${pageContext.request.contextPath}/controller/show_rooms" type="button" class="btn btn-secondary"><fmt:message key="page.show_rooms"/></a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row">
                <div class="col-md-7">
                    <h2>
                        <c:forEach items="${languageMap}" var="language">
                            <c:if test="${language.value.equals(sessionScope.locale)}">
                                ${roomClass.roomClassNameMap.get(language.key)}
                            </c:if>
                        </c:forEach>
                        <fmt:message key="page.room"/>
                    </h2>
                    <p class="lead">
                        <fmt:message key="page.capacity_people"/>: ${room.capacity}</br>
                        <fmt:message key="page.room_price"/>: ${room.price}$</br>
                        <fmt:message key="page.room_number"/>: №${room.roomNumber}</br>
                    </p>
                    <form action="${pageContext.request.contextPath}/controller/create_item_button" method="post">
                        <input type="hidden" name="room_id" value="${room.id}">
                        <div class="button-bottom"><p><button type="submit" class="btn btn-info" role="button"><fmt:message key="page.reservation"/> &raquo;</button></p></div>
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
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>