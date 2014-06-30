package williamsilva.dinheironobolso.models;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.util.List;

import williamsilva.dinheironobolso.DAO.DespesaDAO;

/**
 * Created by William on 24/06/2014.
 */
public class Despesa implements Serializable{

    private String nomeDesp;
    private Integer tipoDesp;
    private String dataVenc;
    private float valorDesp;
    private Integer status;
    private Integer id;

    public Despesa()
    {

    }

    public Despesa(String nomeDesp, Integer tipoDesp, String dataVenc, float valorDesp, Integer status) {

        this.nomeDesp = nomeDesp;
        this.tipoDesp = tipoDesp;
        this.dataVenc = dataVenc;
        this.valorDesp = valorDesp;
        this.status = status;
    }

    public Integer getId(){return id;}

    public void setId(Integer id){ this.id = id;}

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

    public Integer getTipoDesp() {
        return tipoDesp;
    }

    public void setTipoDesp(Integer tipoDesp) {
        this.tipoDesp = tipoDesp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getNomeDesp() {
        return nomeDesp;
    }

    public void setNomeDesp(String nomeDesp) {
        this.nomeDesp = nomeDesp;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public String toString()
    {
        return this.getNomeDesp() + "     R$: "+getValorDesp();
    }

    public Boolean novaDespesa(Despesa despesa,Context contexto) {
        ContentValues dados = new ContentValues();
        dados.put("DBDESPESANOME", despesa.getNomeDesp());
        dados.put("DBDESPESATIPO", despesa.getTipoDesp());
        dados.put("DBDESPESAVALOR", despesa.getValorDesp());
        dados.put("DBDESPESADATAVENC", despesa.getDataVenc());
        dados.put("DBDESPESASTATUS", despesa.getStatus());

        DespesaDAO  dao = new DespesaDAO(contexto);
        return dao.cadastrar(dados);
    }

    public Boolean alteraDespesa(Despesa despesa, Context contexto)
    {
        ContentValues dados = new ContentValues();
        dados.put("DBDESPESANOME", despesa.getNomeDesp());
        dados.put("DBDESPESATIPO", despesa.getTipoDesp());
        dados.put("DBDESPESAVALOR", despesa.getValorDesp());
        dados.put("DBDESPESADATAVENC", despesa.getDataVenc());
        dados.put("DBDESPESASTATUS", despesa.getStatus());

        DespesaDAO  dao = new DespesaDAO(contexto);
        return dao.alterarDespesa(dados, despesa.getId());

    }

    public void excluir(Despesa despesa,Context contexto)
    {
        DespesaDAO dao = new DespesaDAO(contexto);
        dao.excluir(despesa);
    }

    public List<Despesa> getLista(Context contexto){
        DespesaDAO dao = new DespesaDAO(contexto);
        return dao.getLista();
    }

    public Despesa getDespesa(Integer idDespesa, Context contexto) {

        DespesaDAO dao = new DespesaDAO(contexto);
        return dao.getDespesaDAO(idDespesa);
    }
}