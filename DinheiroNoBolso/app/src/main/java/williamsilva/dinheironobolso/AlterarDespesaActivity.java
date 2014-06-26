package williamsilva.dinheironobolso;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;

import williamsilva.dinheironobolso.models.Despesa;

/**
 * Created by William on 26/06/2014.
 */
public class AlterarDespesaActivity extends Activity {

    private int ano, mes, dia;
    private Button dataDesp;

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
         Object despesa =  i.getSerializableExtra("DespesaSelecionada");

        Toast.makeText(this,despesa.toString(),Toast.LENGTH_LONG).show();
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
}
