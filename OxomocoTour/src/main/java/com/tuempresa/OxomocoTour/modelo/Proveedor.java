package com.tuempresa.OxomocoTour.modelo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;

import javax.persistence.*;

@Entity
@Getter@Setter
public class Proveedor {

    @Id
    @Hidden
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    private String oid;

    @Column(length=80, nullable=false)
    @Required
    private String nombre;

    @Column(length=60)
    private String contacto;

    @Column(length=20)
    private String telefono;


    @ManyToOne
    @JoinColumn(name="tipo_proveedor_oid")
    @Required
    private TipoDeProveedor tipoDeProveedor;
    // Ej: Transporte, Restaurante, Hotel
}
