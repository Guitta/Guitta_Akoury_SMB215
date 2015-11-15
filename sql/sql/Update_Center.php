<?php
include 'config.php';
$center_id = $_REQUEST['center_id'];
$center_name = $_REQUEST['center_name'];

mysql_query("update center set center_name = '$center_name' where center_id = '$center_id'", $con);
mysql_close($con);
?>
