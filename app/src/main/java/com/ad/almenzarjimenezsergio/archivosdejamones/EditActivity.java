package com.ad.almenzarjimenezsergio.archivosdejamones;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ad.almenzarjimenezsergio.archivosdejamones.data.Vino;
import com.ad.almenzarjimenezsergio.archivosdejamones.data.util.Csv;
import com.ad.almenzarjimenezsergio.archivosdejamones.data.util.WriteRead;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvIdEdit, tvNombreEdit, tvBodegaEdit, tvColorEdit, tvOrigenEdit, tvGraduacionEdit, tvFechaEdit;
    EditText etIdEdit, etNombreEdit, etBodegaEdit, etColorEdit, etOrigenEdit, etGraduacionEdit, etFechaEdit;
    Button btEditEdit, btDeleteEdit;

    Bundle bundle;
    Vino vino;
    ArrayList<Vino> vinos;
    WriteRead writeRead = new WriteRead();

    private void borraVino(long id) {
        //String salida = "";
        String file = writeRead.readFile(getFilesDir(), "Vinos.csv");
        writeRead.deleteRow(getFilesDir(), Long.toString(id), "Vinos.csv");
        //writeRead.deleteFile(getFilesDir(), "Vinos.csv");
        /*
        String[] lineas = file.split("\n");

        for(String linea : lineas){
            String[] campos = linea.split(";");
            if (linea.length() != 0) {
                long fetchedId = Long.parseLong(campos[0]);
                if (fetchedId != id) {
                    salida += Csv.getCsv(campos)+"\n";
                }
            }
        }

         */

        //writeRead.writeFile(getFilesDir(), "Vinos.csv", salida);
    }


    private void initialize(){
        tvIdEdit = findViewById(R.id.tvIdEdit);
        tvNombreEdit = findViewById(R.id.tvNombreEdit);
        tvBodegaEdit = findViewById(R.id.tvBodegaEdit);
        tvColorEdit = findViewById(R.id.tvColorEdit);
        tvOrigenEdit = findViewById(R.id.tvOrigenEdit);
        tvGraduacionEdit = findViewById(R.id.tvGraduacionEdit);
        tvFechaEdit = findViewById(R.id.tvFechaEdit);

        etIdEdit = findViewById(R.id.etIdEdit);
        etNombreEdit = findViewById(R.id.etNombreEdit);
        etBodegaEdit = findViewById(R.id.etBodegaEdit);
        etColorEdit = findViewById(R.id.etColorEdit);
        etOrigenEdit = findViewById(R.id.etOrigenEdit);
        etGraduacionEdit = findViewById(R.id.etGraduacionEdit);
        etFechaEdit = findViewById(R.id.etFechaEdit);

        try {
            bundle = getIntent().getExtras();
            vino  = bundle.getParcelable("vino");
            vinos = bundle.getParcelableArrayList("vinos");
            setTexts(vino);
        } catch (NullPointerException npe) {
            System.out.println("ha petao");
        }



        btEditEdit = findViewById(R.id.btEditEdit);
        btDeleteEdit = findViewById(R.id.btDeleteEdit);

        btEditEdit.setOnClickListener(this);
        btDeleteEdit.setOnClickListener(this);


    }


    private void setTexts(Vino vino) {
        etIdEdit.setText(Long.toString(vino.getId()));
        etNombreEdit.setText(vino.getNombre());
        etBodegaEdit.setText(vino.getBodega());
        etColorEdit.setText(vino.getColor());
        etOrigenEdit.setText(vino.getOrigen());
        etGraduacionEdit.setText(Double.toString(vino.getGraduacion()));
        etFechaEdit.setText(Integer.toString(vino.getFecha()));
    }

    @Override
    public void onClick(View view) {
        if (view.equals(btEditEdit)){
            borraVino(vino.getId());
            String[] datosVino = { etIdEdit.getText().toString()
                    , etNombreEdit.getText().toString()
                    , etBodegaEdit.getText().toString()
                    , etColorEdit.getText().toString()
                    , etOrigenEdit.getText().toString()
                    , etGraduacionEdit.getText().toString()
                    , etFechaEdit.getText().toString() };

            writeRead.writeFile(getFilesDir(),"Vinos.csv",Csv.getCsv(datosVino));
            Toast.makeText(this, "Vino editado", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (view.equals(btDeleteEdit)){
            borraVino(vino.getId());
            Toast.makeText(this, "Vino borrado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);
        initialize();
    }


}
