# Introduction

`Project can be view and try on: `http://194.26.213.249:9999/

This project is an online mini-game I made when I was a student. The whole project include a springboot server backend and a simple frontend made by a few static html pages under path:/resources/static/

This mini-game is played with up to 4 players including the host in the same room. After game started, using WASD or the arrow keys to move the cross-hair and Space key to shoot paint on a moving board. The blackboard will move faster and more variable as time goes by. Finally, when time runs out, player who has applied the most paint on the board wins.

This web game has been created in a form similar to many online poker game. Multiple game rooms with different room numbers can exist at the same time. The max room limit depends on the server memory size. 

The server emulated an in-memory database with a HashMap to manage all the rooms efficiently. Players can pull up the room list and find the room hosted by their friends with room number. Session is used to authenticate and distinguish users, player can join anonymous without logging in. Reconnect function is also implemented, player can connected back to game after game page closed or refreshed unintentionally.

The frontend is simply a few static html pages controlled by JQuery, using a simple HTML protocol and polling method to synchronize data with the backend.