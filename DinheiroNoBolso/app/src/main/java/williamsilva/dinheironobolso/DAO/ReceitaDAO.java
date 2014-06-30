package williamsilva.dinheironobolso.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import williamsilva.dinheironobolso.models.Receita;

/**
 * Created by William on 27/06/2014.
 */
public class ReceitaDAO {

    private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS DBRECEITAS";
    private static String NOME_BANCO = "DINHEIRONOBOLSODB";
    private static final int VERSAO_BANCO = 1;
    private static String TABLE_RECEITA = "DBRECEITAS";
    private SQLiteHelper dbHelper;
    private SQLiteDatabase db;
    private Context contexto;


    public ReceitaDAO(Context contexto) {
        this.contexto = contexto;
        dbHelper = new SQLiteHelper(this.contexto,ReceitaDAO.NOME_BANCO,ReceitaDAO.VERSAO_BANCO,
                SCRIPT_DATABASE_DELETE);

        db = dbHelper.getWritableDatabase();
    }

    public Boolean cadastrar(ContentValues dados) {
        try {
            db.insert(TABLE_RECEITA, null, dados);
            Log.i("Receita Cadastrada", "Cadastrado");
        } catch (Exception e) {
            Log.i("Erro Cadastro de Despesa", e.getMessage());
            return false;
        } finally {
            fechar();
        }

        return true;
    }

    private void fechar() {
        // fecha o banco de dados
        if (db != null) {
            db.close();
        }
    }

    public List<Receita> getLista() {
        String[] colunas = {"DBRECEITAID", "DBRECEITANOME", "DBRECEITATIPO", "DBRECEITAVALOR", "DBRECEITADATARECEBIMENTO"};
        ArrayList<Receita> receitas = new ArrayList<Receita>();

        try {

            Cursor cursor = db.query("DBRECEITAS", colunas, null, null, null, null, null);
            while (cursor.moveToNext()) {

                Receita receita = new Receita(cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(4),
                        Float.parseFloat(cursor.getString(3)));

                receita.setId(Integer.parseInt(cursor.getString(0)));
                receitas.add(receita);
            }
        } catch (Exception e) {
            Log.i("Erro Listando as Receitas", e.getMessage());
        } finally {
            fechar();
        }
        return receitas;
    }

    public void excluir(Receita receita){

        String argumentos[] = {receita.getId().toString()};
        try {
            db.delete("DBRECEITAS", "DBRECEITAID=?", argumentos);
            Log.i("Deletando uma Receita","DELETADO COM SUCESSO");
        }
        catch (Exception e)
        {
            Log.i("Erro ao deletar despesa",e.getMessage());
        }
        finally {
            fechar();
        }

    }

    public Boolean alterarReceita(ContentValues dados, Integer id) {

        Integer count = 0;
        try{
            count = db.update(TABLE_RECEITA,dados,"DBRECEITAID=?",new String[]{id.toString()});
        }catch (Exception e){
            Log.i("Erro alterar receita",e.getMessage());
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
