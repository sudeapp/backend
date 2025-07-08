package com.sudeca.controller;

import com.sudeca.model.Auxiliar;
import com.sudeca.repository.AuxiliarRepository;
import com.sudeca.impl.AuxiliarServiceImpl;
import com.sudeca.services.IAuxiliarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuxiliaryController {
	
	private static final Logger logger = LogManager.getLogger();

      @Autowired
      AuxiliarRepository auxiliaryRepository;

    @Autowired
    private IAuxiliarService auxiliaryService;


     @GetMapping("/auxiliaries")
      public ResponseEntity<List<Auxiliar>> getAllAuxiliaries() {
        try {
            auxiliaryService = new AuxiliarServiceImpl(auxiliaryRepository);
            List<Auxiliar> auxiliaries = auxiliaryService.getAllAuxiliaries();

              if (auxiliaries.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
              }

          return new ResponseEntity<>(auxiliaries, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("e.getMessage(): "+e.getMessage());
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }

      /*@GetMapping("/auxiliaries/{id}")
      public ResponseEntity<Auxiliary> getAuxiliaryById(@PathVariable("id") long id) {
          auxiliaryService = new AuxiliaryServiceImpl(auxiliaryRepository);
          Optional<Auxiliary> auxiliaryData = auxiliaryService.getAuxiliaryById(id);

          return auxiliaryData.map(auxiliary -> new ResponseEntity<>(auxiliary, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
      }

      @PostMapping("/auxiliaries")
      public String createAuxiliary(@RequestBody Auxiliary auxiliary) {
          Message message;
          Gson gson = new Gson();
            try {
                auxiliaryService = new AuxiliaryServiceImpl(auxiliaryRepository);
                Auxiliary _auxiliary = auxiliaryService.saveAuxiliary(auxiliary);
                logger.info("_auxiliary: "+_auxiliary);

                ResponseEntity<Auxiliary> responseUser = new ResponseEntity<>(_auxiliary, HttpStatus.CREATED);

                if(responseUser.getStatusCodeValue()==HttpStatus.CREATED.value()) {
                    UserReturn userReturn = new UserReturn(_auxiliary.getIdAuxi(),null,null,true,AUXI_STATUS);
                    return gson.toJson(userReturn);
                }
                else {
                    message = new Message(ERROR_INSERT_STATUS+responseUser.getStatusCode(),false);
                    return gson.toJson(message);
                }
            } catch (Exception e) {
                message = new Message(ERROR_EXCEPTION_THROWN+" "+e.getMessage(),false);
                return gson.toJson(message);
            }
      }

  @PutMapping("/auxiliaries/{id}")
  public ResponseEntity<Auxiliary> updateAuxiliary(@PathVariable("id") long id, @RequestBody Auxiliary auxiliary) {
      Message message;
      Gson gson = new Gson();
      logger.info("id: "+id);
      logger.info("auxiliary: "+auxiliary);
      try {
          auxiliaryService = new AuxiliaryServiceImpl(auxiliaryRepository);
          return new ResponseEntity<>(auxiliaryService.updateAuxiliary(id,auxiliary), HttpStatus.OK);
      } catch (Exception e) {
          logger.info("e.getMessage(): "+e.getMessage());
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

  @DeleteMapping("/auxiliaries/{id}")
  public ResponseEntity<HttpStatus> deleteAuxiliary(@PathVariable("id") long id) {
    try {
        auxiliaryService = new AuxiliaryServiceImpl(auxiliaryRepository);
        auxiliaryService.deleteAuxiliary(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }*/
}