jQuery(document).ready(function($){
    var elements = document.getElementsByClassName("location");

    for(var i=0; i < elements.length; i++) {
        elements[i].value =  document.location.href
    }
});



