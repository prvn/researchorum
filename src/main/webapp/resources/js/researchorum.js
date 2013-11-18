$(document).ready(function() {
    $("#abstract").click(function () {
        var div = $(this).parent().next();
        if(div.is(":visible")) {
            div.hide();
        } else {
            div.fadeIn(450);
        };
        return false;
    });
});

function search() {
    var search_term = document.getElementById("search").value;
    var search_url = buildUrl(search_term);
    window.location.href = search_url;
}

function getBaseUrl() {
    if (!window.location.origin)
        window.location.origin = window.location.protocol+"//"+window.location.host;

    return window.location.origin;
}

function buildUrl(query_term) {
    var base_url = getBaseUrl();

    if(query_term.charAt(0) == '#') {
        query_term = query_term.substring(1);
        return base_url + "/search/tag/" + query_term;
    } else {
        return base_url + "/search/" + query_term;
    }
}
