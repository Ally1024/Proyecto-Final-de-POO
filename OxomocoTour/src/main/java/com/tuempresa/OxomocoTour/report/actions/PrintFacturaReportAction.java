package com.tuempresa.OxomocoTour.report.actions;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import org.openxava.actions.JasperReportBaseAction;
import com.tuempresa.OxomocoTour.modelo.Factura;

import java.util.HashMap;
import java.util.Map;

public class PrintFacturaReportAction extends JasperReportBaseAction {

    private Factura factura;

    /** Permite setear la factura desde código */
    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @Override
    public void execute() throws Exception {
        // Si la factura no fue seteada desde código, intentamos obtenerla del view
        if (this.factura == null) {
            if (getView() != null) {
                Object entity = getView().getEntity();
                if (entity instanceof Factura) {
                    this.factura = (Factura) entity;
                }
            }
        }

        // Validación clara si no hay factura
        if (this.factura == null) {
            throw new Exception("No hay factura seleccionada para imprimir. Abre una factura primero o pásala con setFactura().");
        }

        super.execute();
    }

    @Override
    protected JRDataSource getDataSource() throws Exception {
        // Usamos JREmptyDataSource porque todos los datos vienen por parámetros
        return new JREmptyDataSource(1);
    }

    @Override
    protected String getJRXML() throws Exception {
        return "Factura.jrxml"; // Asegúrate que esté en la ruta correcta
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected Map getParameters() throws Exception {
        Map<String, Object> params = new HashMap<>();

        // Parámetros del reporte desde la factura
        params.put("numeroFactura", factura.getNumeroFactura());
        params.put("fechaEmision", factura.getFechaEmision());
        params.put("cliente", factura.getReservacion().getCliente().getNombre());
        params.put("paquete", factura.getReservacion().getPaquete().getNombre());
        params.put("fechaReservacion", factura.getReservacion().getFechaReservada());
        params.put("total", factura.getTotal());
        params.put("anticipo", factura.getAnticipo());
        params.put("saldoPendiente", factura.getSaldoPendiente());
        params.put("estadoPago", factura.getEstadoPago().toString());
        params.put("metodoPago", factura.getMetodoPago() != null ? factura.getMetodoPago().toString() : "");

        return params;
    }
}
