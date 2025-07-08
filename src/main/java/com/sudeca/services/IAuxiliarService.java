package com.sudeca.services;

import com.sudeca.model.Auxiliar;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * * Author: Francisco Hernandez
 **/
@Component
public interface IAuxiliarService {
    Auxiliar saveAuxiliary(Auxiliar auxiliary);
    List<Auxiliar> getAllAuxiliaries();
    Optional<Auxiliar> getAuxiliaryById(long id);
    Auxiliar updateAuxiliary(long id, Auxiliar auxiliary);
    void deleteAuxiliary(long id);
    Auxiliar findByRifAuxiliary(String rif);
    Auxiliar findByCodAuxiliary(String cod);
}
