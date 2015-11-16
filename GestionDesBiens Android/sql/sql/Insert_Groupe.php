<?php
include 'config.php';
$username = $_REQUEST['username'];
$groupname = $_REQUEST['groupname'];
$Description = $_REQUEST['Description'];

mysql_query("insert into groupe (username, groupname, Description) values('$username', '$groupname', '$Description')", $con);
mysql_close($con);
?>
