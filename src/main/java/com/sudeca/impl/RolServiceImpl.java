package com.sudeca.impl;

import com.sudeca.model.Rol;
import com.sudeca.repository.RolRepository;
import com.sudeca.services.IRolService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * * Author: Francisco Hernandez
 **/
@Service
@Component
public class RolServiceImpl implements IRolService {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    RolRepository roleRepository;

    public RolServiceImpl() {

    }

    @Override
    public List<Rol> getAllRoles() {
        List<Rol> roles = new ArrayList<Rol>();
        roleRepository.findAll().forEach(roles::add);
        return roles;
    }

    @Override
    public List<Rol> getRolesCaja() {
        return  roleRepository.findByUsuarioCaja(2);
    }

    @Override
    public List<Rol> getRolesMaster() {
        return  roleRepository.findByUsuarioCaja(1);
    }

    @Override
    public Optional<Rol> getRolById(long id) {
        return  roleRepository.findById(id);
    }

    @Override
    public Rol saveRol(Rol role) {
        Rol roleIn = new Rol(role.getRol(), role.getDescripcion(),role.getEstatus());
        Rol rolExiste = findByRol(roleIn.getRol());
        if (rolExiste==null) {
            return roleRepository.save(roleIn);
        }
        return null;
    }

    @Override
    public Rol updateRol(long id, Rol role) {
        Optional<Rol> roleData = roleRepository.findById(id);
        try {
            if (roleData.isPresent()) {
                Rol _role = roleData.get();
                _role.setRol(role.getRol());
                _role.setDescripcion(role.getDescripcion());
                _role.setEstatus(role.getEstatus());
                _role.setFechaUp(LocalDate.now());
                _role.setFechaReg(roleData.get().getFechaReg());
                return roleRepository.save(_role);
            } else {
                logger.info("Role no Existe en el Sistema");
                return null;
            }
        } catch (Exception e) {
            logger.info("e.getMessage(): "+e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteRol(long id) {
        roleRepository.deleteById(id);
    }

    public Rol findByRol(String role) {
        try {
            return roleRepository.findByRolContainingIgnoreCase(role);
        } catch (Exception e) {
            return null;
        }
    }
}
