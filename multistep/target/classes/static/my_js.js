var current_user = "none";

$(document).ready(function () {
    if (current_user != "none") {
        $("#regDiv").hide();
        $("#formDiv").show();
    }
})

function registerShowPassword() {
    var x = document.getElementById("registerPassword");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}

 var asyncRequest
       function startRandomPrediction(){

                    $.ajax({
                    type: "POST",
                    url: "/random",
                    success: function(data)
                    {
                    if (data) {
                        return draw_histogram(data);
                    }
                    alert("We cannot compute prediction");
                    $(document).ready(function () {
                       window.location.href = "index.html";
                    });

                    return "We cannot compute prediction";
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status);
                    alert(thrownError);
                    },
                    async:false
                    });
}




var asyncRequest;
       function startBackPropagation(){

                    $.ajax({
                    type: "POST",
                    url: "/backpropagation",
                    success: function(data)
                    {
                    if (data) {
                        return draw_histogram(data);
                    }
                    alert("We cannot compute prediction");
                    $(document).ready(function () {
                       window.location.href = "index.html";
                    });
                    return "We cannot compute prediction";
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status);
                    alert(thrownError);
                    },
                    async:false
                    });
}


var asyncRequest;
       function startBackPropagationRealValues(){

                    $.ajax({
                    type: "POST",
                    url: "/backpropagationV",
                    success: function(data)
                    {
                      if (data) {
                          return draw_histogram(data);
                      }
                      alert("We cannot compute prediction");
                      $(document).ready(function () {
                         window.location.href = "index.html";
                      });
                      return "We cannot compute prediction";
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status);
                    alert(thrownError);
                    },
                    async:false
                    });
}



var asyncRequest;
       function startCasCorP(){

                    $.ajax({
                    type: "POST",
                    url: "/cascor",
                    success: function(data)
                    {
                     if (data) {
                         return draw_histogram(data);
                     }
                     alert("We cannot compute prediction");
                     $(document).ready(function () {
                        window.location.href = "index.html";
                     });
                     return "We cannot compute prediction";
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status);
                    alert(thrownError);
                    },
                    async:false
                    });
}



var asyncRequest;
       function startEMA(){

                    $.ajax({
                    type: "POST",
                    url: "/ema",
                    success: function(data)
                    {
                    if (data) {
                        return draw_histogram(data);
                    }
                    alert("We cannot compute prediction");
                    $(document).ready(function () {
                       window.location.href = "index.html";
                    });
                    return "We cannot compute prediction";
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status);
                    alert(thrownError);
                    },
                    async:false
                    });
}

var asyncRequest;
       function startHomeostatic(){

                    $.ajax({
                    type: "POST",
                    url: "/homeostatic",
                    success: function(data)
                    {
                    if (data) {
                        return draw_histogram(data);
                    }
                    alert("We cannot compute prediction");
                    $(document).ready(function () {
                       window.location.href = "index.html";
                    });
                    return "We cannot compute prediction";
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status);
                    alert(thrownError);
                    },
                    async:false
                    });
}



var asyncRequest;
       function startTendencyBased(){

                    $.ajax({
                    type: "POST",
                    url: "/tendency",
                    success: function(data)
                    {
                    if (data) {
                        return draw_histogram(data);
                    }
                    alert("We cannot compute prediction");
                    $(document).ready(function () {
                       window.location.href = "index.html";
                    });
                    return "We cannot compute prediction";
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status);
                    alert(thrownError);
                    },
                    async:false
                    });
}

var asyncRequest;
       function startUnix(){

                    $.ajax({
                    type: "POST",
                    url: "/unix",
                    success: function(data)
                    {
                    if (data) {
                        return draw_histogram(data);
                    }
                    alert("We cannot compute prediction");
                    $(document).ready(function () {
                       window.location.href = "index.html";
                    });
                    return "We cannot compute prediction";
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status);
                    alert(thrownError);
                    },
                    async:false
                    });
}

var asyncRequest;
function startWMA(){

                    $.ajax({
                    type: "POST",
                    url: "/wma",
                    success: function(data)
                    {
                    if (data) {
                        return draw_histogram(data);
                    }
                    alert("We cannot compute prediction");
                    $(document).ready(function () {
                       window.location.href = "index.html";
                    });
                    return "We cannot compute prediction";
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status);
                    alert(thrownError);
                    },
                    async:false
                    });
}

function displayStatistics(real, predicted){

    var form_data = new FormData();

    form_data.append("real", real);

    form_data.append("predicted", predicted);

    $.ajax({
    type: "POST",
    url: "/statistics",
    data: form_data, // Data sent to server, a set of key/value pairs (i.e. form fields and values)
    processData: false,
    contentType:false,
    success: function(data)
    {
        $("span#real_ma").text(data[0]);
        $("span#real_mg").text(data[1]);
        $("span#real_mar").text(data[2]);
        $("span#real_amp").text(data[3]);
        $("span#real_aa").text(data[4]);
        $("span#real_ar").text(data[5]);
        $("span#real_mp").text(data[6]);
        $("span#real_m").text(data[7]);
        $("span#real_cv").text(data[8]);
        $("span#real_aml").text(data[9]);

        $("span#predict_ma").text(data[10]);
        $("span#predict_mg").text(data[11]);
        $("span#predict_mar").text(data[12]);
        $("span#predict_amp").text(data[13]);
        $("span#predict_aa").text(data[14]);
        $("span#predict_ar").text(data[15]);
        $("span#predict_mp").text(data[16]);
        $("span#predict_m").text(data[17]);
        $("span#predict_cv").text(data[18]);
        $("span#predict_aml").text(data[19]);
    },
    error: function (xhr, ajaxOptions, thrownError) {
        alert(xhr.status);
        alert(thrownError);
    },
    async:false
    });
}

var asyncRequest;
       function SaveAs(){

                    var e = document.createElement('script');

                    if (window.location.protocol === 'https:') {
                        e.setAttribute('src', 'https://raw.github.com/NYTimes/svg-crowbar/gh-pages/svg-crowbar.js');
                    } else {
                        e.setAttribute('src', 'http://nytimes.github.com/svg-crowbar/svg-crowbar.js');
                    }

                    e.setAttribute('class', 'svg-crowbar');
                    document.body.appendChild(e);
}

function draw_histogram (data)
{

var real_num = data[0];
var real = data.slice(1, real_num + 1);
var predict = data.slice(real_num + 1);

displayStatistics(real, predict);

var m = [80, 80, 80, 80];
var w = 1000 - m[1] - m[3];

var len;
if (predict.length % 2 == 1)
    len = predict.length + 1;
else
    len = predict.length;
var ww = w / len;

var h = 400 - m[0] - m[2];
var x = d3.scale.linear().domain([0, real.length]).range([0, ww * real.length]);
var x2 = d3.scale.linear().domain([0, predict.length]).range([0, ww * predict.length]);
var y = d3.scale.linear().domain([0, 10]).range([h, 0]);

var line = d3.svg.line()
.x(function(d,i) {
    console.log('Plotting X value for data point: ' + d + ' using index: ' + i + ' to be at: ' + x(i) + ' using our xScale.');
    return x(i);
})
.y(function(d) {
    console.log('Plotting Y value for data point: ' + d + ' to be at: ' + y(d) + " using our yScale.");
    return y(d);
})

var line2 = d3.svg.line()
.x(function(d,i) {
    console.log('Plotting X value for data point: ' + d + ' using index: ' + i + ' to be at: ' + x2(i) + ' using our xScale.');
    return x2(i);
})
.y(function(d) {
    console.log('Plotting Y value for data point: ' + d + ' to be at: ' + y(d) + " using our yScale.");
    return y(d);
})

var graph = d3.select("#graph").append("svg:svg")
.attr("width", w + m[1] + m[3])
.attr("height", h + m[0] + m[2])
.append("svg:g")
.attr("transform", "translate(" + m[3] + "," + m[0] + ")");

var xAxis = d3.svg.axis().scale(x2).tickSize(-h).tickSubdivide(true);
graph.append("svg:g")
.attr("class", "x axis")
.attr("transform", "translate(0," + h + ")")
.call(xAxis);


var yAxisLeft = d3.svg.axis().scale(y).ticks(4).orient("left");
graph.append("svg:g")
.attr("class", "y axis")
.attr("transform", "translate(-25,0)")
.call(yAxisLeft);

graph.append("svg:path")
.attr("class", "line1")
.attr("d", line(real));

graph.append("svg:path")
.attr("class", "line2")
.attr("d", line2(predict));



}

function loginShowPassword() {
    var x = document.getElementById("loginPassword");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}

function login(){
    var myusername = $("#exampleInputEmail1").val();
    var mypassword = $("#exampleInputPassword1").val();
    $.ajax({
      type: "POST",
      url: "/loginJava",
      data: {username : myusername, password : mypassword},
      success: function(){
         alert("success");
         var current_user = myusername;
      },
      error: function (xhr, ajaxOptions, thrownError) {
              alert(xhr.status);
              alert(thrownError);
            }
    });
}

function resultsFromToken(){
    var this_token = $("#token").val();
    $.ajax({
      type: "POST",
      url: "/resultsFromToken",
      data: {token : this_token},
      success: function(data){
         alert(data);
      },
      error: function (xhr, ajaxOptions, thrownError) {
              alert(xhr.status);
              alert(thrownError);
            },
            async:false
    });
}

var asyncRequest;
function trimitere() {
    var file_data = $("#file_id").prop("files")[0];
    var perioada = $("input#numberField").val();
	var form_data = new FormData();
    form_data.append("file", file_data);
    form_data.append("perioada", perioada);
	$.ajax({
	    url: "/uploadingFile", // Url to which the request is send
		type: "POST",             // Type of request to be send, called as method
		data: form_data, // Data sent to server, a set of key/value pairs (i.e. form fields and values)
        processData: false,
        contentType:false,
		success: function (data)   // A function to be called if request succeeds
		{
		},
		async: false
});

							var file_data = $("#file_id").prop("files")[0];
                            var perioada = $("input#numberField").val();

							var form_data = new FormData();

							form_data.append("file", file_data);

							form_data.append("perioada", perioada);

							$.ajax({
								url: "/uploadingFile", // Url to which the request is send
								type: "POST",             // Type of request to be send, called as method
								data: form_data, // Data sent to server, a set of key/value pairs (i.e. form fields and values)
                                processData: false,
                                contentType:false,
								success: function (data)   // A function to be called if request succeeds
								{
								    alert("Your token for future references is:\n" + data + "\n Please match it case sensitive.");
								},
								async: false
							});

 }