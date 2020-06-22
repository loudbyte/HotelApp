<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="facilityPackageDAO" class="com.epam.hotel.dao.impl.FacilityPackageDAOImpl"/>
<jsp:useBean id="facilityDAO" class="com.epam.hotel.dao.impl.FacilityDAOImpl"/>
<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>
<c:set value="${languageDAO.languageMap}" var="languageMap"/>
<c:set var="packageList" value="${facilityPackageDAO.all}"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="edit"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <h3><fmt:message key="edit"/> <fmt:message key="order_number"/> - ${requestScope.order_room_detail_id}</h3>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/edit_order_room_detail" method="post">
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="room_id"><fmt:message key="room"/> ID</label>
                        <input type="text" class="form-control" id="room_id" name="room_id" value="${requestScope.room_id}">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="start_date"><fmt:message key="start_date"/></label>
                        <input type="text" class="form-control" id="start_date" name="start_date" value="${requestScope.start_date}">
                    </div>
                    <div class="form-group col-md-5">
                        <label for="end_date"><fmt:message key="end_date"/></label>
                        <input type="text" class="form-control" id="end_date" name="end_date" value="${requestScope.end_date}">
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
                                <input type="radio" id="${facilityPackage.id}" name="facility_package_id" value="${facilityPackage.id}" <c:if test="${facilityPackage.id == requestScope.facility_package_id}">checked</c:if> >
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
                <input type="hidden" name="order_main_id" value="${requestScope.order_main_id}" >
                <input type="hidden" name="order_room_detail_id" value="${requestScope.order_room_detail_id}" >
                <div class="form-row">
                    <button type="submit" style="width: 100px" class="btn btn-primary"><fmt:message key="save"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>