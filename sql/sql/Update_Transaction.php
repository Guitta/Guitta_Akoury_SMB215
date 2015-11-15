<?php
include '$config.php';
$transaction_id = $_REQUEST['transaction_id'];
$item_id = $_REQUEST['item_id'];
$transaction_date = $_REQUEST['transaction_date'];
$username = $_REQUEST['username'];
$location_id_src = $_REQUEST['location_id_src'];
$location_id_now = $_REQUEST['location_id_now'];
$location_id_dest = $_REQUEST['location_id_dest'];
$transport_id = $_REQUEST['transport_id'];
$status = $_REQUEST['status'];

mysql_query("update `transaction` set item_id = '$item_id', transaction_date = '$transaction_date', username = '$username', location_id_src = '$location_id_src',
location_id_now = '$location_id_now', location_id_dest = '$location_id_dest', transport_id = '$transport_id', status = '$status'
where
transaction_id = '$transaction_id'",$con);
mysql_close($con);
?> 
