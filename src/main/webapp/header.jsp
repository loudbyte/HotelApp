<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="languageDAO" class="com.epam.hotel.dao.impl.LanguageDAOImpl"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>

<style>
    .header {
        margin-top: 50px;
        height: 100px;
    }
</style>

<div class="header">
    <div class="row">
        <div class="col-2">
            <div class="btn-group" role="group" aria-label="Basic example">
                <a href="${pageContext.request.contextPath}/controller/index" type="button" class="btn btn-info"><fmt:message key="page.main_page"/></a>
            </div>
        </div>
        <div class="col-5">
            <c:if test="${sessionScope.role != 'GUEST' || sessionScope.role == null}">
                <h4><fmt:message key="page.hello"/> <c:out value="${sessionScope.person.firstName}"/></h4>
            </c:if>
        </div>
        <div class="btn-group" role="group" style="float: right" aria-label="Basic example">
        </div>
        <div class="col-3">
            <c:if test="${sessionScope.role != 'GUEST' || sessionScope.role == null}">
                <div class="btn-group" role="group" style="float: left" aria-label="Basic example">
                    <a href="${pageContext.request.contextPath}/controller/show_cart" type="button" class="btn btn-info"><fmt:message key="page.cart"/></a>
                    <a href="${pageContext.request.contextPath}/controller/cabinet" type="button" class="btn btn-secondary"><fmt:message key="page.cabinet"/></a>
                </div>
                <div class="btn-group" style="float: right" role="group" aria-label="Basic example">
                    <a href="${pageContext.request.contextPath}/controller/logout" type="button" class="btn btn-outline-light"><fmt:message key="page.exit"/></a>
                </div>
            </c:if>
            <h3></h3>
        </div>
        <c:set value="${languageDAO.languageMap}" var="languageMap"/>
        <div class="col">
            <div class="btn-group" style="float: right">
                <c:forEach var="language" items="${languageMap}">
                    <a class="btn-sm btn-dark" href="${pageContext.request.contextPath}?language=${language.value}">${language.value.substring(3)}</a>
                </c:forEach>
            </div>
            <c:if test="${sessionScope.person.admin}">
                <div class="btn-group" style="float: right">
                    <a href="${pageContext.request.contextPath}/language.jsp" type="button" class="btn-sm btn-dark"><fmt:message key="page.languages"/></a>
                </div>
            </c:if>
        </div>
    </div>
</div>