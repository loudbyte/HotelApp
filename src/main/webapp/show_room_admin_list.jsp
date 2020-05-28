<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="roomDAO" class="com.epam.hotel.dao.impl.RoomDAOImpl"/>
<c:set var="roomList" value="${roomDAO.all}"/>
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
                    <th scope="col">Номер</th>
                    <th scope="col">Класс</th>
                    <th scope="col">Цена</th>
                    <th scope="col">Доступно</th>
                    <th scope="col">Вместимость</th>
                    <th scope="col">Фото</th>
                    <th scope="col"></th>
                    <th scope="col"></td>
                    <th scope="col"></td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="room" items="${roomList}">
                    <tr>
                        <td>${room.id}</td>
                        <td>${room.roomNumber}</td>
                        <td>${room.roomClassRu}</td>
                        <td>${room.price}</td>
                        <td>${room.availability}</td>
                        <td>${room.capacity}</td>
                        <td> ${room.imageList.size()} шт.</td>
                        <td>
                                <form action="${requestScope.pageContext.request.contextPath}/controller/edit_room_button" method="post">
                                    <input type="hidden" name="id" value="${room.id}">
                                    <button type="submit"
                                            class="btn btn-sm btn-warning">Редактировать</button>
                                </form>
                        </td>
                        <td>
                            <form action="${requestScope.pageContext.request.contextPath}/controller/upload_room_image_button" method="post">
                                <input type="hidden" name="id" value="${room.id}">
                                <button type="submit" style="width: 140px"
                                        class="btn btn-sm btn-warning">Добавить фото</button>
                            </form>
                        </td>
                        <td>
                            <form action="${requestScope.pageContext.request.contextPath}/controller/delete_room" method="post">
                                <input type="hidden" name="id" value="${room.id}">
                                <button type="submit"
                                        class="btn btn-sm btn-danger">Удалить</button>
                            </form>
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
                       class="btn btn-dark">Новая комната</a>
                </div>
                </p>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
