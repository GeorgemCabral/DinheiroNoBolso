package williamsilva.dinheironobolso;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import williamsilva.dinheironobolso.models.Despesa;

import static android.widget.AdapterView.OnItemClickListener;

public class ListarDespesaActivity extends ActionBarActivity {

    private ListView lista;
    private Despesa  despesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_despesa);

        lista = (ListView) findViewById(R.id.listaDeDespesas);

        registerForContextMenu(lista);

        lista.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //escreva o codigo para click simples aqui:
                despesa = (Despesa) parent.getItemAtPosition(position);
                Intent i = new Intent(ListarDespesaActivity.this,AlterarDespesaActivity.class);
                i.putExtra("DespesaSelecionada",despesa);
                startActivity(i);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //escreva o codigo para click longo aqui:
                despesa = (Despesa) parent.getItemAtPosition(position);

                return false;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.listar_despesa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.novaDespesa:
                startActivity(new Intent(this,NovaDespesaActivity.class));
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregarListaDespesas();
    }

    private void carregarListaDespesas() {

        Despesa despesa = new Despesa();
        List<Despesa> despesas = despesa.getLista(this);
        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter<Despesa> adapter = new ArrayAdapter<Despesa>(this,layout,despesas);
        lista.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.opcoes,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.OPexcluir:
                excluir();
                break;
            /*case R.id.OPalterar:
                Intent i = new Intent(ListarDespesaActivity.this,AlterarDespesaActivity.class);
                i.putExtra("DespesaSelecionada",despesa);
                startActivity(i);
                break;*/
        }

        return super.onContextItemSelected(item);
    }

    private void excluir() {
        despesa.excluir(despesa,this);
        carregarListaDespesas();
    }
}
