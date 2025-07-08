package com.sudeca.services.UserRole;

import com.google.gson.Gson;
import com.sudeca.dto.Message;
import com.sudeca.model.UsuarioRol;
import com.sudeca.repository.UserRoleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * * Author: Francisco Hernandez
 **/
@Service
@Component
public class UserRoleServiceImpl implements IUserRoleService {

    private static final Logger logger = LogManager.getLogger();

    UserRoleRepository userRoleRepository;

    Message message;
    Gson gson = new Gson();

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UsuarioRol saveUserRole(UsuarioRol userRole) {

        UsuarioRol _userRole = userRoleRepository.save(userRole);
        logger.info("_userRole: "+_userRole);
        return _userRole;
    }

    @Override
    public List<UsuarioRol> getAllUserRoles() {
        List<UsuarioRol> userRoles = new ArrayList<UsuarioRol>();
        userRoleRepository.findAll().forEach(userRoles::add);
        return userRoles;
    }

    @Override
    public Optional<UsuarioRol> getUUserRoleById(long id) {
         return null;
    }

    @Override
    public UsuarioRol updateUserRole(long id, UsuarioRol userRole) {
        Optional<UsuarioRol> userRoleData = userRoleRepository.findById(id);
        try {
            if (userRoleData.isPresent()) {
                //    String jwt = new CreateJWTImpl().createJWT(empresa.getCodempresa(), empresa.getRif(),  empresa.getRazonSocial(),JW_TIME_TO_LIVE);
                UsuarioRol _userRoleData = userRoleData.get();
                _userRoleData.setUsuario(userRole.getUsuario());
                _userRoleData.setRol(userRole.getRol());
                _userRoleData.setFechaReg(userRoleData.get().getFechaReg());
                return userRoleRepository.save(_userRoleData);
            } else {
                logger.info("Relación Usuario-Role no Existe en el Sistema");
                return null;
            }
        } catch (Exception e) {
            logger.info("e.getMessage(): "+e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteUserRole(long id) {
        userRoleRepository.deleteById(id);
    }

}
