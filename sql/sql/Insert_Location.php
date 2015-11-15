<?php
include 'config.php';
$center_id = $_REQUEST['center_id'];
$salle_id = $_REQUEST['salle_id'];
$personnel_id = $_REQUEST['personnel_id'];


mysql_query("insert into location (center_id, salle_id, personnel_id) values('$center_id', '$salle_id', '$personnel_id')",$con);
mysql_close($con);
?> 
