package com.tuempresa.OxomocoTour.report.actions;

import org.openxava.actions.JasperReportBaseAction;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;

import java.util.HashMap;
import java.util.Map;

public class PrintTourReportAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() throws Exception {
        return new JREmptyDataSource(); // No hay datasource aún
    }

    @Override
    protected String getJRXML() throws Exception {
        return "TourOverview.jrxml"; // Nombre EXACTO del archivo
    }

    @Override
    protected Map<String, Object> getParameters() throws Exception {
        return new HashMap<>(); // Sin parámetros todavía
    }
}