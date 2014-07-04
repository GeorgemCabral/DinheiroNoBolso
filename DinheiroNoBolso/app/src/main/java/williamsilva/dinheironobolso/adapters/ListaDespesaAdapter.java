package williamsilva.dinheironobolso.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import williamsilva.dinheironobolso.ListarDespesaActivity;
import williamsilva.dinheironobolso.R;
import williamsilva.dinheironobolso.models.Despesa;

/**
 * Created by William on 04/07/2014.
 */
public class ListaDespesaAdapter extends BaseAdapter {

    private List<Despesa> despesasDoMes;
    private Activity activity;
    private DecimalFormat df = new DecimalFormat("#0.00");

    public ListaDespesaAdapter(List<Despesa> despesasDoMes, Activity activity) {

        this.despesasDoMes = despesasDoMes;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return despesasDoMes.size();
    }

    @Override
    public Object getItem(int position) {
        return despesasDoMes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return despesasDoMes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Despesa despesa = despesasDoMes.get(position);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View linha = layoutInflater.inflate(R.layout.linha_listagem,null);

        TextView nome = (TextView) linha.findViewById(R.id.nomeList);
        nome.setText(despesa.getNomeDesp());

        TextView valor = (TextView) linha.findViewById(R.id.valorList);
        valor.setText("R$ " + df.format(despesa.getValorDesp()));

        TextView data = (TextView) linha.findViewById(R.id.dataList);
        data.setText("Venc: " + despesa.getDataVenc());

        return linha;
    }
}
