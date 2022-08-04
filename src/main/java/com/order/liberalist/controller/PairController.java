package com.order.liberalist.controller;

import com.order.liberalist.service.PairService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PairController {

    @Resource
    private PairService pairService;

    @RequestMapping("/doSleuth")
    public void doSleuth(){
        pairService.doSleuth();
    }

    @RequestMapping("/doSleuthById/{id}")
    public void doSleuthById(@PathVariable Integer id){
        pairService.doSleuthById(id);
    }

}
