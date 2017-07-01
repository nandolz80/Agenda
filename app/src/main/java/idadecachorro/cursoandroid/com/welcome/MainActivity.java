package idadecachorro.cursoandroid.com.welcome;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import idadecachorro.cursoandroid.com.welcome.dao.AlunoDao;
import idadecachorro.cursoandroid.com.welcome.model.Aluno;

public class MainActivity extends AppCompatActivity {


    private Aluno alunoClicado;
    private Aluno aluno;
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.lista);

        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                alunoClicado = (Aluno) adapter.getItemAtPosition(position);
                Intent irParaFormulario = new Intent(MainActivity.this, FormularioActivity.class);
                irParaFormulario.putExtra("AlunoSelecionado", alunoClicado);
                startActivity(irParaFormulario);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                aluno = (Aluno) adapter.getItemAtPosition(position);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem ligar = menu.add("Ligar");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public static final boolean TODO = true;

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent irParaTelaDiscagem = new Intent(Intent.ACTION_CALL);
                Uri discarPara = Uri.parse("tel:" + aluno.getTelefone());
                irParaTelaDiscagem.setData(discarPara);

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return TODO;
                }
                startActivity(irParaTelaDiscagem);
                return false;
            }
        });
        MenuItem sms = menu.add("Enviar SMS");
        sms.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent smsIntent = new Intent(Intent.ACTION_SEND);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "NUMERO DO TELEFONE");
                smsIntent.putExtra("sms_body","MENSAGEM A SER ENVIADA");
                startActivity(smsIntent);
                return false;
            }
        });

        MenuItem site = menu.add("Navegar no site");
        site.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent irParaOSite = new Intent(Intent.ACTION_VIEW);
                Uri localSite = Uri.parse("http://:" + aluno.getSite());
                irParaOSite.setData(localSite);
                startActivity(irParaOSite);
                return false;
            }
        });

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDao dao = new AlunoDao(MainActivity.this);
                dao.deletar(aluno);
                dao.close();
                Toast.makeText(MainActivity.this, "Aluno removido", Toast.LENGTH_SHORT).show();
                carregaLista();
                return false;
            }
        });
        menu.add("Ver no mapa");
        menu.add("Enviar e-mail");


        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    private void carregaLista(){
        AlunoDao dao = new AlunoDao(this);
        List<Aluno> alunos = dao.getLista();
        dao.close();
        //int layout = android.R.layout.simple_list_item_1;
        //ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, layout, alunos);

        int layout = R.layout.linha_listagem;
        ListaALunosAdapter adapter = new ListaALunosAdapter(alunos, this);

        lista.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_alunos, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.novo:
                Intent irFormulario = new Intent(this, FormularioActivity.class);
                startActivity(irFormulario);
                return true;
            case R.id.mapa:
                Intent irMapa = new Intent(this, MostraALunos.class);
                startActivity(irMapa);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
