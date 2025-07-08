package com.sudeca.impl;

import com.sudeca.dto.Message;
import com.sudeca.model.Auxiliar;
import com.sudeca.repository.AuxiliarRepository;
import com.sudeca.services.IAuxiliarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sudeca.security.Constans.*;

/**
 * * Author: Francisco Hernandez
 **/
@Service
@Component
public class AuxiliarServiceImpl implements IAuxiliarService {

    private static final Logger logger = LogManager.getLogger();

    AuxiliarRepository auxiliaryRepository;
    Message message;

    @Override
    public Auxiliar findByCodAuxiliary(String cod) {
        try {
            return auxiliaryRepository.findByCodAuxiContainingIgnoreCase(cod);
        } catch (Exception e) {
            logger.info("Exception e: "+e.getMessage());
            return null;
        }
    }
    public AuxiliarServiceImpl(AuxiliarRepository auxiliaryRepository) {
        this.auxiliaryRepository = auxiliaryRepository;
    }

    @Override
    public Auxiliar saveAuxiliary(Auxiliar auxiliary) {
        //Auxiliar auxiliaryIn = new Auxiliar(auxiliary.getCodAuxi(), auxiliary.getDescAuxi(), auxiliary.getRif(), auxiliary.getAddress(),auxiliary.getPhone());
        Auxiliar auxiliaryIn = new Auxiliar();
        Auxiliar auxiliaryExists = findByCodAuxiliary(auxiliaryIn.getCodAuxi());
        if (auxiliaryExists!=null) {
            message = new Message(AUXI_EXISTS_BD,false);
            //return gson.toJson(message);
        }
        auxiliaryExists = findByRifAuxiliary(auxiliaryIn.getRif());
        if (auxiliaryExists!=null) {
            message = new Message(AUXI_RIF_EXISTS_BD,false);
            //return gson.toJson(message);
        }

        logger.info("auxiliaryIn.getCodAuxi(): "+auxiliaryIn.getCodAuxi());

        Auxiliar _auxiliary = auxiliaryRepository.save(auxiliaryIn);
        return _auxiliary;
    }

    @Override
    public List<Auxiliar> getAllAuxiliaries() {
        List<Auxiliar> auxiliaries = new ArrayList<Auxiliar>();
        auxiliaryRepository.findAll().forEach(auxiliaries::add);
        return auxiliaries;
    }

    @Override
    public Optional<Auxiliar> getAuxiliaryById(long id) {
         //Optional<Auxiliary> auxiliaryData = auxiliaryRepository.findById(id);
         return auxiliaryRepository.findById(id);
    }

    @Override
    public Auxiliar updateAuxiliary(long id, Auxiliar auxiliary) {
        Optional<Auxiliar> auxiliaryData = auxiliaryRepository.findById(id);
        try {
            if (auxiliaryData.isPresent()) {
                Auxiliar _auxiliary = auxiliaryData.get();
                _auxiliary.setCodAuxi(auxiliary.getCodAuxi());
                _auxiliary.setRif(auxiliary.getRif());
                //_auxiliary.setAddress(auxiliary.getAddress());
                //_auxiliary.setPhone(auxiliary.getPhone());
                return auxiliaryRepository.save(_auxiliary);
            } else {
                logger.info("Auxiliar no Existe en el Sistema");
                return null;
            }
        } catch (Exception e) {
            logger.info("e.getMessage(): "+e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteAuxiliary(long id) {
        auxiliaryRepository.deleteById(id);
    }



    public Auxiliar findByRifAuxiliary(String rif) {
        try {
            return auxiliaryRepository.findByRifContainingIgnoreCase(rif);
        } catch (Exception e) {
            logger.info("Exception e: "+e.getMessage());
            return null;
        }
    }
}
