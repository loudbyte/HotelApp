<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="personDAO" class="com.epam.hotel.dao.impl.PersonDAOImpl"/>
<c:set var="personList" value="${personDAO.all}"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title>Пользователи</title>
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
                    <th scope="col"><fmt:message key="first.name"/></th>
                    <th scope="col"><fmt:message key="last.name"/></th>
                    <th scope="col"><fmt:message key="iin"/></th>
                    <th scope="col"><fmt:message key="phone"/></th>
                    <th scope="col"><fmt:message key="email"/></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="person" items="${personList}">
                    <tr>
                        <td>${person.id}</td>
                        <td>${person.firstName}</td>
                        <td>${person.lastName}</td>
                        <td>${person.iin}</td>
                        <td>${person.phone}</td>
                        <td>${person.email}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/edit_person_button" method="post">
                                <input type="hidden" name="person_id" value="${person.id}">
                                <button type="submit"
                                        class="btn  btn-sm btn-warning"><fmt:message key="edit"/></button>
                            </form>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/delete_person" method="post">
                                <input type="hidden" name="person_id" value="${person.id}">
                                <button type="submit"
                                        class="btn  btn-sm btn-danger"><fmt:message key="delete"/></button>
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
                    <a href="${pageContext.request.contextPath}/create_person.jsp" type="button"
                       class="btn btn-dark"><fmt:message key="new.user"/></a>
                </div>
                </p>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
