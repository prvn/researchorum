<%@ include file="/WEB-INF/views/common.jsp"%>
<%@page import="com.researchorum.models.Record"%>
<head>
    <link rel="stylesheet" type="text/css" href="/resources/css/simplePagination.css" />
    <script type="text/javascript">
        $(document).ready(function() {
                document.getElementById('keyword').value = "{{ query }}";
                $('#light-pagination').pagination({
                    pages: {{ pagination_urls|length }},
                    hrefTextPrefix: getBaseUrl() + '?ipp=' + {{ ipp }} + '&pg=',
                    currentPage: {{ pg }},
                    cssStyle: 'light-theme'
                });
        });
        function getCurrentPage() {
            return $("#light-pagination").pagination("getCurrentPage");
        }
        function toggle_visibility(id) {
            var e = document.getElementById(id);
            if(e.style.display == 'block')
                e.style.display = 'none';
            else
                e.style.display = 'block';
        }
    </script>
</head>
<c:forEach var="post" items="${records}" >
<div class="container">
    <div class="content">
        <div>
            <div class="circle" style="float: left; width: 50px; background-image:url('${post.image}'); margin-right: 30px;"></div>
            <div style="float: left;">
                <h2 style="color: #06c; text-decoration: none"><a href="${post.slug}">${post.title}</a></h2>
                <p><a href="#" id="abstract" class="btn btn-large btn-block btn-primary" title="Abstract">Abstract</a></p>
                <div style="display: none;">
                    <p>"${post.body}"</p>
                    <p><img src="${post.image}" /><p>
                </div>
                <p>
                <%--
                <c:set var="total" value="${fn:length(post.comments)}" />
                ${total} comments |
                <c:set var="total_tags" value="${fn:length(post.tags)}" />
                ${total_tags} tags |
                --%>
                <c:forEach var="author" items="${post.authors}" varStatus="author_list">
                <c:set var="author_list" value="${author_list} ${author}" />
                </c:forEach>
                <c:set var="truncated_authors" value="${fn:substring(myVar, 0, 50)}" />
                ${truncated_authors}
                </p>
            </div>
        </div>
        <div style="clear: both;"></div>
    </div>
</div>
</c:forEach>
<br/>
<div class="pagination-holder clearfix">
    <%--<div style="width: 50%; margin: 0 auto;" id="light-pagination" class="light-theme simple-pagination"></div>--%>
</div>
