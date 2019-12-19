<%@ page import="ru.itpark.domain.SearchQuery" %>
<%@ page import="java.util.List" %>
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

<%--            <% if (request.getAttribute("items") != null) { %>--%>
<%--            <% for (SearchQuery item : (List<SearchQuery>) request.getAttribute("items")) { %>--%>
<%--            <div class="col-sm-6 mt-3">--%>
<%--                <h5><%= item.getName() %> successful added</h5>--%>
<%--                <h5><%= request.getContextPath() %>/images/<%= item.getImage() %> successful added</h5>--%>
<%--            </div>--%>
<%--            <% } %>--%>
<%--            <% } %>--%>





            <form action="<%= request.getContextPath() %>" method="post" enctype="multipart/form-data" class="mt-3">
<%--                <div class="form-group">--%>
<%--                    <label for="name">Name</label>--%>
<%--                    <input type="text" id="name" name="name" class="form-control" required>--%>
<%--                </div>--%>
<%--                <div class="form-group">--%>
<%--                    <label for="description">Description</label>--%>
<%--                    <textarea id="description" name="status" class="form-control" required></textarea>--%>
<%--                </div>--%>
                <div class="custom-file">
                    <input type="file" id="file" name="query" class="custom-file-input" accept="text/*" required>
                    <label class="custom-file-label" for="file">Choose text ...</label>
                </div>
                <button type="submit" class="btn btn-primary mt-3">Create</button>
            </form>

            <form class="form-inline mr-auto">
                <%--                <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search" name="name">--%>
                <button class="btn btn-primary mt-3" type="submit" onclick="form.action='result'"> Go To Search</button>
            </form>
        </div>
    </div>
</div>
<%@ include file="bootstrap-scripts.jsp" %>
</body>
</html>
