<?php
include 'config.php';
$type_id = $_REQUEST['type_id'];
$type_name = $_REQUEST['type_name'];


mysql_query("update `type` set type_name = '$type_name' where type_id = '$type_id'",$con);
mysql_close($con);
?> 
