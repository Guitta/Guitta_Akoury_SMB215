<?php
include 'config.php';
$id = $_REQUEST['id'];

mysql_query("delete from groupe where id = '$id'", $con);
mysql_close($con);
?>
