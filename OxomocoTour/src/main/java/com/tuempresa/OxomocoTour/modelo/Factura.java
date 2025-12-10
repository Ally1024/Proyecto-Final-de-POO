package com.tuempresa.OxomocoTour.modelo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.ReadOnly;
import org.openxava.annotations.Required;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Factura {
    @Id
    @Hidden
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    private String oid;

    @Column(length = 20, nullable=false, unique=true)
    @Required(message="El número de factura es obligatorio")
    private String numeroFactura;

    @Column(nullable=false)
    private LocalDate fechaEmision = LocalDate.now();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties="cliente.nombre, paquete.nombre, fechaReservada")
    @Required(message="Debe seleccionar una reservación")
    private Reservacion reservacion;

    // Totales de dinero
    @Column(precision=12, scale=2, nullable=false)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(precision=12, scale=2)
    private BigDecimal anticipo = BigDecimal.ZERO; // normalmente 50%

    @Column(precision=12, scale=2)
    @ReadOnly
    private BigDecimal saldoPendiente = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private EstadoPago estadoPago = EstadoPago.PENDIENTE;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private MetodoPago metodoPago;  // Nuevo enum: TARJETA, EFECTIVO, TRANSFERENCIA

    // ---- Métodos de apoyo (OpenXava permite lógica simple aquí) ----
    @PrePersist
    @PreUpdate
    private void calcularSaldoYEstado() {
        if (total == null) total = BigDecimal.ZERO;
        if (anticipo == null) anticipo = BigDecimal.ZERO;

        saldoPendiente = total.subtract(anticipo);

        if (anticipo.compareTo(BigDecimal.ZERO) == 0) {
            estadoPago = EstadoPago.PENDIENTE;
        }
        else if (saldoPendiente.compareTo(BigDecimal.ZERO) > 0) {
            estadoPago = EstadoPago.ANTICIPO;
        }
        else {
            estadoPago = EstadoPago.PAGADO;
            saldoPendiente = BigDecimal.ZERO;
        }
    }
}