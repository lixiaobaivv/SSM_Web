<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="title" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<head>
    <title><title:insertAttribute name="title"/></title>
    <%-- 添加样式 --%>
    <style>
        table, table td, table th {
            border: 1px solid;
            border-collapse: collapse;
            text-align: center;
        }

        input {
            width: 100%;
            text-align: center;
            padding-left: 2px
        }

        #name, #name2 {
            width: 95%;
            text-align: center;
            padding-left: 2px
        }
    </style>
</head>

<html>
<tiles:insertAttribute name="body"/>
<%-- Sprict--%>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery-1.4.4.min.js"></script>
</html>