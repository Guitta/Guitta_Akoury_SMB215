<?php
include 'config.php';
$salle_id = $_REQUEST['salle_id'];
$salle_name = $_REQUEST['salle_name'];


mysql_query("update salle set salle_name = '$salle_name' where salle_id = '$salle_id'",$con);
mysql_close($con);
?> 
