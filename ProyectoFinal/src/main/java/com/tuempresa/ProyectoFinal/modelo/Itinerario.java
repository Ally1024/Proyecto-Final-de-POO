package com.tuempresa.ProyectoFinal.modelo;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Itinerario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItinerario;

    @Temporal(TemporalType.DATE)
    private Date fechaActividad;

    private String descripcion;
    private String lugar;
    private String horaInicio;
    private String horaFin;

    @ManyToOne
    @JoinColumn(name = "idPaquete")
    private PaqueteTuristico paquete;

    @ManyToOne
    @JoinColumn(name = "idGuia")
    private GuiaTuristico guia;
}