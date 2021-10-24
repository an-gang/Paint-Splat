$(document).ready(function () {
    refresh();
    $("#createRoom").click(function () {
        $.post("/createRoom", {}, function (data) {
            console.log(data);
            refresh();
        })
    });
    $("#refresh").click(function () {
        refresh();
    });
    $("#test").click(function () {
        $.post("/printConnections", {})
    });

});

function refresh() {
    $.post("/getRooms", {}, function (data) {
        $("#roomList").html("");
        for (var i = 0; i < data.length; i++) {
            $("#roomList").append("<div id='" + data[i] + "' onclick='joinRoom(" + data[i] + ")'>" + data[i] + "</div>")
        }
    })
}

function joinRoom(roomId) {
    $.post("/joinRoom", {roomId: roomId}, function (data) {
        console.log(data);
    })

}







