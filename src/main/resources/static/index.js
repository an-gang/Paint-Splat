$(document).ready(function () {
    $.post("/checkRoomId", {}, function (data) {
        if (data) {
            window.location.href = "paint_splat.html";
        } else {
            refresh();
            $("#createRoom").click(function () {
                $.post("/createRoom", {}, function (data) {
                    console.log(data);
                    window.location.href = "paint_splat.html";
                })
            });
            $("#refresh").click(function () {
                refresh();
            });
            $("#test").click(function () {
                $.post("/printConnections", {})
            });
        }
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
        if (data === "success") {
            window.location.href = "paint_splat.html";
        } else if (data === "full") {
            alert("房间已满");
        }
    })

}







