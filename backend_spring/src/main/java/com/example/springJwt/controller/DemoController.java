package com.example.springJwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity<String>demo(){
        return ResponseEntity.ok("Hello from secured URl Democontroller");
    }

    @GetMapping("/admin_only")
    public ResponseEntity<String>adminOnly(){
        return ResponseEntity.ok("Hello from  adminOnly secured URl Democontroller");
    }
}
