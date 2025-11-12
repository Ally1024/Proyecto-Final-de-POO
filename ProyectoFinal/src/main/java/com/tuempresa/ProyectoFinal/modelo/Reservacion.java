package com.tuempresa.ProyectoFinal.modelo;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Reservacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservacion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReserva;

    private String estado;
    private Integer cantidadPersonas;
    private Double total;
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idPaquete")
    private PaqueteTuristico paquete;

}

