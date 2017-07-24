$(function () {
    $("#start-date").datepicker();
    $("#end-date").datepicker();
    $("#start-date").datepicker("option", "dateFormat", "dd.mm.yy");
    $("#end-date").datepicker("option", "dateFormat", "dd.mm.yy");
    var dialog = document.getElementById("dialog");
    dialog.setAttribute("hidden",null);

    $("#create-user").button().on("click", function () {

        $("#dialog").dialog();

        console.warn("click");
        var blanket = document.getElementById("blanket");
        blanket.removeAttribute("hidden");
    });

});

function cancelClicked()
{
    console.warn("click");
    $("#dialog").dialog("close");
    var blanket = document.getElementById("blanket");
    blanket.setAttribute("hidden", null);
}

function createProject()
{
    $("#dialog").dialog();
    var dialog = document.getElementById("dialog");
    dialog.removeAttribute("hidden");
    console.warn("click");
    var blanket = document.getElementById("blanket");
    blanket.removeAttribute("hidden");
}