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
    @Hidden
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

        // Primero, genera el número de factura si es nuevo
        if (numeroFactura == null || numeroFactura.isEmpty()) {
            generarNumeroFactura();
        }

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

    private void generarNumeroFactura() {
    int year = LocalDate.now().getYear();
    // Aquí necesitas consultar el último número del año en BD
    // Si usas JPA puro, usa EntityManager; si Spring Data, un repositorio.
    // Ejemplo simple (ajusta con tu setup):
    // Asume que tienes un EntityManager inyectado o usa un servicio.
    // Para simplicidad, usa una query JPQL. Si no, hardcodea un contador inicial.
    // Ejemplo con EntityManager (inyéctalo si usas CDI o Spring):
    // EntityManager em = ...; // Inyecta
    // Query query = em.createQuery("SELECT MAX(CAST(SUBSTRING(f.numeroFactura, 8) AS int)) FROM Factura f WHERE f.numeroFactura LIKE :prefix");
    // query.setParameter("prefix", "FT-" + year + "-%");
    // Integer ultimo = (Integer) query.getSingleResult();
    // int numero = (ultimo != null) ? ultimo + 1 : 1;
    // Para empezar, usa un contador fijo (cambia a query real):
    int numero = 1; // Reemplaza con la lógica de BD arriba
    numeroFactura = "FT-" + year + "-" + String.format("%03d", numero);
    }

}