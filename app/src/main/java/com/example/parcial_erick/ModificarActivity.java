package com.example.parcial_erick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ModificarActivity extends AppCompatActivity implements View.OnClickListener {

    TextView titulo;

    DBControlador controlador;

    EditText edCedula, edNombre, edSalario;
    Spinner spEstrato, spNivel;

    Button btGuardar, btCancelar;

    int estrato, nivelEducativo, indice, cedula;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titulo = findViewById(R.id.tvTitulo);
        edCedula = findViewById(R.id.edCedula);
        edNombre = findViewById(R.id.edNombre);
        edSalario = findViewById(R.id.edSalario);
        spEstrato = findViewById(R.id.spEstrato);
        spNivel = findViewById(R.id.spNivelEducativo);
        btGuardar = findViewById(R.id.btGuardar);
        btCancelar = findViewById(R.id.btCancelar);

        titulo.setText("Modificar");
        controlador = new DBControlador(getApplicationContext());

        Intent i = getIntent();
        indice = i.getIntExtra("indice", 0);

        ArrayList<Persona> list = controlador.optenerRegistros();

        Persona persona = list.get(indice);
        cedula = persona.getCedula();

        edCedula.setText(String.valueOf(cedula));
        edCedula.setEnabled(false);
        edNombre.setText(persona.getNombre());
        edSalario.setText(String.valueOf(persona.getSalario()));

        String g [] = { "1","2","3","4","5","6"};
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,g);
        spEstrato.setAdapter(aa);
        spEstrato.setSelection(persona.getEstrato());
        spEstrato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estrato = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String gg  [] = { "Bachillerato","Pregrado","Maestria","Doctorado"};
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,gg);
        spNivel.setAdapter(a);
        spNivel.setSelection(persona.getNivel_educativo());
        spNivel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nivelEducativo = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btGuardar.setOnClickListener(this);
        btCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btGuardar:
                try {
                    int salario = edSalario.getText().toString().isEmpty() ? 0 : Integer.parseInt(edSalario.getText().toString());
                    Persona persona = new Persona(cedula,edNombre.getText().toString()
                            , estrato, salario, nivelEducativo);
                    int retorno = controlador.actualizarRegistro(persona);
                    if (retorno == 1) {
                        Toast.makeText(getApplicationContext(), "actualizacion exitosa", Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "fallo en la actualizacion", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException nuEx) {
                    Toast.makeText(getApplicationContext(), "numero muy grande", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btCancelar:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
        }
    }
}
