package com.sudeca.controller;

import com.sudeca.dto.ResponseDTO;
import com.sudeca.impl.AuxiliarServiceImpl;
import com.sudeca.model.Auxiliar;
import com.sudeca.model.AuxiliarTipo;
import com.sudeca.repository.AuxiliarRepository;
import com.sudeca.services.IAuxiliarService;
import com.sudeca.services.IAuxiliarTipoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/auxiliar-tipo")
public class AuxiliarTipoController {
	
	private static final Logger logger = LogManager.getLogger();

    @Autowired
    private IAuxiliarTipoService auxiliarTipoService;

    @GetMapping("/lista-auxiliar-tipo")
    public ResponseEntity<ResponseDTO> getAuxiliarTipo() {
        List<AuxiliarTipo> res = auxiliarTipoService.findByAll();
        ResponseDTO responseDTO = new ResponseDTO();
        if (res != null){
            responseDTO.setData(res);
            responseDTO.setStatus("success");
            responseDTO.setMessage("consulta");
        }else{
            responseDTO.setData(false);
            responseDTO.setStatus("notFound");
            responseDTO.setMessage("Registro no encontrado");
        }
        return ResponseEntity.ok(responseDTO);
    }
}