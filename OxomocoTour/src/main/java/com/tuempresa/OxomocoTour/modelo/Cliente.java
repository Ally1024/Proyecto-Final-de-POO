package com.tuempresa.OxomocoTour.modelo;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter

public class Cliente {
    @Id
    @Hidden
    @GeneratedValue(generator= "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String oid;


    @Column(name="nombre_cliente",length = 50, nullable = false)
    @Required(message="El nombre es obligatorio")
    private String nombre;

    @Column (name="telefono_cliente", length=15)
    private String telefono;
}

