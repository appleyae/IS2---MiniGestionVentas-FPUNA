package data;

/**
 * Created by appleyae on 20/10/2016.
 */

public class ConstantsDB {

    //General
    public static final String DB_NAME ="gestionventas.db";
    public static final int DB_VERSION = 1;


    //TABLAS USADAS
    public static final String TABLA_CLIENTES = "Cliente";
    public static final String TABLA_PRODUCTOS = "Productos";
    public static final String TABLA_PEDIDO_CABECERA = "Pedido_cabecera";
    public static final String TABLA_PEDIDO_DETALLE = "Pedido_detalle";

    //columnas de cliente
    public static final String CLI_ID = "_id";
    public static final String CLI_NOMBRE = "nombre";
    public static final String CLI_APELLIDO = "apellido";
    public static final String CLI_TELEFONO = "telefono";
    public static final String CLI_DIRECCION = "direccion";
    public static final String CLI_CEDULA = "cedula";

    //columnas de productos
    public static final String PRO_ID= "_id";
    public static final String PRO_DESCRIPCION= "descripcion";
    public static final String PRO_PRECIO= "precio";
    public static final String PRO_STOCK = "stock";

    //columnas pedido cabecera
    public static final String PEDIDO_ID = "_id";
    public static final String PEDIDO_COD_CLIENTE = "cod_cliente";
    public static final String PEDIDO_TOTAL = "total_pedido";

    //columnas pedido detalle
    public static final String DETALLE_ID_PEDIDO = "_id";
    public static final String DETALLE_COD_PRODUCTO = "cod_producto";
    public static final String DETALLE_CANTIDAD_PRODUCTO = "cantidad_producto";
    public static final String DETALLE_SUBTOTAL = "subtotal";



    //CREATE TABLE de las tablas utilizadas
    public static final String TABLA_CLIENTES_SQL =
            "CREATE TABLE  " + TABLA_CLIENTES + "(" +
                    CLI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CLI_NOMBRE + " TEXT NOT NULL," +
                    CLI_APELLIDO + " TEXT NOT NULL," +
                    CLI_DIRECCION   + " TEXT NOT NULL," +
                    CLI_TELEFONO   + " TEXT NOT NULL," +
                    CLI_CEDULA   + " TEXT NOT NULL);" ;

    public final static String CREATE_PRODUCTOS =
            "CREATE TABLE " + TABLA_PRODUCTOS + "(" +
                    PRO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PRO_DESCRIPCION + " TEXT NOT NULL," +
                    PRO_PRECIO + " INTEGER NOT NULL," +
                    PRO_STOCK   + " INTEGER NOT NULL);";

    public static final String CREATE_PEDIDO_CABECERA =
            "CREATE TABLE " + TABLA_PEDIDO_CABECERA + "(" +
                    PEDIDO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PEDIDO_COD_CLIENTE + " INTEGER NOT NULL," +
                    PEDIDO_TOTAL + " INTEGER NOT NULL);";

    public static final String CREATE_PEDIDO_DETALLE =
            "CREATE TABLE " + TABLA_PEDIDO_DETALLE + "(" +
                    DETALLE_ID_PEDIDO + " INTEGER NOT NULL, " +
                    DETALLE_COD_PRODUCTO + " INTEGER NOT NULL," +
                    DETALLE_CANTIDAD_PRODUCTO + " INTEGER NOT NULL," +
                    DETALLE_SUBTOTAL + " INTEGER NOT NULL);";

    public static final String QUERY_SPINNER_CLIENTES ="select _id, nombre, apellido from " + TABLA_CLIENTES;
    public static final String CLIENTES_ALL = "Select * from " + TABLA_CLIENTES + ";";
}
