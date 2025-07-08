
package com.sudeca.services;

import com.sudeca.model.Categoria;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * * Author: Luis Lopez
 **/

@Component
public interface ICategoryService {
    List<Categoria> getAllCategories();
}
