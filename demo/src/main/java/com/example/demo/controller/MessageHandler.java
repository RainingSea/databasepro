package com.example.demo.controller;


import com.example.demo.entity.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@CrossOrigin
public class MessageHandler {
    @Autowired
    private MessageRepository messsageRository;

    @GetMapping("/admin_find")
    public List<Message> admin_find() {
        return messsageRository.findMessageByOwn(1);
    }

    @GetMapping("/reply/{phone_num}")
    public List<Message> reply(@PathVariable String phone_num) {
        return messsageRository.findMessageByMessagePK_PhonenumAndOwn(phone_num, 0);
    }

    @PutMapping("/add")
    public String complaint(@RequestBody Message message) {
        System.out.println(message);
        Message result = messsageRository.save(message);
        if (result != null)
            return "complaint success";
        else
            return "complaint error";
    }
}
