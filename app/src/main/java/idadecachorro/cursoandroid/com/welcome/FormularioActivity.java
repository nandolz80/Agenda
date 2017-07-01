package idadecachorro.cursoandroid.com.welcome;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.File;

import idadecachorro.cursoandroid.com.welcome.dao.AlunoDao;
import idadecachorro.cursoandroid.com.welcome.model.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;
    private String caminhoArquivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        Intent intent = getIntent();
        final Aluno alunoParaSerAlterado = (Aluno) intent.getSerializableExtra("AlunoSelecionado");

        helper = new FormularioHelper(this);
        Button botao = (Button) findViewById(R.id.buttonCadastrar);
        if(alunoParaSerAlterado != null){
            botao.setText("Alterar");
            helper.colocaAlunoNoFormulario(alunoParaSerAlterado);
        }
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aluno aluno = helper.pegaAlunoDoFormulario();

                AlunoDao dao = new AlunoDao(FormularioActivity.this);
                if(alunoParaSerAlterado == null){
                    dao.salva(aluno);
                }else{
                    aluno.setId(alunoParaSerAlterado.getId());
                    dao.altera(aluno);
                }

                dao.close();
                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        ImageView foto = helper.getFoto();
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoArquivo = Environment.getExternalStorageDirectory().toString()+"/"+System.currentTimeMillis()+".jpg";
                File arquivo = new File(caminhoArquivo);
                Uri localImagem = Uri.fromFile(arquivo);
                //irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localImagem);
                startActivityForResult(irParaCamera, 123);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123){
            if(resultCode == Activity.RESULT_OK){
                helper.carregaImagem(caminhoArquivo);
            }else{
                caminhoArquivo = null;
            }
        }
    }
}
