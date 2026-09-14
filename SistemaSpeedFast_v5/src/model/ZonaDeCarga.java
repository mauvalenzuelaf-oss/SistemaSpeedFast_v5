package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa la zona de carga compartida
 * por los repartidores.
 */
public class ZonaDeCarga {

    // Pedidos disponibles
    private List<Pedido> pedidos;

    /**
     * Construye una zona de carga vacía.
     */
    public ZonaDeCarga() {
        pedidos = new ArrayList<>();
        System.out.println("[Zona de carga inicializada]");
    }

    /**
     * Agrega un pedido a la zona de carga.
     *
     * @param pedido pedido que será agregado
     */
    public synchronized void agregarPedido(Pedido pedido) {

        pedidos.add(pedido);

        System.out.println(
                "Pedido #" + pedido.getId()
                        + " agregado. Destino: "
                        + pedido.getDireccionEntrega()
        );
    }

    /**
     * Retira de forma segura el siguiente pedido disponible.
     *
     * @return pedido retirado o null si no quedan pedidos
     */
    public synchronized Pedido retirarPedido() {

        if (pedidos.isEmpty()) {
            return null;
        }

        return pedidos.remove(0);
    }
}
