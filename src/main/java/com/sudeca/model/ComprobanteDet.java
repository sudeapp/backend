package com.sudeca.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@Table(name = "comprobante_det", schema = "contabilidad",
        uniqueConstraints = @UniqueConstraint(
                name = "cbted_uk",
                columnNames = {"id_cbte", "id_cbted"}
        ))
public class ComprobanteDet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cbted")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cbte", nullable = false)
    @JsonBackReference // Indica que este es el lado "secundario"
    private Comprobante comprobante;

    @Column(name = "id_plan_contable", nullable = false)
    private Long idPlanContable;

    //@Column(name = "id_plan_catalogo", nullable = false)
    @ManyToOne
    @JoinColumn(name = "id_plan_catalogo", nullable = false)
    private PlanCatalogo planCatalogo;

    @Column(name = "id_taux")
    private Long idTaux;

    @ManyToOne
    @JoinColumn(name = "id_auxi")
    private Auxiliar auxiliar;

    @Column(name = "id_opec")
    private Long idOpec;

    @Size(max = 20)
    @Column(name = "nro_doc", length = 20)
    private String nroDoc;

    @Column(name = "fecha_doc")
    private LocalDate fechaDoc;

    @NotBlank
    @Pattern(regexp = "[DC]", message = "Debe ser 'D' o 'C'")
    @Column(name = "dbcr", nullable = false, length = 1)
    private String dbcr;

    @DecimalMin(value = "0.00", inclusive = true)
    @Digits(integer = 18, fraction = 2)
    @Column(name = "bs_monto", nullable = false, precision = 20, scale = 2)
    private BigDecimal bsMonto = BigDecimal.ZERO;

    @Size(max = 90)
    @Column(name = "descripcion", length = 90)
    private String descripcion;

    @Column(name = "linea", nullable = false)
    private Integer linea;

    @Min(1)
    @Max(2)
    @Column(name = "tplan", nullable = false)
    private Integer tplan;

    // Constructores
    public ComprobanteDet() {
    }

}
