package com.example.demo.controller;

import com.example.demo.entity.Logistics;
import com.example.demo.entity.Mail;
import com.example.demo.entity.Notice;
import com.example.demo.entity.Person;
import com.example.demo.repository.LogisticsRepository;
import com.example.demo.repository.NoticeRepository;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notice")
@CrossOrigin
public class NoticeHandler {
    @Autowired
    private NoticeRepository noticeRepository;

    @GetMapping("/find")
    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    @GetMapping("/findNew")
    public List<Notice> findNew() {
        return noticeRepository.findNoticeByDirty("1");
    }

    @PostMapping("/save")
    public String save(@RequestBody Notice notice) {
        List<Notice> noticeList=noticeRepository.findAll();
        for(Notice notice1 : noticeList){
            notice1.setDirty("0");
        }

        Notice result = noticeRepository.save(notice);
        if (result != null) {
            return "notice save success";
        } else {
            return "error";
        }
    }
}


