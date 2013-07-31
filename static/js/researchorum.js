$(document).ready(function() {
    $(".button").click(function () {
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
    if (!window.location.origin)
        window.location.origin = window.location.protocol+"//"+window.location.host;
    var base_url = window.location.origin;
    var search_url = base_url + "/search/" + document.getElementById("search").value;
    window.location.href = search_url;
}
