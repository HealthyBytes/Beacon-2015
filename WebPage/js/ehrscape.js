

function getSessionId(username,password) {
	
    var response = $.ajax({
        type: "POST",
        url: baseUrl + "/session?username=" + encodeURIComponent(username) +
                "&password=" + encodeURIComponent(password),
        async: false
    });
    return response.responseJSON.sessionId;
}



function query(){
	$.ajaxSetup({
	    headers: {
	        "Ehr-Session": sessionId
	    }
	});
	
	var aql = "SELECT c/uid/value as uid, " +
	    "c/context/start_time as time, " +
	    "c/name/value as name " +
	    "FROM EHR[ehr_id/value = '" + ehrId + "'] CONTAINS COMPOSITION c " +
	    "ORDER BY c/context/start_time DESC";
	$.ajax({
	    url: baseUrl + "/query?" + $.param({"aql": aql}),
	    type: 'GET',
	    success: function (res) {
	        $(".patient_info").html("Compositions<br>");
	        
	        var rows = res.resultSet;
	        for (var i in rows) {
	            $(".patient_info").append(rows[i].uid + ': ' + rows[i].name + ' (on ' +
	                                rows[i].time.value + ")<br>");
	        }
	    }
	});	
}	