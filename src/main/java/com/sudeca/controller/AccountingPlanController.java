package com.sudeca.controller;

import com.google.gson.Gson;
import com.sudeca.dto.Message;
//import com.sudeca.model.UserReturn;
import com.sudeca.model.PlanContable;
import com.sudeca.repository.AccountingPlanRepository;
import com.sudeca.services.accounting.AccountingPlanServiceImpl;
import com.sudeca.services.accounting.IAccountingPlanService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.sudeca.security.Constans.*;

@RestController
@RequestMapping("/api")
public class AccountingPlanController {
	
	private static final Logger logger = LogManager.getLogger();

      @Autowired
      AccountingPlanRepository accountingPlanRepository;

    @Autowired
    private IAccountingPlanService accountingPlanService;


     @GetMapping("/accountingPlan")
      public ResponseEntity<List<PlanContable>> getAllAccountingPlans() {
        try {

            accountingPlanService = new AccountingPlanServiceImpl(accountingPlanRepository);
            List<PlanContable> planContables = accountingPlanService.getAll();

              if (planContables.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
              }

          return new ResponseEntity<>(planContables, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("e.getMessage(): "+e.getMessage());
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }

      @GetMapping("/accountingPlan/{id}")
      public ResponseEntity<PlanContable> getAccountingPlanById(@PathVariable("id") long id) {
          accountingPlanService = new AccountingPlanServiceImpl(accountingPlanRepository);
          Optional<PlanContable> accountingPlanData = accountingPlanService.getById(id);

          return accountingPlanData.map(planContable -> new ResponseEntity<>(planContable, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
      }

      @PostMapping("/accountingPlan")
      public String createAccountingPlan(@RequestBody PlanContable planContable) {
          Message message;
          Gson gson = new Gson();
            try {
                accountingPlanService = new AccountingPlanServiceImpl(accountingPlanRepository);
                PlanContable _PlanContable = accountingPlanService.save(planContable);
                logger.info("_accountingPlan: "+ _PlanContable);

                ResponseEntity<PlanContable> responseUser = new ResponseEntity<>(_PlanContable, HttpStatus.CREATED);

                /*if(responseUser.getStatusCodeValue()==HttpStatus.CREATED.value()) {
                    UserReturn userReturn = new UserReturn(_accountingPlan.getIdPlan(),null,null,true,AUXI_TYPE_STATUS);
                    return gson.toJson(userReturn);
                }
                else {
                    message = new Message(ERROR_INSERT_STATUS+responseUser.getStatusCode(),false);
                    return gson.toJson(message);
                }*/
                return gson.toJson(null);
            } catch (Exception e) {
                message = new Message(ERROR_EXCEPTION_THROWN+" "+e.getMessage(),false);
                return gson.toJson(message);
            }
      }

  @PutMapping("/accountingPlan/{id}")
  public ResponseEntity<PlanContable> updateAccountingPlan(@PathVariable("id") long id, @RequestBody PlanContable planContable) {
      Message message;
      Gson gson = new Gson();
      logger.info("id: "+id);
      logger.info("AccountingPlan: "+ planContable);
      try {
          accountingPlanService = new AccountingPlanServiceImpl(accountingPlanRepository);
          return new ResponseEntity<>(accountingPlanService.update(id, planContable), HttpStatus.OK);
      } catch (Exception e) {
          logger.info("e.getMessage(): "+e.getMessage());
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

  @DeleteMapping("/accountingPlan/{id}")
  public ResponseEntity<HttpStatus> deleteAccountingPlan(@PathVariable("id") long id) {
    try {
        accountingPlanService = new AccountingPlanServiceImpl(accountingPlanRepository);
        accountingPlanService.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}