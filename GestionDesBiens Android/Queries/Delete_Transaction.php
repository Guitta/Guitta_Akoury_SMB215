<?php
include 'config.php';
$transaction_id = $_REQUEST['transaction_id'];
echo "start del2. ";

mysql_query("delete from `transaction` where transaction_id = '$transaction_id'",$con);

echo "end";
?> 
