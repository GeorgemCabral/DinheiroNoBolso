package williamsilva.dinheironobolso.helpers;

import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import williamsilva.dinheironobolso.NovaReceitaActivity;
import williamsilva.dinheironobolso.R;
import williamsilva.dinheironobolso.models.Receita;

/**
 * Created by William on 27/06/2014.
 */
public class ReceitaHelper {

    private EditText campoNomeRec,campoValorRec;
    private Button campoDataRec;
    private CheckBox campoTipoRec;
    private Receita receita;

    private String nomeRec = null;
    private Integer tipoRec = null;
    private String dataRec = null;
    private Float valorRec = null;


    public ReceitaHelper(NovaReceitaActivity activity) {

        this.campoNomeRec = (EditText) activity.findViewById(R.id.nomeReceita);
        this.campoTipoRec = (CheckBox) activity.findViewById(R.id.salariofixoCheck);
        this.campoDataRec = (Button) activity.findViewById(R.id.dataReceb);
        this.campoValorRec = (EditText) activity.findViewById(R.id.valorRec);

    }

    public Receita getReceita() {

        Receita receita = null;

        if(this.campoTipoRec.isChecked())
        {
            tipoRec = 1;
        }
        else
        {
            tipoRec = 0;
        }

        if(ValidaCampoVazioHelper.validar(campoNomeRec,"Insira um nome para a receita!") != true &&
                ValidaCampoVazioHelper.validar(campoValorRec,"Insira um valor para a receita") != true ||
                ValidaCampoVazioHelper.validar(campoNomeRec,"Insira um nome para a receita!") != true ||
                ValidaCampoVazioHelper.validar(campoValorRec,"Insira um valor para a receita") != true)
            return null;

        nomeRec = campoNomeRec.getText().toString();
        dataRec = campoDataRec.getText().toString();

        try
        {
            valorRec = Float.parseFloat(campoValorRec.getText().toString());
        }
        catch (NumberFormatException e)
        {
            Log.i("Erro Receita Helper","Erro ao converter o number para float");
            receita =  null;
        }

        if(nomeRec != null || tipoRec != null || dataRec != null || valorRec != null)
            receita = new Receita(nomeRec,tipoRec,dataRec,valorRec);

        return receita;
    }
}
