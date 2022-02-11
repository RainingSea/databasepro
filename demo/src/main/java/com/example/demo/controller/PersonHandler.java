package com.example.demo.controller;

import com.example.demo.entity.Logistics;
import com.example.demo.entity.Person;
import com.example.demo.repository.LogisticsRepository;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
@CrossOrigin
public class PersonHandler {
    @Autowired
    private PersonRepository personRepository;

    @Autowired

    private LogisticsRepository logisticsRepository;

    @GetMapping("/info/{phone_num}")
    public Optional<Person> info(@PathVariable("phone_num") String phone_num) {
        return personRepository.findById(phone_num);
    }

    @GetMapping("/login/{phone_num}/{passwd}")
    public String login(@PathVariable("phone_num") String phone_num,@PathVariable("passwd") String passwd){
        System.out.println(phone_num);
        Person person=personRepository.findById(phone_num).get();
        if(passwd.equals(person.getPassword())){
            return "密码正确";
        }
        else{
            return "密码错误";
        }
    }
    @PostMapping("/change")
    public String change(@RequestBody Person person){
        Person person1=personRepository.save(person);
        if(person1!=null){
            return "info success";
        }
        else{
            return "info error";
        }
    }
}

