package com.example.demo.repository;

import com.example.demo.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
class MailRepositoryTest {

    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private LogisticsRepository logisticsRepository;
    @Autowired
    private DirectionRepository directionRepository;

    @Test
    void findAll() {
        List<Mail> result = mailRepository.findAll();
//        System.out.println(mailRepository.findAll());
        System.out.println(result);
        System.out.println(result.size());
    }

    @Test
    void findAny() {
        System.out.println(mailRepository.findMailBySender("15564107739"));
    }


    @Test
    void findBySign() {
        String sign = "已签收";
        List<Mail> result1 = mailRepository.findMailBySignIs(sign);
        Type_mail[] typeMail = new Type_mail[result1.size()];
        for (int i = 0; i < result1.size(); i++) {
            typeMail[i] = new Type_mail();
        }
        int j = 0;
        for (Mail mail : result1) {
            typeMail[j].copy(mail);
            typeMail[j].setDeliver_time(logisticsRepository.findById(mail.getLog_id()).get().getDeliver_time());
            typeMail[j].setDeliver_way(logisticsRepository.findById(mail.getLog_id()).get().getDeliver_way());
            System.out.println(typeMail[j++]);
        }
    }

    @Test
    void save() {
//        Mail mail=new Mail();
//        List<Mail> result=mailRepository.findAll();
//        String ID=result.get(result.size()-1).getLog_id();
//        int newID = Integer.parseInt(ID.substring(2,6));
//        System.out.println("XA"+(newID+1));

        Logistics logistics = logisticsRepository.findById("XA6146").get();

    }

    @Test
    void delete() {
        mailRepository.deleteById("XA6155");
    }

    @Test
    void statistics() {
        double all = mailRepository.findAll().size();
        HashMap<String, Double> map = new HashMap<>();
        for (Mail mail : mailRepository.findAll()) {
            if (map.get(mail.getSend_city()) == null) {
                map.put(mail.getSend_city(), 1.0);
            } else {
                map.put(mail.getSend_city(), map.get(mail.getSend_city()) + 1);
            }
        }
        Iterator iter = map.entrySet().iterator();
        System.out.println(map.size());
        Profre[] result = new Profre[3];
        for (int i = 0; i < 3; i++) {
            result[i] = new Profre();
        }
        int j = 0;
        while (iter.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter.next();
            Object key = entry.getKey();
            System.out.println(key);
            String str_key = (String) key;

            Object val = entry.getValue();
            double fre = (double) val;
//            System.out.println(String.format("%.2f", fre/all));
            Profre profre = new Profre();
            profre.setProvince(str_key);
            profre.setFrequency(fre);
            result[j++] = profre;
        }
        for (int i = 0; i < 3; i++) {
            System.out.println(result[i]);
        }
    }

    //管理端确定发货后点击事件 将对应的货物状态置为 运输中
    @Test
    void confirm() {
        String id = "XA6111";
        Mail mail = mailRepository.findById(id).get();
        mail.setSign("运输中");
        Mail result = mailRepository.save(mail);
    }

    @Test
    void live_time() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("当前时间" + df.format(new Date()));// new Date()为获取当前系统时间
    }

    @Test
    void sb() {
        List<Mail> tmp = mailRepository.findMailBySignIs("运输中");
        List<Mail> tmp2 = mailRepository.findMailBySignIs("待签收");
        tmp.addAll(tmp2);

        Type_mail[] result = new Type_mail[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            result[i] = new Type_mail();
        }
        int j = 0;
        for (Mail mail : tmp) {
            result[j].copy(mail);
//            result[j].setDeliver_time(logisticsRepository.findById(mail.getLog_id()).get().getDeliver_time());
//            result[j].setDeliver_way((logisticsRepository.findById(mail.getLog_id())).get().getDeliver_way());
            j++;
        }
        for(int i=0;i<tmp.size();i++){
            System.out.println(result[i]);
        }
    }

}