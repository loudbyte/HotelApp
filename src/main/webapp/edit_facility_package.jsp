<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="facilityDAO" class="com.epam.hotel.dao.impl.FacilityDAOImpl"/>
<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>
<jsp:useBean id="facilityPackageDAO" class="com.epam.hotel.dao.impl.FacilityPackageDAOImpl"/>
<c:set var="languageMap" value="${languageDAO.languageMap}"/>
<c:set var="facilityPackage" value="${facilityPackageDAO.getOneById(param.get('facility_package_id'))}"/>

<c:set var="result" value="${false}"/>

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
    <h3><fmt:message key="edit"/> <fmt:message key="facility_package"/> ID ${param.get("facility_package_id")}</h3>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/edit_facility_package" method="post">
                <div class="form-row">
                    <c:forEach var="language" items="${languageMap}">
                    <div class="form-group col-md-5">
                        <label for="package_name"><fmt:message key="facility_package"/> ${language.value}</label>
                        <input type="text" class="form-control" id="package_name" name="${language.key}" value="${facilityPackageDAO.getFacilityPackageNameMapByFacilityPackageId(param.get('facility_package_id')).get(language.value)}">
                    </div>
                    </c:forEach>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label><fmt:message key="select_services"/></label>
                        <ul>
                            <c:forEach var="facility" items="${facilityDAO.all}">
                                <li>
                                    <c:set value="${false}" var="result"/>
                                        <c:forEach var="facilityChecked" items="${facilityPackage.facilityList}">
                                            <c:if test="${facilityChecked.id == facility.id}"><c:set value="${true}" var="result"/></c:if>
                                        </c:forEach>
                                        <input type="checkbox" name="facilities" value="${facility.id}" id="${facility.id}" multiple <c:if test='${result}' >checked</c:if> />
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
                        <input type="text" class="form-control" id="discount" name="discount" value="${param.get("discount")}"/>
                    </div>
                </div>
                <br/>
                <input type="hidden" name="facility_package_id" value="${param.get("facility_package_id")}" >
                <div class="form-row">
                    <button type="submit" style="width: 100px" class="btn btn-primary"><fmt:message key="save"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
