<?php
include 'config.php';

$transport_id = $_REQUEST['transport_id'];
$personnel_id = $_REQUEST['personnel_id'];


mysql_query("update `transport` set personnel_id = '$personnel_id' where transport_id = '$transport_id'",$con);
mysql_close($con);
?> 
