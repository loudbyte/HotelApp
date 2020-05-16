<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="orderFacilityDetailDAO" class="com.epam.hotel.dao.impl.OrderFacilityDetailDAOImpl"/>
<jsp:useBean id="facilityDAO" class="com.epam.hotel.dao.impl.FacilityDAOImpl"/>
<html>
<head>
    <title>Facilities detail</title>
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
                    <th scope="col">Название</th>
                    <th scope="col">Описание</th>
                    <th scope="col">Цена</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="facility" items="${facilityDAO.all}">
                    <tr>
                        <td>${facility.id}</td>
                        <td>${facility.name}</td>
                        <td>${facility.description}</td>
                        <td>${facility.price}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/edit_facility.jsp" method="post">
                                <input type="hidden" name="facility_id" value="${facility.id}">
                                <input type="hidden" name="facility_name" value="${facility.name}">
                                <input type="hidden" name="facility_description" value="${facility.description}">
                                <input type="hidden" name="facility_price" value="${facility.price}">
                                <button type="submit"
                                        class="btn btn-sm  btn-warning">Редактировать</button>
                            </form>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/delete_facility" method="post">
                                <input type="hidden" name="facility_id" value="${facility.id}">
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
            </c:if>
            <p>
            <div class="btn-group" role="group" aria-label="Basic example">
                <a href="${pageContext.request.contextPath}/create_facility.jsp" type="button"
                   class="btn btn-dark">Новая услуга</a>
            </div>
            </p>
        </div>
    </div>
</div>
</body>
</html>
