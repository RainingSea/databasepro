package com.example.demo.repository;

import com.example.demo.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;


    @Test
    void findAll() {
        System.out.println(personRepository.findAll());
    }

    @Test
    void save() {
        Person person = new Person();
        person.setPhone_num("11111111111");
        person.setProvince("河南");
        person.setCity("许昌");
        Person person1 = personRepository.save(person);
        System.out.println(person1);
    }

    @Test
    void findby() {
        Person person=personRepository.findById("15564107739").get();
        String password="123";
        if(password.equals(person.getPassword())){
            System.out.println("登录成功");
        }
        else{
            System.out.println("登录失败");
        }

    }
}