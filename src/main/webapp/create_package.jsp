<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="facilityDAO" class="com.epam.hotel.dao.impl.FacilityDAOImpl"/>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="new.package"/></title>
</head>
<body>
<div class="container">

    <jsp:include page="header.jsp"/>
    <h3><fmt:message key="new.package"/></h3>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/create_package" method="post">
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="package_name"><fmt:message key="package"/></label>
                        <input type="text" class="form-control" id="package_name" name="package_name">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label><fmt:message key="select.services"/></label>
                        <ul>
                            <c:forEach var="facility" items="${facilityDAO.all}">
                                <li><input type="checkbox" name="facilities" value="${facility.id}" id="${facility.id}" multiple/><label for="${facility.id}"><strong>${facility.name}</strong> <br/> ${facility.description}</label></li>
                            </c:forEach>
                        </ul>
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
