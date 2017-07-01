package idadecachorro.cursoandroid.com.welcome;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import idadecachorro.cursoandroid.com.welcome.model.Aluno;

/**
 * Created by Fernando on 27/06/2017.
 */

class FormularioHelper {

    private EditText editNome;
    private EditText editSite;
    private EditText editTelefone;
    private EditText editEndereco;
    private RatingBar ratingNota;
    private ImageView foto;
    private Aluno aluno;

    public FormularioHelper(FormularioActivity formularioActivity) {
        editNome = (EditText)formularioActivity.findViewById(R.id.editTextNome);
        editSite = (EditText)formularioActivity.findViewById(R.id.editTextSite);
        editTelefone = (EditText)formularioActivity.findViewById(R.id.editTextTelefone);
        editEndereco = (EditText)formularioActivity.findViewById(R.id.editTextEndereco);
        ratingNota = (RatingBar) formularioActivity.findViewById(R.id.ratingNota);
        foto = (ImageView) formularioActivity.findViewById(R.id.imageFoto);
        aluno = new Aluno();
    }

    public Aluno pegaAlunoDoFormulario() {
        aluno.setNome(editNome.getText().toString());
        aluno.setSite(editSite.getText().toString());
        aluno.setEndereco(editEndereco.getText().toString());
        aluno.setTelefone(editTelefone.getText().toString());
        aluno.setNota(Double.valueOf(ratingNota.getRating()));

        return aluno;
    }

    public void colocaAlunoNoFormulario(Aluno alunoParaSerAlterado) {
        aluno = alunoParaSerAlterado;
        editNome.setText(alunoParaSerAlterado.getNome());
        editSite.setText(alunoParaSerAlterado.getSite());
        editTelefone.setText(alunoParaSerAlterado.getTelefone());
        editEndereco.setText(alunoParaSerAlterado.getEndereco());
        ratingNota.setRating(alunoParaSerAlterado.getNota().floatValue());
        if(aluno.getFoto() != null){
            carregaImagem(alunoParaSerAlterado.getFoto());
        }
    }

    public ImageView getFoto(){
        return foto;
    }

    public void carregaImagem(String caminhoArquivo) {
        aluno.setFoto(caminhoArquivo);
        Bitmap imagem = BitmapFactory.decodeFile(caminhoArquivo);
        Bitmap imagemReduzida = Bitmap.createScaledBitmap(imagem, 100, 100, true);

        foto.setImageBitmap(imagemReduzida);

    }
}
