package com.sudeca.impl;

import com.sudeca.repository.FuncionesRepository;
import com.sudeca.services.IFuncionesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * * Author: Luis Lopez
 **/
@Service
@Component
public class FuncionesServiceImpl implements IFuncionesService {
    private static final Logger logger = LogManager.getLogger();

    public FuncionesServiceImpl() {
    }

    @Autowired
    private FuncionesRepository funcionesRepository;

    @Override
    public LocalDate obtenerFechaCierre(LocalDate fechaUmc,int lapsoCierre) {
        return funcionesRepository.getFechaCierre(fechaUmc,lapsoCierre);
    }

    @Override
    public boolean obtenerFechaValor(int idCaho,LocalDate fechaValor) {
        return funcionesRepository.getFechaValor(idCaho,fechaValor);
    }

    @Override
    public boolean getVerificaComprobante(long idComprobante, long idUsuario) {
        return funcionesRepository.getVerificaComprobante(idComprobante,idUsuario);
    }

    @Override
    public boolean getActualizaComprobante(long idComprobante, long idUsuario) {
        return funcionesRepository.getActualizaComprobante(idComprobante,idUsuario);
    }

}
