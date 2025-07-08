package com.sudeca.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categoria_caja_ahorro", schema = "contabilidad")
public class CategoriaCajaAhorro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria_caho")
    private Integer idCategoriaCaho;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_caho")
    @JsonBackReference // Indica que este es el lado "secundario"
    private CajaAhorro cajaAhorro;

    // Constructor vacío requerido por JPA
    public CategoriaCajaAhorro() {
    }

}