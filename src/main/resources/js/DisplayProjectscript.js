window.onload = function addColumns() {
    document.getElementById("startSprint").innerHTML = '<p class="sprint_title">Sprint 1</p><br>\n\
  <input style="width: 120px"  id="sprint_start_date" title="Sprintstartdate" onkeydown="return false" placeholder="Start Date" name="sprint_end_date" type="text"><br>\n\
  <input style="width: 120px"  id="sprint_end_date" title="Sprintenddate" onkeydown="return false" placeholder="End Date" name="sprint_end_date" type="text"><br>\n\
  <input style="width: 50px" type="number" value="' + project.factor + '" >   \n\
    * \n\
  <input style="width: 50px" type="number" value="' + project.personDays + '" ><br>   \n\
    SP: <input style="width: 90px" type="number" value="' + project.storyPoints + '" ><br>';

    var columncount = calculateWeeksBetween(new Date(project.startDate), new Date(project.endDate));

    for (var i = 1; i < columncount; i++) {
        var row = document.getElementById("rp_headers");
        row.insertCell(i).innerHTML = '<td style="min-width:50px"><p class="sprint_title">Sprint ' + (i + 1) + '</p><br> \n\
        <input style="width: 120px"  id="sprint_start_date' + (i + 1) + '" title="Sprintstartdate" onkeydown="return false" placeholder="Start Date" name="sprint_end_date" type="text"><br>\n\
        <input style="width: 120px"  id="sprint_end_date' + (i + 1) + '" title="Sprintenddate" onkeydown="return false" placeholder="End Date" name="sprint_end_date" type="text"><br>\n\
        <input style="width: 50px" type="number" value="' + project.factor + '" >   \n\
        * \n\
        <input style="width: 50px" type="number" value="' + project.personDays + '" ><br>   \n\
        SP: <input style="width: 90px" type="number" value="' + project.storyPoints + '" ><br></td>';

        var row = document.getElementById("rp_row_2");
        row.insertCell(i).innerHTML = '<td > </td>';
    }
    displayProjectStats();
    fillTableWithDates();
}


function calculateWeeksBetween(date1, date2) {
    var ONE_WEEK = 1000 * 60 * 60 * 24 * 7;
    var date1_ms = date1.getTime();
    var date2_ms = date2.getTime();
    var difference_ms = Math.abs(date1_ms - date2_ms);
    return Math.floor(difference_ms / ONE_WEEK);
}

function displayProjectStats() {
    var startdateparts = project.startDate.split('-');
    var enddateparts = project.endDate.split('-');

    //$("#start_date").val('Start: ' + startdateparts[2] + '.' + startdateparts[1] + '.' + startdateparts[0]);
    //$("#end_date").val('End: ' + enddateparts[2] + '.' + enddateparts[1] + '.' + enddateparts[0]);
    //$("#project_title").val(project.title + ' Release - Plan');

    document.getElementById("start_date").innerHTML = 'Start: ' + startdateparts[2] + '.' + startdateparts[1] + '.' + startdateparts[0];
    document.getElementById("end_date").innerHTML = 'End: ' + enddateparts[2] + '.' + enddateparts[1] + '.' + enddateparts[0];
    document.getElementById("project_title").innerHTML = project.title + ' Release - Plan';


}

function fillTableWithDates() {
    var sprintDate = new Date(project.startDate);

    var sprintDuration = 6048e5 * project.sprintDuration;

    $("#sprint_start_date").datepicker({
        showOn: 'focus',
        showButtonPanel: true,
        closeText: 'Clear',
        dateFormat: 'dd.mm.yy'
    });
    $("#sprint_start_date").val(sprintDate.getDate() + '.' + (sprintDate.getMonth() + 1) + '.' + sprintDate.getFullYear());
    $("#sprint_end_date").datepicker({
        showOn: 'focus',
        showButtonPanel: true,
        closeText: 'Clear',
        dateFormat: 'dd.mm.yy'
    });
    sprintDate = new Date(+sprintDate + sprintDuration);
    sprintDate = new Date(+sprintDate - 864e5);
    $("#sprint_end_date").val(sprintDate.getDate() + '.' + (sprintDate.getMonth() + 1) + '.' + sprintDate.getFullYear());

    for (var i = 2; i <= rp_table.rows[0].cells.length; i++) {
        
        $("#sprint_start_date" + i).datepicker({
            showOn: 'focus',
            showButtonPanel: true,
            closeText: 'Clear',
            dateFormat: 'dd.mm.yy'
        });
        sprintDate = new Date(+sprintDate + 864e5);
        $("#sprint_start_date" + i).val(sprintDate.getDate() + '.' + (sprintDate.getMonth() + 1) + '.' + sprintDate.getFullYear());

        $("#sprint_end_date" + i).datepicker({
            showOn: 'focus',
            showButtonPanel: true,
            closeText: 'Clear',
            dateFormat: 'dd.mm.yy'
        });
        sprintDate = new Date(+sprintDate + sprintDuration);
        sprintDate = new Date(+sprintDate - 864e5);
        $("#sprint_end_date" + i).val(sprintDate.getDate() + '.' + (sprintDate.getMonth() + 1) + '.' + sprintDate.getFullYear());

    }

}