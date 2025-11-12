package com.tuempresa.ProyectoFinal.modelo;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProveedor;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipoServicio; // Transporte, Hospedaje, Alimentación

    private String contacto;
    private String telefono;
    private String correo;
}

