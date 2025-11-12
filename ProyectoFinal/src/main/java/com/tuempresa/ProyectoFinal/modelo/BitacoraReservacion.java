package com.tuempresa.ProyectoFinal.modelo;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BitacoraReservacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBitacora;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCambio;

    private String usuarioResponsable;
    private String accion;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "idReservacion")
    private Reservacion reservacion;
}

