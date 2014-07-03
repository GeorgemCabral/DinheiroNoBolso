package williamsilva.dinheironobolso;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.List;

import williamsilva.dinheironobolso.R;
import williamsilva.dinheironobolso.helpers.RelogioHelper;
import williamsilva.dinheironobolso.models.Receita;

public class ListarReceitasActivity extends ActionBarActivity  {

    private ListView lista;
    private Receita receita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_receitas);

        lista = (ListView) findViewById(R.id.listaDeReceitas);

        //informa o android para mostrar o menu quando a receita e selecionada
        registerForContextMenu(lista);

        lista.setOnItemClickListener(clickListener);

        lista.setOnItemLongClickListener(listenerLong);
    }

    //click comum
    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            receita = (Receita) parent.getItemAtPosition(position);
            Intent intent = new Intent(ListarReceitasActivity.this,AlterarReceitaActivity.class);
            intent.putExtra("receitaSelect",receita);
            startActivity(intent);
        }
    };

    //click longo
    private AdapterView.OnItemLongClickListener listenerLong = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            receita = (Receita) parent.getItemAtPosition(position);
            return false;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.listar_receitas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.novaReceita) {
            startActivity(new Intent(this,NovaReceitaActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        carregarListaReceitas();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.opcoes,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.OPexcluir:
                excluir();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void excluir() {
        receita.excluir(receita,this);
        carregarListaReceitas();
    }

    private void carregarListaReceitas() {

        Receita receita = new Receita();

        List<Receita> receitas,receitasDoMes = new ArrayList<Receita>();

        receitas  = receita.getLista(this);

        Integer mesSys,mesBanco,anoSys,anoBanco;

        RelogioHelper dataSys,dataBanco;

        dataSys  = new RelogioHelper(RelogioHelper.dataHoje());

        mesSys = dataSys.getMes();
        anoSys = dataSys.getAno();

        for(int i = 0; i < receitas.size(); i++)
        {
            dataBanco = new RelogioHelper(receitas.get(i).getDataRec());
            mesBanco = dataBanco.getMes();
            anoBanco = dataBanco.getAno();

                if(mesBanco.equals(mesSys) && anoBanco.equals(anoSys))
                    receitasDoMes.add(receitas.get(i));
        }



        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter<Receita> adapter = new ArrayAdapter<Receita>(this,layout,receitasDoMes);
        lista.setAdapter(adapter);
    }

}
