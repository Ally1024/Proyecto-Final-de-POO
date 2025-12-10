package com.tuempresa.OxomocoTour.report.actions;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.openxava.actions.JasperReportBaseAction;
import org.openxava.jpa.XPersistence;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class PrintClienteReportAction extends JasperReportBaseAction {

    @Override
    protected JRDataSource getDataSource() throws Exception {

        EntityManager em = XPersistence.getManager();

        List<?> lista = em.createQuery(
                "from Cliente"
        ).getResultList();

        return new JRBeanCollectionDataSource(lista);
    }

    @Override
    protected String getJRXML() throws Exception {
        return "Cliente.jrxml";
    }

    @Override
    protected Map getParameters() throws Exception {
        return null;
    }
}

