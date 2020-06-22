<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <jsp:include page="style.jsp"/>
    <title><fmt:message key="new_facility"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <h3><fmt:message key="new_facility"/></h3>
    <div class="row">
        <div class="col-sm">
            <form action="${pageContext.request.contextPath}/controller/create_facility" method="post">
                <c:forEach var="language" items="${languageDAO.languageMap}">
                    <div class="form-row">
                        <div class="form-group col-md-5">
                            <label for="facility_name"><fmt:message key="name"/> ${language.value}</label>
                            <input type="text" class="form-control" id="facility_name" name="facility_name${language.key}"/>
                        </div>
                        <div class="form-group col-md-5">
                            <label for="facility_description"><fmt:message key="desc"/> ${language.value}</label>
                            <input type="text" class="form-control" id="facility_description" name="facility_description${language.key}"/>
                        </div>
                    </div>
                </c:forEach>
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="facility_price"><fmt:message key="price"/></label>
                        <input type="text" class="form-control" id="facility_price" name="facility_price">
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