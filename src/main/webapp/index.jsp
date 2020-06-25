<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
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
      <h1><fmt:message key="page.welcome_to_the"/> <strong><fmt:message key="page.hotel_ca"/></strong></h1>
    </div>
    <div class="col">
      <p>
      <div class="btn-group" role="group" aria-label="Basic example">
      <c:if test="${sessionScope.role == 'GUEST' || sessionScope.role == null}">
        <a href="${pageContext.request.contextPath}/controller/login_button" type="button" class="btn btn-secondary"><fmt:message key="page.login"/></a>
        <a href="${pageContext.request.contextPath}/controller/registration_button" type="button" class="btn btn-secondary"><fmt:message key="page.registration"/></a>
      </c:if>
      </div>
      </p>
      <p>
      <div class="btn-group" role="group" aria-label="Basic example">
        <a href="${pageContext.request.contextPath}/controller/show_rooms" type="button" class="btn btn-secondary"><fmt:message key="page.show_rooms"/></a>
      </div>
      </p>
    </div>
  </div>
</div>

</body>
</html>