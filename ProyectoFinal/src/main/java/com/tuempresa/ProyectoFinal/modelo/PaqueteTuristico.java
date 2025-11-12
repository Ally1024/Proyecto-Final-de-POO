package com.tuempresa.ProyectoFinal.modelo;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PaqueteTuristico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaquete;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;
    private String destino;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer cupoTotal;
    private Double precio;
    private String estado;

    @ManyToMany
    private List<GuiaTuristico> guias;

    @OneToMany(mappedBy = "paquete", cascade = CascadeType.ALL)
    private List<Itinerario> itinerarios;
}

