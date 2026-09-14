package app;

import hilos.Repartidor;
import model.Pedido;
import model.ZonaDeCarga;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Clase principal del sistema SpeedFast.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println(
                "=== SISTEMA SPEEDFAST - SEMANA 5 ==="
        );

        System.out.println();

        // Zona de carga compartida
        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();

        System.out.println();

        // Pedidos
        Pedido pedido1 =
                new Pedido(1, "Santiago Centro");

        Pedido pedido2 =
                new Pedido(2, "Providencia");

        Pedido pedido3 =
                new Pedido(3, "Ñuñoa");

        Pedido pedido4 =
                new Pedido(4, "Recoleta");

        Pedido pedido5 =
                new Pedido(5, "Las Condes");

        // Agregar pedidos
        zonaDeCarga.agregarPedido(pedido1);
        zonaDeCarga.agregarPedido(pedido2);
        zonaDeCarga.agregarPedido(pedido3);
        zonaDeCarga.agregarPedido(pedido4);
        zonaDeCarga.agregarPedido(pedido5);

        System.out.println();

        // Repartidores
        Repartidor juan =
                new Repartidor("Juan", zonaDeCarga);

        Repartidor camila =
                new Repartidor("Camila", zonaDeCarga);

        Repartidor pedro =
                new Repartidor("Pedro", zonaDeCarga);

        System.out.println(
                "=== INICIO DE ENTREGAS ==="
        );

        System.out.println();

        // Ejecutar repartidores en paralelo
        ExecutorService executor =
                Executors.newFixedThreadPool(3);

        executor.execute(juan);
        executor.execute(camila);
        executor.execute(pedro);

        executor.shutdown();

        try {

            boolean finalizado =
                    executor.awaitTermination(
                            1,
                            TimeUnit.MINUTES
                    );

            if (finalizado) {

                System.out.println();

                System.out.println(
                        "Todos los pedidos han sido "
                                + "entregados correctamente"
                );

            } else {

                System.out.println(
                        "La simulación excedió "
                                + "el tiempo de espera."
                );

                executor.shutdownNow();
            }

        } catch (InterruptedException e) {

            executor.shutdownNow();
            Thread.currentThread().interrupt();

            System.out.println(
                    "La simulación fue interrumpida."
            );
        }
    }
}