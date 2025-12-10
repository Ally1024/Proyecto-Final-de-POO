package com.tuempresa.OxomocoTour.modelo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.ReadOnly;
import org.openxava.annotations.Required;
import org.openxava.annotations.DescriptionsList;

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
    @Hidden
    private String numeroFactura;

    @Column(nullable=false)
    private LocalDate fechaEmision = LocalDate.now();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties="cliente.nombre, paquete.nombre, fechaReservada")
    @Required(message="Debe seleccionar una reservación")
    private Reservacion reservacion;

    @Column(precision=12, scale=2, nullable=false)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(precision=12, scale=2)
    private BigDecimal anticipo = BigDecimal.ZERO;

    @Column(precision=12, scale=2)
    @ReadOnly
    private BigDecimal saldoPendiente = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private EstadoPago estadoPago = EstadoPago.PENDIENTE;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private MetodoPago metodoPago;

    // ------------------------------
    // Generar número único antes de persistir
    // ------------------------------
    @PrePersist
    private void prePersist() {
        generarNumeroFacturaUnico();
        calcularSaldoYEstado();
    }

    // ------------------------------
    // Genera número secuencial único por año
    // ------------------------------
    private void generarNumeroFacturaUnico() {
        if (numeroFactura != null && !numeroFactura.isEmpty()) return;

        int year = LocalDate.now().getYear();
        EntityManager em = EntityManagerProvider.getEntityManager(); // Necesitas un proveedor de EM

        Integer ultimoNumero = (Integer) em.createQuery(
                        "SELECT MAX(CAST(SUBSTRING(f.numeroFactura, 8) AS int)) " +
                                "FROM Factura f WHERE f.numeroFactura LIKE :prefix")
                .setParameter("prefix", "FT-" + year + "-%")
                .getSingleResult();

        int nuevoNumero = (ultimoNumero != null) ? ultimoNumero + 1 : 1;
        numeroFactura = "FT-" + year + "-" + String.format("%03d", nuevoNumero);
    }

    // ------------------------------
    // Cálculo de saldo y estado de pago
    // ------------------------------
    private void calcularSaldoYEstado() {
        if (total == null) total = BigDecimal.ZERO;
        if (anticipo == null) anticipo = BigDecimal.ZERO;

        saldoPendiente = total.subtract(anticipo);

        if (anticipo.compareTo(BigDecimal.ZERO) == 0) {
            estadoPago = EstadoPago.PENDIENTE;
        } else if (saldoPendiente.compareTo(BigDecimal.ZERO) > 0) {
            estadoPago = EstadoPago.ANTICIPO;
        } else {
            estadoPago = EstadoPago.PAGADO;
            saldoPendiente = BigDecimal.ZERO;
        }
    }
}
