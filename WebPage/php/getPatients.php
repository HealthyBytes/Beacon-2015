<?php
	include '../db_connect/db_connect.php';
	$doctor_id = $_POST["doctor"];
	$q_get_pacients = "SELECT * FROM patient,doctor,queue WHERE patient.patient_id = queue.patient_id AND doctor.id = queue.doctor_id AND queue.doctor_id = $doctor_id ORDER BY queue.wait_idx";
	
	$res = mysqli_query($dbc, $q_get_pacients);	
	
	$arr = array();
	$arr2 = array();
	while ($row = mysqli_fetch_assoc($res)) {
		array_push($arr,$row["ehr_id"]);
		array_push($arr2,$row["status"]);
	}
	$res = array();
	$res["ehr_ids"] = $arr;
	$res["status"] = $arr2;
	echo json_encode($res);

?>