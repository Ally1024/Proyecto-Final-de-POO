package com.tuempresa.ProyectoFinal.modelo;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 50, nullable = false)
    private String apellido;

    @Column(unique = true, nullable = false)
    private String correo;

    @Column(length = 20)
    private String telefono;

    private String direccion;

    @Column(length = 20)
    private String tipoCliente;
}
