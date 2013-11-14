<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="/resources/css/base.css" />
        <link rel="stylesheet" href="/resources/css/bootstrap.css" />
        <link rel="stylesheet" href="/resources/css/researchorum.css" />

        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script type="text/javascript" src="/resources/js/researchorum.js"></script>
        <script type="text/javascript" src="/resources/js/simplePagination.js"></script>
    </head>
    <body>
        <tiles:insertAttribute name="header" />
        <tiles:insertAttribute name="body" />
        <tiles:insertAttribute name="footer" />
    </body>
</html>