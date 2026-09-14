package model;

/**
 * Representa un pedido dentro del sistema SpeedFast.
 */
public class Pedido {

    // Atributos
    private int id;
    private String direccionEntrega;
    private EstadoPedido estado;

    /**
     * Construye un pedido con sus datos principales.
     *
     * @param id identificador del pedido
     * @param direccionEntrega dirección de entrega
     */
    public Pedido(int id, String direccionEntrega) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.estado = EstadoPedido.PENDIENTE;
    }

    /**
     * Actualiza el estado del pedido.
     *
     * @param nuevoEstado nuevo estado del pedido
     */
    public void setEstado(String nuevoEstado) {
        this.estado = EstadoPedido.valueOf(nuevoEstado);
    }

    /**
     * Muestra la información del pedido.
     *
     * @return información del pedido
     */
    @Override
    public String toString() {
        return "Pedido #" + id
                + " - Destino: " + direccionEntrega
                + " - Estado: " + estado;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public EstadoPedido getEstado() {
        return estado;
    }
}