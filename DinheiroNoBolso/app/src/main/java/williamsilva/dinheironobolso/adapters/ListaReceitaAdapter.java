package williamsilva.dinheironobolso.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import williamsilva.dinheironobolso.ListarReceitasActivity;
import williamsilva.dinheironobolso.R;
import williamsilva.dinheironobolso.models.Receita;

/**
 * Created by William on 04/07/2014.
 */
public class ListaReceitaAdapter extends BaseAdapter {
    private List<Receita> receitasDoMes;
    private Activity activity;
    private DecimalFormat df = new DecimalFormat("#0.00");

    public ListaReceitaAdapter(List<Receita> receitasDoMes, Activity activity) {
        this.receitasDoMes = receitasDoMes;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return receitasDoMes.size();
    }

    @Override
    public Object getItem(int position) {
        return receitasDoMes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return receitasDoMes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Receita receita = receitasDoMes.get(position);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View linha = layoutInflater.inflate(R.layout.linha_listagem,null);

        TextView nome = (TextView) linha.findViewById(R.id.nomeList);
        nome.setText(receita.getNomeRec());

        TextView valor = (TextView) linha.findViewById(R.id.valorList);
        valor.setText("R$ " + df.format(receita.getValorRec()));

        TextView data = (TextView) linha.findViewById(R.id.dataList);
        data.setText("Dep: " + receita.getDataRec());

        return linha;
    }
}
