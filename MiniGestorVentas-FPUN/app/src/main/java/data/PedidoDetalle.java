package data;

/**
 * Created by appleyae on 29/10/2016.
 */

public class PedidoDetalle {
    private int idCabecera;
    private int codProducto;
    private int cantidadProducto;
    private int subTotal;

    public PedidoDetalle(int idCabecera, int codProducto, int cantidadProducto, int subTotal) {
        this.idCabecera = idCabecera;
        this.codProducto = codProducto;
        this.cantidadProducto = cantidadProducto;
        this.subTotal = subTotal;
    }

    public PedidoDetalle() {
    }


    public int getIdCabecera() {
        return idCabecera;
    }

    public void setIdCabecera(int idCabecera) {
        this.idCabecera = idCabecera;
    }

    public int getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(int codProducto) {
        this.codProducto = codProducto;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    @Override
    public String toString() {
        return "PedidoDetalle{" +
                "idCabecera=" + idCabecera +
                ", codProducto=" + codProducto +
                ", cantidadProducto=" + cantidadProducto +
                ", subTotal=" + subTotal +
                '}';
    }


}
