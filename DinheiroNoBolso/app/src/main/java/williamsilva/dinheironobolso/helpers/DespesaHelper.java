package williamsilva.dinheironobolso.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import williamsilva.dinheironobolso.AlterarDespesaActivity;
import williamsilva.dinheironobolso.NovaDespesaActivity;
import williamsilva.dinheironobolso.R;
import williamsilva.dinheironobolso.exceptions.CampoVazioException;
import williamsilva.dinheironobolso.exceptions.DataIncorretaException;
import williamsilva.dinheironobolso.exceptions.NomeComMaximoCaracteresException;
import williamsilva.dinheironobolso.exceptions.ValorIndisponivelException;
import williamsilva.dinheironobolso.models.Despesa;
import williamsilva.dinheironobolso.models.Receita;

/**
 * Created by William on 24/06/2014.
 */

public class DespesaHelper {

    private EditText campoNomeDesp;
    private EditText campoValorDesp;
    private CheckBox campoTipoDesp;
    private RadioGroup campoStatusDesp;
    private Button campoDataVenc;

    private Context contexto;

    private static Integer idDespesa = null;
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
        contexto = activity;
    }


    public DespesaHelper(NovaDespesaActivity activity) {

        campoNomeDesp = (EditText) activity.findViewById(R.id.nomeDespesa);
        campoValorDesp = (EditText) activity.findViewById(R.id.valorDesp);
        campoDataVenc = (Button) activity.findViewById(R.id.dataVenc);
        campoTipoDesp = (CheckBox) activity.findViewById(R.id.tipoDespesafixaChek);
        campoStatusDesp = (RadioGroup) activity.findViewById(R.id.statusDespesa);
        campoDataVenc = (Button) activity.findViewById(R.id.dataVenc);
        contexto = activity;

    }

    public void setDespesa(Despesa despesa)
    {
        Integer id = despesa.getId();
        String nome = despesa.getNomeDesp();
        Float valor = despesa.getValorDesp();
        String datavenc = despesa.getDataVenc();
        Integer tipodesp = despesa.getTipoDesp();
        Integer status = despesa.getStatus();

        DespesaHelper.idDespesa = id;
        this.campoNomeDesp.setText(nome);
        this.campoValorDesp.setText(valor.toString());
        this.campoDataVenc.setText(datavenc);

        if(tipodesp.equals(1)) {
            this.campoTipoDesp.setChecked(true);
        }
        switch(status){

            case 1:
                this.campoStatusDesp.check(R.id.statusPago);
                break;
            case 0:
                this.campoStatusDesp.check(R.id.statusNaoPago);
                break;
            default:
                break;
        }
    }

    public Despesa getDespesa() {

        // tratamento sob campos não preenchidos
        if(     ValidaCampoVazioHelper.validar(campoNomeDesp, "Insira um nome para sua despesa!") != true   &&
                ValidaCampoVazioHelper.validar(campoValorDesp, "Insira um valor para sua despesa!") != true ||
                ValidaCampoVazioHelper.validar(campoValorDesp, "Insira um valor para sua despesa!") != true ||
                ValidaCampoVazioHelper.validar(campoNomeDesp, "Insira um nome para sua despesa!") != true
                )
            throw new CampoVazioException("Preencha todos os campos");

        Despesa despesa = null;

        if(campoTipoDesp.isChecked())
            tipoDesp = 1;
        else
            tipoDesp = 0;

        nomeDesp = campoNomeDesp.getText().toString();
        validarDataPagamento();

        if(nomeDesp.length() > 15)
            throw new NomeComMaximoCaracteresException("Insira um nome com no máximo 15 caracteres");

        try {
            valorDesp = Float.parseFloat(campoValorDesp.getText().toString());
        }
        catch (NumberFormatException e)
        {
            Log.i("Nova Despesa Helper" , e.getMessage());
        }

        switch (campoStatusDesp.getCheckedRadioButtonId()) {
            case R.id.statusPago:
                validarPagamento();
                break;
            case R.id.statusNaoPago:
                status = 0;
                break;
        }

        if(nomeDesp != null && tipoDesp != null && dataVenc != null && valorDesp > 0) {
            despesa = new Despesa(nomeDesp, tipoDesp, dataVenc, valorDesp, status);
            despesa.setId(DespesaHelper.idDespesa);
        }
        return  despesa;
    }

    private void validarDataPagamento() {

        RelogioHelper datainformada = new RelogioHelper(campoDataVenc.getText().toString());
        RelogioHelper dataSys = new RelogioHelper(RelogioHelper.dataHoje());

        if(dataSys.getMes() == datainformada.getMes() && dataSys.getAno() == datainformada.getAno())
            dataVenc = datainformada.toString();
        else
            throw new DataIncorretaException("Informe a data da despesa deste mês de " +
                    dataSys.returnMesName());
    }

    private Boolean validarPagamento() {

        Boolean rs;

        if(DespesaJaEstaPaga() == true)
            return true;

        Receita lancamento = new Receita();
        Despesa desp = new Despesa();
        List<Receita> receitas = lancamento.getLista(contexto);
        List<Despesa> despesas = desp.getLista(contexto);
        Float totalReceitas = 0f, totalDespesa = 0f, saldoLiquido = 0f;
        RelogioHelper dataSys,databanco;
        dataSys = new RelogioHelper(RelogioHelper.dataHoje());

        for(Receita receita : receitas)
        {
            databanco = new RelogioHelper(receita.getDataRec());

                if(dataSys.getMes() == databanco.getMes() && dataSys.getAno() == databanco.getAno())
                    totalReceitas = totalReceitas + receita.getValorRec();
        }

        for (Despesa despesa : despesas)
        {
            databanco = new RelogioHelper(despesa.getDataVenc());

               if(dataSys.getMes() == databanco.getMes() && dataSys.getAno() == databanco.getAno() &&
                despesa.getStatus().equals(1))
                   totalDespesa = totalDespesa + despesa.getValorDesp();

        }

        saldoLiquido =  totalReceitas - totalDespesa;

        if(saldoLiquido - valorDesp >= 0) {
            status = 1;
            rs = true;
        }
        else {
            throw new ValorIndisponivelException("Saldo insuficiente");
        }

        return rs;
    }

    private boolean DespesaJaEstaPaga() {
/* este metodo é executado caso for uma alteracao da despesa e a despesa já no banco estiver
   checado que já esta paga, ai o metodo retorna true para nao dar a menssagem de erro de saldo
   insuficiente.
 */
        boolean rs = false;

        if(DespesaHelper.idDespesa != null && DespesaHelper.idDespesa > 0)
        {
            Despesa despesaSelecionada = new Despesa();
            despesaSelecionada = despesaSelecionada.getDespesa(DespesaHelper.idDespesa,contexto);

                if(despesaSelecionada.getStatus().equals(1) && despesaSelecionada.getValorDesp() == valorDesp) {
                    rs = true;
                    status = 1;
                }
                else if(despesaSelecionada.getStatus().equals(1) && despesaSelecionada.getValorDesp()
                        != valorDesp){
                    rs = validarPagamentoJaPago(despesaSelecionada);
                }
        }
     return rs;
    }

    private boolean validarPagamentoJaPago(Despesa despesaSelecionada) {

        Boolean rs = false;
        Receita lancamento = new Receita();
        Despesa desp = new Despesa();
        List<Receita> receitas = lancamento.getLista(contexto);
        List<Despesa> despesas = desp.getLista(contexto);
        Float totalReceitas = 0f, totalDespesa = 0f, saldoLiquido = 0f;
        RelogioHelper dataSys,databanco;
        dataSys = new RelogioHelper(RelogioHelper.dataHoje());

        for(Receita receita : receitas)
        {
            databanco = new RelogioHelper(receita.getDataRec());

            if(dataSys.getMes() == databanco.getMes() && dataSys.getAno() == databanco.getAno())
                totalReceitas = totalReceitas + receita.getValorRec();
        }

        for (Despesa dep : despesas)
        {
            databanco = new RelogioHelper(dep.getDataVenc());

            if(dataSys.getMes() == databanco.getMes() && dataSys.getAno() == databanco.getAno() &&
                    dep.getStatus().equals(1))
                totalDespesa = totalDespesa + dep.getValorDesp();

        }

        saldoLiquido = (totalReceitas - totalDespesa) + despesaSelecionada.getValorDesp();

        if(saldoLiquido - valorDesp >= 0) {
            status = 1;
            rs = true;
        }
        else {
            throw new ValorIndisponivelException("Saldo insuficiente");
        }

        return rs;
    }
}
