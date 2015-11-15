<?php
include 'config.php';
$personnel_id = $_REQUEST['personnel_id'];
$personnel_name = $_REQUEST['personnel_name'];


mysql_query("update personnel set personnel_name = '$personnel_name' where personnel_id = '$personnel_id'",$con);
mysql_close($con);
?> 
