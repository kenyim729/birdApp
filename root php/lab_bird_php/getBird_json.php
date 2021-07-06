<?php
$host = "127.0.0.1";
$username = "root";
$password = "usbw";   // default password for "root" user is empty
$dbname =  "bird_system";

// Connect to server
$connect=mysql_connect($host, $username, $password) 
                    or die ("Sorry, unable to connect database server");

$dbselect=mysql_select_db($dbname,$connect) 
                    or die ("Sorry, unable to connect database");

$CreateUser = $_POST["CreateUser"];

// Run the query
$query = "SELECT * FROM birdhistory where createUser = '$CreateUser'";

// $query = "SELECT * FROM birdhistory";
				   
// printf($query);
$result = mysql_query($query);

if ($result) {

    while ($row = mysql_fetch_assoc($result)) {
	$output[] = $row;
    }

    print("{\"birdhistory\":");
    print(json_encode($output));
    print("}");
}

mysql_close();
?>

