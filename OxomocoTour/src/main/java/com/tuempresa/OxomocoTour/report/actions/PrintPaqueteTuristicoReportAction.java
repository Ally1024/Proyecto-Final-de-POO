package com.tuempresa.OxomocoTour.report.actions;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.openxava.actions.JasperReportBaseAction;

import java.util.Collection;
import java.util.Map;

public class PrintPaqueteTuristicoReportAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() throws Exception {
        return new JREmptyDataSource();
    }

    @Override
    protected String getJRXML() throws Exception {
        return "PaqueteTuristico.jrxml";
    }

    @Override
    protected Map getParameters() throws Exception {
        return null;
    }
}
