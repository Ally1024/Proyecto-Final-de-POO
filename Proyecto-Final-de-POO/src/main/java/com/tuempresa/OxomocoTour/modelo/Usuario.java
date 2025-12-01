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

public class Usuario {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(length = 32)
    private String idusuario;


    @Column(length = 30, nullable = false)
    @Required(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;

    private String rol ;
    // ya sea administrador, secretaria, guia, vendedor, etc




}
