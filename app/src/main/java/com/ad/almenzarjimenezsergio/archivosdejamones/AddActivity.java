package com.ad.almenzarjimenezsergio.archivosdejamones;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ad.almenzarjimenezsergio.archivosdejamones.data.Vino;
import com.ad.almenzarjimenezsergio.archivosdejamones.data.util.Csv;
import com.ad.almenzarjimenezsergio.archivosdejamones.data.util.WriteRead;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    TextView tvIdAdd, tvNombreAdd, tvBodegaAdd, tvColorAdd, tvOrigenAdd, tvGraduacionAdd, tvFechaAdd;
    EditText etIdAdd, etNombreAdd, etBodegaAdd, etColorAdd, etOrigenAdd, etGraduacionAdd, etFechaAdd;
    Button btAddAdd;

    ArrayList<Vino> vinos;
    Bundle bundle;
    WriteRead writeRead = new WriteRead();


    private boolean camposBuenos() {
        boolean bueno = true;
        try {
            int id = Integer.parseInt(etIdAdd.getText().toString());

            if (vinos.size() > 0){
                for (Vino vino: vinos) {
                    if (vino.getId() == id){
                        bueno = false;
                        etIdAdd.setHint("Ya existe este Id");
                    }
                }
            }

        } catch (NumberFormatException nfe) {
            etIdAdd.setText("");
            System.out.println("ID MALO");
            bueno = false;
        }

        try {
            String str = "";
            for (int i = 0; i < etGraduacionAdd.getText().toString().length(); i++){
                if (etGraduacionAdd.getText().toString().charAt(i) == ','){
                    str += ".";
                } else {
                    str += etGraduacionAdd.getText().toString().charAt(i);
                }
            }

            double graduacion = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            System.out.println("GRADUACION MALA");
            etGraduacionAdd.setText("");
            bueno = false;
        }

        try {
            int fecha = Integer.parseInt(etFechaAdd.getText().toString());
        } catch (NumberFormatException nfe) {
            System.out.println("FECHA MALA");
            etFechaAdd.setText("");
            bueno = false;
        }

        return bueno;
    }

    private boolean emptyFields() {
        int cont = 0;

        if (etIdAdd.getText().length() > 0){
            cont++;
        }

        if (etNombreAdd.getText().length() > 0){
            cont++;
        }

        if (etBodegaAdd.getText().length() > 0){
            cont++;
        }

        if (etColorAdd.getText().length() > 0){
            cont++;
        }

        if (etOrigenAdd.getText().length() > 0){
            cont++;
        }

        if (etGraduacionAdd.getText().length() > 0){
            cont++;
        }

        if (etFechaAdd.getText().length() > 0){
            cont++;
        }

        if(cont == 7){
            return false;
        } else {
            return true;
        }
    }

    private void initialize() {
        tvIdAdd = findViewById(R.id.tvIdAdd);
        tvNombreAdd = findViewById(R.id.tvNombreAdd);
        tvBodegaAdd = findViewById(R.id.tvBodegaAdd);
        tvColorAdd = findViewById(R.id.tvColorAdd);
        tvOrigenAdd = findViewById(R.id.tvOrigenAdd);
        tvGraduacionAdd = findViewById(R.id.tvGraduacionAdd);
        tvFechaAdd = findViewById(R.id.tvFechaAdd);

        etIdAdd = findViewById(R.id.etIdAdd);
        etNombreAdd = findViewById(R.id.etNombreAdd);
        etBodegaAdd = findViewById(R.id.etBodegaAdd);
        etColorAdd = findViewById(R.id.etColorAdd);
        etOrigenAdd = findViewById(R.id.etOrigenAdd);
        etGraduacionAdd = findViewById(R.id.etGraduacionAdd);
        etFechaAdd = findViewById(R.id.etFechaAdd);

        btAddAdd = findViewById(R.id.btAddAdd);
        try {
            bundle = getIntent().getExtras();
            vinos = bundle.getParcelableArrayList("vinos");
            System.out.println("!null bundle");
        } catch (NullPointerException npe) {
            vinos = new ArrayList<>();
            System.out.println("bundle");
        }


        btAddAdd.setOnClickListener(view -> {
            if (emptyFields()){
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_LONG).show();
            } else if(!camposBuenos()){
                Toast.makeText(this, "Rellene los campos correctamente", Toast.LENGTH_LONG).show();
            } else {
                Vino vino = new Vino(Long.parseLong(etIdAdd.getText().toString())
                        , etNombreAdd.getText().toString()
                        , etBodegaAdd.getText().toString()
                        , etColorAdd.getText().toString()
                        , etOrigenAdd.getText().toString()
                        , Double.parseDouble(etGraduacionAdd.getText().toString())
                        , Integer.parseInt(etFechaAdd.getText().toString()));
                String csv = Csv.getCsv(vino);
                writeRead.writeFile(getFilesDir(), "Vinos.csv", csv);
                Toast.makeText(this, "Vino añadido", Toast.LENGTH_LONG).show();
                vaciaCampos();
            }
        });
    }

    private void vaciaCampos() {
        etIdAdd.setText("");
        etNombreAdd.setText("");
        etBodegaAdd.setText("");
        etColorAdd.setText("");
        etOrigenAdd.setText("");
        etGraduacionAdd.setText("");
        etFechaAdd.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        initialize();
    }


}
