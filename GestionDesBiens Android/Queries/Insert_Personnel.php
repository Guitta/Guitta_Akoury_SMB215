<?php
include 'config.php';
$personnel_name = $_REQUEST['personnel_name'];


mysql_query("insert into personnel (personnel_name) values('$personnel_name')",$con);
mysql_close($con);
?> 
