<?php  #Get sessionID
	$baseUrl = 'https://rest.ehrscape.com/rest/v1';
	$json = array();
	$data = array();
	$username = "ales.smokvina@gmail.com";
	$password = "ehr4ales";
	
	//set POST variables	
	$fields = array(
			'username'=> $username,
			'password' => $password			
	);	
	//url-ify the data for the POST
	$fields_string = "";
	foreach($fields as $key=>$value) { 
		$fields_string .= $key.'='.$value.'&'; 
	}
	rtrim($fields_string, '&');
	
	//open curl connection
	$ch = curl_init();
	# echo $baseUrl."/session?".$fields_string;
	//set the url, number of POST vars, POST data
	curl_setopt($ch,CURLOPT_URL, $baseUrl."/session?username=$username&password=$password");
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($ch,CURLOPT_POST, count($fields));
	curl_setopt($ch,CURLOPT_POSTFIELDS, $fields_string);
	
	//execute post
	$result = curl_exec($ch);
	
	//close connection
	curl_close($ch);
	$json = json_decode($result, true);
	# print_r($json);
	$sessionID = $json["sessionId"];	
	

	$data["sessionID"] = $sessionID;
?>

<?php #Create new user 
	
	//open connection
	$ch = curl_init();
	
	# echo $baseUrl."/session?".$fields_string;
	//set the url, number of POST vars, POST data
	curl_setopt($ch,CURLOPT_URL, $baseUrl."/ehr");
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($ch,CURLOPT_POST, count($fields));
	curl_setopt($ch,CURLOPT_POSTFIELDS, $fields_string);
	curl_setopt($ch, CURLOPT_HTTPHEADER, array(
    	"Ehr-Session: $sessionID"
    ));
	
	//execute post
	$result = curl_exec($ch);
	
	//close connection
	curl_close($ch);

	
	$json = json_decode($result, true);
	$ehrID = $json["ehrId"];	
	
	$data["ehrID"] = $ehrID;
	
	$fName = "Mary";
	$lName = "Novak";
	$dataBirth = "1982-7-18T19:30";
	print_r($data);

	$data = array(
			"firstNames" => urlencode($fName), 
			"lastNames" => urlencode($lName),
			/*"dateOfBirth" => urlencode($dataBirth),*/
			"partyAdditionalInfo" => array(
				"key" => urlencode("ehrId"),
				"value" => urlencode($ehrID)
				));                                                                    
	$data_string = json_encode($data);                                                                                   
                                    
	//open connection
	$ch = curl_init();
	
	# echo $baseUrl."/session?".$fields_string;
	//set the url, number of POST vars, POST data
	curl_setopt($ch,CURLOPT_URL, $baseUrl."/demographics/party");
	curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST"); 
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($ch,CURLOPT_POST, count($data));
	curl_setopt($ch,CURLOPT_POSTFIELDS, $data_string);
	curl_setopt($ch, CURLOPT_HTTPHEADER, array(                                                        
	   	"contentType: application/json",
    	"Ehr-Session: $sessionID")
    );
	
	//execute post
	$result = curl_exec($ch);
	
	//close connection
	curl_close($ch);

	
	$json = json_decode($result, true);
	
	if(strcmp($json["action"] , "CREATE") == 0)
		echo "successfully created patient".$json["meta"]["href"];
	print_r($data_string);
	echo "<br><br>";	
	# print_r($data);
	print_r($json);
?>