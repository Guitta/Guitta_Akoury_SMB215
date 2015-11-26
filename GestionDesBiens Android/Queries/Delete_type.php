<?php
include 'config.php';
$type_id = $_REQUEST['type_id'];

mysql_query("delete from `type` where type_id = '$type_id'",$con);
mysql_close($con);
?> 
