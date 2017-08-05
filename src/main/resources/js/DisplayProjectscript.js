window.onload = function addColumns() {
    var columncount = 2; //get columns

    for (var i = 1; i < columncount; i++) {
        var row = document.getElementById("rp_headers");
        row.insertCell(i).innerHTML = '<td ><p>Sprint '+(i+1)+'</p><p>SP:</p></td>';

        var row = document.getElementById("rp_row_2");
        row.insertCell(i).innerHTML = '<td > </td>';

        var row = document.getElementById("rp_row_3");
        row.insertCell(i).innerHTML = '<td > </td>';

    }
}