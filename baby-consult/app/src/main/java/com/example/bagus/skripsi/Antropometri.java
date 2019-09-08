package com.example.bagus.skripsi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class Antropometri extends AppCompatActivity {
    Toolbar myToolbar;
    EditText age, weight, height;
    TextView status;
    String umur, berat, tinggi,jk;
    int u;
    RadioButton jkBtn;
    RadioGroup jkgrop;
    Button reset,simpan;
    ProgressDialog pd;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    SessionManager session;

    private static String LOG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reset = (Button)findViewById(R.id.reset);
        simpan = (Button)findViewById(R.id.go);
        jkgrop = (RadioGroup)findViewById(R.id.grop);
        age = (EditText)findViewById(R.id.age);
        weight = (EditText)findViewById(R.id.weight);
        height = (EditText)findViewById(R.id.height);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        Toolbar ToolBarAtas2 = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(ToolBarAtas2);
        ToolBarAtas2.setLogoDescription(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d(LOG,"oncreate");

            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    age.setText("");
                    weight.setText("");
                    height.setText("");

                    }
            });


            simpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int pilih = jkgrop.getCheckedRadioButtonId();
                    jkBtn = (RadioButton)findViewById(pilih);
                    umur = age.getText().toString();


                    try{
                        Integer.parseInt(age.getText().toString());
                        u = Integer.parseInt(umur);
                    }catch (NumberFormatException a){
                        a.printStackTrace();
                    }

                    berat = weight.getText().toString();
                    tinggi = height.getText().toString();
                    jk = jkBtn.getText().toString();


                    if(umur.matches("") || berat.matches("") || tinggi.matches("") || jk.isEmpty()){
                        Toast.makeText(MainActivity.this, "\n" +
                                "data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }else if (u<6){
                        Toast.makeText(MainActivity.this, "\n" +
                                "UMUR tidak boleh kurang dari 6 bulan", Toast.LENGTH_SHORT).show();

                    }
                    else if (u>72){
                        Toast.makeText(MainActivity.this, "\n" +
                                "UMUR tidak boleh lebih dari 72 bulan", Toast.LENGTH_SHORT).show();

                    }
                    else {

                        Intent intent = new Intent(MainActivity.this, Indikasi.class);
                        intent.putExtra("umur", umur);
                        intent.putExtra("berat_badan", berat);
                        intent.putExtra("tinggi_badan", tinggi);
                        intent.putExtra("jenis_kelamin", jk);
                        startActivity(intent);

                    }
                }
            });



        }

    private void logOut(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder
                .setMessage("Keluar dari aplikasi?")
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        session.logoutUser();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();
    }



    private void showDialog() {
        if (!pd.isShowing())
            pd.show();
    }

    private void hideDialog() {
        if (pd.isShowing())
            pd.dismiss();
    }




//    @Override
//    protected void onStart(){
//        super.onStart();
//        Log.d(LOG,"onStart");
//    }
//    @Override
//    protected void onRestart(){
//        super.onRestart();
//        Log.d(LOG,"onRestart");
//    }
//    @Override
//    protected void onResume(){
//        super.onResume();
//        Log.d(LOG,"onResume");
//    }
//    @Override
//    protected void onPause(){
//        super.onPause();
//        Log.d(LOG,"onPAUSE");
//    }
//    @Override
//    protected void onDestroy(){
//        super.onDestroy();
//        Log.d(LOG,"onDestroy");
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int ID = item.getItemId();
        switch (ID){
            case R.id.menu_1:
               logOut();
                break;
            case R.id.menu_2:
                Intent a = new Intent(MainActivity.this, Home.class);
                startActivity(a);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
