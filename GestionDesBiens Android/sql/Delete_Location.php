<?php
include 'config.php';
$location_id = $_REQUEST['location_id'];


mysql_query("delete from location where location_id = '$location_id'",$con);
mysql_close($con);
?> 
