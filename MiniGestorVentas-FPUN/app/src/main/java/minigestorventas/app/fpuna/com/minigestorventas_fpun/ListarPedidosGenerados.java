package minigestorventas.app.fpuna.com.minigestorventas_fpun;

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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import data.Cliente;
import data.ClientesDB;
import data.DetallePedido;
import data.ItemSpinner;
import data.PedidoCabecera;
import data.PedidoDetalle;
import data.Producto;

public class ListarPedidosGenerados extends AppCompatActivity {
    ClientesDB db;
    Button botonMostrar;
    Button botonVolverAtras;
    Spinner spinnerPedidos;
    ListView listaDetalles;
    TextView tvTotalPedido;
    int idCabeceraSelected = 1;
    int idprueba = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pedidos_generados);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new ClientesDB(this);

        //Capturamos los ids de la parte gráfica
        botonMostrar = (Button) findViewById(R.id.boton_mostrar);
        botonVolverAtras = (Button) findViewById(R.id.btnVolverAtras);
        spinnerPedidos = (Spinner)findViewById(R.id.spinner_pedidos);
        listaDetalles = (ListView)findViewById(R.id.lista_detalles);
        tvTotalPedido = (TextView)findViewById(R.id.tvTotalPedido);

        List<ItemSpinner> spinnerCabeceras = new ArrayList<ItemSpinner>();
       ArrayList<PedidoCabecera> listaCabecera = new ArrayList<PedidoCabecera>();
        listaCabecera = db.loadPedidos();

        if (listaCabecera.size() == 0){
            Toast.makeText(getApplicationContext(), "No existen pedidos generados", Toast.LENGTH_SHORT).show();
        }
        for(PedidoCabecera pedidoCabecera : listaCabecera) {

            Cliente cliente = db.buscarCliente(pedidoCabecera.getCodCliente());
            ItemSpinner cargar = new ItemSpinner(pedidoCabecera.getId(), String.valueOf(pedidoCabecera.getId()) + "- "
                    + cliente.getNombre() +" " + cliente.getApellido());
            spinnerCabeceras.add(cargar);
        }
        ArrayAdapter<ItemSpinner> pedidos = new ArrayAdapter<ItemSpinner>
                (this, android.R.layout.simple_spinner_dropdown_item, spinnerCabeceras);
        pedidos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPedidos.setAdapter(pedidos);
        spinnerPedidos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCabeceraSelected = position+1;
                idprueba = position;
                Log.i("seleccionaCabecera: pos", String.valueOf(idCabeceraSelected));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayList<PedidoCabecera> finalListaCabecera = listaCabecera;
        botonMostrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("botonMostrar: ", "hace click");
                if (finalListaCabecera.size() == 0) {
                    Toast.makeText(getApplicationContext(), "No existen pedidos generados", Toast.LENGTH_SHORT).show();
                } else {

                    DetallePedido detalle = new DetallePedido();
                    ArrayList<ItemSpinner> listViewPedidos = db.buscarPedidoDetalleLindo(idCabeceraSelected);
                    Log.i("botonMostrar ", listViewPedidos.toString());
                    List<ItemSpinner> listaDetalle = new ArrayList<ItemSpinner>();

                    ArrayAdapter<ItemSpinner> arrayAdapter = new ArrayAdapter<ItemSpinner>
                            (getApplicationContext(), android.R.layout.simple_list_item_activated_1, listViewPedidos);
                    listaDetalles.setAdapter(arrayAdapter);
                    tvTotalPedido.setText("Total del pedido: " + String.valueOf(finalListaCabecera.get(idprueba).getTotalPedido()));
                }
            }
        });
        botonVolverAtras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();

            }
        });


    }

}
