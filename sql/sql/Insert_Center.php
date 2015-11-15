<?php
include 'config.php';
$center_name = $_REQUEST['center_name'];

mysql_query("insert into center (center_name) values('$center_name')", $con);
mysql_close($con);
?>
