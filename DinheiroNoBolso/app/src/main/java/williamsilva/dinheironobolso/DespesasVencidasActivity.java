package williamsilva.dinheironobolso;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import williamsilva.dinheironobolso.R;
import williamsilva.dinheironobolso.adapters.ListaDespesaAdapter;
import williamsilva.dinheironobolso.helpers.RelogioHelper;
import williamsilva.dinheironobolso.models.Despesa;

public class DespesasVencidasActivity extends ActionBarActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas_vencidas);

        lista = (ListView) findViewById(R.id.listaDeDespesasVencidas);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.despesas_vencidas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    private void carregarLista() {

        Despesa despesa = new Despesa();
        List<Despesa> despesas,despesasVencidas = new ArrayList<Despesa>();
        RelogioHelper dataSys,dataBanco;
        Integer mesSys,mesBanco,anoSys,anoBanco,diaSys,diaBanco;

        despesas = despesa.getLista(this);
        dataSys = new RelogioHelper(RelogioHelper.dataHoje());
        mesSys = dataSys.getMes();
        anoSys = dataSys.getAno();
        diaSys = dataSys.getDia();




        for (int i = 0; i < despesas.size(); i++)
        {
            dataBanco = new RelogioHelper(despesas.get(i).getDataVenc());
            mesBanco = dataBanco.getMes();
            anoBanco = dataBanco.getAno();
            diaBanco = dataBanco.getDia();

            if( diaBanco < diaSys && mesBanco <= mesSys && anoBanco <= anoSys && despesas.get(i).getStatus().equals(0) ||
                    diaBanco < diaSys && mesBanco.equals(mesSys) && anoBanco.equals(anoSys) && despesas.get(i).getStatus().equals(0) ||
                    mesBanco < mesSys && anoBanco <= anoSys && despesas.get(i).getStatus().equals(0) ||
                    mesBanco < mesSys && anoBanco.equals(anoSys)&& despesas.get(i).getStatus().equals(0) ||
                    anoBanco < anoSys && despesas.get(i).getStatus().equals(0))
                despesasVencidas.add(despesas.get(i));

        }


        ListaDespesaAdapter adapter = new ListaDespesaAdapter(despesasVencidas,this);
        lista.setAdapter(adapter);
    }
}
