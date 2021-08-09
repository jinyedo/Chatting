package com.example.chatting.controller;

import com.example.chatting.dto.RoomDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class MainController {

    List<RoomDTO> roomList = new ArrayList<>();
    static int roomNumber = 0;

    @GetMapping("/chatting")
    public String getChatting() {
        return  "chatting";
    }

    @GetMapping("/")
    public String getRoom() {
        return "room";
    }

    // 방 생성하기
    @RequestMapping("/createRoom")
    @ResponseBody
    public List<RoomDTO> createRoom(@RequestParam HashMap<Object, Object> params) {
        String roomName = (String) params.get("roomName");
        if (roomName != null && !roomName.trim().equals("")) {
            RoomDTO roomDTO = RoomDTO.builder()
                    .roomName(roomName)
                    .roomNumber(++roomNumber)
                    .build();
            roomList.add(roomDTO);
        }
        return roomList;
    }

    // 방 정보 가져오기
    @RequestMapping("/getRoom")
    @ResponseBody
    public List<RoomDTO> getRoom(@RequestParam HashMap<Object, Object> params) {
        return roomList;
    }

    // 채팅방
    @RequestMapping("/moveChatting")
    public ModelAndView chatting(@RequestParam HashMap<Object, Object> params) {
        log.info("채팅방 입장");
        ModelAndView mv = new ModelAndView();
        int roomNumber = Integer.parseInt((String) params.get("roomNumber"));
        List<RoomDTO> result = roomList.stream().filter(o -> o.getRoomNumber() == roomNumber).collect(Collectors.toList());
        if (result.size() > 0) {
            mv.addObject("roomName", params.get("roomName"));
            mv.addObject("roomNumber", params.get("roomNumber"));
            mv.setViewName("chatting");
        } else {
            mv.setViewName("room");
        }
        return mv;
    }
}
