package williamsilva.dinheironobolso;

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

import williamsilva.dinheironobolso.helpers.DespesaHelper;
import williamsilva.dinheironobolso.models.Despesa;

public class NovaDespesaActivity extends ActionBarActivity {

    private int ano, mes, dia;
    private Button dataDesp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_despesa);

        getWindow().setSoftInputMode(
        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // tratando o campo dataVenc
        Calendar calendario = Calendar.getInstance();
        ano = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        dataDesp = (Button) findViewById(R.id.dataVenc);
        dataDesp.setText(dia + "/" + (mes + 1) + "/" + ano);
        // fim do tratamento
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nova_despesa, menu);
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

    public void selecionarData(View view) {
        showDialog(view.getId());
    }

    protected Dialog onCreateDialog(int id) {
        if (R.id.dataVenc == id) {
            return new DatePickerDialog(this, listener, ano, mes, dia);
        }
        return null;
    }

    // quando a data selecionada e alterada atraves do click
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;
            dataDesp.setText(dia + "/" + (mes + 1) + "/" + ano);

        }
    };

    public void salvarDespesa(View view) {

        AlertDialog alerta;
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Dinheiro no Bolso");
        //define a mensagem
        builder.setMessage("Deseja salvar a despesa ?");
        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                //escreva aqui
                DespesaHelper helper = new DespesaHelper(NovaDespesaActivity.this);
                Despesa despesa = null;
                despesa = helper.getDespesa(NovaDespesaActivity.this);

                if(despesa == null)
                {
                    Toast.makeText(NovaDespesaActivity.this,"Preencha todos os campos!",Toast.LENGTH_SHORT).show();
                }
                else if(despesa != null)
                {
                    salvar(despesa);
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

    private void salvar(Despesa despesa) {


       // Toast.makeText(NovaDespesaActivity.this, "Salvo com sucesso!", Toast.LENGTH_LONG).show();
        if(despesa.novaDespesa(despesa,this) != true)
            return;


        Toast.makeText(this,"A Despesa " + despesa.getNomeDesp() + " foi salva com Sucesso!",Toast.LENGTH_LONG).show();
        finish();

    }
}
