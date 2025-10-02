package com.sudeca.services.accounting;

import com.google.gson.Gson;
import com.sudeca.dto.Message;
import com.sudeca.model.PlanContable;
import com.sudeca.repository.AccountingPlanRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sudeca.security.JwtConstans.AUXI_EXISTS_BD;

/**
 * * Author: Francisco Hernandez
 **/
@Service
@Component
public class AccountingPlanServiceImpl implements IAccountingPlanService {

    private static final Logger logger = LogManager.getLogger();

    AccountingPlanRepository accountingPlanRepository;

    Message message;
    Gson gson = new Gson();

    public AccountingPlanServiceImpl(AccountingPlanRepository accountingPlanRepository) {
        this.accountingPlanRepository = accountingPlanRepository;
    }

    @Override
    public PlanContable save(PlanContable planContable) {
                                                //AccountingAuxiliaryType(String codTauxi, String descTaux, String oby, String stTaux, String idPlan)
        //AccountingPlan accountingPlanIn = new AccountingPlan(accountingPlan.getCodPlan(), accountingPlan.getDescription(), accountingPlan.getLevels(), accountingPlan.getLengths(),accountingPlan.getStPlan(),accountingPlan.getVali());
        PlanContable planContableExits = findByCod(planContable.getCodPlan());
        if (planContableExits !=null) {
            message = new Message(AUXI_EXISTS_BD,false);
        }

        logger.info("accountingPlan.getCodPlan(): "+ planContable.getCodPlan());

        return accountingPlanRepository.save(planContable);
    }

    @Override
    public  List<PlanContable> getAll() {
        return new ArrayList<PlanContable>(accountingPlanRepository.findAll());
    }

    @Override
    public Optional<PlanContable> getById(long id) {
         return accountingPlanRepository.findById(id);
    }

    @Override
    public PlanContable update(long id, PlanContable planContable) {
        Optional<PlanContable> accountingPlanData = accountingPlanRepository.findById(id);
        try {
            if (accountingPlanData.isPresent()) {
                PlanContable _PlanContable = accountingPlanData.get();
                _PlanContable.setCodPlan(planContable.getCodPlan());
                _PlanContable.setDescription(planContable.getDescription());
                _PlanContable.setLevels(planContable.getLevels());
                _PlanContable.setLengths(planContable.getLengths());
                _PlanContable.setStPlan(planContable.getStPlan());
                _PlanContable.setValid(planContable.getValid());
                return accountingPlanRepository.save(_PlanContable);
            } else {
                logger.info("El Plan Contable cond código: "+ planContable.getCodPlan()+" no Existe en el Sistema");
                return null;
            }
        } catch (Exception e) {
            logger.info("e.getMessage(): "+e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(long id) {
        accountingPlanRepository.deleteById(id);
    }

    public PlanContable findByCod(String cod) {
        try {
            return accountingPlanRepository.findByCodPlanContainingIgnoreCase(cod);
        } catch (Exception e) {
            logger.info("Exception e: "+e.getMessage());
            return null;
        }
    }
}
