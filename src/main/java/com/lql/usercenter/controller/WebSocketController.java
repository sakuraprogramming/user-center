package com.lql.usercenter.controller;

import com.lql.usercenter.service.WebSocketService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/websocket")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8000"}, allowCredentials = "true")
public class WebSocketController {

    @GetMapping("/send")
    public void send() {
        WebSocketService.sendMessage("badao", "公众号:霸道的程序猿");
    }
}