<?php
include '$config.php';
$item_id = $_REQUEST['item_id'];

mysql_query("delete from item where item_id = '$item_id'", $con);
mysql_close($con);
?>
