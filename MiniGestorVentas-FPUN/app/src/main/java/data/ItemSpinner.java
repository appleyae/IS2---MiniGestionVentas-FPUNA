package data;

/**
 * Created by appleyae on 25/10/2016.
 * Creado para el manejo de los Spinners
 */

public class ItemSpinner {
    int codigo;
    String descripcion;

    public ItemSpinner() {
    }

    public ItemSpinner(int codigo, String descripcion) {

        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String toString() {
        return descripcion;
    }
}
