<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    .header {
        margin-top: 50px;
        height: 100px;
    }
</style>

<div class="header">
    <div class="row">
        <div class="col">
            <div class="btn-group" role="group" aria-label="Basic example">
                <a href="${pageContext.request.contextPath}/controller/index" type="button" class="btn btn-info">Главная страница</a>
            </div>
        </div>
        <div class="col-6">
            <h4>Привет <c:out value="${sessionScope.person.firstName}" default="посетитель"/>! Роль - ${sessionScope.role}</h4>
        </div>
        <div class="btn-group" role="group" style="float: right" aria-label="Basic example">
        </div>
        <div class="col">
            <c:if test="${sessionScope.role != 'GUEST' || sessionScope.role == null}">
                <div class="btn-group" role="group" style="float: left" aria-label="Basic example">
                    <a href="${pageContext.request.contextPath}/controller/show_cart" type="button" class="btn btn-info">Корзина</a>
                    <a href="${pageContext.request.contextPath}/controller/cabinet" type="button" class="btn btn-secondary">Кабинет</a>
                </div>
                <div class="btn-group" style="float: right" role="group" aria-label="Basic example">
                    <a href="${pageContext.request.contextPath}/controller/logout" type="button" class="btn btn-light">Выход</a>
                </div>
            </c:if>
            <h3></h3>
        </div>
    </div>
</div>
