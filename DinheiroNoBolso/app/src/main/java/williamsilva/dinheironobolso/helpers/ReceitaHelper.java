package williamsilva.dinheironobolso.helpers;

import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import williamsilva.dinheironobolso.AlterarReceitaActivity;
import williamsilva.dinheironobolso.NovaReceitaActivity;
import williamsilva.dinheironobolso.R;
import williamsilva.dinheironobolso.exceptions.CampoVazioException;
import williamsilva.dinheironobolso.exceptions.DataIncorretaException;
import williamsilva.dinheironobolso.exceptions.NomeComMaximoCaracteresException;
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
    private static Integer id;

    public ReceitaHelper(AlterarReceitaActivity activity){

        this.campoNomeRec = (EditText) activity.findViewById(R.id.nomeReceita);
        this.campoTipoRec = (CheckBox) activity.findViewById(R.id.salariofixoCheck);
        this.campoDataRec = (Button) activity.findViewById(R.id.dataReceb);
        this.campoValorRec = (EditText) activity.findViewById(R.id.valorRec);
    }

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
            throw new CampoVazioException("Preencha todos os campos");

        nomeRec = campoNomeRec.getText().toString();
        validarDataReceita();

        if(nomeRec.length() > 15)
            throw new NomeComMaximoCaracteresException("Insira um nome com no máximo 15 caracteres");

        try
        {
            valorRec = Float.parseFloat(campoValorRec.getText().toString());
        }
        catch (NumberFormatException e)
        {
            Log.i("Erro Receita Helper","Erro ao converter o number para float");
            receita =  null;
        }

        if(nomeRec != null || tipoRec != null || dataRec != null || valorRec != null){
            receita = new Receita(nomeRec,tipoRec,dataRec,valorRec);
            receita.setId(ReceitaHelper.id);
        }


        return receita;
    }

    private void validarDataReceita() {

        RelogioHelper dataInformada = new RelogioHelper(campoDataRec.getText().toString());
        RelogioHelper dataSys = new RelogioHelper(RelogioHelper.dataHoje());

        if(dataInformada.getMes() == dataSys.getMes() && dataInformada.getAno() == dataInformada.getAno())
            dataRec = dataInformada.toString();
        else
            throw new DataIncorretaException("Informe a data de recebimento deste mês de " +
                    dataSys.returnMesName());
    }

    public void setReceita(Receita receita) {

        ReceitaHelper.id = receita.getId();
        this.campoNomeRec.setText(receita.getNomeRec());
        this.campoDataRec.setText(receita.getDataRec());
        this.campoValorRec.setText(""+receita.getValorRec());

        if(receita.getTipoRec() == 1) {
            campoTipoRec.setChecked(true);
        }
    }
}
