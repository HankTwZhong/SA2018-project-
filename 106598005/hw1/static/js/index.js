window.onload=Function;

function Function() {
    var VarA = setInterval(get_status_A, 3000);
    var VarB = setInterval(get_status_B, 3000);
    var VarC = setInterval(get_status_C, 3000);
}

function get_time() {
    var currentdate = new Date();
    var datetime =  currentdate.getFullYear() + "/"
                    + (currentdate.getMonth()+1)  + "/"
                    + currentdate.getDate() + " "
                    + currentdate.getHours() + ":"
                    + currentdate.getMinutes() + ":"
                    + currentdate.getSeconds();
    return datetime;
}

function get_status_A() {

    document.getElementById("Time_A").innerHTML = get_time();
    $.ajax({
        type: 'POST',
        url: '/monitor/get_status_A',
        contentType: "application/json; charset=utf-8",
        error: function() { //print error
            document.getElementById("A").innerHTML = 'WARNING';
            $('#A').addClass('greenBackground');
        }
    }).done(function(msg) {
        var json = msg;
        var status = JSON.parse(json);
        document.getElementById("A").innerHTML = status['status'];
        $('#A').addClass('yellowBackground');


    });
}


function get_status_B() {
    document.getElementById("Time_B").innerHTML = get_time();
    $.ajax({
        type: 'POST',
        url: '/monitor/get_status_B',
        contentType: "application/json; charset=utf-8",
        error: function() { //print error
            document.getElementById("B").innerHTML = 'WARNING';
            $('#B').addClass('greenBackground');
        }
    }).done(function(msg) {
        var json = msg;
        var status = JSON.parse(json);
        document.getElementById("B").innerHTML = status['status'];
        $('#B').addClass('yellowBackground');

    });
}


function get_status_C() {
    document.getElementById("Time_C").innerHTML = get_time();
    $.ajax({
        type: 'POST',
        url: '/monitor/get_status_C',
        contentType: "application/json; charset=utf-8",
        error: function() { //print error
            document.getElementById("C").innerHTML = 'WARNING';
            $('#C').addClass('greenBackground');

        }
    }).done(function(msg) {
        var json = msg;
        var status = JSON.parse(json);
        document.getElementById("C").innerHTML = status['status'];
        $('#C').addClass('yellowBackground');

    });
}