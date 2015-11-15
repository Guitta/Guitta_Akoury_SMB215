<?php
include 'config.php';
$location_id = $_REQUEST['location_id'];
$center_id = $_REQUEST['center_id'];
$salle_id = $_REQUEST['salle_id'];
$personnel_id = $_REQUEST['personnel_id'];


mysql_query("update location center_id = '$center_id', center_id', salle_id = '$salle_id', personnel_id = '$personnel_id' where location_id = '$location_id'",$con);
mysql_close($con);
?> 
