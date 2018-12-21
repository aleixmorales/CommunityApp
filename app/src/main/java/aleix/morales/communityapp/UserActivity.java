package aleix.morales.communityapp;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserActivity extends AppCompatActivity {

    private String user;



    private void saveUser() {
        try {
            FileOutputStream outputStream = openFileOutput("users.txt", MODE_PRIVATE);
            outputStream.write(user.getBytes());

        } catch (FileNotFoundException e) {
            // Aquest codi s'executa quan hi ha hagut un error (excepció) de tipus FileNotFound...
            Toast.makeText(this, "No he pogut obrir el fitxer", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // Posem IOException _després_ de FileNotFound perquè IOException engloba tots
            // els tipus d'errors de Fil/Sortida (incloent FileNotFound)
            Toast.makeText(this, "No he pogut escriure al fitxer", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUser2(){
        SharedPreferences prefs = getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", user);
        editor.commit();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user2);



    }

    public void onClickOK(View view) {

        EditText entrada = findViewById(R.id.edit_name);
        user = entrada.getText().toString();
        //saveUser();
        saveUser2();
        //Ja tenim el user capturat.
        finish();
        //Tanca la activitat i torna a la main.
    }
}
