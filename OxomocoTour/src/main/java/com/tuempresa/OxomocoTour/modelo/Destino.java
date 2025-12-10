package com.tuempresa.OxomocoTour.modelo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Destino {
    @Id
    @Hidden
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    private String oid;

    @Column(length=50, nullable=false)
    @Required
    private String nombre;  //

    @Column(length=200)
    private String descripcion;  // Opcional: descripción del destino

    @OneToMany(mappedBy = "destino", fetch = FetchType.LAZY)
    private List<PaqueteTuristico> paquetes;  // Bidireccional: paquetes en este destino
}