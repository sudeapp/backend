package com.sudeca.services;

import com.sudeca.dto.ComprobanteDTO;
import com.sudeca.model.Comprobante;
import com.sudeca.model.ComprobanteDet;
import com.sudeca.model.Usuario;
import com.sudeca.repository.ComprobanteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public interface IComprobanteService {

    Comprobante saveComprobanteWithDetails(ComprobanteDTO comprobanteDTO);
    List<Comprobante> buscarComprobantes(Long idCaho,
                                         LocalDate fechaInicio,
                                         LocalDate fechaFin,
                                         Long nroComprobante,
                                         String nombreUsuario,
                                         Integer idEstatus);
    Comprobante actualizarComprobante(Long id, ComprobanteDTO comprobanteActualizado);
}