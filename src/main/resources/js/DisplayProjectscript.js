window.onload = function onLoad() {
    document.getElementById("startSprint").innerHTML = '<p class="sprint_title">Sprint 1</p><br>\n\
  <input style="width: 120px"  id="sprint_start_date" title="Sprintstartdate" onkeydown="return false" placeholder="Start Date" name="sprint_end_date" type="text"><br>\n\
  <input style="width: 120px"  id="sprint_end_date" title="Sprintenddate" onkeydown="return false" placeholder="End Date" name="sprint_end_date" type="text"><br>\n\
  <input id="factorInput_0" style="width: 50px" type="number" value="' + project.factor + '" onChange="factorPDchanged(0)">   \n\
    * \n\
  <input id="personDaysInput_0" style="width: 50px" type="number" value="' + project.personDays + '" onChange="factorPDchanged(0)" ><br>   \n\
    SP: <input id="storyPointsInput_0" style="width: 90px" type="text" disabled="true" value="'  + project.storyPoints + '" ><br>';

    var columncount = calculateWeeksBetween(new Date(project.startDate), new Date(project.endDate));

    for (var i = 1; i < columncount; i++) {
        var row = document.getElementById("rp_headers");
        row.insertCell(i).innerHTML = '<td style="min-width:50px"><p class="sprint_title">Sprint ' + (i + 1) + '</p><br> \n\
        <input style="width: 120px"  id="sprint_start_date' + (i) + '" title="Sprintstartdate" onkeydown="return false" placeholder="Start Date" name="sprint_end_date" type="text"><br>\n\
        <input style="width: 120px"  id="sprint_end_date' + (i) + '" title="Sprintenddate" onkeydown="return false" placeholder="End Date" name="sprint_end_date" type="text"><br>\n\
        <input  id="factorInput_'+i+'" style="width: 50px" type="number" value="' + project.factor + '" onChange="factorPDchanged('+i+')" >   \n\
        * \n\
        <input id="personDaysInput_'+i+'" style="width: 50px" type="number" value="' + project.personDays + '" onChange="factorPDchanged('+i+')" ><br>   \n\
        SP: <input id="storyPointsInput_'+i+'" style="width: 90px" type="text" disabled="true" value="' + project.storyPoints + '" ><br></td>';

    }
    
    displayProjectStats();
    fillTableWithDates(columncount);
    addTextHeader(columncount);
    innerTable(columncount);
    dragDrop();
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

    document.getElementById("start_date").innerHTML = 'Start: ' + startdateparts[2] + '.' + startdateparts[1] + '.' + startdateparts[0];
    document.getElementById("end_date").innerHTML = 'End: ' + enddateparts[2] + '.' + enddateparts[1] + '.' + enddateparts[0];
    document.getElementById("project_title").innerHTML = project.title + ' Release - Plan';


}

function fillTableWithDates(columncount) {
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
    
    for (var i = 1; i <= columncount; i++) {
         console.log("loop"+i);
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
            console.log("loop"+i);

    }

}


function addTextHeader(columncount) {
        for (var i = 0; i < columncount; i++) {
        var row = document.getElementById("rp_textHeader");
                row.insertCell(i).innerHTML = '  <td> <textarea maxlength="50" rows="3" ></textarea> </td>';
        }
    }
    
    /**
 * Comment
 */

function innerTable(columncount) {
    document.getElementById("colSpan").colSpan = columncount;
    var row = document.getElementById("inner_Table_tr");
    row.insertCell(0).innerHTML = '<td id="inner_Table_td_'+i+'">  <span draggable="true" class="event" background="black" >Epic 1</span> </td>';
    for (var i = 1; i < columncount; i++) {
        row.insertCell(i).innerHTML = '<td >  </td>';
    }
      //  $("#inner_Table_td_0").innerHTML='<span draggable="true" class="dragEvent" >asdf</span>';

}

/**
 * Comment
 */
function factorPDchanged(columncount) {
    console.log("before");
 document.getElementById("storyPointsInput_"+columncount).value=
         document.getElementById("factorInput_"+columncount).value  *  document.getElementById("personDaysInput_"+columncount).value;
     console.log("after");

}

/**
 * Comment
 */
function dragDrop() {
      $(document).ready(function () {
        $('.event').on("dragstart", function (event) {
            var dt = event.originalEvent.dataTransfer;
            dt.setData('Text', $(this).attr('id'));
        });
        $('#inner_Table td').on("dragenter dragover drop", function (event) {
            event.preventDefault();
           // console.table(event);
            if (event.type === 'drop') {
                var data = event.originalEvent.dataTransfer.getData('Text', $(this).attr('id'));
                console.table(event);
                console.log("orE:");
                console.log(event.originalEvent.dataTransfer.getData('Text', $(this).attr('id')));

                de = $('#' + data).detach();
                if (event.originalEvent.target.tagName === "SPAN") {
                    de.insertBefore($(event.originalEvent.target));
                   
                    console.log("==span");
                }
                else {
                                        console.log("not span");

                    de.appendTo($(this));
                }
            };
        });
    })
}