window.onload = function addColumns() {
    var columncount = project.sprints; //get columns
    
    document.getElementById("startSprint").innerHTML='<p>Sprint 1</p><p >SP: '+project.storyPoints+'</p>';
    
    for (var i = 1; i < columncount; i++) {
        var row = document.getElementById("rp_headers");
        row.insertCell(i).innerHTML = '<td ><p>Sprint '+(i+1)+'</p><p>SP: '+project.storyPoints+'</p></td>';

        var row = document.getElementById("rp_row_2");
        row.insertCell(i).innerHTML = '<td > </td>';

        var row = document.getElementById("rp_row_3");
        row.insertCell(i).innerHTML = '<td > </td>';
    }
    var startdateparts=project.startDate.split('-');
    var enddateparts=project.endDate.split('-');
       document.getElementById("start_date").innerHTML='Start: '+startdateparts[2]+'.'+startdateparts[1]+'.'+startdateparts[0];
        document.getElementById("end_date").innerHTML='End: '+enddateparts[2]+'.'+enddateparts[1]+'.'+enddateparts[0];
        document.getElementById("project_name").innerHTML='Start: '+project.name +' - Plan';
}