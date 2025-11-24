package com.tuempresa.OxomocoTour.modelo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity@Getter@Setter
public class ServicioProveedorPaquete {

    @Id
    @Hidden
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid2")
    @Column(length=32)
    private String oid;

    @ManyToOne
    @JoinColumn(name="paquete_oid")
    @Required
    private PaqueteTuristico paquete;

    @ManyToOne
    @JoinColumn(name="proveedor_oid")
    @Required
    private Proveedor proveedor;

    @Column(length=40)
    private String tipoServicio;
    // "TRANSPORTE", "DESAYUNO", "ALMUERZO", "HOSPEDAJE", "ENTRADA_SITIOS", etc.
    // Puedes usar el mismo valor que TipoDeProveedor o algo más específico

    @Column(length=120)
    private String descripcion;
    // "Transporte climatizado saliendo desde Puma Metrocentro"

}
