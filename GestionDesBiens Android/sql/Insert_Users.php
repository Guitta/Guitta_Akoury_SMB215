<?php
include 'config.php';

$username = $_REQUEST['username'];
$password = $_REQUEST['password'];
$name = $_REQUEST['name'];
$register_dt = $_REQUEST['register_dt'];
$groupname = $_REQUEST['groupname'];

mysql_query("INSERT INTO `groupe`(`username`, `groupname`) VALUES ('$username','$groupname')",$con);

mysql_query("INSERT INTO `users`(`username`, `password`, `name`, `register_dt`) VALUES ('$username','$password','$name',now())",$con);


?> 
