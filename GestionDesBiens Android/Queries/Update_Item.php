<?php
include 'config.php';
$item_id = $_REQUEST['item_id'];
$item_code = $_REQUEST['item_code'];

$item_name = $_REQUEST['item_name'];
$type_id = $_REQUEST['type_id'];
$item_specification = $_REQUEST['item_specification'];
$location_id = $_REQUEST['location_id'];


mysql_query("update item set item_code = '$item_code',
item_name = '$item_name', type_id = '$type_id', item_specification = '$item_specification', location_id = '$location_id'
where
item_id = '$item_id'", $con);
mysql_close($con);
?>
