<?php
include '$config.php';
$item_id = $_REQUEST['item_id'];
$transaction_date = $_REQUEST['transaction_date'];
$username = $_REQUEST['username'];
$location_id_src = $_REQUEST['location_id_src'];
$location_id_now = $_REQUEST['location_id_now'];
$location_id_dest = $_REQUEST['location_id_dest'];
$transport_id = $_REQUEST['transport_id'];
$status = $_REQUEST['status'];

mysql_query("insert into salle (item_id, transaction_date, username, location_id_src, location_id_now, location_id_dest, transport_id, status)
values
('$item_id', '$transaction_date', '$username', '$location_id_src', '$location_id_now', '$location_id_dest', '$transport_id', '$status')",$con);
mysql_close($con);
?> 
