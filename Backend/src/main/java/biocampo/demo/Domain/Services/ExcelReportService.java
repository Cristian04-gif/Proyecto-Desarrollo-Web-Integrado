package biocampo.demo.Domain.Services;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Persistance.CRUD.RepoCliente;
import biocampo.demo.Persistance.CRUD.RepoCosecha;
import biocampo.demo.Persistance.CRUD.RepoCultivo;
import biocampo.demo.Persistance.CRUD.RepoDetallePedido;
import biocampo.demo.Persistance.CRUD.RepoDetalleVenta;
import biocampo.demo.Persistance.CRUD.RepoEmpleado;
import biocampo.demo.Persistance.CRUD.RepoOrden;
import biocampo.demo.Persistance.CRUD.RepoProducto;
import biocampo.demo.Persistance.CRUD.RepoVenta;
import biocampo.demo.Persistance.Entity.Cliente;
import biocampo.demo.Persistance.Entity.Cosecha;
import biocampo.demo.Persistance.Entity.Cultivo;
import biocampo.demo.Persistance.Entity.DetallePedido;
import biocampo.demo.Persistance.Entity.DetalleVenta;
import biocampo.demo.Persistance.Entity.Empleado;
import biocampo.demo.Persistance.Entity.Pedido;
import biocampo.demo.Persistance.Entity.Producto;
import biocampo.demo.Persistance.Entity.Venta;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class ExcelReportService {
    @Autowired
    private RepoCliente repoCliente;

    @Autowired
    private RepoEmpleado repoEmpleado;

    @Autowired
    private RepoCultivo repoCultivo;

    @Autowired
    private RepoCosecha repoCosecha;

    @Autowired
    private RepoOrden repoOrden;

    @Autowired
    private RepoDetallePedido repoDetallePedido;

    @Autowired
    private RepoProducto repoProducto;

    @Autowired
    private RepoVenta repoVenta;

    @Autowired
    private RepoDetalleVenta repoDetalleVenta;

    public byte[] generarExcelClientes() throws JRException {
        List<Cliente> clientes = repoCliente.findAll();
        InputStream reporteStream = getClass().getResourceAsStream("/Reports/ReporteClientes.jasper");
        JasperReport jasperReport;
        if (reporteStream == null) {
            InputStream inputStream = getClass().getResourceAsStream("/Reports/ReporteClientes.jrxml");
            jasperReport = JasperCompileManager.compileReport(inputStream);
        } else {
            jasperReport = (JasperReport) JRLoader.loadObject(reporteStream);
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("detallesClientes", new JRBeanCollectionDataSource(clientes));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setDetectCellType(true);
        configuration.setWhitePageBackground(false);
        configuration.setCollapseRowSpan(false);

        exporter.setConfiguration(configuration);
        exporter.exportReport();

        return outputStream.toByteArray();
    }

    public byte[] generarExcelEmpleados() throws JRException {
        List<Empleado> empleados = repoEmpleado.findAll();
        InputStream reporteStream = getClass().getResourceAsStream("/Reports/ReporteEmpleados.jasper");
        JasperReport jasperReport;
        if (reporteStream == null) {
            InputStream inputStream = getClass().getResourceAsStream("/Reports/ReporteEmpleados.jrxml");
            jasperReport = JasperCompileManager.compileReport(inputStream);
        } else {
            jasperReport = (JasperReport) JRLoader.loadObject(reporteStream);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("detallesEmpleados", new JRBeanCollectionDataSource(empleados));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setDetectCellType(true);
        configuration.setWhitePageBackground(false);
        configuration.setCollapseRowSpan(false);

        exporter.setConfiguration(configuration);
        exporter.exportReport();
        return outputStream.toByteArray();
    }

    public byte[] generarExcelCultivos() throws JRException {
        List<Cultivo> cultivos = repoCultivo.findAll();

        InputStream reporteStream = getClass().getResourceAsStream("/Reports/ReporteCultivo.jasper");
        JasperReport jasperReport;
        if (reporteStream == null) {
            InputStream inputStream = getClass().getResourceAsStream("/Reports/ReporteCultivo.jrxml");
            jasperReport = JasperCompileManager.compileReport(inputStream);
        } else {
            jasperReport = (JasperReport) JRLoader.loadObject(reporteStream);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("detallesCultivos", new JRBeanCollectionDataSource(cultivos));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setDetectCellType(true);
        configuration.setWhitePageBackground(false);
        configuration.setCollapseRowSpan(false);

        exporter.setConfiguration(configuration);
        exporter.exportReport();
        return outputStream.toByteArray();
    }

    public byte[] generarExcelCosecha() throws JRException {
        List<Cosecha> cosechas = repoCosecha.findAll();

        InputStream reporteStream = getClass().getResourceAsStream("/Reports/ReporteCosecha.jasper");
        JasperReport jasperReport;
        if (reporteStream == null) {
            InputStream inputStream = getClass().getResourceAsStream("/Reports/ReporteCosecha.jrxml");
            jasperReport = JasperCompileManager.compileReport(inputStream);
        } else {
            jasperReport = (JasperReport) JRLoader.loadObject(reporteStream);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("detallesCosecha", new JRBeanCollectionDataSource(cosechas));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setDetectCellType(true);
        configuration.setWhitePageBackground(false);
        configuration.setCollapseRowSpan(false);

        exporter.setConfiguration(configuration);
        exporter.exportReport();
        return outputStream.toByteArray();
    }

    public byte[] generarExcelPedidos() throws JRException {
        List<Pedido> pedidos = repoOrden.findAll();
        List<DetallePedido> detallePedidos = repoDetallePedido.findAll();

        InputStream reporteStream = getClass().getResourceAsStream("/Reports/ReportePedido.jasper");
        JasperReport jasperReport;
        if (reporteStream == null) {
            InputStream inputStream = getClass().getResourceAsStream("/Reports/ReportePedido.jrxml");
            jasperReport = JasperCompileManager.compileReport(inputStream);
        } else {
            jasperReport = (JasperReport) JRLoader.loadObject(reporteStream);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("pedidos", new JRBeanCollectionDataSource(pedidos));
        params.put("detalles", new JRBeanCollectionDataSource(detallePedidos));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setDetectCellType(true);
        configuration.setWhitePageBackground(false);
        configuration.setCollapseRowSpan(false);

        exporter.setConfiguration(configuration);
        exporter.exportReport();

        return outputStream.toByteArray();
    }

    public byte[] generarExcelProductos() throws JRException {
        List<Producto> productos = repoProducto.findAll();

        InputStream reporteStream = getClass().getResourceAsStream("/Reports/ReporteProductos.jasper");
        JasperReport jasperReport;
        if (reporteStream == null) {
            InputStream inputStream = getClass().getResourceAsStream("/Reports/ReporteProductos.jrxml");
            jasperReport = JasperCompileManager.compileReport(inputStream);
        } else {
            jasperReport = (JasperReport) JRLoader.loadObject(reporteStream);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("productos", new JRBeanCollectionDataSource(productos));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setDetectCellType(true);
        configuration.setWhitePageBackground(false);
        configuration.setCollapseRowSpan(false);

        exporter.setConfiguration(configuration);
        exporter.exportReport();

        return outputStream.toByteArray();
    }

    public byte[] generarExcelVentas() throws JRException {
        List<Venta> ventas = repoVenta.findAll();
        List<DetalleVenta> detalleVentas = repoDetalleVenta.findAll();

        InputStream reporteStream = getClass().getResourceAsStream("/Reports/ReporteVenta.jasper");
        JasperReport jasperReport;
        if (reporteStream == null) {
            InputStream inputStream = getClass().getResourceAsStream("/Reports/ReporteVenta.jrxml");
            jasperReport = JasperCompileManager.compileReport(inputStream);
        } else {
            jasperReport = (JasperReport) JRLoader.loadObject(reporteStream);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("ventas", new JRBeanCollectionDataSource(ventas));
        params.put("detalles", new JRBeanCollectionDataSource(detalleVentas));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setDetectCellType(true);
        configuration.setWhitePageBackground(false);
        configuration.setCollapseRowSpan(false);

        exporter.setConfiguration(configuration);
        exporter.exportReport();

        return outputStream.toByteArray();
    }
}
