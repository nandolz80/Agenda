package idadecachorro.cursoandroid.com.welcome;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import idadecachorro.cursoandroid.com.welcome.dao.AlunoDao;
import idadecachorro.cursoandroid.com.welcome.model.Aluno;

public class MostraALunos extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_alunos);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        // Add a marker in Sydney and move the camera
        LatLng local = new LatLng(-30.035292, -51.226415);
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(local).title("Faculdade"));

        AlunoDao dao = new AlunoDao(this);
        List<Aluno> alunos = dao.getLista();

        for (Aluno aluno: alunos) {
            LatLng localAluno = new Localizador(this).getCoordenada(aluno.getEndereco());
            mMap = googleMap;
            mMap.addMarker(new MarkerOptions().position(localAluno).title(aluno.getNome()));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localAluno, 500));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 15));

        dao.close();


    }

}
