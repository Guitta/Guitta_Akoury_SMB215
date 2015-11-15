<?php
include '$config.php';
$item_code = $_REQUEST['item_code'];
$item_date_created = $_REQUEST['item_date_created'];
$item_name = $_REQUEST['item_name'];
$type_id = $_REQUEST['type_id'];
$item_specification = $_REQUEST['item_specification'];
$location_id = $_REQUEST['location_id'];


mysql_query("insert into item (item_code, item_date_created, item_name, type_id, item_specification, location_id) values('$item_code', '$item_date_created', '$item_name', '$type_id', '$item_specification', '$location_id')", $con);
mysql_close($con);
?>
