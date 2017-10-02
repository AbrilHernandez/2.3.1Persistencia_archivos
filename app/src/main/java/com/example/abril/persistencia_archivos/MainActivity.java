package com.example.abril.persistencia_archivos;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText texto;
    Button guardar, abrir,gExterno,aExterno;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto=(EditText)findViewById(R.id.editText);
        //guardar=(Button)findViewById(R.id.button);
        //abrir=(Button)findViewById(R.id.button2);
        gExterno=(Button)findViewById(R.id.button3);
        aExterno=(Button)findViewById(R.id.button4);

       if (shouldAskPermissions()) {
          askPermissions();
        }


        gExterno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)){

                          File path = Environment.getExternalStorageDirectory(
                                   );
                        //File path = getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath());
                            //File file = new File(path, "archivoprueba.txt");
                        File file = new File(path.getAbsolutePath(), "ficherosd.txt");

                            try {
                                // Make sure the Pictures directory exists.
                              path.mkdirs();

                            OutputStreamWriter archivo = new OutputStreamWriter(new FileOutputStream(file));

                            archivo.write(texto.getText().toString());
                            archivo.close();

                            texto.setText("");
                            Toast.makeText(MainActivity.this,"Se guardo correctamente",Toast.LENGTH_LONG).show();
                                Toast.makeText(MainActivity.this, path.toString(),Toast.LENGTH_LONG).show();

                        }
                        catch (Exception e)
                        {
                            AlertDialog.Builder alerta= new AlertDialog.Builder(MainActivity.this);
                            alerta.setTitle("Error al escribir archivo en tarjeta SD").setMessage(e.getMessage()).show();

                        }

                    }else{
                        AlertDialog.Builder alerta= new AlertDialog.Builder(MainActivity.this);
                        alerta.setTitle("Error").setMessage("Su tarjeta SD es de SOLO LECTURA").show();

                    }
                }else{
                    AlertDialog.Builder alerta= new AlertDialog.Builder(MainActivity.this);
                    alerta.setTitle("Error").setMessage("No hay Tarjeta SD En su sistema Android, Inserte una e intentelo de nuevo").show();

                }
            }
        });

        aExterno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

                    try
                    {

                        File path = Environment.getExternalStorageDirectory(
                        );
                       // File file = new File(path, "archivoprueba.txt");
                        File file = new File(path.getAbsolutePath(), "ficherosd.txt");

                        BufferedReader archivo= new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        texto.setText(archivo.readLine());
                        archivo.close();


                    }
                    catch (Exception e)
                    {
                        AlertDialog.Builder alerta= new AlertDialog.Builder(MainActivity.this);
                        alerta.setTitle("Error al Leer archivo de la tarjeta SD").setMessage(e.getMessage()).show();

                    }
                }else{
                    AlertDialog.Builder alerta= new AlertDialog.Builder(MainActivity.this);
                    alerta.setTitle("Error").setMessage("No hay Tarjeta SD En su sistema Android, Inserte una e intentelo de nuevo").show();

                }
            }
        });


    }

    public void guardarEnArchivoInterno(View v){
        try {
            //tarea investigar la clase Environment  --Manejo de targeta SD
            OutputStreamWriter archivo= new OutputStreamWriter(openFileOutput("archivo.txt",MODE_PRIVATE));

            archivo.write(texto.getText().toString());
            archivo.close();

            texto.setText("");
            Toast.makeText(this,"SE ALMACENO CORRECTAMENTE",Toast.LENGTH_LONG).show();

        }
        catch (Exception e){
            AlertDialog.Builder alert= new AlertDialog.Builder(this);
            alert.setTitle("Error").setMessage(e.getMessage()).show();
        }}
    public void abrirDesdeArchivoInterno(View v){

        try
        {
            BufferedReader archivo= new BufferedReader(new InputStreamReader(openFileInput("archivo.txt")));
            texto.setText(archivo.readLine());
        }
        catch (Exception e){
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("ERROR").setMessage(e.getMessage());
            alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        }
    }

}


