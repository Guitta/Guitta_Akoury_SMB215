<?php


$servername = "localhost";
$username = "root";
$password = "admin";
$dbname = "GestionBiens";

$con = new mysqli($servername, $username, $password, $dbname);

if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
}

$sql = "SELECT * FROM transaction";

$result = $con->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row["transaction_id"].",".$row["item_id"].",".$row["username"].",".$row["location_id_src"].",".$row["location_id_dest"].",".$row["transport_id"].",".$row["status"].",".$row["transaction_date"].";";

    }
}

$con->close();


?> 
