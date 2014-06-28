package williamsilva.dinheironobolso.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import williamsilva.dinheironobolso.models.Despesa;

/**
 * Created by William on 25/06/2014.
 */
public class DespesaDAO {

    private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS DBDESPESAS";
    private static String NOME_BANCO = "DINHEIRONOBOLSODB";
    private static final int VERSAO_BANCO = 1;
    private static String TABLE_DESPESA = "DBDESPESAS";
    private SQLiteHelper dbHelper;
    private SQLiteDatabase db;
    private Context contexto;

    public DespesaDAO(Context contexto)
    {
        this.contexto = contexto;
        dbHelper = new SQLiteHelper(this.contexto,DespesaDAO.NOME_BANCO,DespesaDAO.VERSAO_BANCO,
         SCRIPT_DATABASE_DELETE);

        db = dbHelper.getWritableDatabase();
    }

    public Boolean cadastrar(ContentValues dados)
    {
        try {
            db.insert(TABLE_DESPESA, null, dados);
            Log.i("Despesa Cadastrada",dados.get("DBDESPESANOME") + " " + dados.get("DBDESPESATIPO") + " " +
            dados.get("DBDESPESAVALOR") + " " + dados.get("DBDESPESADATAVENC") + " "+ dados.get("DBDESPESASTATUS"));
        }
        catch (Exception e)
        {
            Log.i("Erro Cadastro de Despesa",e.getMessage());
            return false;
        }
        finally {
            fechar();
        }

        return true;
    }

    public List<Despesa> getLista() {

        String[] colunas = {"DBDESPESANOME", "DBDESPESATIPO", "DBDESPESAVALOR", "DBDESPESADATAVENC", "DBDESPESASTATUS","DBDESPESAID"};
        ArrayList<Despesa> despesas = new ArrayList<Despesa>();

         try{

            Cursor cursor = db.query("DBDESPESAS", colunas, null, null, null, null, null);
            while (cursor.moveToNext()) {

                Despesa despesa = new Despesa(cursor.getString(0), Integer.parseInt(cursor.getString(1)), cursor.getString(3),
                        Float.parseFloat(cursor.getString(2)), Integer.parseInt(cursor.getString(4)), contexto);
                despesa.setId(Integer.parseInt(cursor.getString(5)));
                despesas.add(despesa);
            }
        }
        catch (Exception e)
        {
            Log.i("Erro Listando as Despesas",e.getMessage());
        }
        finally {
            fechar();
        }


        return despesas;
    }

    public void excluir(Despesa despesa){

        String argumentos[] = {despesa.getId().toString()};
        try {
            db.delete("DBDESPESAS", "DBDESPESAID=?", argumentos);
            Log.i("Deletando uma Despesa","DELETADO COM SUCESSO");
        }
        catch (Exception e)
        {
            Log.i("Erro ao deletar despesa",e.getMessage());
        }
        finally {
            fechar();
        }


    }

    public Despesa getDespesaDAO(Integer id)
    {
       String[] colunas = {"DBDESPESANOME", "DBDESPESATIPO", "DBDESPESAVALOR", "DBDESPESADATAVENC", "DBDESPESASTATUS","DBDESPESAID"};
       String[] desp = {id.toString()};
       Despesa despesa = null;

        try {

            Cursor cursor = db.query("DBDESPESAS", colunas,"DBDESPESAID=?", desp, null, null, null);
             cursor.moveToNext();
            despesa = new Despesa(cursor.getString(0), Integer.parseInt(cursor.getString(1)), cursor.getString(3),
                    Float.parseFloat(cursor.getString(2)), Integer.parseInt(cursor.getString(4)), contexto);

        }catch (Exception e)
        {
            Log.i("Erro buscar despesas", e.getMessage());
        }
        finally {
            fechar();
        }

        return despesa;
    }

    public void fechar() {
        // fecha o banco de dados
        if (db != null) {
            db.close();
        }
    }

    public Boolean alterarDespesa(ContentValues dados,Integer id) {

        int count;

        try {
            count = db.update(TABLE_DESPESA, dados,"DBDESPESAID=?",new String[]{id.toString()});
            Log.i("Despesa Alterada",dados.get("DBDESPESANOME") + " " + dados.get("DBDESPESATIPO") + " " +
                    dados.get("DBDESPESAVALOR") + " " + dados.get("DBDESPESADATAVENC") + " "+ dados.get("DBDESPESASTATUS"));
        }
        catch (Exception e)
        {
            Log.i("Erro Alterar de Despesa",e.getMessage());
            return false;
        }
        finally {
            fechar();
        }

        if(count > 0)
            return true;
        else
            return false;
    }
}
