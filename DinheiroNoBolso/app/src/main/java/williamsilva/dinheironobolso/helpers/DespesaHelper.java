package williamsilva.dinheironobolso.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
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
    private CheckBox campoTipoDesp;
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
        this.campoStatusDesp = (RadioGroup) activity.findViewById(R.id.statusDespesa);
        this.campoDataVenc = (Button) activity.findViewById(R.id.dataVenc);
        this.campoTipoDesp = (CheckBox) activity.findViewById(R.id.tipoDespesafixaChek);
        this.campoStatusDesp = (RadioGroup) activity.findViewById(R.id.statusDespesa);
    }


    public DespesaHelper(NovaDespesaActivity activity) {

        campoNomeDesp = (EditText) activity.findViewById(R.id.nomeDespesa);
        campoValorDesp = (EditText) activity.findViewById(R.id.valorDesp);
        campoDataVenc = (Button) activity.findViewById(R.id.dataVenc);
        campoTipoDesp = (CheckBox) activity.findViewById(R.id.tipoDespesafixaChek);
        campoStatusDesp = (RadioGroup) activity.findViewById(R.id.statusDespesa);
        campoDataVenc = (Button) activity.findViewById(R.id.dataVenc);

    }

    public void setDespesa(Despesa despesa)
    {
        DespesaHelper.idDespesa = despesa.getId();
        this.campoNomeDesp.setText(despesa.getNomeDesp());
        this.campoValorDesp.setText(""+despesa.getValorDesp());
        this.campoDataVenc.setText(despesa.getDataVenc());

        if(despesa.getTipoDesp() == 1) {
            this.campoTipoDesp.setChecked(true);
        }


        if(despesa.getStatus() == 0) {
            this.campoStatusDesp.check(R.id.statusPago);
        }
        else if(despesa.getStatus() == 1) {
            this.campoStatusDesp.check(R.id.statusNaoPago);
        }


    }

    public Despesa getDespesa() {

        Despesa despesa = null;

        if(campoTipoDesp.isChecked())
        {
            tipoDesp = 1;
        }
        else {
            tipoDesp = 0;
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
        if(     ValidaCampoVazioHelper.validar(campoNomeDesp, "Insira um nome para sua despesa!") != true   &&
                ValidaCampoVazioHelper.validar(campoValorDesp, "Insira um valor para sua despesa!") != true ||
                ValidaCampoVazioHelper.validar(campoValorDesp, "Insira um valor para sua despesa!") != true ||
                ValidaCampoVazioHelper.validar(campoNomeDesp, "Insira um nome para sua despesa!") != true
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
            despesa = new Despesa(nomeDesp, tipoDesp, dataVenc, valorDesp, status);
            despesa.setId(DespesaHelper.idDespesa);
        }
        return  despesa;
    }
}
