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
    private static String[] CREATE_DATA_BASE = new String[]{"CREATE TABLE `DBDESPESAS` (\n" +
            "\t`DBDESPESAID`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`DBDESPESANOME`\tTEXT NOT NULL,\n" +
            "\t`DBDESPESATIPO`\tTEXT NOT NULL,\n" +
            "\t`DBDESPESAVALOR`\tREAL NOT NULL,\n" +
            "\t`DBDESPESADATAVENC`\tTEXT NOT NULL,\n" +
            "\t`DBDESPESASTATUS`\tTEXT\n" +
            ");"};
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
                DespesaDAO.CREATE_DATA_BASE,SCRIPT_DATABASE_DELETE);
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

        String[] colunas = {"DBDESPESANOME", "DBDESPESATIPO", "DBDESPESAVALOR", "DBDESPESADATAVENC", "DBDESPESASTATUS"};
        ArrayList<Despesa> despesas = new ArrayList<Despesa>();

         try{

            Cursor cursor = db.query("DBDESPESAS", colunas, null, null, null, null, null);

            while (cursor.moveToNext()) {

                Despesa despesa = new Despesa(cursor.getString(0), cursor.getString(1), cursor.getString(3),
                        Float.parseFloat(cursor.getString(2)), cursor.getString(4), contexto);
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

        String argumentos[] = {despesa.getNomeDesp()};
        try {
            db.delete("DBDESPESAS", "DBDESPESANOME=?", argumentos);
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

    public void fechar() {
        // fecha o banco de dados
        if (db != null) {
            db.close();
        }
    }

}
