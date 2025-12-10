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
public class TipoDeProveedor {
    @Id
    @Hidden
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String oid;

    @Column(length=40, nullable=false)
    @Required
    private String nombre; // TRANSPORTE, HOSPEDAJE, ALIMENTACION, GUIA_LOCAL...

    @Column(length=100)
    private String descripcion;
}