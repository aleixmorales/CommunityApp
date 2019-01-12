package aleix.morales.communityapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewFilActivity extends AppCompatActivity {
    private EditText edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_fil);

        // Paso 2: Obtenemos los datos de la llamada.
        // O sea, inicializamos la caja de texto con el texto a editar.
        Intent intent = getIntent();
        String texto_a_editar = intent.getStringExtra("text");
        edit_text = findViewById(R.id.editText_titol);
        edit_text.setText(texto_a_editar);

    }

    public void onSave(View view) {
        // Paso 3: Devolvemos como resultado el texto editado.
        String texto_editado = edit_text.getText().toString();
        Intent data = new Intent();
        data.putExtra("text", texto_editado);
        setResult(RESULT_OK, data);
        finish();
    }



}
