package com.sudeca.dto;

import com.sudeca.model.CajaAhorro;
import com.sudeca.model.Usuario;
import com.sudeca.model.UsuarioRol;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UsuarioDTO {
    private long idUsuario;
    private String pass;
    private String nombre;
    private String apellido;
    private String cedula;
    private String direccion;
    private String telefono;
    private String celular;
    private String email;
    private LocalDate fechaNac;
    private String fechaExpCod;
    private Integer estatus;
    private Long id_caho;
    private Long idPlanContable;
    private List<Long> rol = new ArrayList<>();
    private Long idRol;
    private Integer permisologia;
    private String token;
    private int usuarioCaja;

    public UsuarioDTO toDTO(Usuario entity) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setPass(entity.getPass());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setCedula(entity.getCedula());
        dto.setDireccion(entity.getDireccion());
        dto.setTelefono(entity.getTelefono());
        dto.setCelular(entity.getCelular());
        dto.setEmail(entity.getEmail());
        dto.setFechaNac(entity.getFechaNac());
        dto.setFechaExpCod(entity.getFechaExpCod());
        dto.setEstatus(entity.getEstatus());
        dto.setToken(entity.getToken());

        //dto.setUsuario(entity.getUsuario());

        // Mapeo de relaciones (IDs)
        if (entity.getCajaAhorro() != null) {
            dto.setId_caho(entity.getCajaAhorro().getIdCaho());
            dto.setIdPlanContable(entity.getCajaAhorro().getIdPlanContable());
        }

        // Mapeo de roles (IDs)
        if (entity.getRol() != null) {
            dto.setRol(
                    entity.getRol().stream()
                            .map(UsuarioRol::getIdUsuarioRol)
                            .collect(Collectors.toList())
            );

            dto.setIdRol(entity.getRol().get(0).getRol().getIdRol());
            dto.setUsuarioCaja(entity.getRol().get(0).getRol().getUsuarioCaja());
            if(entity.getRol().get(0).getRol().getRolPermisologia() != null){
                dto.setPermisologia(entity.getRol().get(0).getRol().getRolPermisologia().get(0).getPermisologia().getCodigo());
            }else{
                dto.setPermisologia(0);
            }
        }

        return dto;
    }

    public Usuario fromDTO(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(dto.getIdUsuario());
        usuario.setPass(dto.getPass());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCedula(dto.getCedula());
        usuario.setDireccion(dto.getDireccion());
        usuario.setTelefono(dto.getTelefono());
        usuario.setCelular(dto.getCelular());
        usuario.setEmail(dto.getEmail());
        usuario.setFechaNac(dto.getFechaNac());
        usuario.setFechaExpCod(dto.getFechaExpCod());
        usuario.setEstatus(dto.getEstatus());
        usuario.setToken(dto.getToken());
        //usuario.setUsuario(dto.getUsuario());

        // Mapeo de CajaAhorro (solo ID)
        if (dto.getId_caho() != null) {
            CajaAhorro caja = new CajaAhorro();
            caja.setIdCaho(dto.getId_caho());
            usuario.setCajaAhorro(caja);
        }

        // NOTA: Los roles se manejan en servicio separado
        return usuario;
    }
}
