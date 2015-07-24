<div style="height: 100%">

  <!-- Nav tabs -->
  <ul class="nav nav-tabs" role="tablist">
    <li role="presentation" class="tab active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Basic</a></li>
 <!--<li role="presentation" class="tab"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Timeline</a></li> -->
    <li role="presentation" class="tab"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">Laboratory</a></li>
    <li role="presentation" class="tab graphs"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">Graphs</a></li>
  </ul>
  

  <!-- Tab panes -->
  <div class="tab-content" style="height: 90%">
    <div role="tabpanel" class="tab-pane active" id="home" style="height: 100%">
    	<?php include "./phr/basic.php"; ?>
    </div>
    <!--
    <div role="tabpanel" class="tab-pane" id="profile" style="height: 100%">
		<?php include "./phr/timeline.php"; ?>
	</div>
	-->
    <div role="tabpanel" class="tab-pane" id="messages" style="height: 100%">
		<?php include './phr/lab.php'; ?>
	</div>
    <div role="tabpanel" class="tab-pane" id="settings" style="height: 100%">
    	<?php include './phr/graph.php'; ?>
    </div>
   
		
	

</div>
