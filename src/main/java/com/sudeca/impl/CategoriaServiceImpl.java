package com.sudeca.impl;

import com.sudeca.model.Categoria;
import com.sudeca.repository.CategoriaRepository;
import com.sudeca.services.ICategoryService;
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
public class CategoriaServiceImpl implements ICategoryService {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    CategoriaRepository categoryRepository;

    public CategoriaServiceImpl() {
    }

    @Override
    public List<Categoria> getAllCategories() {
        return categoryRepository.findAll();
    }

}
