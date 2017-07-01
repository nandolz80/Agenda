package idadecachorro.cursoandroid.com.welcome.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import idadecachorro.cursoandroid.com.welcome.model.Aluno;

/**
 * Created by Fernando on 28/06/2017.
 */

public class AlunoDao extends SQLiteOpenHelper{

    private static final int VERSAO = 1;
    private static final String DATABASE = "Agenda";

    public AlunoDao(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    public void salva(Aluno aluno) {

        ContentValues values = new ContentValues();

        values.put("nome",aluno.getNome());
        values.put("site",aluno.getSite());
        values.put("endereco",aluno.getEndereco());
        values.put("telefone",aluno.getTelefone());
        values.put("nota",aluno.getNota());
        values.put("foto",aluno.getFoto());

        getWritableDatabase().insert("Alunos", null, values);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, " +
                " nome TEXT UNIQUE NOT NULL, " +
                " telefone TEXT, " +
                " endereco TEXT, " +
                " site TEXT, " +
                " foto TEXT, " +
                " nota REAL);";
        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String ddl = "DROP TABLE IF EXITS Alunos";
        db.execSQL(ddl);
        this.onCreate(db);
    }

    public List<Aluno> getLista() {
        String sql = "SELECT * FROM Alunos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Aluno> alunos = new ArrayList<Aluno>();
        while (c.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setFoto(c.getString(c.getColumnIndex("foto")));

            alunos.add(aluno);

        }
        c.close();

        return alunos;
    }

    public void deletar(Aluno aluno) {
        //String[] args = {aluno.getId().toString()};
        getWritableDatabase().delete("Alunos","id=?",new String[]{String.valueOf(aluno.getId())});

    }

    public void altera(Aluno aluno) {
        ContentValues values = new ContentValues();

        values.put("nome",aluno.getNome());
        values.put("site",aluno.getSite());
        values.put("endereco",aluno.getEndereco());
        values.put("telefone",aluno.getTelefone());
        values.put("nota",aluno.getNota());
        values.put("foto",aluno.getFoto());

        String[] args = {aluno.getId().toString()};
        getWritableDatabase().update("Alunos",values,"id=?",args);
    }
}
