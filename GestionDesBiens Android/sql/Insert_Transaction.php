<?php
include 'config.php';

$item_id = $_REQUEST['item_id'];
$username = $_REQUEST['username'];
$location_id_src = $_REQUEST['location_id_src'];
$location_id_dest = $_REQUEST['location_id_dest'];
$transport_id = $_REQUEST['transport_id'];


mysql_query("INSERT INTO `transaction`(`item_id`, `transaction_date`, `username`, `location_id_src`, `location_id_dest`, `transport_id`, `status`) VALUES ($item_id,now(),'$username',$location_id_src,$location_id_dest,$transport_id,'Not Delivered')",$con);


?> 
