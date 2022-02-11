package com.example.demo.repository;

import com.example.demo.entity.Count_mail;
import com.example.demo.entity.Mail;
import com.example.demo.entity.Profre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
class Count_mailRepositoryTest {

    @Autowired
    private Count_mailRepository count_mailRepository;
    @Autowired
    private MailRepository mailRepository;

    //统计各月份的发货数量 必须是出现在direction中的记录才可以
    @Test
    public void month_count() {
        List<Count_mail> result = count_mailRepository.findAll();

        HashMap<Integer, Integer> map = new HashMap<>();
        for (Count_mail count_mail : result) {
//            System.out.println(mail.getSend_city());
            System.out.println(count_mail.getDeliver_time().substring(5, 7));
            int month = Integer.parseInt(count_mail.getDeliver_time().substring(5, 7));
            if (map.get(month) == null) {
                map.put(month, 1);
            } else {
                map.put(month, map.get(month) + 1);
            }
        }
        //遍历出map中的所有月份和件数
        int[] resultM = new int[12];
        for (int i = 0; i < 12; i++) {
            resultM[i] = 0;
        }

        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iter.next();
            Object key = entry.getKey();
            int int_key = (Integer) key;
            System.out.print(key + ": ");
            Object val = entry.getValue();
            int fre = (Integer) val;
            System.out.println(val);
            resultM[int_key - 1] = fre;
        }
        for (int i = 0; i < 12; i++) {
            System.out.println(i + 1 + " " + resultM[i]);
        }
    }

    @Test
    public void pro_count() {
        double all = mailRepository.findAll().size();
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
//            Profre profre = new Profre();
////            profre.setProvince(str_key);
////            profre.setFrequency(dou_fre);
//            result[j++] = profre;
        }
        Profre profre = new Profre();
        profre.setProvince(max_key);
        profre.setFrequency(max);
        result[j++]=profre;

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

        for (int i = 0; i < 3; i++) {
            System.out.println(result[i]);
        }
    }
}