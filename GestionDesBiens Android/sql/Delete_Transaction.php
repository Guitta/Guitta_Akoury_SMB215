<?php
include '$config.php';
$transaction_id = $_REQUEST['transaction_id'];

mysql_query("delete from `transaction` where transaction_id = '$transaction_id'",$con);
mysql_close($con);
?> 
