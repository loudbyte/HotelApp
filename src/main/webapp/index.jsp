<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="language"/>
<head>
  <jsp:include page="style.jsp"/>
  <title><fmt:message key="title.index"/></title>
</head>
<body>

<div class="container">

  <jsp:include page="header.jsp"/>

  <div class="row">
    <div class="col-9">
      <h1><fmt:message key="welcome.to.the"/> <strong><fmt:message key="hotel.ca"/></strong></h1>
    </div>
    <div class="col">
      <p>
      <div class="btn-group" role="group" aria-label="Basic example">
      <c:if test="${sessionScope.role == 'GUEST' || sessionScope.role == null}">
        <a href="${pageContext.request.contextPath}/controller/login_button" type="button" class="btn btn-secondary"><fmt:message key="login"/></a>
        <a href="${pageContext.request.contextPath}/controller/registration_button" type="button" class="btn btn-secondary"><fmt:message key="registration"/></a>
      </c:if>
      </div>
      </p>
      <p>
      <div class="btn-group" role="group" aria-label="Basic example">
        <a href="${pageContext.request.contextPath}/controller/show_rooms" type="button" class="btn btn-secondary"><fmt:message key="show.rooms"/></a>
      </div>
      </p>
    </div>
  </div>
</div>

</body>
</html>