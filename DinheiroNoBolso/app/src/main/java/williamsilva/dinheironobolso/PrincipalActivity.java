package williamsilva.dinheironobolso;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class PrincipalActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.listarDespesas:
                startActivity(new Intent(this,ListarDespesaActivity.class));
                break;

            case R.id.listarReceitas:
                startActivity(new Intent(this,ListarReceitasActivity.class));
                break;

            case R.id.despesasPagas:
                teste("Despesas Pagas");
                break;

            case R.id.despesasVencidas:
                startActivity(new Intent(this,DespesasVencidasActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selecionarOpcao(View view)
    {
        switch (view.getId()) {
            case R.id.novaDespesa:
                startActivity(new Intent(this,NovaDespesaActivity.class));
                break;
            case R.id.novaReceita:
                startActivity(new Intent(this,NovaReceitaActivity.class));
                break;
            case R.id.financasDoMes:
                startActivity(new Intent(this,FinancasMesActivity.class));
                break;
            case R.id.informacoes:
                teste("Informações");
                break;
        }

    }

    private void teste (String msg)
    {
        Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();
    }
}
