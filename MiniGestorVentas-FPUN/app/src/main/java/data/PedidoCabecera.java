package data;

/**
 * Created by appleyae on 29/10/2016.
 */

public class PedidoCabecera {
    private int id;
    private int codCliente;
    private int totalPedido;

    public PedidoCabecera() {
    }

    public PedidoCabecera(int id, int codCliente, int totalPedido) {
        this.id = id;
        this.codCliente = codCliente;
        this.totalPedido = totalPedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public int getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(int totalPedido) {
        this.totalPedido = totalPedido;
    }

    @Override
    public String toString() {
        return "PedidoCabecera{" +
                "id=" + id +
                ", codCliente=" + codCliente +
                ", totalPedido=" + totalPedido +
                '}';
    }
}

