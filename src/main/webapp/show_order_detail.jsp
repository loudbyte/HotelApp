<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Order detail</title>
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
                    <th scope="col">Номер комнаты</th>
                    <th scope="col">Пакет услуг</th>
                    <th scope="col">Дата начала</td>
                    <th scope="col">Дата конца</td>
                    <th scope="col"></td>
                    <th scope="col"></td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="orderDetail" items="${requestScope.order_detail_list}">
                        <tr>
                            <td>${orderDetail.id}</td>
                            <td>${requestScope.room_dao.getOneById(orderDetail.roomId).roomNumber}</td>
                            <td>${requestScope.order_facility_detail_dao.getOneById(orderDetail.orderFacilityDetailId).facilityPackageName}</td>
                            <td>${orderDetail.startDate}</td>
                            <td>${orderDetail.endDate}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${sessionScope.role == 'ADMIN'}">
                                        <form action="${pageContext.request.contextPath}/controller/edit_order_detail_button" method="post">
                                            <input type="hidden" name="order_detail_id" value="${orderDetail.id}">
                                            <input type="hidden" name="start_date" value="${orderDetail.startDate}">
                                            <input type="hidden" name="end_date" value="${orderDetail.endDate}">
                                            <input type="hidden" name="room_id" value="${orderDetail.roomId}">
                                            <input type="hidden" name="order_main_id" value="${orderDetail.orderMainid}">
                                            <input type="hidden" name="order_facility_detail_id" value="${orderDetail.orderFacilityDetailId}">
                                            <button type="submit"
                                                    class="btn btn-sm  btn-warning">Редактировать</button>
                                        </form>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${requestScope.order_status_id == 1}">
                                        <form action="${pageContext.request.contextPath}/controller/delete_order_detail" method="post">
                                            <input type="hidden" name="order_detail_id" value="${orderDetail.id}">
                                            <button type="submit"
                                                    class="btn btn-sm  btn-danger">Удалить</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <button title="Нельзя удалить обрабатываемый заказ." type="button" class="btn btn-sm btn-dark">Удалить</button>
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
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
