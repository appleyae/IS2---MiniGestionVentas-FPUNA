package data;

/**
 * Created by appleyae on 28/10/2016.
 */

public class DetallePedido {
    private int idProducto;
    private String descripcionProducto;
    private int cantidadPedido;
    private int subtotal;

    public DetallePedido() {
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public DetallePedido(String descripcionProducto, int cantidadPedido, int subtotal) {
        this.descripcionProducto = descripcionProducto;
        this.cantidadPedido = cantidadPedido;
        this.subtotal = subtotal;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public int getCantidadPedido() {
        return cantidadPedido;
    }

    public void setCantidadPedido(int cantidadPedido) {
        this.cantidadPedido = cantidadPedido;
    }

    public int getSubtotal() {
        return subtotal;
    }

    /*@Override
    public String toString() {
        return "DetallePedido{" +
                "descripcionProducto= '" + descripcionProducto + '\'' +
                ", cantidadPedido= " + cantidadPedido +
                ", subtotal= " + subtotal +
                '}';
    }*/
    public String toString() {
        return  descripcionProducto + "; Cantidad: " + cantidadPedido +
                " Subtotal: " + subtotal;
    }
    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
}
