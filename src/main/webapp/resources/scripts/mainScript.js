$ = jQuery;

    $("#divWithImageMap").on('click', function (e) {
        let offset = $(this).offset();
        let X = (e.pageX - offset.left - 200) / 35;
        let Y = -1 * (e.pageY - offset.top - 200) / 35;
        $("#hiddenForm\\:hiddenX").val(X);
        $("#hiddenForm\\:hiddenY").val(Y);
        csClickOnMap();
    });

    $("#tableR commandLink").on('click', function (e) {
       document.getElementById("form:errorR").style.display = "none";
    });



