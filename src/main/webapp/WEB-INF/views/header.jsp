<%@ include file="/WEB-INF/views/common.jsp"%>
<head>
    <title>Researchorum</title>
</head>
    <div id="fb-root"></div>
        <script>
            (function(d, s, id) {
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id)) return;
                    js = d.createElement(s); js.id = id;
                    js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
                    fjs.parentNode.insertBefore(js, fjs);
                }(document, 'script', 'facebook-jssdk'));
        </script>
        <div class="topbar">
            <div class="fill">
                <div class="container">
                    <h2><a href="/" class="brand">Researchorum</a><small>Revolutionizing research collaboration</small></h2>
                </div>
            </div>
        </div>

    <div class="container">
        <div class="content" style="padding-top: 60px;">
            <form name="search-form" id="search-form" action="search">
                <input style="width: 400px; height: 25px;" type="text" id="keyword" name="keyword" placeholder="Search (ex: hadoop or #hadoop for tag search)"/>
            </form>
        </div>
    </div>
