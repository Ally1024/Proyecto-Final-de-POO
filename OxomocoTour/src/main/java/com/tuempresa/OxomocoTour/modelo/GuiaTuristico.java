package com.tuempresa.OxomocoTour.modelo;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class GuiaTuristico {

    @Id
    @Hidden
    @GeneratedValue(generator= "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String idGuia;

    @Column(length = 50)
    private String nombre;

    @Column(length = 12)
    private String telefono;

    @Column(length = 50)
    private String especialidad ;

}
