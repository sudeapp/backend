package com.sudeca.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Data
@Entity
@Table(name = "auxiliar_tipo", schema = "contabilidad")
public class AuxiliarTipo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_taux", nullable = false)
    private Long idTaux;

    @JsonProperty("codTaux")
    @Column(name = "cod_taux")
    private String codTaux;

    @JsonProperty("descTaux")
    @Column(name = "desc_taux")
    private String descTaux;

    @ColumnDefault("true")
    @Column(name = "st_taux", nullable = false, length = 255)
    private String stTaux = "true";

    @ColumnDefault("10")
    @Column(name = "oby", nullable = false)
    private Short oby = 10;

}
