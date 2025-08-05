package com.sudeca.services;

import com.sudeca.dto.EstadoResultadoDTO;
import com.sudeca.dto.LibroDiarioDTO;
import com.sudeca.dto.ListaComprobanteDTO;
import com.sudeca.dto.UltimosMovimientosDTO;
import com.sudeca.model.view.Vpc;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * * Author: Luis Lopez
 **/

@Service
public interface IFuncionesService {
    public LocalDate obtenerFechaCierre(LocalDate fechaUmc,int lapsoCierre);
    public boolean obtenerFechaValor(int idCaho,LocalDate fechaValor);
    public boolean obtenerMesCierre(int idCaho,LocalDate fecha);
    public List<UltimosMovimientosDTO> obtenerUltimosMovimientos(Long idCaho, Integer dias);
    public boolean getVerificaComprobante(long idComprobante, long idUsuario);
    public boolean getActualizaComprobante(long idComprobante, long idUsuario);
    public List<LibroDiarioDTO> obtenerLibroDiario(Long idCaho, LocalDate fecha);
    public List<ListaComprobanteDTO> obtenerListaComprobante(Long idCaho, LocalDate fechaDesde,LocalDate fechaHasta,Integer tipo);
    public List<EstadoResultadoDTO> obtenerEstadoResultado(Long idCaja, LocalDate fecha, Integer periodo, Boolean tipo);
    public List<Vpc> obtenerVpc(Long id);
}
