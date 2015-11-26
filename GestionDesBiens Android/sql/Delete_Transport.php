<?php
include 'config.php';
$transport_id = $_REQUEST['transport_id'];


mysql_query("delete from transport where transport_id = '$transport_id'",$con);
mysql_close($con);
?> 
