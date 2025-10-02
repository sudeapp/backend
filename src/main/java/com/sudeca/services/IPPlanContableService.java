
package com.sudeca.services;

import com.sudeca.model.PlantillaPlanContable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * * Author: Luis Lopez
 **/

@Component
public interface IPPlanContableService {
    List<PlantillaPlanContable> getAllPPlanContable();
}
