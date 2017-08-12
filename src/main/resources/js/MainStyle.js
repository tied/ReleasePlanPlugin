$(function ()
{
    var dialog = document.getElementById("dialog");
    dialog.setAttribute("hidden", null);
//    document.getElementById("man-days").addEventListener("input", calcSP());
//    document.getElementById("man-days").addEventListener("input", calcSP());
    init();
    datePickers();

});

function init()
{
    console.log("INIT");
    $("#man-days").change(calcSP);
    $("#factor").change(calcSP);
}

function calcSP()
{
    console.log("calc");
    var sp = ($("#man-days").val()*$("#factor").val());
    $("#story-points").val(sp);
}

function cancelClicked()
{
    console.warn("click");
    $("#dialog").dialog("close");
}

function createProject()
{
    $("#dialog").dialog({
        resizable: false,
        height: 380,
        width: 400,
        modal: true
    });
    var dialog = document.getElementById("dialog");
    dialog.removeAttribute("hidden");
    console.warn("click");
}

function datePickers()
{
    var sd = document.getElementById("start-date");
    var d = new Date();
    var mo = d.getMonth() + 1;
    if (mo.toString().length === 1)
    {
        mo = "0" + mo.toString();
    }
    var day = d.getDate();
    if (day.toString().length === 1)
    {
        day = "0" + day.toString();
        console.log(day.toString());
    }
    sd.value = day + "." + mo + "." + d.getFullYear();

    $("#start-date").datepicker({
        showOn: 'focus',
        showButtonPanel: true,
        closeText: 'Clear', // Text to show for "close" button
        dateFormat: 'dd.mm.yy',
        onClose: function ()
        {
            var event = arguments.callee.caller.caller.arguments[0];
            // If "Clear" gets clicked, then really clear it
            if ($(event.delegateTarget).hasClass('ui-datepicker-close'))
            {
                $(this).val('');
            }
        }
    });

    $("#end-date").datepicker({
        showOn: 'focus',
        showButtonPanel: true,
        closeText: 'Clear', // Text to show for "close" button
        dateFormat: 'dd.mm.yy',
        onClose: function ()
        {
            var event = arguments.callee.caller.caller.arguments[0];
            // If "Clear" gets clicked, then really clear it
            if ($(event.delegateTarget).hasClass('ui-datepicker-close'))
            {
                $(this).val('');
            }
        }
    });

    restrictEndDate();

}

function restrictEndDate()
{

    var day = $("#start-date").datepicker('getDate').getDate();
    var month = $("#start-date").datepicker('getDate').getMonth() + 1;
    var year = $("#start-date").datepicker('getDate').getFullYear();
    var date = new Date(year + "-" + month + "-" + day);
    console.log(date);

    $("#end-date").datepicker("destroy");
    $("#end-date").datepicker({
        showOn: 'focus',
        showButtonPanel: true,
        closeText: 'Clear', // Text to show for "close" button
        dateFormat: 'dd.mm.yy',
        minDate: date,
        onClose: function ()
        {
            var event = arguments.callee.caller.caller.arguments[0];
            // If "Clear" gets clicked, then really clear it
            if ($(event.delegateTarget).hasClass('ui-datepicker-close'))
            {
                $(this).val('');
            }
        }
    });

}

function validateForm()
{
    var form = document.forms["createProjectForm"];
    var projectName = form["ProjectName"].value;
    var startDate = form["StartDate"].value;
    var endDate = form["EndDate"].value;
    var storyPoints = form["StoryPoints"].value;

    if (projectName == "" || startDate == "" || endDate == "" || storyPoints == "")
    {
        alert("Irgendwas");
        return false;
    }
}