<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="language"/>

<script>
    function refreshPage(){
        window.location.reload();
    }
</script>

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
                <a href="${pageContext.request.contextPath}/controller/index" type="button" class="btn btn-info"><fmt:message key="main.page"/></a>
            </div>
        </div>
        <div class="col-5">
            <c:if test="${sessionScope.role != 'GUEST' || sessionScope.role == null}">
                <h4><fmt:message key="hello"/> <c:out value="${sessionScope.person.firstName}"/></h4>
            </c:if>
        </div>
        <div class="btn-group" role="group" style="float: right" aria-label="Basic example">
        </div>
        <div class="col-3">
            <c:if test="${sessionScope.role != 'GUEST' || sessionScope.role == null}">
                <div class="btn-group" role="group" style="float: left" aria-label="Basic example">
                    <a href="${pageContext.request.contextPath}/controller/show_cart" type="button" class="btn btn-info"><fmt:message key="cart"/></a>
                    <a href="${pageContext.request.contextPath}/controller/cabinet" type="button" class="btn btn-secondary"><fmt:message key="cabinet"/></a>
                </div>
                <div class="btn-group" style="float: right" role="group" aria-label="Basic example">
                    <a href="${pageContext.request.contextPath}/controller/logout" type="button" class="btn btn-outline-light"><fmt:message key="exit"/></a>
                </div>
            </c:if>
            <h3></h3>
        </div>
        <div class="col">
            <div class="btn-group" style="float: right">
                <a class="btn-sm btn-dark" href="${pageContext.request.contextPath}?language=en">EN</a>
                <a class="btn-sm btn-dark" href="${pageContext.request.contextPath}?language=ru">RU</a>
            </div>
        </div>
    </div>
</div>