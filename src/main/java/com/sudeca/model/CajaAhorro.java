package com.sudeca.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "caja_ahorro", schema = "contabilidad")
public class CajaAhorro implements Serializable {

    /*public enum SectorCajaAhorro {
        PUBLICO, PRIVADO
    }

    public enum Estatus {
        ACTIVO, SUSPENDIDO, RETIRADO
    }*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caho")
    private long idCaho;

    @Column(name = "cod_caho", length = 10, nullable = false, unique = true)
    private String codigoCaho;

    @Column(length = 11, nullable = false, unique = true)
    private String rif;

    @Column(name = "desc_caho")
    private String descripcion;

    @Column(name = "nombre_caho")
    private String nombre;

    @Column(name = "patrono")
    private String patrono;

    @Column(name = "sector_caho", nullable = false)
    private Integer sector;

    @Column(name = "id_plan_contable")
    private long idPlanContable;

    @Column(name = "mes_cierre", precision = 2, scale = 0, nullable = false)
    private Short mesCierre = 12;

    @Column(name = "ctd_periodos", precision = 2, scale = 0, nullable = false)
    private Short periodosEjercicio;

    @Column(name = "ult_mes_cerrado", nullable = false)
    private LocalDate ultimoMesCerrado;

    @Column(name = "ult_periodo_cerrado", precision = 2, scale = 0, nullable = false)
    private Short ultimoPeriodoCerrado;

    @Column(name = "lapso_cierre_mensual", precision = 2, scale = 0, nullable = false)
    private Short lapsoCierreMensual = 15;

    @Column(name = "id_moneda_local")
    private Integer idMonedaLocal;

    @Column(name = "fecha_inicio_vigencia")
    private LocalDate inicioVigencia;

    @Column(name = "fecha_fin_vigencia")
    private LocalDate finVigencia;

    @Column(name = "ult_lapso_generado")
    private LocalDate ultimoLapsoGenerado;

    @Column(name = "usuario_creacion",nullable = false)
    private Integer usuarioCreacion;

    @Column(name = "estatus",nullable = false, length = 2)
    private Integer estatus;

    @OneToMany(mappedBy = "cajaAhorro",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true)
    @JsonManagedReference // Indica que este es el lado "principal" de la relación (evita la redundancia)
    private List<CategoriaCajaAhorro> categoriaCajaAhorros = new ArrayList<>();

    @OneToMany(mappedBy = "cajaAhorro",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true)
    @JsonManagedReference // Indica que este es el lado "principal" de la relación (evita la redundancia)
    private List<Usuario> usuarios = new ArrayList<>();

    public CajaAhorro() {}

    @Override
    public String toString() {
        return "CajaAhorro [idCaho=" + idCaho + ", codigoCaho=" + codigoCaho + "]";
    }
}
