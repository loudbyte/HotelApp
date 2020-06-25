<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="roomClassDAO" class="com.epam.hotel.dao.impl.RoomClassDAOImpl"/>
<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>
<c:set value="${languageDAO.languageMap}" var="languageMap"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="title.room_classes"/></title>
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
                    <th scope="col"><fmt:message key="page.name"/></th>
                    <th scope="col"><fmt:message key="page.desc"/></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="roomClass" items="${roomClassDAO.all}">
                    <tr>
                        <td>${roomClass.id}</td>
                        <td>
                            <c:forEach items="${languageMap}" var="language">
                                <c:if test="${language.value.equals(sessionScope.locale)}">
                                    ${roomClass.roomClassNameMap.get(language.key)}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach items="${languageMap}" var="language">
                                <c:if test="${language.value.equals(sessionScope.locale)}">
                                    ${roomClass.roomClassDescriptionMap.get(language.key)}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/edit_room_class.jsp" method="post">
                                <input type="hidden" name="room_class_id" value="${roomClass.id}">
                                <button type="submit"
                                        class="btn btn-sm  btn-warning"><fmt:message key="page.edit"/></button>
                            </form>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/controller/delete_room_class" method="post">
                                <input type="hidden" name="room_class_id" value="${roomClass.id}">
                                <button type="submit"
                                        class="btn btn-sm btn-danger"><fmt:message key="page.delete"/></button>
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
                <a href="${pageContext.request.contextPath}/create_room_class.jsp" type="button"
                   class="btn btn-dark"><fmt:message key="page.new_room_class"/></a>
            </div>
            </p>
        </div>
    </div>
</div>
</body>
</html>
