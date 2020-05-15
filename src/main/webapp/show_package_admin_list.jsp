<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="orderFacilityDetailDAO" class="com.epam.hotel.dao.impl.OrderFacilityDetailDAOImpl"/>
<jsp:useBean id="facilityDAO" class="com.epam.hotel.dao.impl.FacilityDAOImpl"/>
<html>
<head>
    <title>Facility packages</title>
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
                    <th scope="col">Название пакета</th>
                    <th scope="col"></td>
                    <th scope="col"></td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="orderDetail" items="${orderFacilityDetailDAO.all}">
                    <tr>
                        <td>${orderDetail.id}</td>
                        <td>${orderDetail.facilityPackageName}</td>
                        <td>
<%--                            <c:choose>--%>
<%--                                <c:when test="${requestScope.order_status_id == 1}">--%>
<%--                                    <form action="${pageContext.request.contextPath}/controller/delete_order_detail" method="post">--%>
<%--                                        <input type="hidden" name="order_detail_id" value="${orderDetail.id}">--%>
<%--                                        <button type="submit"--%>
<%--                                                class="btn btn-sm  btn-danger">Удалить</button>--%>
<%--                                    </form>--%>
<%--                                </c:when>--%>
<%--                                <c:otherwise>--%>
<%--                                    <button title="Нельзя удалить обрабатываемый заказ." type="button" class="btn btn-sm btn-dark">Удалить</button>--%>
<%--                                </c:otherwise>--%>

<%--                            </c:choose>--%>
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
