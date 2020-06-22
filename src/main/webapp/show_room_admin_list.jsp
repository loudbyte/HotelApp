<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="roomDAO" class="com.epam.hotel.dao.impl.RoomDAOImpl"/>
<c:set var="roomList" value="${roomDAO.all}"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title>Комнаты</title>
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
                    <th scope="col"><fmt:message key="number"/></th>
                    <th scope="col"><fmt:message key="class"/></th>
                    <th scope="col"><fmt:message key="price"/></th>
                    <th scope="col"><fmt:message key="available"/></th>
                    <th scope="col"><fmt:message key="capacity"/></th>
                    <th scope="col"><fmt:message key="photo"/></th>
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
                            <c:choose>
                                <c:when test="${room.roomClass == 1}"><fmt:message key="deluxe"/></c:when>
                                <c:when test="${room.roomClass == 2}"><fmt:message key="suite"/></c:when>
                                <c:when test="${room.roomClass == 3}"><fmt:message key="standard"/></c:when>
                            </c:choose>
                        </td>
                        <td>${room.price}$</td>
                        <td>${room.availability}</td>
                        <td>${room.capacity}</td>
                        <td>${room.imageList.size()}</td>
                        <td>
                            <c:choose>
                                <c:when test="${!room.availability}">
                                    <form action="${pageContext.request.contextPath}/controller/set_available" method="post">
                                        <input type="hidden" name="room_id" value="${room.id}">
                                        <button type="submit" class="btn btn-sm btn-warning"><fmt:message key="set_available"/></button>
                                    </form>
                                </c:when>
                                <c:when test="${room.availability}">
                                    <form action="${pageContext.request.contextPath}/controller/set_reserved" method="post">
                                        <input type="hidden" name="room_id" value="${room.id}">
                                        <button type="submit" class="btn btn-sm btn-warning"><fmt:message key="set_reserved"/></button>
                                    </form>
                                </c:when>
                            </c:choose>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/edit_room_button" method="post">
                                <input type="hidden" name="id" value="${room.id}">
                                <button type="submit" class="btn btn-sm btn-warning"><fmt:message key="edit"/></button>
                            </form>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/upload_room_image_button" method="post">
                                <input type="hidden" name="id" value="${room.id}">
                                <button type="submit"
                                        class="btn btn-sm btn-warning"><fmt:message key="add_photo"/></button>
                            </form>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${room.availability}">
                                    <form action="${pageContext.request.contextPath}/controller/delete_room" method="post">
                                        <input type="hidden" name="id" value="${room.id}">
                                        <button type="submit"
                                                class="btn btn-sm btn-danger"><fmt:message key="delete"/></button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <button title='<fmt:message key="cannot_delete_room"/>' type="button" class="btn btn-sm btn-dark"><fmt:message key="delete"/></button>
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
                    <a href="${requestScope.pageContext.request.contextPath}/create_room.jsp" type="button"
                       class="btn btn-dark"><fmt:message key="new_room"/></a>
                </div>
                </p>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>