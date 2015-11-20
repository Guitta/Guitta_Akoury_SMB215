<?php
$mysql_server='localhost';
$mysql_user='root';
$mysql_userpass='admin';
$mysql_db='GestionBiens';
//mysql_connect($mysql_server, $mysql_user, $mysql_userpass);
//mysql_select_db($mysql_db);

$con=mysql_connect("$mysql_server", "$mysql_user", "$mysql_userpass")or die("cannot connect");
mysql_select_db("$mysql_db")or die("cannot select DB");
