package com.ad.almenzarjimenezsergio.archivosdejamones;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ad.almenzarjimenezsergio.archivosdejamones.data.Vino;
import com.ad.almenzarjimenezsergio.archivosdejamones.data.util.WriteRead;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getName() + "xyzyx";
    Button btAdd, btEdit;
    EditText etId;
    TextView tvWines;
    String fileName;

    ArrayList<Vino> vinos;
    WriteRead writeRead = new WriteRead();

    private boolean existeId(long id){
        boolean exists = false;

        for (Vino vino : vinos){
            if (vino.getId() == id){
                exists = true;
            }
        }

        return exists;

    }
    public Vino getVinoById(long id) {
        Vino vino = null;

        for (int i = 0; i < vinos.size(); i++){
            if (vinos.get(i).getId() == id){
                 vino = vinos.get(i);
                 return vino;
            }
        }

        return vino;
    }

    public void initialize() {


        btAdd = findViewById(R.id.btAdd);
        btEdit = findViewById(R.id.btEdit);
        etId = findViewById(R.id.etId);
        tvWines = findViewById(R.id.tvWines);

        btAdd.setOnClickListener(this);
        btEdit.setOnClickListener(this);

        //TODO SETTER DE LOS TEXTOS A PARTIR DEL ARCHIVO ¿done?
        fileName = "Vinos.csv";

        try {
            setTvVinos(writeRead.readFile(getFilesDir(), fileName));
            vinos = putArrayList();
            Log.v("jamaica", vinos.toString());
        } catch (NullPointerException npe) {
            //writeRead.writeFile(getFilesDir(), fileName, "");
            vinos = new ArrayList<>();
            System.out.println("vinos not setted");
        }



    }

    private ArrayList<Vino> putArrayList() {
        String csvVinos = writeRead.readFile(getFilesDir(), fileName);
        String[] linea = csvVinos.split("\n");
        Vino vino;
        ArrayList<Vino> vinos = new ArrayList<>();
        for (String s : linea) {
            String[] datosVino = s.split(";");
            vino = new Vino(Long.parseLong(datosVino[0].trim()), datosVino[1], datosVino[2], datosVino[3], datosVino[4], Double.parseDouble(datosVino[5].trim()), Integer.parseInt(datosVino[6].trim()));
            vinos.add(vino);
        }
        return vinos;
    }


    private void setTvVinos(String texto) {
       String strVinos = "";
       String[] linea = texto.split("\n");

        for (String s : linea) {
            String[] datosVino = s.split(";");

            strVinos += "Id:" + datosVino[0]
                    + "\tNombre: " + datosVino[1]
                    + "\tBodega: " + datosVino[2]
                    + "\tColor: " + datosVino[3]
                    + "\tOrigen: " + datosVino[4]
                    + "\tGraduación: " + datosVino[5]
                    + "\tFecha: " + datosVino[6]
                    + "\n\n";
        }

       tvWines.setText(strVinos);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(btAdd)){
            Intent intentAdd = new Intent(this, AddActivity.class);
            Bundle bundleAdd = new Bundle();
            bundleAdd.putParcelableArrayList("vinos", vinos);
            intentAdd.putExtras(bundleAdd);
            startActivity(intentAdd);
        }

        if (view.equals(btEdit)){
            String str = etId.getText().toString();
            try{
                long id = Long.parseLong(str);
                if (!existeId(id)){
                    etId.setText("");
                    Toast.makeText(this, "Esta id no existe", Toast.LENGTH_LONG).show();
                } else {
                    Vino vino = getVinoById(id);
                    if (vino != null){
                       // System.out.println("vino no nulo");
                        Intent intentEdit = new Intent(this, EditActivity.class);
                        Bundle bundleEdit = new Bundle();
                        bundleEdit.putParcelable("vino", vino);
                        bundleEdit.putParcelableArrayList("vinos", vinos);
                       // System.out.println(bundleEdit.getParcelable("vino").toString());
                        intentEdit.putExtras(bundleEdit);
                        //System.out.println(intentEdit.getExtras().getParcelable("vino").toString());
                        startActivity(intentEdit);

                    }
                }
            } catch (NumberFormatException nfe) {
                Toast.makeText(this, "Please, Insert an Id", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        etId.setText("");
        try {
            setTvVinos(writeRead.readFile(getFilesDir(), fileName));
            vinos = putArrayList();
            Log.v("jamaica", vinos.toString());
        } catch (NullPointerException npe) {
            //writeRead.writeFile(getFilesDir(), fileName, "");
            vinos = new ArrayList<>();
            System.out.println("vinos not setted");
        }
    }

    /*Vino v =  new Vino("Betis", "manque pierda", "Verde", "Benito Villamarín", 12.5, 2015);
        String csv = Csv.getCsv(v);
        Log.v(TAG, csv);

        Vino v2 = Csv.getVino(csv);
        Log.v(TAG, v2.toString());*/

}