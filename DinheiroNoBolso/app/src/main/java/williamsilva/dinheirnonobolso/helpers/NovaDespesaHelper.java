package williamsilva.dinheirnonobolso.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Calendar;

import williamsilva.dinheirnonobolso.NovaDespesaActivity;
import williamsilva.dinheirnonobolso.R;
import williamsilva.dinheirnonobolso.models.Despesa;

/**
 * Created by William on 24/06/2014.
 */

public class NovaDespesaHelper {

    private EditText campoNomeDesp;
    private EditText campoValorDesp;
    private RadioGroup campoTipoDesp;
    private RadioGroup campoStatusDesp;
    private Button campoDataVenc;

    private String tipoDesp = null;
    private String nomeDesp  = null;
    private String dataVenc = null;
    private float valorDesp = 0f;
    private String status = null;

    public NovaDespesaHelper()
    {

    }

    public NovaDespesaHelper(NovaDespesaActivity activity) {

        campoNomeDesp = (EditText) activity.findViewById(R.id.nomeDespesa);
        campoValorDesp = (EditText) activity.findViewById(R.id.valorDesp);
        campoDataVenc = (Button) activity.findViewById(R.id.dataVenc);
        campoTipoDesp = (RadioGroup) activity.findViewById(R.id.tipoDespesa);
        campoStatusDesp = (RadioGroup) activity.findViewById(R.id.statusDespesa);
        campoDataVenc = (Button) activity.findViewById(R.id.dataVenc);

        switch (campoTipoDesp.getCheckedRadioButtonId()) {
            case R.id.tipoDespesaFixa:
                tipoDesp = "Despesa Fixa";
                break;
            case R.id.tipoDespesaVariavel:
                tipoDesp = "Despesa Variável";
                break;
        }

        switch (campoStatusDesp.getCheckedRadioButtonId()) {
            case R.id.statusPago:
                status = "Despesa Paga";
                break;
            case R.id.statusNaoPago:
                status = "Despesa à Pagar";
                break;
        }

        // tratamento sob campos não preenchidos
        if(     ValidaCampoVazioHelper.validar(campoNomeDesp, "Coloque um nome para sua despesa!") != true   &&
                ValidaCampoVazioHelper.validar(campoValorDesp, "Coloque um valor para sua despesa!") != true ||
                ValidaCampoVazioHelper.validar(campoValorDesp, "Coloque um valor para sua despesa!") != true ||
                ValidaCampoVazioHelper.validar(campoNomeDesp, "Coloque um nome para sua despesa!") != true
           )
            return;


        nomeDesp = campoNomeDesp.getText().toString();
        dataVenc = campoDataVenc.getText().toString();

        try {
            valorDesp = Float.parseFloat(campoValorDesp.getText().toString());
        }
        catch (NumberFormatException e)
        {
            Log.i("Nova Despesa Helper" , e.getMessage());
        }
    }

    public Despesa retornarDespesa(Context contexto) {
        if(nomeDesp != null && tipoDesp != null && dataVenc != null && valorDesp > 0)
            return new Despesa(nomeDesp, tipoDesp, dataVenc, valorDesp,status ,contexto);

        return null;
    }
}
