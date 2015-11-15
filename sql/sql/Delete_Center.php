<?php
include 'config.php';
$center_id = $_REQUEST['center_id'];

mysql_query("delete from center where center_id = '$center_id'", $con);
mysql_close($con);
?>
