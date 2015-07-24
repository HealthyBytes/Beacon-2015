<?php

	include '../db_connect/db_connect.php';
	include '../setup/functions.php';	
	$doctorID = $_POST["doctor"]; 
	
		
	$fPos = $_POST["firstPos"];
	$sPos = $_POST["secondPos"];
	
	$sq1 = "SELECT wait_idx,patient_id FROM queue WHERE doctor_id = $doctorID order by wait_idx limit 1 offset $fPos";
	$sq2 = "SELECT wait_idx,patient_id FROM queue WHERE doctor_id = $doctorID order by wait_idx limit 1 offset $sPos";
	
	#Pridobimo wait_idx
	$idx_fPos = mysqli_query($dbc, $sq1);
	$idx_sPos = mysqli_query($dbc, $sq2);

	$idx_rfPos = mysqli_fetch_assoc($idx_fPos);
	$idx_rsPos = mysqli_fetch_assoc($idx_sPos);

	$fID = $idx_rfPos["patient_id"];
	$sID = $idx_rsPos["patient_id"];	
	
	
	$q_inc = "";
	$q_update_changed = "";
	#prestavlanje pacienta navzgor
	if($idx_rfPos > $idx_rsPos){
		$q_inc = "UPDATE queue SET wait_idx = wait_idx + 1 WHERE doctor_id = $doctorID AND wait_idx < ".$idx_rfPos['wait_idx']." AND wait_idx >= ".$idx_rsPos["wait_idx"].";";
		$q_update_changed = "UPDATE queue SET wait_idx = ".$idx_rsPos['wait_idx']." WHERE queue.patient_id = '$fID'";
	}
	else{
		$q_inc = "UPDATE queue SET wait_idx = wait_idx - 1 WHERE doctor_id = $doctorID AND wait_idx > ".$idx_rfPos['wait_idx']." AND wait_idx <= ".$idx_rsPos["wait_idx"]."; ";
		$q_update_changed = "UPDATE queue SET wait_idx = ".$idx_rsPos['wait_idx']." WHERE queue.patient_id = '$fID' ";
	}
	$res = mysqli_query($dbc, "START TRANSACTION;");
	$res1 = mysqli_query($dbc, $q_inc);
	$res2 = mysqli_query($dbc, $q_update_changed);
	$res3 = mysqli_query($dbc, "COMMIT;");
	
	$res = array();
	$res["fid"] = $fID;
	if(!$res1 or !$res2 or !$res or !$res3){
		$res4 = mysqli_query($dbc, "ROLLBACK;");
		$res["sq1"] = $sq1;
		$res["sq2"] = $sq2;
		$res["status"] = "OK";
		$res["q1"] = $q_inc;
		$res["q2"] = $q_update_changed;
		$res["fid"] = $fID;
		$res["sid"] = $sID;		
		$res["error"] = mysqli_error($dbc);
		echo json_encode($res);
		return;
	}

	$res["sq1"] = $sq1;
	$res["sq2"] = $sq2;
	$res["status"] = "OK";
	$res["q1"] = $q_inc;
	$res["q2"] = $q_update_changed;


	echo json_encode($res);
?>
