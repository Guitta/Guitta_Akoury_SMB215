<?php
include 'config.php';
$type_name = $_REQUEST['type_name'];


mysql_query("insert into type (type_name) values('$type_name')",$con);
mysql_close($con);
?> 
