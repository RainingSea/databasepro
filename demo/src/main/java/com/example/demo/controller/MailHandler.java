package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.DirectionRepository;
import com.example.demo.repository.LogisticsRepository;
import com.example.demo.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/mail")
@CrossOrigin
public class MailHandler {

    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private DirectionHandler directionHandler;
    @Autowired
    private LogisticsRepository logisticsRepository;


    @GetMapping("/findAll")
    public List<Mail> findAll() {
        return mailRepository.findAll();
    }

    @GetMapping("/findSend/{sender}")
    public Site_mail[] findSend(@PathVariable("sender") String sender) {
        System.out.println("你好");
        List<Mail> tmp = mailRepository.findMailBySender(sender);
        Site_mail[] result = new Site_mail[mailRepository.findMailBySender(sender).size()];
        for (int i = 0; i < tmp.size(); i++) {
            result[i] = new Site_mail();
        }
        int j = 0;
        for (Mail mail : tmp) {
            result[j].copy(mail);
            if (result[j].getSign().equals("未发送")) {
                result[j].setNext_site(null);
                result[j].setEnd_place(null);
            } else {
                result[j].setNext_site(directionHandler.findNext(mail.getLog_id()));
                result[j].setEnd_place(directionRepository.findById(mail.getLog_id()).get().getRece_place());
            }
            System.out.println(result[j++]);
        }
        return result;
    }

    @GetMapping("/findReceive/{receiver}")
    public Site_mail[] findReceive(@PathVariable("receiver") String receiver) {
        List<Mail> tmp = mailRepository.findMailByReceiver(receiver);
        Site_mail[] result = new Site_mail[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            result[i] = new Site_mail();
        }
        int j = 0;
        for (Mail mail : tmp) {
            result[j].copy(mail);
            if (result[j].getSign().equals("未发送")) {
                result[j].setNext_site(null);
                result[j].setEnd_place(null);
            } else {
                result[j].setNext_site(directionHandler.findNext(mail.getLog_id()));
                result[j].setEnd_place(directionRepository.findById(mail.getLog_id()).get().getRece_place());
            }
            System.out.println(result[j++]);
        }
        return result;
    }

    @GetMapping("/findById/{log_id}")
    public Site_mail findById(@PathVariable("log_id") String log_id) {
        if(mailRepository.findById(log_id).orElse(null)==null){
            Site_mail site_mail=new Site_mail();
            site_mail.setLog_id("000000");
            return site_mail;
        }
        Mail mail=mailRepository.findById(log_id).get();

        Site_mail site_mail=new Site_mail();
        site_mail.copy(mail);
        if (site_mail.getSign().equals("未发送")) {
            site_mail.setNext_site(null);
            site_mail.setEnd_place(null);
        }
        else{
            site_mail.setNext_site(directionHandler.findNext(mail.getLog_id()));
            site_mail.setEnd_place(directionRepository.findById(mail.getLog_id()).get().getRece_place());
        }
        return site_mail;
    }


    @GetMapping("/findBySign/{sign}")
    public Type_mail[] findBySign(@PathVariable("sign") String sign) {
        List<Mail> tmp = mailRepository.findMailBySignIs(sign);
        if(sign.equals("运输中")){
            List<Mail> tmp2=mailRepository.findMailBySignIs("待签收");
            tmp.addAll(tmp2);
        }
        Type_mail[] result = new Type_mail[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            result[i] = new Type_mail();
        }
        int j = 0;
        for (Mail mail : tmp) {
            result[j].copy(mail);
            result[j].setDeliver_time(logisticsRepository.findById(mail.getLog_id()).get().getDeliver_time());
            result[j].setDeliver_way((logisticsRepository.findById(mail.getLog_id())).get().getDeliver_way());
            j++;
        }
        return result;
    }

    @GetMapping("/findBySign1/{sign1}")
    public Site_mail[] findBySign1(@PathVariable("sign1") String sign1) {
        List<Mail> tmp = mailRepository.findMailBySignIs(sign1);
        if(sign1.equals("运输中")){
            List<Mail> tmp2=mailRepository.findMailBySignIs("待签收");
            tmp.addAll(tmp2);
        }

        Site_mail[] result = new Site_mail[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            result[i] = new Site_mail();
        }
        int j = 0;
        for (Mail mail : tmp) {
            result[j].copy(mail);
            if (result[j].getSign().equals("未发送")) {
                result[j].setNext_site(null);
                result[j].setEnd_place(null);
            } else {
                result[j].setNext_site(directionHandler.findNext(mail.getLog_id()));
                result[j].setEnd_place(directionRepository.findById(mail.getLog_id()).get().getRece_place());
            }
            System.out.println(result[j++]);
        }
        return result;
    }




    @PostMapping("/save")
    public String save(@RequestBody Mail mail) {
        Mail save_mail = mail;
        List<Mail> resultF = mailRepository.findAll();
        String ID = resultF.get(resultF.size() - 1).getLog_id();
        int tempID = Integer.parseInt(ID.substring(2, 6));
        tempID++;
        String newID = "XA" + tempID;
//        System.out.println(newID);
        save_mail.setLog_id(newID);
        save_mail.setSign("未发送");
//        System.out.println(mail);
        Mail result = mailRepository.save(save_mail);
        if (result != null) {
            return "mail save success";
        } else {
            return "mail save error";
        }
    }

    @PutMapping("/confirm")
    public String confirm(@RequestBody Mail mail) {
        mail.setSign("运输中");
        Mail result = mailRepository.save(mail);
        System.out.println(result);
        if (result != null) {
            return "mail confirm success";
        } else {
            return "mail confirm error";
        }
    }
}

