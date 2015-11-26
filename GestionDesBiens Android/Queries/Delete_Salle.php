<?php
include 'config.php';
$salle_id = $_REQUEST['salle_id'];

mysql_query("delete from salle where salle_id = '$salle_id'",$con);
mysql_close($con);
?> 
