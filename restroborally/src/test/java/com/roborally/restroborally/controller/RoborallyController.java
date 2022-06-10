package com.roborally.restroborally.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoborallyController {

    @RequestMapping("/")
    public String getHellowWorld(){
        return "This works christiaan";
    }
}
