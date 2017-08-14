window.onload = function addColumns() {
    var columncount = project.sprints; //get columns
   // document.getElementById("startSprint").innerHTML='<p style="width: 100px">Sprint 1</p><br>   SP: <input style="width: 60px" type="number" value="'+project.storyPoints+'" ><br>';
    /*
    var incol='';
    for (var i = 0; i < columncount; i++) {
        incol=incol+'<col style="width: 10px"/>';
    }
    document.getElementById("col_group").innerHTML=incol;
    */
    for (var i = 1; i < columncount; i++) {
        var row = document.getElementById("rp_headers");
        row.insertCell(i).innerHTML = '<td style="min-width:50px"><p style="width: 100px">Sprint '+(i+1)+'</p><br>   SP: <input style="width: 60px" type="number" value="'+project.storyPoints+'" ><br></td>';

        var row = document.getElementById("rp_row_2");
        row.insertCell(i).innerHTML = '<td > </td>';

        var row = document.getElementById("rp_row_3");
        row.insertCell(i).innerHTML = '<td > </td>';
        
        
    }
    var startdateparts=project.startDate.split('-');
    var enddateparts=project.endDate.split('-');
       document.getElementById("start_date").innerHTML='Start: '+startdateparts[2]+'.'+startdateparts[1]+'.'+startdateparts[0];
        document.getElementById("end_date").innerHTML='End: '+enddateparts[2]+'.'+enddateparts[1]+'.'+enddateparts[0];
        document.getElementById("project_name").innerHTML=project.name +' Release - Plan';
}