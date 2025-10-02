package com.sudeca.impl;

import com.sudeca.dto.Message;
import com.sudeca.model.Auxiliar;
import com.sudeca.model.AuxiliarTipo;
import com.sudeca.repository.AuxiliarRepository;
import com.sudeca.repository.AuxiliarTipoRepository;
import com.sudeca.services.IAuxiliarService;
import com.sudeca.services.IAuxiliarTipoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sudeca.security.JwtConstans.AUXI_EXISTS_BD;
import static com.sudeca.security.JwtConstans.AUXI_RIF_EXISTS_BD;

/**
 * * Author: Luis López
 **/
@Service
@Component
public class AuxiliarTipoServiceImpl implements IAuxiliarTipoService {

    private static final Logger logger = LogManager.getLogger();
    @Autowired
    AuxiliarTipoRepository auxiliarTipoRepository;

    @Override
    public List<AuxiliarTipo> findByAll(){
        return auxiliarTipoRepository.findAll();
    }

}
