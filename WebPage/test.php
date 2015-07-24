<!DOCTYPE html>
<html>
<head>
  <title>Twitter Bootstrap : Tabs Events using Javascript </title>
<link rel="stylesheet"
      href="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css">
<link rel="stylesheet"
     href="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap-theme.min.css">
<script src="http://code.jquery.com/jquery.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
</head>
<script>
$(document).ready(function(){
    $('a[data-toggle="tab"]').on('shown.bs.tab', function(e){
    	alert($(e.target).text());
        var currentTab = $(e.target).text(); // get current tab
        var LastTab = $(e.relatedTarget).text(); // get last tab
        $(".current-tab span").html(currentTab); 
        $(".last-tab span").html(LastTab);
    });
});
</script>
<style>
.DemoBS3{
      margin:40px;
}

</style>
<body>
<div class="DemoBS3">
 <ul id="myTab" class="nav nav-tabs">
      <li class="active"><a href="#android" data-toggle="tab">Android</a></li>
      <li><a href="#microsoft" data-toggle="tab">microsoft</a></li>
   
      <li class="dropdown">
        
        <a href="#" id="myTabDrop1" class="dropdown-toggle" 
        data-toggle="dropdown">More..<b class="caret"></b></a>
        
        <ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop1">
          <li><a href="#twitter" tabindex="-1" data-toggle="tab">Twitter</a></li>
          <li><a href="#wikipedia" tabindex="-1" data-toggle="tab">Wikipedia</a></li>
        </ul>
        
      </li>
    </ul>
    <div id="myTabContent" class="tab-content">
      <div class="tab-pane in active" id="android">
      <p>Android is an operating system based on the Linux kernel,
        and designed primarily for touchscreen mobile devices 
        and tablet computers. Initially developed by Android, Inc., 
        which was later bought in 2005 by Google Inc.</p>
      </div>
      <div class="tab-pane " id="microsoft">
       <p>Microsoft Windows is a series of graphical
         interface operating systems developed, marketed,
         and sold by Microsoft</p> 
    </div>
      <div class="tab-pane " id="twitter">
     <p> Twitter is an online social networking and 
       microblogging service that enables users 
       to send and read "tweets", which are text
       messages limited to 140 characters..</p>
     </div>
      <div class="tab-pane " id="wikipedia">
    <p>Wikipedia is the worlds largest, collaboratively edited,
      multilingual, free Internet encyclopedia that 
      is supported by the non-profit Wikimedia Foundation..</p>
    </div>
    </div>
 <hr>
  <p class="current-tab"><strong>Current Tab</strong>: <span></span></p>
    <p class="last-tab"><strong>Last Tab</strong>: <span></span></p>
  </div>
</body>
</html>