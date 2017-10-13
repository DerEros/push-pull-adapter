var stompClient = null;

function setConnected(connected) {

}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        setConnected(true);
        console.log("Connected: " + frame);
        stompClient.subscribe('/topic/time', function(time) {
            showTime(JSON.parse(time.body).time);
        })
    })
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showTime(time) {
    $("#time").append("<p>" + time + "</p>");
}

$(function () {
  connect();
});