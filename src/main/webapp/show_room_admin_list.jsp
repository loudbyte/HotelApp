<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="roomDAO" class="com.epam.hotel.dao.impl.RoomDAOImpl"/>
<c:set var="roomList" value="${roomDAO.all}"/>
<jsp:useBean id="roomClassDAO" class="com.epam.hotel.dao.impl.RoomClassDAOImpl"/>
<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>

<c:set var="roomClassList" value="${roomClassDAO.all}"/>
<c:set var="languageMap" value="${languageDAO.languageMap}"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="title.rooms"/></title>
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
                    <th scope="col"><fmt:message key="page.number"/></th>
                    <th scope="col"><fmt:message key="page.class"/></th>
                    <th scope="col"><fmt:message key="page.price"/></th>
                    <th scope="col"><fmt:message key="page.available"/></th>
                    <th scope="col"><fmt:message key="page.capacity"/></th>
                    <th scope="col"><fmt:message key="page.photo"/></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="room" items="${roomList}">
                    <tr>
                        <td>${room.id}</td>
                        <td>${room.roomNumber}</td>
                        <td>
                            <c:forEach var="roomClass" items="${roomClassList}">
                                <c:if test="${roomClass.id == room.roomClassId}">
                                    <c:forEach items="${languageMap}" var="language">
                                        <c:if test="${language.value.equals(sessionScope.locale)}">
                                            ${roomClass.roomClassNameMap.get(language.key)}
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>${room.price}$</td>
                        <td>
                            <c:choose>
                                <c:when test="${room.availability}">
                                    <fmt:message key="page.yes"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="page.no"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${room.capacity}</td>
                        <td>${room.imageList.size()}</td>
                        <td>
                            <c:choose>
                                <c:when test="${!room.availability}">
                                    <form action="${pageContext.request.contextPath}/controller/set_available" method="post">
                                        <input type="hidden" name="room_id" value="${room.id}">
                                        <button type="submit" class="btn btn-sm btn-warning"><fmt:message key="page.set_available"/></button>
                                    </form>
                                </c:when>
                                <c:when test="${room.availability}">
                                    <form action="${pageContext.request.contextPath}/controller/set_reserved" method="post">
                                        <input type="hidden" name="room_id" value="${room.id}">
                                        <button type="submit" class="btn btn-sm btn-warning"><fmt:message key="page.set_reserved"/></button>
                                    </form>
                                </c:when>
                            </c:choose>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/edit_room.jsp" method="post">
                                <input type="hidden" name="room_id" value="${room.id}">
                                <button type="submit" class="btn btn-sm btn-warning"><fmt:message key="page.edit"/></button>
                            </form>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/upload_room_image_button" method="post">
                                <input type="hidden" name="room_id_for_upload" value="${room.id}">
                                <button type="submit"
                                        class="btn btn-sm btn-warning"><fmt:message key="page.add_photo"/></button>
                            </form>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${room.availability}">
                                    <form action="${pageContext.request.contextPath}/controller/delete_room" method="post">
                                        <input type="hidden" name="id" value="${room.id}">
                                        <button type="submit"
                                                class="btn btn-sm btn-danger"><fmt:message key="page.delete"/></button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <button title='<fmt:message key="page.cannot_delete_room"/>' type="button" class="btn btn-sm btn-dark"><fmt:message key="page.delete"/></button>
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
                <p>
                <div class="btn-group" role="group" aria-label="Basic example">
                    <a href="${pageContext.request.contextPath}/create_room.jsp" type="button"
                       class="btn btn-dark"><fmt:message key="page.new_room"/></a>
                </div>
                </p>
                <p>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>