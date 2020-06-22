<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="roomDAO" class="com.epam.hotel.dao.impl.RoomDAOImpl"/>
<jsp:useBean id="facilityPackageDAO" class="com.epam.hotel.dao.impl.FacilityPackageDAOImpl"/>
<jsp:useBean id="facilityDAO" class="com.epam.hotel.dao.impl.FacilityDAOImpl"/>
<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>
<c:set value="${languageDAO.languageMap}" var="languageMap"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="title_create_item"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <h3><fmt:message key="room_reservation" /> №${param.room_id}</h3>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/create_item" method="post">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="start_date"><fmt:message key="start_date"/></label>
                        <input type="date" class="form-control" id="start_date" name="start_date">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="end_date"><fmt:message key="end_date"/></label>
                        <input type="date" class="form-control" id="end_date" name="end_date">
                    </div>
                </div>
                <h3><fmt:message key="choose_facility_package"/></h3>
                <div class="form-row">
                    <c:forEach var="facilityPackage" items="${facilityPackageDAO.all}">
                        <div class="col-3" style="border-top:1px solid lightgray;">
                            <div>
                                <label for="${facilityPackage.id}"><h4>
                                    <c:forEach items="${languageMap}" var="language">
                                        <c:if test="${language.value.equals(sessionScope.locale)}">
                                            ${facilityPackage.facilityPackageNameMap.get(language.key)}
                                        </c:if>
                                    </c:forEach>
                                </h4></label>
                                <input type="radio" id="${facilityPackage.id}" name="detail_id" value="${facilityPackage.id}">
                                <ul>
                                    <c:forEach var="facility" items="${facilityDAO.getFacilityListByFacilityPackageId(facilityPackage.id)}">
                                        <li><strong>
                                            <c:forEach items="${languageMap}" var="language">
                                                <c:if test="${language.value.equals(sessionScope.locale)}">
                                                    ${facility.facilityNameMap.get(language.key)}
                                                </c:if>
                                            </c:forEach>
                                            </strong>
                                            <br/>
                                            <c:forEach items="${languageMap}" var="language">
                                                <c:if test="${language.value.equals(sessionScope.locale)}">
                                                    ${facility.facilityDescriptionMap.get(language.key)}
                                                </c:if>
                                            </c:forEach>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <br/>
                <input type="hidden" name="room_id" value="${param.room_id}" >
                <div class="form-row">
                    <button type="submit" style="width: 100px" class="btn btn-primary"><fmt:message key="add_to_card"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>