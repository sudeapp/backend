package com.sudeca.controller;

import com.sudeca.dto.ResponseDTO;
import com.sudeca.dto.ValidaCodigoContableDTO;
import com.sudeca.model.PlanCatalogo;
import com.sudeca.services.IFuncionesService;
import com.sudeca.services.IPlanCatalogoService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plan-catalogo")
public class PlanCatalogoController {

    @Autowired
    private IPlanCatalogoService planCatalogoService;
    @Autowired
    private IFuncionesService funcionesService;
    private static final Logger logger = LogManager.getLogger();

    @GetMapping("/validar-cuenta-caja")
    public ResponseEntity<ResponseDTO> getValidaCuentaCaja(@RequestParam("idCaho") Long idCaho, @RequestParam("cuenta") String cuenta) {
        logger.info("getValidaCuentaCaja: "+cuenta);
        ValidaCodigoContableDTO res = funcionesService.validarCodigoContableCaja(idCaho,cuenta);
        ResponseDTO responseDTO = new ResponseDTO();
        if (res != null){
            responseDTO.setData(res);
            responseDTO.setStatus("success");
            responseDTO.setMessage("validación");
        }else{
            responseDTO.setData(false);
            responseDTO.setStatus("notFound");
            responseDTO.setMessage("Registro no encontrado");
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanCatalogo> getById(@PathVariable Long id) {
        Optional<PlanCatalogo> planCatalogo = planCatalogoService.findById(id);
        return planCatalogo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/lista")
    public ResponseEntity<ResponseDTO> getByPlanContable(@RequestParam("idPlanContable") Long idPlanContable) {
        List<PlanCatalogo> planCatalogos = planCatalogoService.findByIdPlanContable(idPlanContable);
        //return new ResponseEntity<>(planCatalogos, HttpStatus.OK);

        ResponseDTO responseDTO = new ResponseDTO();
        if (planCatalogos != null){
            responseDTO.setData(planCatalogos);
            responseDTO.setStatus("success");
            responseDTO.setMessage("validación");
        }else{
            responseDTO.setData(false);
            responseDTO.setStatus("notFound");
            responseDTO.setMessage("Registro no encontrado");
        }
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/save")
    public ResponseEntity<?> create(@Valid @RequestBody PlanCatalogo planCatalogo, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        logger.info("************************* Create PlanCatalogo");

        try {
            PlanCatalogo savedPlanCatalogo = planCatalogoService.save(planCatalogo);
            return new ResponseEntity<>(savedPlanCatalogo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody PlanCatalogo planCatalogo,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        /*if (!planCatalogoService.findById(id).isPresent()) {
            return new ResponseEntity<>("PlanCatalogo no encontrado", HttpStatus.NOT_FOUND);
        }*/

        //planCatalogo.setIdPlanCatalogo(id);
        try {
            PlanCatalogo updatedPlanCatalogo = planCatalogoService.update(planCatalogo);
            return new ResponseEntity<>(updatedPlanCatalogo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
        if (!planCatalogoService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        planCatalogoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
