<?php
include 'config.php';

$personnel_id = $_REQUEST['personnel_id'];


mysql_query("insert into transport (personnel_id) values('$personnel_id')",$con);
mysql_close($con);
?> 
