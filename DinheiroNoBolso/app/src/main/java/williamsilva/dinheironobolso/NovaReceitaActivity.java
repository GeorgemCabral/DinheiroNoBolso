package williamsilva.dinheironobolso;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import williamsilva.dinheironobolso.helpers.ReceitaHelper;
import williamsilva.dinheironobolso.models.Receita;

public class NovaReceitaActivity extends Activity {

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

    public void salvarReceita(View view) {

        AlertDialog alerta;
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Dinheiro no Bolso");
        //define a mensagem
        builder.setMessage("Deseja salvar ?");
        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                //escreva aqui
                ReceitaHelper helper = new ReceitaHelper(NovaReceitaActivity.this);
                Receita receita = null;

                try {
                    receita = helper.getReceita();
                }catch (Exception e){
                   Toast.makeText(NovaReceitaActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }


                if(receita != null)
                {
                    salvar(receita);
                }
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //escreva aqui
                finish();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        alerta.show();


    }

    public void salvar(Receita receita)
    {
        if (receita.novaReceita(receita,this) == true) {
            Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
