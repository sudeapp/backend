package com.sudeca.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * * Author: Luis Lopez
 **/

@Service
public interface IFuncionesService {
    public LocalDate obtenerFechaCierre(LocalDate fechaUmc,int lapsoCierre);
    public boolean obtenerFechaValor(int idCaho,LocalDate fechaValor);
    public boolean getVerificaComprobante(long idComprobante, long idUsuario);
    public boolean getActualizaComprobante(long idComprobante, long idUsuario);
}
