package com.tuempresa.OxomocoTour.modelo;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;
import org.openxava.annotations.TextArea;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Getter
@Setter

public class Reservacion {

    @Id
    @Hidden
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String oid;



    @ManyToOne
    @JoinColumn(name = "cliente_oid")
    @Required(message = "Debe seleccionar un cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "paquete_oid")
    @Required(message = "Debe seleccionar un paquete turístico")
    private PaqueteTuristico paquete;

    @ManyToOne
    @JoinColumn(name = "guia_oid")
    private GuiaTuristico guia; // opcional

    @ManyToOne
    @JoinColumn(name = "usuario_oid")
    @Required(message = "Debe seleccionar el usuario que registra la reservacion")
    private Usuario usuario;


    @Column(nullable = false)
    @Required(message = "Debe ingresar la cantidad de personas")
    private Integer cantidadPersonas;

    @Column(nullable = false)
    @Required(message = "Debe indicar la fecha de la reservación")
    private LocalDate fechaReservada;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Required
    private EstadoReservacion estado;

    @Column(precision = 10, scale = 2, nullable = false)
    @Required(message = "Debe indicar el total")
    private BigDecimal total;

    @TextArea
    private String observaciones;

    // ---------------------------
    // LÓGICA DE CUPOS
    // ---------------------------

    @PrePersist
    private void validarYReservarCupos() {
        int disponibles = paquete.getCupoDisponible();
        int personas = this.cantidadPersonas;

        if (personas > disponibles) {
            throw new RuntimeException(
                    "No hay suficientes cupos. Disponibles: " + disponibles
            );
        }

        // Reservar cupos
        paquete.setCupoReservado(paquete.getCupoReservado() + personas);
    }

    // Si después editas/cancelas, esto libera cupos al borrar
    @PreRemove
    private void liberarCuposAlEliminar() {
        if (paquete != null && cantidadPersonas != null) {
            paquete.setCupoReservado(
                    paquete.getCupoReservado() - cantidadPersonas
            );
        }
    }
}