package williamsilva.dinheironobolso;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import williamsilva.dinheironobolso.helpers.RelogioHelper;
import williamsilva.dinheironobolso.models.Despesa;
import williamsilva.dinheironobolso.models.Receita;

public class FinancasMesActivity extends Activity {

    private Float despesaTotal = 0f,saldoTotal = 0f;
    DecimalFormat df = new DecimalFormat("#.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financas_mes);

        retornarToalReceitaBruta();
        retornarTotalDespesas();
        retornarTotalReceitaLiquida();
    }

    private void retornarTotalReceitaLiquida() {
        TextView receitaLiquida = (TextView) findViewById(R.id.saldoAtual);
        receitaLiquida.setText("R$ "+df.format(saldoTotal - despesaTotal));

        if(saldoTotal - despesaTotal < 0)
        {
            receitaLiquida.setTextColor(Color.parseColor("#d01716"));
        }
    }

    private void retornarTotalDespesas() {

        Despesa dep = new Despesa();
        List<Despesa>despesas = dep.getLista(this);
        RelogioHelper relogioSys = new RelogioHelper(RelogioHelper.dataHoje());
        Integer mesSys, mesBanco,anoSys,anoBanco;

        mesSys = relogioSys.getMes();
        anoSys = relogioSys.getAno();


        for (int i = 0; i < despesas.size(); i++){

            RelogioHelper relogioBanco = new RelogioHelper(despesas.get(i).getDataVenc());
            mesBanco = relogioBanco.getMes();
            anoBanco = relogioBanco.getAno();

            if(mesSys.equals(mesBanco) && anoSys.equals(anoBanco)) {
                despesaTotal = despesaTotal + despesas.get(i).getValorDesp();
            }
        }

        TextView totalDespesa = (TextView) findViewById(R.id.totalDespesa);
        totalDespesa.setText("R$ "+df.format(despesaTotal));
    }

    private void retornarToalReceitaBruta() {


        Receita rec = new Receita();
        List<Receita> receitas = rec.getLista(this);
        RelogioHelper relogioSys = new RelogioHelper(RelogioHelper.dataHoje());
        Integer mesSys,mesBanco,anoSys,anoBanco;
        mesSys = relogioSys.getMes();
        anoSys = relogioSys.getAno();

        for ( int i = 0; i < receitas.size(); i++){

            RelogioHelper relogioBanco = new RelogioHelper(receitas.get(i).getDataRec());
            mesBanco = relogioBanco.getMes();
            anoBanco = relogioBanco.getAno();

            if(mesBanco.equals(mesSys) && anoBanco.equals(anoSys)) {
                saldoTotal = saldoTotal + receitas.get(i).getValorRec();
            }
        }

        TextView saldobruto = (TextView) findViewById(R.id.saldoLiquido);
        saldobruto.setText("R$ "+df.format(saldoTotal));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.financas_mes, menu);
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
}
