package com.sudeca.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@Table(name = "comprobante",
        schema = "contabilidad",
        uniqueConstraints = @UniqueConstraint(
                name = "cbte_uk",
                columnNames = {"id_caho", "nro_cbte"}
        ))
public class Comprobante implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cbte")
    private Long id;

    @Column(name = "id_caho", nullable = false)
    private Long idCaho;

    @Column(name = "nro_cbte", nullable = false)
    private Long nroCbte;

    @Column(name = "fecha_cbte", nullable = false)
    private LocalDate fechaCbte;

    @Column(name = "periodo", nullable = false)
    private Integer periodo;

    @Column(name = "estatus_cbte", nullable = false)
    private Short estatusCbte = 0;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @ManyToOne()
    @JoinColumn(name = "id_usuario_creacion", referencedColumnName = "id_usuario", nullable = false)
    private Usuario usuarioCreacion;

    @Column(name = "fecha_verificacion")
    private LocalDate fechaVerificacion;

    @Column(name = "id_usuario_verificacion")
    private Long idUsuarioVerificacion;

    @Column(name = "fecha_actualizacion")
    private LocalDate fechaActualizacion;

    @Column(name = "id_usuario_actualizacion")
    private Long idUsuarioActualizacion;

    @Column(name = "id_tcbte", nullable = false)
    private Integer idTcbte;

    @Digits(integer = 18, fraction = 2)
    @Column(name = "bs_monto_diferencia", nullable = false, precision = 20, scale = 2)
    private BigDecimal bsMontoDiferencia = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", inclusive = true)
    @Digits(integer = 18, fraction = 2)
    @Column(name = "bs_monto_debito", nullable = false, precision = 20, scale = 2)
    private BigDecimal bsMontoDebito = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", inclusive = true)
    @Digits(integer = 18, fraction = 2)
    @Column(name = "bs_monto_credito", nullable = false, precision = 20, scale = 2)
    private BigDecimal bsMontoCredito = BigDecimal.ZERO;

  /*  @OneToMany(mappedBy = "comprobante",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true)
    @JsonManagedReference
    private List<ComprobanteDet> comprobanteDet = new ArrayList<>();
*/
    @OneToMany(
            mappedBy = "comprobante",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<ComprobanteDet> comprobanteDet = new ArrayList<>();

    // Constructores
    public Comprobante() {
    }
    // Método para inicializar la colección
    public void inicializarDetalles() {
        if (this.comprobanteDet == null) {
            this.comprobanteDet = new ArrayList<>();
        }
    }

    // NO PERMITIR SETTER PARA LA COLECCIÓN
    // Eliminar cualquier setter que reemplace la colección completa

    // Solo usar estos métodos para manipular la colección
    public void agregarDetalle(ComprobanteDet detalle) {
        this.comprobanteDet.add(detalle);
        detalle.setComprobante(this);
    }

    public void eliminarDetalle(ComprobanteDet detalle) {
        this.comprobanteDet.remove(detalle);
        detalle.setComprobante(null);
    }

    public void recalcularMontos() {
        BigDecimal totalDebito = BigDecimal.ZERO;
        BigDecimal totalCredito = BigDecimal.ZERO;

        for (ComprobanteDet detalle : comprobanteDet) {
            if ("D".equals(detalle.getDbcr())) {
                totalDebito = totalDebito.add(detalle.getBsMonto());
            } else if ("C".equals(detalle.getDbcr())) {
                totalCredito = totalCredito.add(detalle.getBsMonto());
            }
        }

        this.bsMontoDebito = totalDebito;
        this.bsMontoCredito = totalCredito;
        this.bsMontoDiferencia = totalDebito.subtract(totalCredito).abs();
    }
}
