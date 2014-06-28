package williamsilva.dinheironobolso;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by William on 28/06/2014.
 */
public class AlterarReceitaActivity extends ActionBarActivity {

    private int ano,mes,dia;
    private Button dataRec;

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

        //para nao abrir o teclado ao usuario entrar na activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


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

    public void salvarReceita(View view) {}
}
