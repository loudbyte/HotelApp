<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="roomClassDAO" class="com.epam.hotel.dao.impl.RoomClassDAOImpl"/>
<jsp:useBean id="roomDAO" class="com.epam.hotel.dao.impl.RoomDAOImpl"/>
<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>
<jsp:useBean id="imageEncoder" class="com.epam.hotel.util.ImageEncoder"/>

<c:set value="${languageDAO.languageMap}" var="languageMap"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="title.rooms"/></title>
    <style>
        .wrapper {
            width: 20em;
            height: 20em;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .wrapper img {
            max-width: 100%;
            max-height: 100%;
            object-fit: contain;
            border: calc(1em / 12) solid black;
            border-radius: calc(11em / 12);
        }
    </style>
</head>
<body>
<div class="container marketing">
    <jsp:include page="header.jsp"/>
    <div class="row">
        <c:forEach var="roomClass" items="${roomClassDAO.all}">
            <div class="col-lg-4">
                <form action="show_rooms_by_class.jsp" >
                    <a class="btn btn-outline-dark" href="${pageContext.request.contextPath}/show_rooms_by_class.jsp?room_class_id=${roomClass.id}" role="button">
                        <div class="wrapper">
                            <img src="<c:if test="${roomDAO.getAllByRoomClassId(roomClass.id).get(0).imageList.size() > 0 && roomDAO.getAllByRoomClassId(roomClass.id).get(0).imageList.get(0).image != null}">${imageEncoder.encode(roomDAO.getAllByRoomClassId(roomClass.id).get(0).imageList.get(0).image)}</c:if>" class="rounded-circle" alt="image" width="300" height="300"/>
                        </div>
                        <c:forEach items="${languageMap}" var="language">
                            <h2>
                                <c:if test="${language.value.equals(sessionScope.locale)}">
                                    ${roomClass.roomClassNameMap.get(language.key)}
                                </c:if>
                            </h2>
                            <p>
                                <c:if test="${language.value.equals(sessionScope.locale)}">
                                    ${roomClass.roomClassDescriptionMap.get(language.key)}
                                </c:if>
                            </p>
                        </c:forEach>
                        <button type="button" class="btn btn-primary"><fmt:message key="page.show_rooms"/> &raquo;</button>
                    </a>
                </form>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>