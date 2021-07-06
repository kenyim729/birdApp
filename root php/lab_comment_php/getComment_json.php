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

// Run the query
$query = "SELECT * FROM discussrecord";
				   
// printf($query);
$result = mysql_query($query);

if ($result) {

    while ($row = mysql_fetch_assoc($result)) {
	$output[] = $row;
    }

    print("{\"discussrecord\":");
    print(json_encode($output));
    print("}");
}

mysql_close();
?>

