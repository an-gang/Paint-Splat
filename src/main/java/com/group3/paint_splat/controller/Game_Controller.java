package com.group3.paint_splat.controller;


import com.group3.paint_splat.entity.Game;
import com.group3.paint_splat.service.Game_Service_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpSession;
import java.util.*;

//@CrossOrigin(origins = "http://localhost:63343", allowCredentials = "true")
@CrossOrigin("*")
@RestController
public class Game_Controller {
    @Autowired
    private Game_Service_Interface service;

    @RequestMapping("/getRooms")
    public Set<String> getRooms() {
        return service.getRooms();
    }

    @RequestMapping("/createRoom")
    public String createRoom() {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return service.createRoom(session.getId());
    }

    @RequestMapping("/joinRoom")
    public String joinGame(String roomId) {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return service.joinRoom(session.getId(), roomId);
    }

    @RequestMapping("/checkRoomId")
    public String checkRoomId() {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return service.checkRoomId(session.getId());
    }

    @RequestMapping("/getGame")
    public Game getGame() {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return service.getGame(session.getId());
    }

    @RequestMapping("/getTimeAfterStart")
    public long getTimeAfterStart() {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return service.getTimeAfterStart(session.getId());
    }

    @RequestMapping("/quitRoom")
    public void quitRoom() {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        service.quitRoom(session.getId());
    }

    @RequestMapping("/getPlayerId")
    public String getPlayerId() {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return session.getId();
    }

    @RequestMapping("/getPlayerId2")
    public String[] getPlayerId2() {
        String[] letters = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        Random random = new Random();
        StringBuilder color = new StringBuilder("#");
        for (int i = 0; i < 6; i++) {
            color.append(letters[random.nextInt(16)]);
        }
        color.append("FF");
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return new String[]{session.getId(), color.toString()};
    }


    @RequestMapping("/startGame")
    public void startGame() {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        service.startGame(session.getId());
    }

    @RequestMapping("/shoot")
    public boolean shoot(double top, double left) {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return service.shoot(session.getId(), new double[]{top, left});
    }

//    @RequestMapping("/printConnections")
//    public void printConnections() {
//        service.printConnections();
//    }

}
