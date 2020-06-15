<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="orderFacilityDetailDAO" class="com.epam.hotel.dao.impl.FacilityPackageDAOImpl"/>
<jsp:useBean id="facilityDAO" class="com.epam.hotel.dao.impl.FacilityDAOImpl"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="packages"/></title>
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
                    <th scope="col"><fmt:message key="package"/></th>
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
                            <form action="${pageContext.request.contextPath}/edit_package.jsp" method="post">
                                <input type="hidden" name="package_id" value="${orderDetail.id}">
                                <input type="hidden" name="package_name" value="${orderDetail.facilityPackageName}">
                                <button type="submit"
                                        class="btn btn-sm  btn-warning"><fmt:message key="edit"/></button>
                            </form>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/delete_package" method="post">
                                <input type="hidden" name="package_id" value="${orderDetail.id}">
                                <button type="submit"
                                        class="btn btn-sm btn-danger"><fmt:message key="delete"/></button>
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
                    <a href="${pageContext.request.contextPath}/create_package.jsp" type="button"
                       class="btn btn-dark"><fmt:message key="new.package"/></a>
                </div>
                </p>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>