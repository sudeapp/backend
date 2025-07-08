package com.sudeca.controller;

import com.sudeca.model.view.Vpc;
import com.sudeca.services.IVpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vpc")
public class VpcController {
    @Autowired
    private IVpcService vpcService;

    @GetMapping
    public List<Vpc> obtenerTodosLosVpc() {
        return vpcService.obtenerTodosVpc();
    }
}