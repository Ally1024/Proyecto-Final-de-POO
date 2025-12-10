package com.tuempresa.OxomocoTour.modelo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Money;
import org.openxava.annotations.Required;
import java.util.List;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class PaqueteTuristico {
    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;

    @Column(length = 50, nullable = false)
    @Required
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "destino_oid")
    @Required(message = "Debe seleccionar un destino")
    private Destino destino;  // Reemplaza "lugar" (String) con relación a Destino

    @ManyToMany (cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "paquete_actividad",
            joinColumns = @JoinColumn(name = "paquete_oid"),
            inverseJoinColumns = @JoinColumn(name = "actividad_oid")
    )
    private List<Actividad> actividades;  // Muchas actividades en un paquete

    @ManyToMany (cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "paquete_proveedor",
            joinColumns = @JoinColumn(name = "paquete_oid"),
            inverseJoinColumns = @JoinColumn(name = "proveedor_oid")
    )
    private List<Proveedor> proveedores;  // Proveedores (transporte, comida, hoteles) en un paquete

    @Money
    private BigDecimal tarifa;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @Column(length = 200)
    private String descripcion;

    @Column(nullable = false)
    @Required(message = "Debe indicar el cupo total")
    private Integer cupoTotal;

    @Column(nullable = false)
    private Integer cupoReservado = 0;

    // Campos opcionales para descripciones (si no quieres tablas intermedias)
    @Column(length = 500)
    private String descripcionActividades;  // Ej. "Selva Negra: Caminata guiada"

    @Column(length = 500)
    private String descripcionProveedores;  // Ej. "Transporte: Bus climatizado"

    @Transient
    public Integer getCupoDisponible() {
        return cupoTotal - cupoReservado;
    }
}