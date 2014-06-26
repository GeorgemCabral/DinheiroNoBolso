package williamsilva.dinheironobolso.models;

import android.content.ContentValues;
import android.content.Context;

import java.io.Serializable;

import williamsilva.dinheironobolso.DAO.DespesaDAO;

/**
 * Created by William on 24/06/2014.
 */
public class Despesa extends DespesaDAO implements Serializable {

    private String nomeDesp;
    private String tipoDesp;
    private String dataVenc;
    private float valorDesp;
    private String status;

    public Despesa(Context contexto)
    {
        super(contexto);
    }

    public Despesa(String nomeDesp, String tipoDesp, String dataVenc, float valorDesp, String status ,Context contexto) {
        super(contexto);
        this.nomeDesp = nomeDesp;
        this.tipoDesp = tipoDesp;
        this.dataVenc = dataVenc;
        this.valorDesp = valorDesp;
        this.status = status;
    }

    public float getValorDesp() {
        return valorDesp;
    }

    public void setValorDesp(float valorDesp) {
        this.valorDesp = valorDesp;
    }

    public String getDataVenc() {
        return dataVenc;
    }

    public void setDataVenc(String dataVenc) {
        this.dataVenc = dataVenc;
    }

    public String getTipoDesp() {
        return tipoDesp;
    }

    public void setTipoDesp(String tipoDesp) {
        this.tipoDesp = tipoDesp;
    }

    public String getStatus() {
        return status;
    }

    public String getNomeDesp() {
        return nomeDesp;
    }

    public void setNomeDesp(String nomeDesp) {
        this.nomeDesp = nomeDesp;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String toString()
    {
        return this.getNomeDesp() + "     R$: "+getValorDesp();
    }

    public Boolean novaDespesa(Despesa despesa) {
        ContentValues dados = new ContentValues();
        dados.put("DBDESPESANOME", despesa.getNomeDesp());
        dados.put("DBDESPESATIPO", despesa.getTipoDesp());
        dados.put("DBDESPESAVALOR", despesa.getValorDesp());
        dados.put("DBDESPESADATAVENC", despesa.getDataVenc());
        dados.put("DBDESPESASTATUS", despesa.getStatus());
        return this.cadastrar(dados);
    }

    /*public List<Despesa> getLista() {
        return this.getLista();
    }*/
}