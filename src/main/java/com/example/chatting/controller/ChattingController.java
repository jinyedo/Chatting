package com.example.chatting.controller;

import com.example.chatting.service.chatting.ChattingRepository;
import com.example.chatting.service.room.RoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChattingController {

    private final ChattingRepository chattingRepository;

    // 방 생성하기
    @RequestMapping("/createRoom")
    @ResponseBody
    public List<RoomDTO> createRoom(@RequestParam HashMap<Object, Object> params) {
        return chattingRepository.createRoom(params);
    }

    // 방 정보 가져오기
    @RequestMapping("/getRoomList")
    @ResponseBody
    public List<RoomDTO> getRoomList() {
        return chattingRepository.findAll();
    }

    // 채팅방 이동하기
    @RequestMapping("/moveChatting")
    public ModelAndView chatting(@RequestParam HashMap<Object, Object> params) {
        ModelAndView mv = new ModelAndView();
        List<RoomDTO> result = chattingRepository.findByRoomNumber(params);
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
