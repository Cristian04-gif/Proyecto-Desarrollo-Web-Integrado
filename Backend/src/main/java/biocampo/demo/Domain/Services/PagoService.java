package biocampo.demo.Domain.Services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

import biocampo.demo.Domain.DTO.Config.MercadoPagoConfiguration;
import biocampo.demo.Domain.Model.Sale;
import biocampo.demo.Domain.Model.SaleDetail;
import biocampo.demo.Persistance.CRUD.RepoCliente;
import biocampo.demo.Persistance.CRUD.RepoProducto;
import biocampo.demo.Persistance.Entity.Cliente;
import biocampo.demo.Persistance.Entity.DetalleVenta;
import biocampo.demo.Persistance.Entity.Producto;
import biocampo.demo.Persistance.Entity.Venta;
import biocampo.demo.Persistance.Mappings.SaleDetailMapper;
import biocampo.demo.Persistance.Mappings.SaleMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final MercadoPagoConfiguration configuration;

    @Autowired
    private SaleDetailMapper detailMapper;
    @Autowired
    private RepoProducto repoProducto;
    @Autowired
    private SaleMapper saleMapper;
    @Autowired
    private RepoCliente repoCliente;

    public Preference crearPago(Sale sale, List<SaleDetail> details) throws MPException, MPApiException {
        // calcular el costo total
        double total = 0.0;
        for (SaleDetail saleDetail : details) {
            DetalleVenta detalleVenta = detailMapper.toDetalleVenta(saleDetail);
            Producto producto = repoProducto.findById(detalleVenta.getProducto().getIdProducto()).orElseThrow();

            if (detalleVenta.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad no puede ser negatiba o cero");
            }
            if (detalleVenta.getCantidad() > producto.getStock()) {
                throw new IllegalArgumentException(
                        "Stock isuficiente para el producto: " + producto.getEtiqueta());
            }
            double subtotal = producto.getPrecio() * detalleVenta.getCantidad();
            total += subtotal;
        }
        // descripciones
        Venta venta = saleMapper.toVenta(sale);
        Cliente cliente = repoCliente.findByUsuarioEmail(venta.getCliente().getUsuario().getEmail()).orElseThrow();
        String idParameter = cliente.getIdCliente().toString();
        String titulo = "Productos agrucolas";
        String descripcion = details.size() + " productos agricolas comprados";

        //

        MercadoPagoConfig.setAccessToken(configuration.getAccessToken());
        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .id(idParameter)
                .title(titulo)
                .description(descripcion)
                .quantity(1)
                .currencyId("PEN")
                .unitPrice(new BigDecimal(total))
                .build();
        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("https://youtu.be/w6uvYEoC-LU?list=RDKkGK6UbgazA")
                .pending("https://www.tu-sitio/pending")
                .failure("https://www.tu-sitio/failure")
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .notificationUrl("")
                .build();
        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);
        return preference;
    }

    /*public Payment getPayment(String paymentId) throws MPException {
        MercadoPagoConfig.setAccessToken(configuration.getAccessToken()); // si no está global

        // Consulta a la API de Mercado Pago
        Payment payment = Payment.findById(paymentId);
        return payment;
    }*/

}
