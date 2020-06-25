<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="roomClassDAO" class="com.epam.hotel.dao.impl.RoomClassDAOImpl"/>
<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>

<c:set var="roomClassList" value="${roomClassDAO.all}"/>
<c:set var="languageMap" value="${languageDAO.languageMap}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="title.new_room"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/create_room" method="post">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="roomNumber"><fmt:message key="page.room_number"/></label>
                        <input type="number" class="form-control" id="roomNumber" name="room_number">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="capacity"><fmt:message key="page.capacity"/></label>
                        <input type="number" class="form-control" id="capacity" name="capacity">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="price"><fmt:message key="page.price"/></label>
                        <input type="number" class="form-control" id="price" placeholder="" name="price">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <c:forEach var="roomClass" items="${roomClassList}">
                            <input type="radio" id="${roomClass.id}" name="room_class_id" value="${roomClass.id}" class="radio"/>
                            <label for="${roomClass.id}">
                                <c:forEach items="${languageMap}" var="language">
                                    <c:if test="${language.value.equals(sessionScope.locale)}">
                                        ${roomClass.roomClassNameMap.get(language.key)}
                                    </c:if>
                                </c:forEach>
                            </label>
                        </c:forEach>
                    </div>
                </div>
                <div class="form-row">
                    <div class="custom-control custom-checkbox my-2 mr-sm-5">
                        <input type="checkbox" class="custom-control-input" id="availability" name="availability" checked>
                        <label class="custom-control-label" for="availability"><fmt:message key="page.available"/></label>
                    </div>
                </div>
                <br/>
                <div class="form-row">
                    <button type="submit" class="btn btn-primary"><fmt:message key="page.save"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>