package com.sudeca.services;

import com.sudeca.model.Auxiliar;
import com.sudeca.model.AuxiliarTipo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * * Author: Luis López
 **/
@Component
public interface IAuxiliarTipoService {
    List<AuxiliarTipo> findByAll();
}
