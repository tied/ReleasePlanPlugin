
window.onload = function addColumns() {
    var columncount = project.sprints; //get columns
    document.getElementById("startSprint").innerHTML = '<p class="sprint_title">Sprint 1</p><br>\n\
  <input style="width: 120px"  id="sprint_start_date" title="Sprintstartdate" onkeydown="return false" placeholder="Start Date" name="sprint_end_date" type="text"><br>\n\
  <input style="width: 120px"  id="sprint_end_date" title="Sprintenddate" onkeydown="return false" placeholder="End Date" name="sprint_end_date" type="text"><br>\n\
  <input style="width: 50px" type="number" value="' + 1 + '" >   \n\
    * \n\
  <input style="width: 50px" type="number" value="' + 1 + '" ><br>   \n\
    SP: <input style="width: 90px" type="number" value="' + project.storyPoints + '" ><br>';
    for (var i = 1; i < columncount; i++) {
        var row = document.getElementById("rp_headers");
        row.insertCell(i).innerHTML = '<td style="min-width:50px"><p class="sprint_title">Sprint ' +(i + 1) + '</p><br> \n\
        <input style="width: 120px"  id="sprint_start_date" title="Sprintstartdate" onkeydown="return false" placeholder="Start Date" name="sprint_end_date" type="text"><br>\n\
        <input style="width: 120px"  id="sprint_end_date" title="Sprintenddate" onkeydown="return false" placeholder="End Date" name="sprint_end_date" type="text"><br>\n\
        <input style="width: 50px" type="number" value="' + 1 + '" >   \n\
        * \n\
        <input style="width: 50px" type="number" value="' + 1 + '" ><br>   \n\
        SP: <input style="width: 90px" type="number" value="' + project.storyPoints + '" ><br></td>';

        $("#sprint_end_date" + i).datepicker({
            showOn: 'focus',
            showButtonPanel: true,
            closeText: 'Clear',
            dateFormat: 'dd.mm.yy'
        });

        var row = document.getElementById("rp_row_2");
        row.insertCell(i).innerHTML = '<td > </td>';


    }
    var startdateparts = project.startDate.split('-');
    var enddateparts = project.endDate.split('-');
    document.getElementById("start_date").innerHTML = 'Start: ' + startdateparts[2] + '.' + startdateparts[1] + '.' + startdateparts[0];
    document.getElementById("end_date").innerHTML = 'End: ' + enddateparts[2] + '.' + enddateparts[1] + '.' + enddateparts[0];
    document.getElementById("project_name").innerHTML = project.title + ' Release - Plan';
    //Hinweis: probier des moi für textfelder: $("#id").val("wert");
    //Project_name vielleicht in title umbenennen ^^

    $("#sprint_end_date").datepicker({
        showOn: 'focus',
        showButtonPanel: true,
        closeText: 'Clear',
        dateFormat: 'dd.mm.yy'
    });
}
