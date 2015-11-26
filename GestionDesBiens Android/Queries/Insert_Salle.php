<?php
include 'config.php';
$salle_name = $_REQUEST['salle_name'];


mysql_query("insert into salle (salle_name) values('$salle_name')",$con);
mysql_close($con);
?> 
