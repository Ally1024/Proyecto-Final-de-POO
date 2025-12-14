package com.tuempresa.OxomocoTour.report.actions;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.openxava.actions.JasperReportBaseAction;
import com.tuempresa.OxomocoTour.modelo.PaqueteTuristico;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.openxava.jpa.XPersistence;

public class PrintPaqueteTuristicoReportAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() throws Exception {
        EntityManager em = XPersistence.getManager();
        List<PaqueteTuristico> paquetes = em.createQuery(
                        "SELECT p FROM PaqueteTuristico p", PaqueteTuristico.class)
                .getResultList();
        return new JRBeanCollectionDataSource(paquetes);
    }

    @Override
    protected String getJRXML() throws Exception {
        return "PaqueteTuristico.jrxml";
    }

    @Override
    protected Map getParameters() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("ReportTitle", "Reporte de Paquetes Turísticos");
        return params;
    }
}
