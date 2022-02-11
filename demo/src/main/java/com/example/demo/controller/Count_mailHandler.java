package com.example.demo.controller;


import com.example.demo.entity.Count_mail;
import com.example.demo.entity.Mail;
import com.example.demo.entity.Profre;
import com.example.demo.repository.Count_mailRepository;
import com.example.demo.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/statistic")
@CrossOrigin
public class Count_mailHandler {

    @Autowired
    private Count_mailRepository count_mailRepository;
    @Autowired
    private MailRepository mailRepository;

    @GetMapping("/month_count")
    public int[] month_count(){
        List<Count_mail> result=count_mailRepository.findAll();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (Count_mail count_mail : result) {
//            System.out.println(mail.getSend_city());
            System.out.println(count_mail.getDeliver_time().substring(5,7));
            int month=Integer.parseInt(count_mail.getDeliver_time().substring(5,7));
            if(map.get(month)==null){
                map.put(month,1);
            }
            else{
                map.put(month,map.get(month)+1);
            }
        }
        //遍历出map中的所有月份和件数
        int [] resultM=new int[12];
        for(int i=0;i<12;i++){
            resultM[i]=0;
        }

        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter.next();
            Object key = entry.getKey();
            int int_key=(Integer)key;
            System.out.print(key+": ");
            Object val = entry.getValue();
            int fre=(Integer) val;
            System.out.println(val);
            resultM[int_key-1]=fre;
        }
        return resultM;
    }

//    @GetMapping("/pro_count")
//    Profre[] statistics() {
//        double all=mailRepository.findAll().size();
//        HashMap<String, Double> map = new HashMap<>();
//        for (Mail mail : mailRepository.findAll()) {
//            if (map.get(mail.getSend_city()) == null) {
//                map.put(mail.getSend_city(), 1.0);
//            } else {
//                map.put(mail.getSend_city(), map.get(mail.getSend_city()) + 1);
//            }
//        }
//        Iterator iter = map.entrySet().iterator();
//        System.out.println(map.size());
//        Profre[] result=new Profre[3];
//        for(int i=0;i<3;i++){
//            result[i]=new Profre();
//        }
//        int j=0;
//        while (iter.hasNext()) {
//            HashMap.Entry entry = (HashMap.Entry) iter.next();
//            Object key = entry.getKey();
//            System.out.println(key);
//            String str_key=(String)key;
//
//            Object val = entry.getValue();
//            double fre=(double) val;
////            System.out.println(String.format("%.2f", fre/all));
//            Profre profre=new Profre();
//            profre.setProvince(str_key);
//            profre.setFrequency(fre);
//            result[j++]=profre;
//        }
//        for(int i=0;i<3;i++) {
//            System.out.println(result[i]);
//        }
//        return result;
//    }

    @GetMapping("/pro_send_count")
    public Profre[] pro_send_count(){
        HashMap<String, Integer> map = new HashMap<>();
        for (Mail mail : mailRepository.findAll()) {
            if (map.get(mail.getSend_city()) == null) {
                map.put(mail.getSend_city(), 1);
            } else {
                map.put(mail.getSend_city(), map.get(mail.getSend_city()) + 1);
            }
        }
        Iterator iter = map.entrySet().iterator();
//        System.out.println(map.size());
        Profre[] result = new Profre[3];
        for (int i = 0; i < 3; i++) {
            result[i] = new Profre();
        }

        int j = 0;
        int max=0;
        String max_key="";
        while (iter.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter.next();
            Object key = entry.getKey();
            String str_key = (String) key;
            System.out.println(str_key);

            Object fre = entry.getValue();
            int int_fre = (int) fre;
            System.out.println(int_fre);
            if(int_fre>max){
                max=int_fre;
                max_key=str_key;
            }
        }
        Profre profre = new Profre();
        profre.setProvince(max_key);
        profre.setFrequency(max);
        result[j++]=profre;
//        得到发件第一的省份
        System.out.println(max_key+"<-->"+max);
        map.remove(max_key);

        max=0;
        max_key="";
        Iterator iter2 = map.entrySet().iterator();
        while (iter2.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter2.next();
            Object key = entry.getKey();
            String str_key = (String) key;
            System.out.println(str_key);

            Object fre = entry.getValue();
            int int_fre = (int) fre;
            System.out.println(int_fre);
            if(int_fre>max){
                max=int_fre;
                max_key=str_key;
            }
        }

        profre = new Profre();
        profre.setProvince(max_key);
        profre.setFrequency(max);
        result[j++]=profre;
        System.out.println(max_key+"<-->"+max);
        map.remove(max_key);
//        得到发件第二的省份

        max=0;
        max_key="";
        Iterator iter3 = map.entrySet().iterator();
        while (iter3.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter3.next();
            Object key = entry.getKey();
            String str_key = (String) key;
            System.out.println(str_key);

            Object fre = entry.getValue();
            int int_fre = (int) fre;
            System.out.println(int_fre);
            if(int_fre>max){
                max=int_fre;
                max_key=str_key;
            }
        }
        profre = new Profre();
        profre.setProvince(max_key);
        profre.setFrequency(max);
        result[j++]=profre;
        System.out.println(max_key+"<-->"+max);

//        得到发件第三的省份
        return result;
    }

    @GetMapping("/pro_receive_count")
    public Profre[] pro_receive_count(){
        HashMap<String, Integer> map = new HashMap<>();
        for (Mail mail : mailRepository.findAll()) {
            if (map.get(mail.getRece_city()) == null) {
                map.put(mail.getRece_city(), 1);
            } else {
                map.put(mail.getRece_city(), map.get(mail.getRece_city()) + 1);
            }
        }
        Iterator iter = map.entrySet().iterator();
//        System.out.println(map.size());
        Profre[] result = new Profre[3];
        for (int i = 0; i < 3; i++) {
            result[i] = new Profre();
        }

        int j = 0;
        int max=0;
        String max_key="";
        while (iter.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter.next();
            Object key = entry.getKey();
            String str_key = (String) key;
            System.out.println(str_key);

            Object fre = entry.getValue();
            int int_fre = (int) fre;
            System.out.println(int_fre);
            if(int_fre>max){
                max=int_fre;
                max_key=str_key;
            }
        }
        Profre profre = new Profre();
        profre.setProvince(max_key);
        profre.setFrequency(max);
        result[j++]=profre;
//        得到发件第一的省份
        System.out.println(max_key+"<-->"+max);
        map.remove(max_key);

        max=0;
        max_key="";
        Iterator iter2 = map.entrySet().iterator();
        while (iter2.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter2.next();
            Object key = entry.getKey();
            String str_key = (String) key;
            System.out.println(str_key);

            Object fre = entry.getValue();
            int int_fre = (int) fre;
            System.out.println(int_fre);
            if(int_fre>max){
                max=int_fre;
                max_key=str_key;
            }
        }

        profre = new Profre();
        profre.setProvince(max_key);
        profre.setFrequency(max);
        result[j++]=profre;
        System.out.println(max_key+"<-->"+max);
        map.remove(max_key);
//        得到发件第二的省份

        max=0;
        max_key="";
        Iterator iter3 = map.entrySet().iterator();
        while (iter3.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter3.next();
            Object key = entry.getKey();
            String str_key = (String) key;
            System.out.println(str_key);

            Object fre = entry.getValue();
            int int_fre = (int) fre;
            System.out.println(int_fre);
            if(int_fre>max){
                max=int_fre;
                max_key=str_key;
            }
        }
        profre = new Profre();
        profre.setProvince(max_key);
        profre.setFrequency(max);
        result[j++]=profre;
        System.out.println(max_key+"<-->"+max);

//        得到发件第三的省份
        return result;
    }
}
