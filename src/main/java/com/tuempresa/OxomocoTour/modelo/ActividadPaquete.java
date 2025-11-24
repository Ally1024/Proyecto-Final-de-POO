package com.tuempresa.OxomocoTour.modelo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ActividadPaquete {
    @Id
    @Hidden
    @GeneratedValue(generator= "system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    @Column(length=32)
    private String oid;

    @ManyToOne
    @JoinColumn(name="actividad_oid")
    @Required
    private Actividad actividad;

    @ManyToOne
    @JoinColumn(name="paquete_oid")
    @Required
    private PaqueteTuristico paquete;

    @Column(length=120)
    private String descripcion;

}
