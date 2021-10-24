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


@RestController
public class Game_Controller {
    @Autowired
    private Game_Service_Interface service;

    @RequestMapping("/test")
    @CrossOrigin("*")
    public int[] test() {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();

        System.out.println(session.getId());

        Random random = new Random();
        random.nextInt(300);

        return new int[]{random.nextInt(300), random.nextInt(300)};
    }

    @RequestMapping("/getRooms")
    @CrossOrigin("*")
    public Set<String> getRooms() {
        return service.getRooms();
    }

    @RequestMapping("/createRoom")
    @CrossOrigin("*")
    public String createRoom() {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return service.createRoom(session.getId());
    }

    @RequestMapping("/joinRoom")
    @CrossOrigin("*")
    public String joinGame(String roomId) {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return service.joinRoom(session.getId(), roomId);
    }

    @RequestMapping("/checkRoomId")
    @CrossOrigin("*")
    public String checkRoomId() {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return service.checkRoomId(session.getId());
    }

    @RequestMapping("/getGame")
    @CrossOrigin("*")
    public Game getGame(){
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return service.getGame(session.getId());
    }

    @RequestMapping("/getTime")
    @CrossOrigin("*")
    public long getTime(){
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return service.getTime(session.getId());
    }

    @RequestMapping("/quitRoom")
    @CrossOrigin("*")
    public void quitRoom(){
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        service.quitRoom(session.getId());
    }

    @RequestMapping("/countPlayer")
    @CrossOrigin("*")
    public int countPlayer(){
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return service.countPlayer(session.getId());
    }

    @RequestMapping("/getPlayerId")
    @CrossOrigin("*")
    public String getPlayerId(){
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return session.getId();
    }

    @RequestMapping("/printConnections")
    @CrossOrigin("*")
    public void printConnections(String roomId) {
        service.printConnections();
    }


}
