package williamsilva.dinheironobolso;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import williamsilva.dinheironobolso.helpers.ReceitaHelper;
import williamsilva.dinheironobolso.models.Receita;

/**
 * Created by William on 28/06/2014.
 */
public class AlterarReceitaActivity extends ActionBarActivity {

    private int ano,mes,dia;
    private Button dataRec;
    private Receita receita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_receita);

        TextView titulo;
        Button btSalvar;

        btSalvar = (Button) findViewById(R.id.BtSalvarReceita);
        btSalvar.setText("Alterar");

        titulo = (TextView) findViewById(R.id.LabelNovaReceita);
        titulo.setText("Alterar Receita");

        //para nao abrir o teclado ao usuario acessar a activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

       /* LinearLayout linear = (LinearLayout) findViewById(R.id.LinearReceitas);
        linear.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));*/

        Intent intent = getIntent();
        receita = (Receita) intent.getSerializableExtra("receitaSelect");


        //tratando o campo dataRec
        Calendar calendario = Calendar.getInstance();
        ano = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        dataRec = (Button) findViewById(R.id.dataReceb);
        dataRec.setText(dia + "/" + (mes+1) + "/" + ano);
        //fim do tratamento
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

    @Override
    protected void onResume() {
        super.onResume();
        ReceitaHelper helper = new ReceitaHelper(this);
        helper.setReceita(receita);
    }

    public void salvarReceita(View view) {


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
                ReceitaHelper helper = new ReceitaHelper(AlterarReceitaActivity.this);
                Receita receita = null;

                try {
                    receita = helper.getReceita();
                }catch (Exception e){
                    Toast.makeText(AlterarReceitaActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }


                if(receita != null)
                {
                    alterar(receita);
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

    public void alterar(Receita receita)
    {
        if(receita.alterarReceita(receita, this) != true)
            return;
        Toast.makeText(this,"Alterado com Sucesso!",Toast.LENGTH_LONG).show();
        finish();
    }
}
