 <div class="well demographics">
    <div class="row">
        <div class="col-md-3 col-md-offset-1">
            <div class="patient-pic"></div>
        </div>
        <div class="col-md-6 col-md-offset-1">
            <div class="patient-info">
                <h4 id="patient-name"></h4>
                <p>Age: <span class="patient-age"></span></p>
                <p>Gender: <span id="patient-gender"></span></p>
                <p>DOB: <span class="patient-dob"></span></p>
                <p>Address: <span> - </span></p>
            </div>
        </div>        
    </div>
</div>
<div class="patient-stats well" style="position: relative">
    <div class="row">
        <div class="col-md-8 bordered">
            <div class="row" style="min-height: 57px;">
                <div class="col-md-4 bordered centered" style="min-height: 67px;">
                    <p>Age:</p>
                    <div class="value patient-age-years"></div>
                </div>
                <div class="col-md-4 bordered centered" style="min-height: 67px;">
                    <p>Weight:</p>
                    <div class="value">
                        <span class="weight-placeholder-value"></span> <span class="weight-placeholder-unit"></span>
                    </div>
                </div>
                <div class="col-md-4 centered">
                    <p>BMI:</p>
                    <div class="value patient-bmi"></div>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-4">
                                <p>Blood Pressure:</p>
                                <div class="value last-bp"></div>
                        </div>
                        <div class="col-md-4 centered">
                            <div class="progress">
                                <div class="progress-bar bar-sys"></div>
                            </div>
                            <p>(Systolic)</p>
                        </div>
                        <div class="col-md-4 centered">
                            <div class="progress">
                                <div class="progress-bar bar-dia"></div>
                            </div>
                            <p>(Diastolic)</p>
                        </div>
                    </div>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-4">
                                <p>Oxygen Saturation:</p>
                                <div class="value last-spo2"></div>
                        </div>
                        <div class="col-md-8">
                            <div class="progress">
                                <div class="progress-bar progress-bar-success bar-spo2"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <img class="patient-height-image" src="./phr/img/body-placeholder.png" alt="Body">
                <div class="patient-height-stats">
                    <p>Height:</p>
                    <div class="value">
                        <span class="height-placeholder-value"></span> <span class="height-placeholder-unit"></span>
                    </div>
                </div>
        </div>
    </div>
</div>