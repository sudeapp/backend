package com.sudeca.controller;

import com.sudeca.model.Categoria;
import com.sudeca.services.ICategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoriaController {
	
	private static final Logger logger = LogManager.getLogger();

      @Autowired
      ICategoryService categoryService;

     @GetMapping("/categories")
      public ResponseEntity<List<Categoria>> getAllCategories() {
        try {
            List<Categoria> category = categoryService.getAllCategories();

            logger.info("getAllCategories");
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Error getAllCategories e.getMessage(): "+e.getMessage());
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }


}