package biocampo.demo.Domain.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import biocampo.demo.Domain.Model.Customer;
import biocampo.demo.Domain.Model.Sale;
import biocampo.demo.Domain.Model.SaleDetail;
import biocampo.demo.Domain.Repository.CustomerRepository;
import biocampo.demo.Domain.Repository.SaleRepository;
import biocampo.demo.Persistance.CRUD.RepoCliente;
import biocampo.demo.Persistance.CRUD.RepoDetalleVenta;
import biocampo.demo.Persistance.CRUD.RepoProducto;
import biocampo.demo.Persistance.CRUD.RepoVenta;
import biocampo.demo.Persistance.Entity.Cliente;
import biocampo.demo.Persistance.Entity.DetalleVenta;
import biocampo.demo.Persistance.Entity.Producto;
import biocampo.demo.Persistance.Entity.Venta;
import biocampo.demo.Persistance.Entity.Venta.Metodo;
import biocampo.demo.Persistance.Mappings.SaleDetailMapper;
import biocampo.demo.Persistance.Mappings.SaleMapper;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SaleMapper saleMapper;
    @Autowired
    private RepoVenta repoVenta;
    @Autowired
    private RepoDetalleVenta repoDetalleVenta;
    @Autowired
    private SaleDetailMapper saleDetailMapper;
    @Autowired
    private RepoCliente repoCliente;
    @Autowired
    private RepoProducto repoProducto;

    // Obtener todas las ventas
    public List<Sale> getAllSales() {
        return saleRepository.getAll();
    }

    public List<Sale> getSaleByCustomerId(String emailuser){
        Customer customer = customerRepository.findByUsuarioEmail(emailuser).orElseThrow();
        return saleRepository.getSaleByCustomerId(customer.getCustomerId());
    }

    // Obtener venta por ID
    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.getById(id);
    }

    @Transactional
    public Sale registerSale(Sale sale, List<SaleDetail> details) {
        Venta ventaEntity = saleMapper.toVenta(sale);
        Cliente clienteEntity = repoCliente.findById(ventaEntity.getCliente().getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("El cliente no existe"));
        ventaEntity.setCliente(clienteEntity);

        Venta ventaGuardada = repoVenta.save(ventaEntity);

        double subTotal = 0.0, totalImpuestos = 0.0;
        
        for (SaleDetail saleDetail : details) {
            DetalleVenta detalleVenta = saleDetailMapper.toDetalleVenta(saleDetail);
            Producto productoEntity = repoProducto.findById(detalleVenta.getProducto().getIdProducto()).orElseThrow();

            if (detalleVenta.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad no puede ser negatiba o cero");
            }
            if (detalleVenta.getCantidad() > productoEntity.getStock()) {
                throw new IllegalArgumentException(
                        "Stock isuficiente para el producto: " + productoEntity.getEtiqueta());
            }
            double subTotalSinImpuestos = productoEntity.getPrecio() * detalleVenta.getCantidad();
            double impuesto = Math.round((subTotalSinImpuestos * 0.18) * 100) / 100;
            productoEntity.setStock(productoEntity.getStock() - detalleVenta.getCantidad());
            repoProducto.save(productoEntity);

            detalleVenta.setVenta(ventaGuardada);
            detalleVenta.setProducto(productoEntity);
            detalleVenta.setImpuestos(impuesto);
            detalleVenta.setPorcentajeImpuestos(18.0);
            detalleVenta.setSubtotal(subTotalSinImpuestos + impuesto);
            DetalleVenta detalleGuardado = repoDetalleVenta.save(detalleVenta);
            System.out.println("id detalle ventata guardada: " + detalleGuardado.getIdDetalleVenta());
            System.out.println("productos del detalle guardado: " + detalleGuardado.getProducto().getEtiqueta());
            if (ventaGuardada.getDetalle() == null) {
                ventaGuardada.setDetalle(new ArrayList<>());
            }
            ventaGuardada.getDetalle().add(detalleGuardado);
            subTotal += subTotalSinImpuestos;
            totalImpuestos += impuesto;
        }
        // ventaGuardada.setTotal((double) Math.round(total * 100) / 100);
        ventaGuardada.setSubTotal(subTotal);
        ventaGuardada.setImpuestoTotal(totalImpuestos);
        ventaGuardada.setTotal(subTotal + totalImpuestos);
        try {
            ventaGuardada.setPago(Metodo.valueOf(sale.getPaymentMethod().toUpperCase()));
        } catch (IllegalArgumentException e) {
            System.out.println("error: " + e);
            throw new IllegalArgumentException("El método de pago no es válido");
        }

        Venta ventaFinal = repoVenta.save(ventaGuardada);
        return saleMapper.toSale(ventaFinal);
    }

    // Eliminar venta
    @Transactional
    public void deleteSale(Long id) {
        System.out.println("Servicio de delete");
        Venta venta = repoVenta.findById(id).orElseThrow();
        for (DetalleVenta detalle : venta.getDetalle()) {
            repoDetalleVenta.deleteById(detalle.getIdDetalleVenta());
        }
        repoVenta.deleteById(id);
    }
}
