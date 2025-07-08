package com.sudeca.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rol_permisologia", schema = "acceso")
public class RolPermisologia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol_permisologia", nullable = false)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol", nullable = false)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "id_permisologia", referencedColumnName = "id_permisologia", nullable = false)
    private Permisologia permisologia;

    // Constructor por defecto
    public RolPermisologia() {
    }

}
