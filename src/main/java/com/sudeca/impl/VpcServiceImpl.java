package com.sudeca.impl;

import com.sudeca.model.view.Vpc;
import com.sudeca.repository.VpcRepository;
import com.sudeca.services.IVpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VpcServiceImpl implements IVpcService{
    private final VpcRepository vpcRepository;

    @Autowired
    public VpcServiceImpl(VpcRepository vpcRepository) {
        this.vpcRepository = vpcRepository;
    }

    @Override
    public List<Vpc> obtenerTodosVpc() {
        return vpcRepository.findAll();
    }
}
