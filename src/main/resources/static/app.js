var stompClient = null;
var lastTimeStamp = 0;
var lastReading = 0;
var newTimeStamp = 0;
var newReading = 0;

var counter1UUID = "fde8f1d0-c5d0-11e0-856e-f9e4360ced10";

function setConnected(connected) {

}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        setConnected(true);
        console.log("Connected: " + frame);
        stompClient.subscribe('/topic/data', onReceiveData)
    })
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function onReceiveData(data) {
    var tuple = jsonPath(JSON.parse(data.body), "$.data[?(@.uuid == '" + counter1UUID + "')].tuples[0]");

    newTimeStamp = tuple[0][0];
    newReading = tuple[0][1];

    showConsumption();

}

function showConsumption() {
    if (lastTimeStamp === 0 || lastReading === 0) {
        $("#elapsedTime").html("0");
        $("#consumedPower").html("0");
    } else {
        var elapsedSeconds = (newTimeStamp - lastTimeStamp) / 1000;
        var elapsedHours = elapsedSeconds / 3600;
        var diffReading = newReading - lastReading;
        var consumption = diffReading / elapsedHours;

        $("#elapsedTime").html(elapsedSeconds.toFixed(1) + " s");
        $("#consumedPower").html(consumption.toFixed(1) + " W");
    }

    lastReading = newReading;
    lastTimeStamp = newTimeStamp;

    console.log(lastTimeStamp, lastReading, newTimeStamp, newReading);
}

$(function () {
  connect();
});