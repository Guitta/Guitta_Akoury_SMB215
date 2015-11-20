<?php
include 'config.php';
$id = $_REQUEST['id'];
$username = $_REQUEST['username'];
$groupname = $_REQUEST['groupname'];
$Description = $_REQUEST['Description'];

mysql_query("update groupe set username = '$username', groupname = '$groupname', Description = '$Description' where id = '$id'", $con);
mysql_close($con);
?>
