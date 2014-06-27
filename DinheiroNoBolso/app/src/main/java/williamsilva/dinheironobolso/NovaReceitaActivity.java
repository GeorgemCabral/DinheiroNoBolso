package williamsilva.dinheironobolso;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class NovaReceitaActivity extends ActionBarActivity {

    private int ano,mes,dia;
    private Button dataRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_receita);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //tratando o campo dataRec
        Calendar calendario = Calendar.getInstance();
        ano = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        dataRec = (Button) findViewById(R.id.dataReceb);
        dataRec.setText(dia + "/" + (mes+1) + "/" + ano);
        //fim do tratamento
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nova_receita, menu);
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

    public void selecionarData(View view)
    {
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(R.id.dataReceb == id)
        {
            return new DatePickerDialog(this, listener, ano, mes, dia);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;
            dataRec.setText(dia + "/" + (mes+1) + "/" + ano);

        }
    };

    public void salvarReceita(View view)
    {
        /*
        ReceitaHelper helper = new ReceitaHelper(this);
        Receita receita = helper.getReceita();

        if(receita.novaReceita(receita,this) != true)
            return;

        Toast.makeText(this,"A Receita " + receita.getNomeRec() + " foi salva com Sucesso!",Toast.LENGTH_LONG).show();
        finish();
     */
    }

}
