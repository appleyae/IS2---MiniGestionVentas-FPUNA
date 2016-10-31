package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static data.ConstantsDB.CLIENTES_ALL;
import static data.ConstantsDB.CREATE_PRODUCTOS;
import static data.ConstantsDB.DETALLE_CANTIDAD_PRODUCTO;
import static data.ConstantsDB.DETALLE_COD_PRODUCTO;
import static data.ConstantsDB.DETALLE_ID_PEDIDO;
import static data.ConstantsDB.DETALLE_SUBTOTAL;
import static data.ConstantsDB.PEDIDO_COD_CLIENTE;
import static data.ConstantsDB.PEDIDO_ID;
import static data.ConstantsDB.PEDIDO_TOTAL;
import static data.ConstantsDB.PRO_DESCRIPCION;
import static data.ConstantsDB.PRO_ID;
import static data.ConstantsDB.PRO_PRECIO;
import static data.ConstantsDB.PRO_STOCK;
import static data.ConstantsDB.QUERY_SPINNER_CLIENTES;
import static data.ConstantsDB.TABLA_CLIENTES;
import static data.ConstantsDB.TABLA_PEDIDO_CABECERA;
import static data.ConstantsDB.TABLA_PEDIDO_DETALLE;
import static data.ConstantsDB.TABLA_PRODUCTOS;

/**
 * Created by appleyae on 20/10/2016.
 */

public class ClientesDB {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public ClientesDB(Context context) {
        dbHelper = new DBHelper(context);
    }

    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if(db!=null){
            db.close();
        }
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, ConstantsDB.DB_NAME, null, ConstantsDB.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ConstantsDB.TABLA_CLIENTES_SQL);
            db.execSQL(ConstantsDB.CREATE_PRODUCTOS);
            db.execSQL(ConstantsDB.CREATE_PEDIDO_CABECERA);
            db.execSQL(ConstantsDB.CREATE_PEDIDO_DETALLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           db.execSQL("DROP TABLE IF EXISTS " + TABLA_CLIENTES);
           db.execSQL("DROP TABLE IF EXISTS " + TABLA_PRODUCTOS);
           db.execSQL("DROP TABLE IF EXISTS " + TABLA_PEDIDO_CABECERA);
           db.execSQL("DROP TABLE IF EXISTS " + TABLA_PEDIDO_DETALLE);

            onCreate(db);

        }

    }

    private ContentValues clienteMapperContentValues(Cliente cliente) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.CLI_ID, cliente.getId());
        cv.put(ConstantsDB.CLI_NOMBRE, cliente.getNombre());
        cv.put(ConstantsDB.CLI_APELLIDO, cliente.getApellido());
        cv.put(ConstantsDB.CLI_TELEFONO, cliente.getTelefono());
        cv.put(ConstantsDB.CLI_DIRECCION, cliente.getDireccion());
        cv.put(ConstantsDB.CLI_CEDULA, cliente.getCedula());
        return cv;
    }

    private ContentValues productoMapperContentValues(Producto producto) {
        ContentValues cv = new ContentValues();
        cv.put(PRO_ID, producto.getId());
        cv.put(PRO_DESCRIPCION,producto.getDescripcion());
        cv.put(PRO_PRECIO,producto.getPrecio());
        cv.put(PRO_STOCK,producto.getStock());
        return cv;
    }


    private ContentValues pedidoCabeceraMapperContentValues(PedidoCabecera pedidocabecera) {
        ContentValues cv = new ContentValues();
        cv.put(PEDIDO_ID, pedidocabecera.getId());
        cv.put(PEDIDO_COD_CLIENTE,pedidocabecera.getCodCliente());
        cv.put(PEDIDO_TOTAL,pedidocabecera.getTotalPedido());
        return cv;
    }

    private ContentValues pedidoDetalleMapperContentValues(PedidoDetalle pedidoDetalle) {
        ContentValues cv = new ContentValues();
        cv.put(DETALLE_ID_PEDIDO, pedidoDetalle.getIdCabecera());
        cv.put(DETALLE_COD_PRODUCTO,pedidoDetalle.getCodProducto());
        cv.put(DETALLE_CANTIDAD_PRODUCTO,pedidoDetalle.getCantidadProducto());
        cv.put(DETALLE_SUBTOTAL,pedidoDetalle.getSubTotal());
        Log.i("contenDetalle" ,cv.toString());
        return cv;
    }

    public long insertProducto(Producto producto) {
        this.openWriteableDB();
        long rowID = db.insert(ConstantsDB.TABLA_PRODUCTOS, null, productoMapperContentValues(producto));
        this.closeDB();
        return rowID;
    }

    public long insertPedido(PedidoCabecera pedidocabecera, ArrayList<DetallePedido> detalleVenta) {
        this.openWriteableDB();
        //inserta en la cabecera
        long rowID = db.insert(ConstantsDB.TABLA_PEDIDO_CABECERA, null, pedidoCabeceraMapperContentValues(pedidocabecera));
        this.closeDB();

        for (DetallePedido pedido : detalleVenta){
            PedidoDetalle pedido2 = new PedidoDetalle();
            pedido2.setIdCabecera(pedidocabecera.getId());
            pedido2.setCodProducto(pedido.getIdProducto());
            Log.i("cabecera cant", String.valueOf(pedido.getCantidadPedido()));
            pedido2.setCantidadProducto(pedido.getCantidadPedido());
            Log.i("cabecera cant2", String.valueOf(pedido2.getCantidadProducto()));
            pedido2.setSubTotal(pedido.getSubtotal());
            this.openWriteableDB();
            //inserta en el detalle fila por fila
            long rowID2 = db.insert(ConstantsDB.TABLA_PEDIDO_DETALLE, null, pedidoDetalleMapperContentValues(pedido2));

            this.closeDB();
        }
        Log.i("-----------","--------------");
        Log.i("insertPedido", String.valueOf(this.buscarPedidoDetalle(pedidocabecera.getId())));

        return rowID;
    }


    public long insertCliente(Cliente cliente) {
        this.openWriteableDB();
        long rowID = db.insert(ConstantsDB.TABLA_CLIENTES, null, clienteMapperContentValues(cliente));
        this.closeDB();

        return rowID;
    }

    public void updateCliente(Cliente cliente) {
        this.openWriteableDB();
        String where = ConstantsDB.CLI_ID + "= ?";
        db.update(ConstantsDB.TABLA_CLIENTES, clienteMapperContentValues(cliente), where, new String[]{String.valueOf(cliente.getId())});
        db.close();
    }

    public void updateProducto(Producto producto) {
        this.openWriteableDB();
        String where = ConstantsDB.PRO_ID + "= ?";
        db.update(ConstantsDB.TABLA_PRODUCTOS, productoMapperContentValues(producto), where,
                new String[]{String.valueOf(producto.getId())});
        db.close();
    }


    public void deleteCliente(int id) {
        this.openWriteableDB();
        String where = ConstantsDB.CLI_ID + "= ?";
        db.delete(ConstantsDB.TABLA_CLIENTES, where, new String[]{String.valueOf(id)});
        this.closeDB();
    }

    public void deleteProducto(int id) {
        this.openWriteableDB();
        String where = ConstantsDB.PRO_ID + "= ?";
        db.delete(ConstantsDB.TABLA_PRODUCTOS, where, new String[]{String.valueOf(id)});
        this.closeDB();
    }

    public ArrayList loadClientes() {

        ArrayList <Cliente> list = new ArrayList<Cliente>();

        this.openReadableDB();
        String[] campos = new String[]{ConstantsDB.CLI_ID, ConstantsDB.CLI_NOMBRE, ConstantsDB.CLI_APELLIDO,
                ConstantsDB.CLI_TELEFONO, ConstantsDB.CLI_DIRECCION, ConstantsDB.CLI_CEDULA};
        Cursor c = db.query(ConstantsDB.TABLA_CLIENTES, campos, null, null, null, null, null);

        try {
            while (c.moveToNext()) {
                Cliente cliente = new Cliente();
                cliente.setId(c.getInt(0));
                cliente.setNombre(c.getString(1));
                cliente.setApellido(c.getString(2));
                cliente.setTelefono(c.getString(3));
                cliente.setDireccion(c.getString(4));
                cliente.setCedula(c.getString(5));
                list.add(cliente);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }
    public ArrayList loadProductos() {

        ArrayList <Producto> list = new ArrayList<Producto>();

        this.openReadableDB();
        String[] campos = new String[]{PRO_ID, PRO_DESCRIPCION, PRO_PRECIO,
                PRO_STOCK};
        Cursor c = db.query(ConstantsDB.TABLA_PRODUCTOS, campos, null, null, null, null, null);

        try {
            while (c.moveToNext()) {
                Producto producto = new Producto();
                producto.setId(c.getInt(0));
                producto.setDescripcion(c.getString(1));
                producto.setPrecio(c.getInt(2));
                producto.setStock(c.getInt(3));
                list.add(producto);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    public ArrayList loadPedidos() {

        ArrayList <PedidoCabecera> list = new ArrayList<PedidoCabecera>();

        this.openReadableDB();
        String[] campos = new String[]{PEDIDO_ID, PEDIDO_COD_CLIENTE, PEDIDO_TOTAL};
        Cursor c = db.query(ConstantsDB.TABLA_PEDIDO_CABECERA, campos, null, null, null, null, null);

        try {
            while (c.moveToNext()) {
                PedidoCabecera pedidoCabecera = new PedidoCabecera();
                pedidoCabecera.setId(c.getInt(0));
                pedidoCabecera.setCodCliente(c.getInt(1));
                pedidoCabecera.setTotalPedido(c.getInt(2));
                list.add(pedidoCabecera);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    public ArrayList<String> recuperaClientes() {
        int codigo;
        String descripcion;

        ArrayList<String> todosClientes = new ArrayList<String>();
        Cursor cursor = db.rawQuery(QUERY_SPINNER_CLIENTES, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                codigo = Integer.parseInt(cursor.getString(0));
                descripcion = cursor.getString(1) + " - " + cursor.getString(2);
                todosClientes.add(codigo + ' ' + descripcion);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return todosClientes;
    }

    public Cliente buscarCliente(int id) {
        Cliente cliente = new Cliente();
        this.openReadableDB();
        String where = ConstantsDB.CLI_ID + "= ?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor c = db.query(ConstantsDB.TABLA_CLIENTES, null, where, whereArgs, null, null, null);

        if( c != null || c.getCount() <=0) {
            c.moveToFirst();
            cliente.setId(c.getInt(0));
            cliente.setNombre(c.getString(1));
            cliente.setApellido(c.getString(2));
            cliente.setDireccion(c.getString(3));
            cliente.setTelefono(c.getString(4));
            cliente.setCedula(c.getString(5));
            c.close();
        }
        this.closeDB();
        return cliente;
    }

    public Producto buscarProducto(int id) {
        Producto producto = new Producto();
        this.openReadableDB();
        String where = ConstantsDB.PRO_ID + "= ?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor c = db.query(ConstantsDB.TABLA_PRODUCTOS, null, where, whereArgs, null, null, null);

        if( c != null || c.getCount() <=0) {
            c.moveToFirst();
            producto.setId(Integer.parseInt(c.getString(0)));
            producto.setDescripcion(c.getString(1));
            producto.setPrecio(Integer.parseInt(c.getString(2)));
            producto.setStock(Integer.parseInt(c.getString(3)));
            c.close();
        }
        this.closeDB();
        return producto;
    }

    public PedidoCabecera buscarPedidoCabecera(int id) {
        PedidoCabecera pedidoCabecera = new PedidoCabecera();
        this.openReadableDB();
        String where = ConstantsDB.PEDIDO_ID + "= ?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor c = db.query(ConstantsDB.TABLA_PEDIDO_CABECERA, null, where, whereArgs, null, null, null);

        if( c != null || c.getCount() <=0) {
            c.moveToFirst();
            pedidoCabecera.setId(Integer.parseInt(c.getString(0)));
            pedidoCabecera.setCodCliente(Integer.parseInt(c.getString(1)));
            pedidoCabecera.setTotalPedido(Integer.parseInt(c.getString(2)));
            c.close();
        }
        this.closeDB();
        return pedidoCabecera;
    }

    public ArrayList buscarPedidoDetalle(int id) {

        this.openReadableDB();
        String where = ConstantsDB.DETALLE_ID_PEDIDO + "= ?";
        String[] whereArgs = {String.valueOf(id)};
        String[] campos = new String[]{DETALLE_ID_PEDIDO, DETALLE_COD_PRODUCTO, DETALLE_CANTIDAD_PRODUCTO, DETALLE_SUBTOTAL};
        Cursor c = db.query(ConstantsDB.TABLA_PEDIDO_DETALLE, campos, where, whereArgs, null, null, null);
        ArrayList <PedidoDetalle> list = new ArrayList<PedidoDetalle>();

        this.openReadableDB();

        try {
            while (c.moveToNext()) {
                PedidoDetalle pedidoDetalle = new PedidoDetalle();
                pedidoDetalle.setIdCabecera(c.getInt(0));
                pedidoDetalle.setCodProducto(Integer.parseInt(c.getString(1)));
                pedidoDetalle.setCantidadProducto(c.getInt(2));
                pedidoDetalle.setSubTotal(c.getInt(3));

                list.add(pedidoDetalle);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;

    }

    public int countClientes(){

        this.openReadableDB();
       /* Cursor c = db.query(TABLA_CLIENTES, null, null, null, null, null, null);
        c.close();*/
        return (int) DatabaseUtils.queryNumEntries(db, TABLA_CLIENTES);
        //retornamos la cantidad
        //return c.getCount();
    }

    public int countProductos(){

        this.openReadableDB();
       /* Cursor c = db.query(TABLA_CLIENTES, null, null, null, null, null, null);
        c.close();*/
        return (int) DatabaseUtils.queryNumEntries(db, TABLA_PRODUCTOS);
        //retornamos la cantidad
        //return c.getCount();
    }

    public int countPedidos(){

        this.openReadableDB();
       /* Cursor c = db.query(TABLA_CLIENTES, null, null, null, null, null, null);
        c.close();*/
        return (int) DatabaseUtils.queryNumEntries(db, TABLA_PEDIDO_CABECERA);
        //retornamos la cantidad
        //return c.getCount();
    }




}
