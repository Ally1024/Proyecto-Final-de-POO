package com.tuempresa.OxomocoTour.report.actions;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import org.openxava.actions.JasperReportBaseAction;
import com.tuempresa.OxomocoTour.modelo.Reservacion;

import java.util.HashMap;
import java.util.Map;

public class PrintReservacionReportAction extends JasperReportBaseAction {

    private Reservacion reservacion;

    public void setReservacion(Reservacion reservacion) {
        this.reservacion = reservacion;
    }

    @Override
    public void execute() throws Exception {
        // Obtener la reservación desde la vista si no se pasó por set
        if (this.reservacion == null && getView() != null) {
            Object entity = getView().getEntity();
            if (entity instanceof Reservacion) {
                this.reservacion = (Reservacion) entity;
            }
        }
        if (this.reservacion == null) {
            throw new Exception("No hay reservación seleccionada para imprimir.");
        }
        super.execute();
    }

    @Override
    protected JRDataSource getDataSource() throws Exception {
        // Solo una fila, datos vienen por parámetros
        return new JREmptyDataSource(1);
    }

    @Override
    protected String getJRXML() throws Exception {
        return "Reservacion.jrxml"; // Asegúrate que esté en resources
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected Map getParameters() throws Exception {
        Map<String, Object> params = new HashMap<>();

        // Cliente
        params.put("cliente", reservacion.getCliente() != null ? reservacion.getCliente().getNombre() : "");
        params.put("telefono", reservacion.getCliente() != null ? reservacion.getCliente().getTelefono() : "");

        // Paquete
        params.put("paquete", reservacion.getPaquete() != null ? reservacion.getPaquete().getNombre() : "");

        // Guía (opcional)
        params.put("guia", reservacion.getGuia() != null ? reservacion.getGuia().getNombre() : "");

        // Usuario que registró la reservación (toString() evita errores)
        params.put("usuario", reservacion.getUsuario() != null ? reservacion.getUsuario().toString() : "");

        // Cantidad de personas
        params.put("cantidadPersonas", reservacion.getCantidadPersonas());

        // Fecha reservada
        params.put("fechaReservada", reservacion.getFechaReservada());

        // Estado
        params.put("estado", reservacion.getEstado() != null ? reservacion.getEstado().toString() : "");

        // Total
        params.put("total", reservacion.getTotal() != null ? reservacion.getTotal() : 0);

        // Observaciones
        params.put("observaciones", reservacion.getObservaciones() != null ? reservacion.getObservaciones() : "");

        return params;
    }
}
