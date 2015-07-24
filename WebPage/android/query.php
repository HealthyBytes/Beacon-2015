<?php 
	include '../db_connect/db_connect.php';
	if($dbc)
		echo "Successfully connected";
	
	 $q = "Select * from patient;";#$_GET["query"];
	$q = mysqli_escape_string($dbc, $q);
	
	$result = mysqli_query($dbc, $q); 
	$res = array();
	$res["result"] = array();
	if($result){
		#$res = mysqli_fetch_array($result, MYSQLI_ASSOC);
		while($row = mysqli_fetch_assoc($result))
			array_push($res["result"],$row);
		$res["mysql_error"] = "No";
	}
	else
		$res["mysql_error"] = "Yes";
	print json_encode($res);
?>