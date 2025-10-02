package com.sudeca.impl;

import com.sudeca.model.Categoria;
import com.sudeca.model.PlantillaPlanContable;
import com.sudeca.repository.CategoriaRepository;
import com.sudeca.repository.PPlanContableRepository;
import com.sudeca.services.ICategoryService;
import com.sudeca.services.IPPlanContableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * * Author: Luis Lopez
 **/
@Service
@Component
public class PPlanContableServiceImpl implements IPPlanContableService {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    PPlanContableRepository ppcRepository;

    @Override
    public List<PlantillaPlanContable> getAllPPlanContable() {
        return ppcRepository.findAll();
    }

}
