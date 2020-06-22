<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="facilityDAO" class="com.epam.hotel.dao.impl.FacilityDAOImpl"/>
<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>
<jsp:useBean id="facilityPackageDAO" class="com.epam.hotel.dao.impl.FacilityPackageDAOImpl"/>
<c:set var="languageMap" value="${languageDAO.languageMap}"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="new_facility_package"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <h3><fmt:message key="new_facility_package"/></h3>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/create_facility_package" method="post">
                <div class="form-row">
                    <c:forEach var="language" items="${languageDAO.languageMap}">
                        <div class="form-group col-md-5">
                            <label for="package_name"><fmt:message key="facility_package"/> ${language.value}</label>
                            <input type="text" class="form-control" id="package_name" name="${language.key}"/>
                        </div>
                    </c:forEach>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label><fmt:message key="select_services"/></label>
                        <ul>
                            <c:forEach var="facility" items="${facilityDAO.all}">
                                <li>
                                    <input type="checkbox" name="facilities" value="${facility.id}" id="${facility.id}" multiple/>
                                    <label for="${facility.id}">
                                        <c:forEach var="language" items="${languageMap}">
                                            <c:if test="${language.value.equals(sessionScope.locale)}">
                                                <strong>${facility.facilityNameMap.get(language.key)}</strong> <br/> ${facility.facilityDescriptionMap.get(language.key)}
                                            </c:if>
                                        </c:forEach>
                                    </label>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="discount"><fmt:message key="discount"/></label>
                        <input type="text" class="form-control" id="discount" name="discount"/>
                    </div>
                </div>
                <br/>
                <div class="form-row">
                    <button type="submit" style="width: 100px" class="btn btn-primary"><fmt:message key="save"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
