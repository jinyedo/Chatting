package com.example.chatting.controller;

import com.example.chatting.security.AuthMemberDTO;
import com.example.chatting.service.room.RoomDTO;
import com.example.chatting.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;

    // 방 정보 가져오기
    @RequestMapping("/getPagingList")
    @ResponseBody
    public Page<RoomDTO> getPagingList(Pageable pageable) {
        return roomService.getPagingList(pageable);
    }

    // 방 생성하기
    @RequestMapping("/createRoom")
    @ResponseBody
    public RoomDTO createRoom(@RequestBody RoomDTO roomDTO) {
        log.info(roomDTO.toString());
        return roomService.createRoom(roomDTO);
    }

    // 채팅방 이동하기
    @RequestMapping("/moveChatting")
    public ModelAndView chatting(@RequestParam HashMap<Object, Object> params, @AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        ModelAndView mv = new ModelAndView();
        RoomDTO result = roomService.findByRoomId(params);
        log.info(authMemberDTO.toString());
        if (result != null) {
            mv.addObject("roomId", result.getRoomId());
            mv.addObject("roomName", result.getRoomName());
            mv.addObject("userId", authMemberDTO.getUsername());
            mv.addObject("username", authMemberDTO.getName());
            mv.setViewName("chatting");
        } else {
            mv.setViewName("room");
        }
        return mv;
    }
}
