
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
        <script src="http://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/sha256.js"></script>
    </head>
    <body>
        <input type="hidden" id="user" value="<%= request.getUserPrincipal().getName()%>" />
        <div id="loads"></div>

        <div class="Items">
            <table> 

                <tr><td>Name</td><td><input id="name" type="text" value="" /></td></tr>
                <tr><td>Current password</td><td><input id="old" type="password" value="" /></td></tr>
                <tr><td>password</td><td><input id="password" type="password" value="" /></td></tr>
                <tr><td>Confirm password</td><td><input id="password2" type="password" value="" /></td></tr>
                <tr><td colspan="2"></td><input id="date" type="hidden" value="" /></tr>
                <tr><td colspan="2"><button id="save" value="save">Save</button></td></tr>
            </table>
        </div>

        <script type="text/javascript">
            var user = $("#user").val();
            $(document).ready(function () {
                edit();

                function edit() {


                    $.ajax({
                        url: 'http://localhost:8080/GestionDesBiens/webresources/model.users/' + user,
                        type: "GET",
                        dataType: "json",
                        success: function (data) {
                            $('#loads').empty();
                            $("#password").val();
                            $("#old").val();
                            $("#name").val(data.name);
                            $("#date").val(data.registerDt);

                        }
                    });

                }


                $('#save').click(function (e) {

                    var name = $("#name").val();
                    var pass = $("#password").val();
                    var pass2 = $("#password2").val();
                    var date = $("#date").val();
                    $.ajax({
                        url: 'http://localhost:8080/GestionDesBiens/webresources/model.users/' + user,
                        type: "GET",
                        dataType: "json",
                        success: function (data) {
                            var check = data.password;
                            var old = $("#old").val();
                            old = CryptoJS.SHA256(old);
                            
                            if (check == old) {
                                if (pass = pass2) {



                                    var edit = '{"name":"' + name + '","password":"' + pass + '","registerDt":"' + date + '","username":"' + user + '"}';
                                    jQuery.ajax({
                                        url: "http://localhost:8080/GestionDesBiens/webresources/model.users/" + user,
                                        type: 'PUT',
                                        data: edit,
                                        contentType: 'application/json; charset=utf-8',
                                        success: function (html) {
                                            //noinspection DocumentWriteJS
                                            alert("password updated")

                                        }
                                    });
                                }
                                else {
                                    alert("Password doesn't match");
                                }


                            }
                            else{
                                alert("Kindly insert your correct current password");
                            }
                        }
                    });

                });



            });

        </script>

    </body>
</html>