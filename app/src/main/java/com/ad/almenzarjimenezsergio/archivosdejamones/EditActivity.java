package com.ad.almenzarjimenezsergio.archivosdejamones;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.ad.almenzarjimenezsergio.archivosdejamones.data.Vino;
import com.ad.almenzarjimenezsergio.archivosdejamones.data.util.Csv;
import com.ad.almenzarjimenezsergio.archivosdejamones.data.util.WriteRead;
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
        String file = writeRead.readFile(getFilesDir(), "Vinos.csv");
        writeRead.deleteRow(getFilesDir(), Long.toString(id), "Vinos.csv");
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
         etIdEdit.setEnabled(false);


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
            AlertDialog.Builder builder  = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure?")
                    .setMessage("Are you sure do you want to delete this character?")
                    .setNegativeButton(android.R.string.no, (dialog, which) -> {})
                    .setPositiveButton( android.R.string.ok, (dialog, which) -> {
                        borraVino(vino.getId());
                        Toast.makeText(this, "Vino borrado", Toast.LENGTH_SHORT).show();
                        finish();
                    });
            builder.create().show();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);
        initialize();
    }


}
