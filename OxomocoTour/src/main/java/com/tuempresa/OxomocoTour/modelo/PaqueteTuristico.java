package com.tuempresa.OxomocoTour.modelo;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Money;
import org.openxava.annotations.Required;
import java.util.List;
import javax.persistence.OneToMany;
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
    private String oid;   // Cambié a "oid" para que sea consistente con tus otras entidades

    @Column(length = 50, nullable = false)
    @Required
    private String nombre;

    @Column(length = 100)
    private String lugar;

    @OneToMany(mappedBy = "paquete")
    private List<ActividadPaquete> actividades;

    @Money
    private BigDecimal tarifa;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @Column(length = 200)
    private String descripcion;

    // la cantidad maxima de cupos disponibles para el mero paqueton turistico
    @Column(nullable = false)
    @Required(message = "Debe indicar el cupo total")
    private Integer cupoTotal;

    @Column(nullable = false)
    private Integer cupoReservado = 0;

    @Transient
    public Integer getCupoDisponible() {
        return cupoTotal - cupoReservado;
    }


}
