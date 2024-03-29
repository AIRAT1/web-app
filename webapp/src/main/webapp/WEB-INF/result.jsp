<%@ page import="ru.itpark.domain.SearchQuery" %>
<%@ page import="java.util.List" %>
<%@ page import="java.nio.file.Path" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>RFC search</title>
    <%@ include file="bootstrap-css.jsp" %>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
            <h1>RFC search</h1>
            <form class="form-inline mr-auto">
                <button class="btn btn-primary mt-3" type="submit" onclick="form.action='/'"> Go Home</button>
            </form>

            <form action="<%= request.getContextPath() %>" method="post" class="form-inline mr-auto">
                <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search" name="query">
                <button class="btn blue-gradient btn-rounded btn-sm my-0" type="submit">Search</button>
            </form>

            <%  List<String> queries = (List<String>) request.getAttribute("userQueries");%>
            <%String name = request.getParameter("query");%>
            <%    Path result = (Path) request.getAttribute("path");%>
              <%  if (queries != null && !queries.isEmpty() && result != null) {%>
            <li><%out.println(name);%></li>
            <button type="button" class="btn btn-link" name="link" onclick="<%=result.toUri() + ".txt"%>"><%=result.toUri() + ".txt"%></button>
<%--            <a href="<%=result.toUri() + ".txt"%>" class="btn btn-info" role="button">Link Button</a>--%>
            <%}%>

        </div>
    </div>
</div>
<%@ include file="bootstrap-scripts.jsp" %>
</body>
</html>

