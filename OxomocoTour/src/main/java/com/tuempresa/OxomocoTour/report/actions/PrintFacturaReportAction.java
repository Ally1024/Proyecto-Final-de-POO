package com.tuempresa.OxomocoTour.report.actions;

import com.tuempresa.OxomocoTour.modelo.Factura;
import com.tuempresa.OxomocoTour.modelo.Reservacion;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.jpa.XPersistence;

import java.util.HashMap;
import java.util.Map;

public class PrintFacturaReportAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() {
        // Todos los datos se pasan por parámetros
        return new JREmptyDataSource(1);
    }

    @Override
    protected String getJRXML() {
        return "Factura.jrxml";
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected Map getParameters() throws Exception {

        // 1?? Validar que hay una factura abierta
        if (getView() == null || getView().getKeyValues().isEmpty()) {
            throw new Exception(
                    "Debes abrir una factura guardada antes de imprimir el reporte."
            );
        }

        // 2?? Obtener el OID de la factura desde la vista
        String oid = (String) getView().getKeyValues().get("oid");

        // 3?? Recargar la factura REAL desde la base de datos
        Factura factura = XPersistence.getManager().find(Factura.class, oid);

        if (factura == null) {
            throw new Exception("No se encontró la factura en la base de datos.");
        }

        // 4?? Validar que tenga reservación
        Reservacion reservacion = factura.getReservacion();
        if (reservacion == null) {
            throw new Exception(
                    "La factura no tiene una reservación asignada. " +
                            "Guarda la factura y vuelve a abrirla antes de imprimir."
            );
        }

        // 5?? Parámetros para el reporte
        Map<String, Object> params = new HashMap<>();

        // FACTURA
        params.put("numeroFactura", factura.getNumeroFactura());
        params.put("fechaEmision", factura.getFechaEmision());
        params.put("total", factura.getTotal());
        params.put("anticipo", factura.getAnticipo());
        params.put("saldoPendiente", factura.getSaldoPendiente());
        params.put("estadoPago", factura.getEstadoPago().toString());
        params.put("metodoPago",
                factura.getMetodoPago() != null
                        ? factura.getMetodoPago().toString()
                        : "");

        // RESERVACIÓN
        params.put("fechaReservacion", reservacion.getFechaReservada());
        params.put("cantidadPersonas", reservacion.getCantidadPersonas());

        // CLIENTE
        params.put("cliente",
                reservacion.getCliente() != null
                        ? reservacion.getCliente().getNombre()
                        : "");

        // PAQUETE
        params.put("paquete",
                reservacion.getPaquete() != null
                        ? reservacion.getPaquete().getNombre()
                        : "");

        return params;
    }
}