package com.sudeca.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudeca.dto.*;
import com.sudeca.model.view.Vpc;
import com.sudeca.repository.*;
import com.sudeca.services.IFuncionesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    @Autowired
    private RLibroDiarioRepository libroDiarioRepository;
    @Autowired
    private RListaComprobanteRepository listaComprobanteRepository;
    @Autowired
    private REstadoResultadoRepository estadoResultadoRepository;
    @Autowired
    private RBalanceComprobacionRepository balanceComprobacionRepository;
    @Autowired
    private RBalanceGeneralRepository balanceGeneralRepository;
    @Autowired
    private UltimosMovimientosRepository ultimosMovimientosRepository;
    @Autowired
    private VpcRepository vpcRepository;
    @Override
    public LocalDate obtenerFechaCierre(LocalDate fechaUmc,int lapsoCierre) {
        return funcionesRepository.getFechaCierre(fechaUmc,lapsoCierre);
    }

    @Override
    public boolean obtenerFechaValor(int idCaho,LocalDate fechaValor) {
        return funcionesRepository.getFechaValor(idCaho,fechaValor);
    }

    @Override
    public boolean obtenerMesCierre(int idCaho,LocalDate fecha) {
        return funcionesRepository.getMesCierre(idCaho,fecha);
    }

    @Override
    public boolean getVerificaComprobante(long idComprobante, long idUsuario) {
        return funcionesRepository.getVerificaComprobante(idComprobante,idUsuario);
    }

    @Override
    public boolean getActualizaComprobante(long idComprobante, long idUsuario) {
        return funcionesRepository.getActualizaComprobante(idComprobante,idUsuario);
    }

    @Override
    public List<LibroDiarioDTO> obtenerLibroDiario(Long idCaho, LocalDate fecha) {
        return libroDiarioRepository.findLibroDiario(idCaho,fecha);
    }

    @Override
    public List<ListaComprobanteDTO> obtenerListaComprobante(Long idCaho, LocalDate fechaDesde,LocalDate fechaHasta,Integer tipo) {
        return listaComprobanteRepository.findListaComprobante(idCaho,fechaDesde,fechaHasta,tipo);
    }

    @Override
    public List<EstadoResultadoDTO> obtenerEstadoResultado(Long idCaja, LocalDate fecha, Integer periodo, Boolean tipo) {
        return estadoResultadoRepository.findEstadoResultado(idCaja,fecha,periodo,tipo);
    }

    @Override
    public List<BalanceGeneralDTO> obtenerBalanceGeneral(Long idCaja, LocalDate fecha, Integer periodo, Boolean tipo) {
        return balanceGeneralRepository.findBalanceGeneral(idCaja,fecha,periodo,tipo);
    }

    @Override
    public List<BalanceComprobacionDTO> obtenerBalanceComprobacion(Long idCaja, LocalDate fecha, Integer periodo, Boolean tipo) {
        return balanceComprobacionRepository.findBalanceComprobacion(idCaja,fecha,periodo,tipo);
    }

    @Override
    public List<UltimosMovimientosDTO> obtenerUltimosMovimientos(Long idCaho, Integer dias) {
        return ultimosMovimientosRepository.findUltimosMovimientos(idCaho,dias);
    }

    public List<Vpc> obtenerVpc(Long id) {
        return vpcRepository.findByIdCaho(id);
    }

    @Override
    public ValidaCodigoContableDTO validarCodigoContableCaja(Long pIdCaho, String pCuenta) {
        String jsonResult = funcionesRepository.callValidaCodigoContable(pIdCaho, pCuenta);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResult, ValidaCodigoContableDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear el resultado JSON", e);
        }
    }

    @Override
    public ValidaCodigoContableDTO validarCodigoContablePlantilla(Long pIdCaho, String pCuenta) {
        String jsonResult = funcionesRepository.callValidaCodigoContablePlantilla(pIdCaho, pCuenta);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResult, ValidaCodigoContableDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear el resultado JSON", e);
        }
    }

    @Override
    public boolean getValidaNivelPlantilla(Long idPPlanContable) {
        return funcionesRepository.getValidaNivelPlantilla(idPPlanContable);
    }

    @Override
    public boolean getCierreMensual(Long idCaho,Long idUsuario) {
        return funcionesRepository.getCierreMensual(idCaho,idUsuario);
    }

    @Override
    public boolean getCierreAnual(Long idCaho,Long idUsuario) {
        return funcionesRepository.getCierreAnual(idCaho,idUsuario);
    }
}
