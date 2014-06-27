package williamsilva.dinheironobolso.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import williamsilva.dinheironobolso.AlterarDespesaActivity;
import williamsilva.dinheironobolso.NovaDespesaActivity;
import williamsilva.dinheironobolso.R;
import williamsilva.dinheironobolso.models.Despesa;

/**
 * Created by William on 24/06/2014.
 */

public class DespesaHelper {

    private EditText campoNomeDesp;
    private EditText campoValorDesp;
    private RadioGroup campoTipoDesp;
    private RadioGroup campoStatusDesp;
    private Button campoDataVenc;


    private static Integer idDespesa = 0;
    private Integer tipoDesp;
    private String nomeDesp  = null;
    private String dataVenc = null;
    private float valorDesp = 0f;
    private Integer status;

    public DespesaHelper(AlterarDespesaActivity activity) {

        this.campoNomeDesp = (EditText) activity.findViewById(R.id.nomeDespesa);
        this.campoValorDesp = (EditText) activity.findViewById(R.id.valorDesp);
        this.campoTipoDesp = (RadioGroup) activity.findViewById(R.id.tipoDespesa);
        this.campoStatusDesp = (RadioGroup) activity.findViewById(R.id.statusDespesa);
        this.campoDataVenc = (Button) activity.findViewById(R.id.dataVenc);
        this.campoTipoDesp = (RadioGroup) activity.findViewById(R.id.tipoDespesa);
        this.campoStatusDesp = (RadioGroup) activity.findViewById(R.id.statusDespesa);
    }


    public DespesaHelper(NovaDespesaActivity activity) {

        campoNomeDesp = (EditText) activity.findViewById(R.id.nomeDespesa);
        campoValorDesp = (EditText) activity.findViewById(R.id.valorDesp);
        campoDataVenc = (Button) activity.findViewById(R.id.dataVenc);
        campoTipoDesp = (RadioGroup) activity.findViewById(R.id.tipoDespesa);
        campoStatusDesp = (RadioGroup) activity.findViewById(R.id.statusDespesa);
        campoDataVenc = (Button) activity.findViewById(R.id.dataVenc);

    }

    public void setDespesa(Despesa despesa)
    {
        DespesaHelper.idDespesa = despesa.getId();
        this.campoNomeDesp.setText(despesa.getNomeDesp());
        this.campoValorDesp.setText(""+despesa.getValorDesp());
        this.campoDataVenc.setText(despesa.getDataVenc());

        if(despesa.getTipoDesp() == 0) {
            this.campoTipoDesp.check(R.id.tipoDespesaFixa);
        }
        else if(despesa.getTipoDesp() == 1) {
            this.campoTipoDesp.check(R.id.tipoDespesaVariavel);
        }

            if(despesa.getStatus() == 0) {
                this.campoStatusDesp.check(R.id.statusPago);
            }
            else if(despesa.getStatus() == 1) {
                this.campoStatusDesp.check(R.id.statusNaoPago);
            }


    }

    public Despesa getDespesa(Context contexto) {

        Despesa despesa = null;

        switch (campoTipoDesp.getCheckedRadioButtonId()) {
            case R.id.tipoDespesaFixa:
                tipoDesp = 0;
                break;
            case R.id.tipoDespesaVariavel:
                tipoDesp = 1;
                break;
        }

        switch (campoStatusDesp.getCheckedRadioButtonId()) {
            case R.id.statusPago:
                status = 0;
                break;
            case R.id.statusNaoPago:
                status = 1;
                break;
        }

        // tratamento sob campos não preenchidos
        if(     ValidaCampoVazioHelper.validar(campoNomeDesp, "Coloque um nome para sua despesa!") != true   &&
                ValidaCampoVazioHelper.validar(campoValorDesp, "Coloque um valor para sua despesa!") != true ||
                ValidaCampoVazioHelper.validar(campoValorDesp, "Coloque um valor para sua despesa!") != true ||
                ValidaCampoVazioHelper.validar(campoNomeDesp, "Coloque um nome para sua despesa!") != true
                )
            return despesa;


        nomeDesp = campoNomeDesp.getText().toString();
        dataVenc = campoDataVenc.getText().toString();

        try {
            valorDesp = Float.parseFloat(campoValorDesp.getText().toString());
        }
        catch (NumberFormatException e)
        {
            Log.i("Nova Despesa Helper" , e.getMessage());
        }

        if(nomeDesp != null && tipoDesp != null && dataVenc != null && valorDesp > 0) {
            despesa = new Despesa(nomeDesp, tipoDesp, dataVenc, valorDesp, status, contexto);
            despesa.setId(DespesaHelper.idDespesa);
        }
        return  despesa;
    }
}
