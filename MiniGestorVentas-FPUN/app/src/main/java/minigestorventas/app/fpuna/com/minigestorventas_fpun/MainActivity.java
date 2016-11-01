package minigestorventas.app.fpuna.com.minigestorventas_fpun;

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
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import data.Cliente;
import data.ClientesDB;
import data.ItemSpinner;
import data.Producto;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ClientesDB db;
    Spinner spinner_cliente;
    TextView tvDireccion;
    TextView tvTelefono;
    TextView tvCedula;
    int idPasar;
    Button botonSgte;
    Button botonPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new ClientesDB(this);
        int cantCLientes= db.countClientes();
        int cantProductos= db.countProductos();
        Log.i("Cantidad de Clientes: ", String.valueOf(cantCLientes));
        Log.i("Cantidad de Productos: ", String.valueOf(cantProductos));
        primeraVezRun(cantCLientes, cantProductos);


        final List<Cliente> listaClientes = db.loadClientes();
        //capturo los elementos del layout
        spinner_cliente = (Spinner) findViewById(R.id.spinner_clientes);
        tvDireccion = (TextView) findViewById(R.id.tvDireccion);
        tvTelefono = (TextView) findViewById(R.id.tvTelefono);
        tvCedula = (TextView) findViewById(R.id.tvCedula);
        botonSgte = (Button) findViewById(R.id.botonSiguiente);
        botonPedidos = (Button) findViewById(R.id.botonPedidos);
        botonSgte.setOnClickListener(this);
        botonPedidos.setOnClickListener(this);

        //se carga los valores para el spinner
        List<ItemSpinner> spinnerClientes = new ArrayList<ItemSpinner>();
        Log.i("un cliente--> :", String.valueOf(listaClientes.get(1).getNombre()));
        for(Cliente cliente : listaClientes) {
            ItemSpinner cargar = new ItemSpinner(cliente.getId(), cliente.getNombre() +" " + cliente.getApellido());
            spinnerClientes.add(cargar);
        }
        ArrayAdapter<ItemSpinner> clientes = new ArrayAdapter<ItemSpinner>(this, android.R.layout.simple_spinner_dropdown_item, spinnerClientes);
        clientes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cliente.setAdapter(clientes);
        spinner_cliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               Log.i("posicion spinner", String.valueOf(position));
                tvDireccion.setText(listaClientes.get(position).getDireccion());
                tvTelefono.setText(listaClientes.get(position).getTelefono());
                tvCedula.setText(listaClientes.get(position).getCedula());
                idPasar = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

    }


    public void primeraVezRun(int cant, int cantProductos){
           // int cant = db.countClientes();
            //db = new ClientesDB(this);
            if (cant == 0){
                Cliente cliente = new Cliente(0, "Eduardo", "Appleyard", "Venancio Gonzalez N° 1314", "0971490111", "5057106");
                Log.i("primeraVezRun():", cliente.toString());
                db.insertCliente(cliente);
                cliente = new Cliente(1, "Juan", "Riella", "Pitiantuta y Elsa de Escalante n° 4547", "0991555878", "3254789");
                Log.i("primeraVezRun():", cliente.toString());
                db.insertCliente(cliente);
                cliente  = new Cliente(2, "Antonio", "Caceres", "Acceso Sur N° 545", "0971444565", "5057104");
                db.insertCliente(cliente);
                Log.i("primeraVezRun() :", cliente.toString());
                cliente = new Cliente(3, "Paraguay", "Refrescos S.A", "Mcal. Estigarribia 1570 c/ Av. Perú Asunción", "0971444565", "80017437-2");
                db.insertCliente(cliente);
                cliente = new Cliente(4, "Bimbo", "Paraguay", "Pedro Getto, Fernando De La Mora", "021525878", "80017837-2");
                db.insertCliente(cliente);
                //Cliente cliente3 = new Cliente("Pedro","33333","pedro@mail.net");
                //Cliente cliente4 = new Cliente("David","44444","david@mail.net");
                mostrarClientesLog();
            }
            if (cantProductos == 0){
                Producto producto = new Producto(0, "Pilsen'i", 2500, 250);
                db.insertProducto(producto);
                Log.i("primeraVezRun Producto", producto.toString());
                producto = new Producto(1, "Coca Cola 3lts.", 13000, 250);
                db.insertProducto(producto);
                Log.i("primeraVezRun Producto", producto.toString());
                producto = new Producto(2, "Pringles", 10000, 250);
                db.insertProducto(producto);
                Log.i("primeraVezRun Producto", producto.toString());
                producto = new Producto(3, "Doritos", 7000, 250);
                db.insertProducto(producto);
                Log.i("primeraVezRun Producto", producto.toString());
                producto = new Producto(4, "Maní Tostado", 5000, 250);
                db.insertProducto(producto);
                Log.i("primeraVezRun Producto", producto.toString());
                mostrarProductosLog();

            }



            }

    private void mostrarClientesLog() {
        List<Cliente> list = db.loadClientes();

        for(Cliente cliente : list) {
            Log.i("---> Base de datos: ", cliente.toString());
        }
    }

    private void mostrarProductosLog() {
        List<Producto> list = db.loadProductos();

        for(Producto producto : list) {
            Log.i("---> BD producto: ", producto.toString());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.botonSiguiente:
                int parametro = idPasar;
                Log.i("parametro:", String.valueOf(parametro));
                Intent intent = new Intent(MainActivity.this, PedidoActivity.class);
                intent.putExtra("idCliente", parametro);
                startActivity(intent);
                break;
            case R.id.botonPedidos:
                Intent intent2 = new Intent(MainActivity.this, ListarPedidosGenerados.class);
                startActivity(intent2);
                break;
        }
    }
}
