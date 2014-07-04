package williamsilva.dinheironobolso;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import williamsilva.dinheironobolso.helpers.DespesaHelper;
import williamsilva.dinheironobolso.models.Despesa;

/**
 * Created by William on 26/06/2014.
 */
public class AlterarDespesaActivity extends Activity {

    private int ano, mes, dia;
    private Button dataDesp;
    private  Despesa despesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_despesa);

        TextView  titulo;
        Button btSalvar;

        btSalvar = (Button) findViewById(R.id.BtSalvarDespesa);
        btSalvar.setText("Alterar");

        titulo = (TextView) findViewById(R.id.LabelNovaDespesa);
        titulo.setText("Alterar Despesa");

        //para nao abrir o teclado ao usuario acessar a activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // tratando o campo dataVenc
        Calendar calendario = Calendar.getInstance();
        ano = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        dataDesp = (Button) findViewById(R.id.dataVenc);
        dataDesp.setText(dia + "/" + (mes + 1) + "/" + ano);
        // fim do tratamento

         Intent i = getIntent();
         despesa = (Despesa)  i.getSerializableExtra("DespesaSelecionada");


        /*
            altera a cor do titulo da activity
        LinearLayout linear = (LinearLayout) findViewById(R.id.linearDespesa);
        linear.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e84e40")));*/

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

    @Override
    protected void onResume() {
        super.onResume();
        DespesaHelper helper = new DespesaHelper(this);
        helper.setDespesa(despesa);
    }

    public void salvarDespesa(View view) {

        AlertDialog alerta;
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Dinheiro no Bolso");
        //define a mensagem
        builder.setMessage("Deseja salvar a alteração ?");
        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                //escreva aqui
                DespesaHelper helper = new DespesaHelper(AlterarDespesaActivity.this);
                Despesa despesa = null;

                try {
                    despesa = helper.getDespesa();
                }catch (Exception e){

                    Toast.makeText(AlterarDespesaActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                }


                if(despesa != null)
                {
                    alterar(despesa);
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

    private void alterar(Despesa despesa) {


        // Toast.makeText(NovaDespesaActivity.this, "Salvo com sucesso!", Toast.LENGTH_LONG).show();
        if(despesa.alteraDespesa(despesa, this) != true)
            return;
        Toast.makeText(this,"Alterado com sucesso!",Toast.LENGTH_SHORT).show();
        finish();
    }
}
