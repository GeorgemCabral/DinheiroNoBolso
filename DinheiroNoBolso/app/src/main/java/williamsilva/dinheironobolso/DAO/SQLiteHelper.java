package williamsilva.dinheironobolso.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Implementacao de SQLiteOpenHelper
 *
 * Classe utilitária para abrir, criar, e atualizar o banco de dados
 *
 * @author William
 */
class SQLiteHelper extends SQLiteOpenHelper {

    private static final String CATEGORIA = "SQLITE HELPER";

    private String[] scriptSQLCreate = {"CREATE TABLE `DBDESPESAS` (" +
            " `DBDESPESAID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            " `DBDESPESANOME` TEXT NOT NULL, " +
            " `DBDESPESATIPO` INTEGER NOT NULL, " +
            " `DBDESPESAVALOR` REAL NOT NULL, " +
            " `DBDESPESADATAVENC` TEXT NOT NULL, " +
            " `DBDESPESASTATUS` INTEGER NOT NULL " +
            ");", "CREATE TABLE `DBRECEITAS` ( " +
            " `DBRECEITAID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            " `DBRECEITANOME` TEXT NOT NULL, " +
            " `DBRECEITATIPO` INTEGER NOT NULL, " +
            " `DBRECEITAVALOR` REAL NOT NULL, " +
            " `DBRECEITADATARECEBIMENTO` TEXT NOT NULL " +
            ");" };

    private String scriptSQLDelete;

    /**
     * Cria uma instância de SQLiteHelper
     *
     * @param context
     * @param nomeBanco nome do banco de dados
     * @param versaoBanco versão do banco de dados (se for diferente é para atualizar)
     *  scriptSQLCreate SQL com o create table..
     *  scriptSQLDelete SQL com o drop table...
     */
    SQLiteHelper(Context context, String nomeBanco, int versaoBanco, String scriptSQLDelete) {
        super(context, nomeBanco, null, versaoBanco);
        this.scriptSQLDelete = scriptSQLDelete;
    }

    @Override
    // Criar novo banco...
    public void onCreate(SQLiteDatabase db) {
        Log.i(CATEGORIA, "Criando banco com sql");
        int qtdeScripts = scriptSQLCreate.length;

        // Executa cada sql passado como parâmetro
        for (int i = 0; i < qtdeScripts; i++) {
            String sql = scriptSQLCreate[i];
            Log.i(CATEGORIA, sql);
            // Cria o banco de dados executando o script de criação
            db.execSQL(sql);
        }
    }

    @Override
    // Mudou a versão...
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
        Log.w(CATEGORIA, "Atualizando da versão " + versaoAntiga + " para " + novaVersao + ". Todos os registros serão deletados.");
        Log.i(CATEGORIA, scriptSQLDelete);
        // Deleta as tabelas...
        db.execSQL(scriptSQLDelete);
        // Cria novamente...
        onCreate(db);
    }
}