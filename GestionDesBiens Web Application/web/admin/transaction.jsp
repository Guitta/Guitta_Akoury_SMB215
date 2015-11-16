
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="../css/style.css"/>
        <link rel="stylesheet" type="text/css" href="../css/jquery.datetimepicker.css"/>

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script type="text/javascript" language="javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
        <script type="text/javascript" language="javascript" src="http://code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
        <script type='text/javascript' src="http://codeinnovators.meximas.com/pdfexport/jspdf.debug.js"></script>

    </head>
    <body>
        <div class="menu"><a href="center.html">Centers</a><a href="item.html">Items</a> <a href="location.html">Location</a>
            <a href="salle.html">Salle</a><a href="transaction.jsp">Transaction</a><a href="transport.html">Transport</a>
            <a href="type.html">Type</a> <a href="personnel.html">Personnel</a><a href="users.html">Users</a> <a href="groupe.html">Groupe</a>
        </div>
        <input type="hidden" id="user" value="<%= request.getUserPrincipal().getName()%>" />
        <div id="loads"></div>
        <div class="choose">
            <input type="radio" name="options" value="interne">Interne<br>
            <input type="radio" name="options" value="externe">Externe <br />
            <button class="btn btn-warning btn-sm " id="options">Choose</button>
        </div>
        <div class="showme">
            <table id="table" class="hidden">
                <tr class="head">
                    <th class="export">Id</th>
                    <th class="export">Item</th>
                    <th class="export">Date</th>
                    <th class="export">Source</th>

                    <th class="export">Destination</th>

                    <th class="externe">Transport ID</th>
                    <th class="export map">Status</th>
                    <th class="map">Map</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
            </table>
            <div class="btn-group open">
                <button class="btn btn-warning btn-sm " id="export">Export Table Data</button>

            </div>
            <div><button class="new">New</button></div>

            <div class="Items none">
                <table>  
                    <tr class="justedit"><td>transaction Id</td><td><input id="transactionid" type="text" value=""  readonly /></td></tr>
                    <tr><td>Item</td><td><select id="item" ></select></td></tr>
                    <tr><td>Date</td><td><input type="datetime" id="date" /></td></tr>
                    <tr><td>Source</td><td><select id="source"></select></td></tr>
                    <tr><td>Destination</td><td><select id="destination"></select></td></tr>   
                    <tr class="externe"><td>Transport</td><td><select id="transport"></select></td></tr>

                    <tr class="externe map"><td>Status</td>
                        <td><select id="status" >
                                <option value="Delivered">Delivered</option>
                                <option value="Not Delivered">Not Delivered</option>
                            </select></td></tr>
                    <tr><td colspan="2"><button id="save" value="save">Save</button></td></tr>
                </table>
            </div>
        </div>

        <script src="../js/jquery.datetimepicker.full.js"></script>

        <script type="text/javascript">
            var txt = "";

            var trans = "";
            var items = "";
            $(document).ready(function () {
                $(".showme").css("display", "none");
                //$("#table").hide();
                $("#options").click(function () {

                    var option = $('input[name=options]:checked').val();
                    if (option == 'interne') {
                        $("#table tr:not(.head)").remove();
                        $(".map").hide();
                        $("#save").removeClass("externesave");
                        listinterne();
                        $(".externe").hide();
                    }
                    else {
                        if (option == 'externe') {
                            $("#table tr:not(.head)").remove();
                            listexterne();
                            $(".externe").show();
                            $(".map").show();
                            $("#save").addClass("externesave");
                        }
                    }
                    $(".showme").css("display", "block");
                });
                $.datetimepicker.setLocale('en');
                $('#date').datetimepicker({format: 'Y-m-d h:m:s', defaultHours: 0, defaultMinutes: 0});


                getAllLocation();

                getAlltransport();
                getAllitem();



                $('#export').click(demoFromHTML);

            });
            function demoFromHTML() {
                var pdf = new jsPDF('p', 'pt', 'letter');

                pdf.cellInitialize();
                pdf.setFontSize(10);
                var width = '';
                $.each($('#table tr'), function (i, row) {
                    $.each($(row).find("td.export, th.export"), function (j, cell) {
                        var txt = $(cell).text().trim() || " ";

                        var width = (j == 2) ? 150 : 59; //make with column smaller
                        pdf.cell(10, 50, width, 30, txt, i);
                    });
                });

                pdf.save('transaction.pdf');
            }

            function getAllitem() {

                $.ajax({
                    url: 'http://localhost:8080/GestionDesBiens/webresources/model.item/',
                    type: "GET",
                    dataType: "json",
                    success: function (result) {
                        if (result) {
                            var len = result.length;
                            console.log(result.length);
                            if (len > 0) {
                                for (var i = 0; i < len; i++) {


                                    items += "<option value='" + result[i].itemId + "'>" + result[i].itemName + "</option>";

                                    console.log(items);

                                }
                                console.log(items + "afetr for loop");

                            }
                        }
                        $("#item").append(items);


                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert('error: ' + textStatus + ': ' + errorThrown);
                    }

                });
                console.log(txt);

            }

            function getAllLocation() {

                $.ajax({
                    url: 'http://localhost:8080/GestionDesBiens/webresources/model.location/',
                    type: "GET",
                    dataType: "json",
                    success: function (result) {
                        if (result) {
                            var len = result.length;
                            console.log(result.length);
                            if (len > 0) {
                                for (var i = 0; i < len; i++) {


                                    txt += "<option value='" + result[i].locationId + "'>" + result[i].centerId.centerName + " - " + result[i].salleId.salleName + " - " + result[i].personnelId.personnelName + "</option>";

                                    console.log(txt);

                                }
                                console.log(txt + "afetr for loop");

                            }
                        }
                        $("#source").append(txt);
                        $("#destination").append(txt);

                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert('error: ' + textStatus + ': ' + errorThrown);
                    }

                });
                console.log(txt);

            }

            function getAlltransport() {

                $.ajax({
                    url: 'http://localhost:8080/GestionDesBiens/webresources/model.transport/',
                    type: "GET",
                    dataType: "json",
                    success: function (result) {
                        if (result) {
                            var len = result.length;
                            console.log(result.length);
                            if (len > 0) {
                                for (var i = 0; i < len; i++) {


                                    trans += "<option value='" + result[i].transportId + "'>" + result[i].personnelId.personnelName + " - " + result[i].transportDate + "</option>";

                                    console.log(trans);

                                }
                                console.log(trans + "afetr for loop");

                            }
                        }
                        $("#transport").append(trans);


                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert('error: ' + textStatus + ': ' + errorThrown);
                    }

                });
                console.log(txt);

            }
            function listexterne() {
                $.ajax({
                    type: 'GET',
                    url: 'http://localhost:8080/GestionDesBiens/webresources/model.transaction',
                    data: $('#table').serialize(),
                    dataType: "json", //to parse string into JSON object,
                    beforeSend: function () {
                        // this is where we append a loading image
                        $('#loads').html('<div class="loading"><img src="../images/loading.gif" alt="Loading..." /></div>');
                    },
                    success: function (data) {

                        $('#loads').empty();
                        if (data) {
                            $("#table tr:not(.head)").remove();
                            var len = data.length;
                            var txt = "";
                            if (len > 0) {
                                for (var i = 0; i < len; i++) {
                                    if (data[i].transactionId) {
                                        var keys = Object.keys(data[i]);
                                        var check = false;
                                        for (var x = 0; x < keys.length; x++) {
                                            if (keys[x] == 'transportId') {
                                                check = true;
                                            }
                                        }

                                        //if (data[i].hasOwnProperty("transportId"))
                                        if (check)
                                        {
                                            if (data[i].status == 'Not Delivered') {
                                                var map = '<a href="maplocation.html">Map Location</a>';
                                            }
                                            else {
                                                var map = 'Delivered';
                                            }

                                            var transportData = data[i].transportId.transportId;

                                            txt += "<tr id='" + data[i].transactionId + "' ><td id='transaction_" + data[i].transactionId + "' class='export'>" + data[i].transactionId + "</td>\n\
                                               <td class='export'>" + data[i].itemId.itemName + "</td><td class='export'>" + data[i].transactionDate + "</td><td id='center_" + data[i].locationIdDest.locationId + "' class='export'>" + data[i].locationIdDest.locationId + "</td> <td id='personnel_" + data[i].locationIdSrc.locationId + "' class='export'>"
                                                    + data[i].locationIdSrc.locationId + "</td><td class='export externe'>" + data[i].transportId.transportId + "</td><td class='export'>" + data[i].status + "</td><td>" + map + "</td>\n\
                                                <td class='edit' id='edit_" + data[i].transactionId + "'>Edit </td><td class='delete' id='delete_" + data[i].transactionId + "'>Delete </td></tr>";

                                        }


                                    }
                                }
                                if (txt != "") {
                                    $("#table").append(txt).removeClass("hidden");
                                }
                                $(".externe").show();
                            }
                        }
                        $('.edit').click(edit);
                        $('.delete').click(delete2);
                        $('.new').click(New);

                    },
                    error: function () {
                        // failed request; give feedback to user
                        $('#loads').html('<p class="error"><strong>Oops!</strong> Try that again in a few moments.</p>');
                    }
                });
            }
            function listinterne() {
                $.ajax({
                    type: 'GET',
                    url: 'http://localhost:8080/GestionDesBiens/webresources/model.transaction',
                    data: $('#table').serialize(),
                    dataType: "json", //to parse string into JSON object,
                    beforeSend: function () {
                        // this is where we append a loading image
                        $('#loads').html('<div class="loading"><img src="../images/loading.gif" alt="Loading..." /></div>');
                    },
                    success: function (data) {
                        $('#loads').empty();
                        $("#table tr:not(.head)").remove();
                        if (data) {

                            var len = data.length;
                            var txt = "";
                            if (len > 0) {
                                for (var i = 0; i < len; i++) {
                                    if (data[i].transactionId) {
                                        var keys = Object.keys(data[i]);
                                        var check = false;
                                        for (var x = 0; x < keys.length; x++) {
                                            if (keys[x] == 'transportId') {
                                                check = true;
                                            }
                                        }

                                        //if (data[i].hasOwnProperty("transportId"))
                                        if (!check)

                                        {

                                            var transportData = 'Null';
                                            txt += "<tr id='" + data[i].transactionId + "' ><td id='transaction_" + data[i].transactionId + "' class='export'>" + data[i].transactionId + "</td>\n\
                                               <td class='export'>" + data[i].itemId.itemName + "</td><td class='export'>" + data[i].transactionDate + "</td><td id='center_" + data[i].locationIdDest.locationId + "' class='export'>" + data[i].locationIdDest.locationId + "</td><td id='personnel_" + data[i].locationIdSrc.locationId + "' class='export'>"
                                                    + data[i].locationIdSrc.locationId + "</td>\n\
                                                <td class='edit' id='edit_" + data[i].transactionId + "'>Edit </td><td class='delete' id='delete_" + data[i].transactionId + "'>Delete </td></tr>";


                                        }
                                    }
                                }
                                if (txt != "") {
                                    $("#table").append(txt).removeClass("hidden");
                                }
                            }
                        }
                        $('.edit').click(edit);
                        $('.delete').click(delete2);
                        $('.new').click(New);

                    },
                    error: function () {
                        // failed request; give feedback to user
                        $('#loads').html('<p class="error"><strong>Oops!</strong> Try that again in a few moments.</p>');
                    }
                });
            }
            function delete2() {

                console.log("beforedelete");

                var id = $(this).attr('id');
                var res = id.substring(7);

                $.ajax({
                    type: 'DELETE',
                    url: 'http://localhost:8080/GestionDesBiens/webresources/model.transaction/' + res,
                    beforeSend: function () {
                        // this is where we append a loading image
                        $('#loads').html('<div class="loading"><img src="../images/loading.gif" alt="Loading..." /></div>');
                    },
                    success: function (data) {
                        $("#loads").empty();

                        console.log('Item deleted successfully');
                    },
                    error: function (jqXHR) {
                        $("#loads").empty();
                        console.log('delete item error');
                    }
                });

                $("#" + res).remove();
                console.log("hi");

                console.log("delete");
            }


            function edit() {
                var id = $(this).attr('id');
                var res = id.substring(5);
                $.ajax({
                    url: 'http://localhost:8080/GestionDesBiens/webresources/model.transaction/' + res,
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
                        $('#loads').empty();
                        $(".justedit").empty();
                        $(".justedit").html("<td>transaction Id</td><td><input id='transactionid' value='" + data.transactionId + "' readonly /></td>");


                        $("#status").val(data.status);
                        $("#transactionid").data(data.transactionId);
                        $("#item").val(data.itemId.itemId);
                        $("#date").val(data.transactionDate);
                        $("#source").val(data.locationIdSrc.locationId);

                        $("#destination").val(data.locationIdDest.locationId);
                        var keys = Object.keys(data);
                        var check = false;
                        for (var x = 0; x < keys.length; x++) {
                            if (keys[x] == 'transportId') {
                                check = true;
                            }
                        }

                        if (check) {
                            $("#transport").val(data.transportId.transportId);
                        }
                        else {
                            $("#transport").val();
                        }


                    }
                });

                $(".none").css("display", "block");
                $(".justedit").show();

                console.log("delete");
            }

            function saveexterne() {

                var id = $("#transactionid").val();


                var item = $("#item").val();
                var itemname = $('#item').find(":selected").text();
                var source = $("#source").val();

                var destination = $("#destination").val();

                var status = $("#status").val();

                var username = $('#user').val();
                var transport = $("#transport").val();
                var firstdate = $("#date").val();
                var date = firstdate.substring(0, 10);
                date = date + "T" + firstdate.substring(11, 19) + "+00:00";

                if (id == '') {
                    console.log("vide");

                    $.ajax({url: "http://localhost:8080/GestionDesBiens/webresources/model.transaction/lastid", success: function (count) {

                            var len = count.length;

                            newid = 1;
                            if (len > 0) {
                                for (var i = 0; i < len; i++) {

                                    newid = Number(count[i].transactionId) + 1;

                                }

                            }
                            console.log(newid);

                            var edit = ' {"itemId":{"itemId":' + item + '},"locationIdDest":{"locationId":' + destination + '},"locationIdSrc":{"locationId":' + source + '},"status":"' + status + '","transactionDate":"' + date + '","transactionId":' + newid + ',"transportId":{"transportId":' + transport + '},"username":{"username":"' + username + '"}}';

                            $.ajax({
                                type: 'POST', // Use POST with X-HTTP-Method-Override or a straight PUT if appropriate.
                                dataType: 'application/json', // Set datatype - affects Accept header
                                contentType: "application/json; charset=utf-8",
                                url: "http://localhost:8080/GestionDesBiens/webresources/model.transaction/", // A valid URL
                                headers: {"X-HTTP-Method-Override": "PUT"}, // X-HTTP-Method-Override set to PUT.
                                data: edit, // Some data e.g. Valid JSON as a string
                                success: function (html) {
                                    $("#table tr:not(.head)").remove();
                                    listexterne();
                                    //noinspection DocumentWriteJS
                                    //  $(".none").hide();
                                    //  $("#table tr:not(.head)").remove();

                                    var row = "<tr id='" + newid + "' ><td id='transaction_" + newid + "'>" + newid + "</td>\n\
                                                <td>" + itemname + "</td><td>" + date + "</td><td id='center_" + destination + "'>" + destination + "</td><td id='personnel_" + source + "'>"
                                            + source + "</td><td>" + transport + "</td><td>" + status + "</td>\n\
                                                 <td class='edit' id='edit_" + newid + "'>Edit </td><td class='delete' id='delete_" + newid + "'>Delete </td></tr>";



                                    console.log("try to update");


                                    $("#table").append(row);
                                    console.log("updated");

                                }
                            });
                        }});




                }
                else {
                    var edit = ' {"itemId":{"itemId":' + item + '},"locationIdDest":{"locationId":' + destination + '},"locationIdSrc":{"locationId":' + source + '},"status":"' + status + '","transactionDate":"' + date + '","transactionId":' + id + ',"transportId":{"transportId":' + transport + '},"username":{"username":"' + username + '"}}';


                    jQuery.ajax({
                        url: "http://localhost:8080/GestionDesBiens/webresources/model.transaction/" + id,
                        type: 'PUT',
                        data: edit,
                        contentType: 'application/json; charset=utf-8',
                        success: function (html) {
                            //noinspection DocumentWriteJS
                            $(".none").hide();
                            $("#table tr:not(.head)").remove();
                            console.log("try to update");
                            listexterne();
                            console.log("updated");

                        }
                    });
                }
            }


            function saveinterne() {

                var id = $("#transactionid").val();


                var item = $("#item").val();
                var itemname = $('#item').find(":selected").text();
                var source = $("#source").val();

                var destination = $("#destination").val();

                var username = $("#user").val();

                var firstdate = $("#date").val();
                var date = firstdate.substring(0, 10);
                date = date + "T" + firstdate.substring(11, 19) + "+00:00";

                if (id == '') {

                    console.log("vide");

                    $.ajax({url: "http://localhost:8080/GestionDesBiens/webresources/model.transaction/lastid", success: function (count) {

                            var len = count.length;

                            newid = 1;
                            if (len > 0) {
                                for (var i = 0; i < len; i++) {

                                    newid = Number(count[i].transactionId) + 1;

                                }

                            }
                            console.log(newid);

                            var edit = ' {"itemId":{"itemId":' + item + '},"locationIdDest":{"locationId":' + destination + '},"locationIdSrc":{"locationId":' + source + '},"transactionDate":"' + date + '","status":" ","transactionId":' + newid + ',"username":{"username":"' + username + '"}}';

                            $.ajax({
                                type: 'POST', // Use POST with X-HTTP-Method-Override or a straight PUT if appropriate.
                                dataType: 'application/json', // Set datatype - affects Accept header
                                contentType: "application/json; charset=utf-8",
                                url: "http://localhost:8080/GestionDesBiens/webresources/model.transaction/", // A valid URL
                                headers: {"X-HTTP-Method-Override": "PUT"}, // X-HTTP-Method-Override set to PUT.
                                data: edit, // Some data e.g. Valid JSON as a string
                                success: function (html) {
                                    //noinspection DocumentWriteJS
                                    //  $(".none").hide();
                                    //  $("#table tr:not(.head)").remove();

                                    var row = "<tr id='" + newid + "' ><td id='transaction_" + newid + "'>" + newid + "</td>\n\
                                                <td>" + itemname + "</td><td>" + date + "</td><td id='center_" + destination + "'>" + destination + "</td><td id='personnel_" + source + "'>"
                                            + source + "</td>\n\
                                                 <td class='edit' id='edit_" + newid + "'>Edit </td><td class='delete' id='delete_" + newid + "'>Delete </td></tr>";



                                    console.log("try to update");

                                    $("#table").append(row);
                                    console.log("updated");

                                }
                            });
                        }});




                }
                else {
                    var edit = ' {"itemId":{"itemId":' + item + '},"locationIdDest":{"locationId":' + destination + '},"locationIdSrc":{"locationId":' + source + '},"transactionDate":"' + date + '","status":" ","transactionId":' + id + ',"username":{"username":"' + username + '"}}';


                    jQuery.ajax({
                        url: "http://localhost:8080/GestionDesBiens/webresources/model.transaction/" + id,
                        type: 'PUT',
                        data: edit,
                        contentType: 'application/json; charset=utf-8',
                        success: function (html) {
                            //noinspection DocumentWriteJS
                            $(".none").hide();
                            $("#table tr:not(.head)").remove();
                            console.log("try to update");
                            listinterne();
                            console.log("updated");

                        }
                    });
                }
            }
            $('#save').click(function (e) {
                if ($(this).hasClass("externesave")) {

                    saveexterne();

                }
                else {

                    saveinterne();


                }

            });
            function New() {

                $(".justedit").hide();
                $("#item").val("");
                $("#date").val("");
                $("#source").val("");

                $("#destination").val("");
                $("#transactionid").val("");

                $("#status").val("");
                $(".none").css("display", "block");
            }

        </script>

    </body>
</html>