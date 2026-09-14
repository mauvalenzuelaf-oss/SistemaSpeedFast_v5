package hilos;

import model.Pedido;
import model.ZonaDeCarga;

/**
 * Representa a un repartidor que retira y entrega
 * pedidos desde una zona de carga compartida.
 */
public class Repartidor implements Runnable {

    // Atributos
    private String nombre;
    private ZonaDeCarga zonaDeCarga;

    /**
     * Construye un repartidor.
     *
     * @param nombre nombre del repartidor
     * @param zonaDeCarga zona compartida de pedidos
     */
    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    /**
     * Retira y entrega pedidos mientras
     * existan pedidos disponibles.
     */
    @Override
    public void run() {

        while (true) {

            Pedido pedido = zonaDeCarga.retirarPedido();

            if (pedido == null) {
                break;
            }

            System.out.println(
                    "[Repartidor - " + nombre
                            + "] Retirando pedido #"
                            + pedido.getId() + "..."
            );

            pedido.setEstado("EN_REPARTO");

            System.out.println(
                    "[Repartidor - " + nombre
                            + "] Estado: "
                            + pedido.getEstado()
            );

            System.out.println(
                    "[Repartidor - " + nombre
                            + "] Entregando pedido #"
                            + pedido.getId() + "..."
            );

            try {

                Thread.sleep(2000);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                System.out.println(
                        "[Repartidor - " + nombre
                                + "] Proceso interrumpido."
                );

                return;
            }

            pedido.setEstado("ENTREGADO");

            System.out.println(
                    "[Repartidor - " + nombre
                            + "] Estado: "
                            + pedido.getEstado()
            );
        }

        System.out.println(
                "[Repartidor - " + nombre
                        + "] Finalizó sus entregas."
        );
    }
}