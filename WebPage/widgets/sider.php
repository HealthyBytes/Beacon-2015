<div class="sider_label">
	<h4>Waiting patients</h1>
	<button type="button"class="btn btn-success next_patient">Next patient</button>	
</div>	
<div id="cssmenu" style="overflow-y: auto; max-height: 600px">	
	<ul class="list-group" id="patient_order">	
		<?php	 
		$q_get_pacients = "SELECT * FROM patient,doctor,queue WHERE patient.patient_id = queue.patient_id AND doctor.id = queue.doctor_id AND queue.doctor_id = ".$_SESSION['user_id']." ORDER BY queue.wait_idx";
		$res = mysqli_query($dbc, $q_get_pacients);	 
		echo mysqli_error($dbc);
			while($row = mysqli_fetch_assoc($res)){
		?>
			<li class="ui-state-default dropdown-toggle patients <?php echo $row["status"];?>" id="<?php echo $row["ehr_id"];?>">		
			  <a href="#" class="list-group-item">
				<div class="row">
					<div class="col-md-10 onlist_patient">
						<h4 class="list-group-item-heading">
							<span class="wait_name"></span>
							<span class="wait_surname"></span>
						</h4>
						<p class="list-group-item-text">
								
							<span class="wait_age"></span>												
							<span class="wait_gender"></span>	
							<span class="wait_born"></span>		
													
						</p>
					</div>	
					<div class="col-md-2 bars"><i class="fa fa-bars"></i></div>
				</div>
			  </a>
			  

			  <ul class="sider_dropright">
			  	<li class="uvrsti">Uvrsti</li>
			  	<li class="sprejmi">Sprejmi</li>
			  	<li class="odpusti">Odpusti</li> 			 
		
			  </ul> 
			  
			</li>

		<?php
			}
		?>
  	</ul>							
</div>