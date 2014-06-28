package williamsilva.dinheironobolso.models;

import android.content.ContentValues;
import android.content.Context;
import android.widget.Toast;

import java.util.List;

import williamsilva.dinheironobolso.DAO.ReceitaDAO;
import williamsilva.dinheironobolso.ListarReceitasActivity;
import williamsilva.dinheironobolso.NovaReceitaActivity;

/**
 * Created by William on 27/06/2014.
 */
public class Receita {

    private String nomeRec;
    private Integer tipoRec;
    private String dataRec;
    private Float valorRec;
    private Integer id;

    public Receita()
    {

    }

    public Receita(String nomeRec, Integer tipoRec, String dataRec, Float valorRec) {
        this.nomeRec = nomeRec;
        this.tipoRec = tipoRec;
        this.dataRec = dataRec;
        this.valorRec = valorRec;
    }

    public String getNomeRec() {return nomeRec; }

    public void setNomeRec(String nomeRec) { this.nomeRec = nomeRec; }

    public Integer getTipoRec() { return tipoRec; }

    public void setTipoRec(Integer tipoRec) { this.tipoRec = tipoRec; }

    public String getDataRec() { return dataRec; }

    public void setDataRec(String dataRec) { this.dataRec = dataRec; }

    public Float getValorRec() { return valorRec;  }

    public void setValorRec(Float valorRec) { this.valorRec = valorRec; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String toString()
    {
        return this.getNomeRec() + "     R$: "+getValorRec();
    }

    public boolean novaReceita(Receita receita, Context contexto) {

        ContentValues dados = new ContentValues();
        dados.put("DBRECEITANOME", receita.getNomeRec());
        dados.put("DBRECEITATIPO",receita.getTipoRec());
        dados.put("DBRECEITAVALOR",receita.getValorRec());
        dados.put("DBRECEITADATARECEBIMENTO",receita.getDataRec());

        ReceitaDAO dao = new ReceitaDAO(contexto);
        return dao.cadastrar(dados);
    }

    public List<Receita> getLista(Context contexto) {

       ReceitaDAO dao = new ReceitaDAO(contexto);
       return dao.getLista();
    }

    public void excluir(Receita receita, Context contexto) {
        ReceitaDAO dao = new ReceitaDAO(contexto);
        dao.excluir(receita);
    }
}
