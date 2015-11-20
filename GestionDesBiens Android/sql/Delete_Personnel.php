<?php
include 'config.php';
$personnel_id = $_REQUEST['personnel_id'];


mysql_query("delete from personnel where personnel_id = '$personnel_id'",$con);
mysql_close($con);
?> 
