package com.tuempresa.ProyectoFinal.modelo;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class GuiaTuristico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGuia;

    @Column(nullable = false)
    private String nombre;

    private String telefono;
    private String correo;
    private String especialidad;
    private Boolean disponible;
}

