package minigestorventas.app.fpuna.com.minigestorventas_fpun;
/*
* Falta agregar la parte de registrar el pedido y ya estamos!!
*
* */
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LoggingMXBean;

import data.Cliente;
import data.ClientesDB;
import data.DetallePedido;
import data.ItemSpinner;
import data.PedidoCabecera;
import data.Producto;

public class PedidoActivity extends AppCompatActivity implements  View.OnClickListener{
    private ClientesDB db;
    TextView prueba;
    int idCliente;
    TextView totalView;
    TextView tvPrecioUnit;
    Spinner spinner_producto;
    Spinner spinner_cantidad;
    int idProductoSelect;
    int idCantidadSelect;
    int totalPedido = 0;

    ArrayList<DetallePedido> listViewPedidos = new ArrayList<DetallePedido>();
    //String[] listViewPedidos = new String[]{};
    ListView detalleDeLosPedidos;
    Button botonAgregar;
    Button botonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //decalaramos el objeto de BD
        db = new ClientesDB(this);

        //capturamos lo que trajo la activity anterior
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        //capturamos las variables graficas
        prueba = (TextView) findViewById(R.id.prueba);
        spinner_producto = (Spinner) findViewById(R.id.spinner_productos);
        spinner_cantidad = (Spinner) findViewById(R.id.spinner_cantidad);
        totalView = (TextView) findViewById(R.id.tvTotalPedido);
        detalleDeLosPedidos = (ListView) findViewById(R.id.listaPedido);
        botonAgregar = (Button) findViewById(R.id.botonAgregar);
        botonRegistrar = (Button) findViewById(R.id.botonRegistrar);
        tvPrecioUnit = (TextView) findViewById(R.id.tvPrecioUnit);

        if (extra != null){
            idCliente = extra.getInt("idCliente");
            Cliente cliente = db.buscarCliente(idCliente);
            Producto producto = db.buscarProducto(2);

            Log.i("clienteNuevo", cliente.toString());
            prueba.setText("Cliente: " + cliente.getNombre() + " " + cliente.getApellido() );

            Log.i("productoNuevo", producto.toString());

        }

        //para el spinner
        final List<Producto> listaProducto = db.loadProductos();
        List<ItemSpinner> spinnerProductos = new ArrayList<ItemSpinner>();
        Log.i("un producto--> :", String.valueOf(listaProducto.get(1).getDescripcion()));
        for(Producto producto : listaProducto) {
            ItemSpinner cargar = new ItemSpinner(producto.getId(), producto.getDescripcion());
            spinnerProductos.add(cargar);
        }
        ArrayAdapter<ItemSpinner> productos = new ArrayAdapter<ItemSpinner>(this, android.R.layout.simple_spinner_dropdown_item, spinnerProductos);
        productos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_producto.setAdapter(productos);

        List<ItemSpinner> cantidadVenta = new ArrayList<ItemSpinner>();

        for (int i=1; i<=50; i++ ){
            ItemSpinner cargar2 = new ItemSpinner(i, String.valueOf(i));
            cantidadVenta.add(cargar2);
        }
        ArrayAdapter<ItemSpinner> cantidades = new ArrayAdapter<ItemSpinner>(this, android.R.layout.simple_spinner_dropdown_item, cantidadVenta);
        cantidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_cantidad.setAdapter(cantidades);

        spinner_producto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idProductoSelect = position;
                tvPrecioUnit.setText(String.valueOf (listaProducto.get(position).getPrecio()));
                Log.i("seleccionaProducto: pos", String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_cantidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCantidadSelect = position + 1;


                Log.i("cantidadSelect", String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        botonAgregar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("botonAgregar: ", "hace click");
                DetallePedido detalle = new DetallePedido();
                Producto producto = db.buscarProducto(idProductoSelect);
                detalle.setCantidadPedido(idCantidadSelect);
                detalle.setDescripcionProducto(producto.getDescripcion());
                detalle.setIdProducto(idProductoSelect);
                detalle.setSubtotal(producto.getPrecio()*idCantidadSelect);

                Log.i("botonAgregar: ", detalle.toString());
                listViewPedidos.add(detalle);
                List<ItemSpinner> listaDetalle = new ArrayList<ItemSpinner>();

               ArrayAdapter<DetallePedido> arrayAdapter = new ArrayAdapter<DetallePedido>
                        (getApplicationContext(), android.R.layout.simple_list_item_activated_1, listViewPedidos);
                detalleDeLosPedidos.setAdapter(arrayAdapter);


                Log.i("botonAgregar: ", "detallenuevo: "+ detalle.toString());
                Log.i("botonAgregar: ", "array: "+ listViewPedidos.toString());
                totalPedido = totalPedido + detalle.getSubtotal();
                totalView.setText(String.valueOf(totalPedido));
            }
        });
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("botonRegistrar", "empieza el evento");
                PedidoCabecera pedidoCabecera = new PedidoCabecera();
                pedidoCabecera.setId(db.countPedidos()+1);
                pedidoCabecera.setCodCliente(idCliente);
                pedidoCabecera.setTotalPedido(totalPedido);

                db.insertPedido(pedidoCabecera, listViewPedidos);

                Log.i("Cabecera", String.valueOf(db.loadPedidos()));
                /*Log.i("Detalle 1", String.valueOf(db.buscarPedidoDetalle(1)));
                Log.i("Detalle 2", String.valueOf(db.buscarPedidoDetalle(2)));
                Log.i("Detalle 3", String.valueOf(db.buscarPedidoDetalle(3)));*/

            }

        });







    }

    private void mostrarProductosLog() {
        List<Producto> list = db.loadProductos();

        for(Producto producto : list) {
            Log.i("productoActivity: ", producto.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botonAgregar:
                Log.i("botonAgregar: ", "entramos por aca");

                break;
            case R.id.botonRegistrar:
                Log.i("Registrar","hola");

        }
    }
}
