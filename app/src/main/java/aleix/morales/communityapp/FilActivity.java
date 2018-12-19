package aleix.morales.communityapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FilActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<Resposta> respostes;

    private RecyclerView recycler_view;
    private Adapter adapter;

    private EditText novaentrada;


    //Calendario para obtener fecha
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio =c.get(Calendar.YEAR);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fil);
        novaentrada = findViewById(R.id.edittext_view);


        respostes = new ArrayList<>();
        // respostes.add(new Resposta("No", "Username6", "19/11/2018"));
        // respostes.add(new Resposta("No, però Dilluns si.","Username7","19/11/2018"));
        // respostes.add(new Resposta("No tinc ni idea.","Username8","20/11/2018"));

        adapter = new Adapter();
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(adapter);

        /*
        db.collection("fils").document("filtest").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                setTitle(documentSnapshot.getString("titol"));
            }
        });
        */
    }


    @Override
    protected void onStart() {
        super.onStart();
        db.collection("fils").document("filtest").collection("respostes")
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        respostes.clear();
                        for (DocumentSnapshot doc : documentSnapshots) {
                            Resposta resp = doc.toObject(Resposta.class);
                            respostes.add(resp);
                        }
                        adapter.notifyDataSetChanged();

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item:
                Intent intent = new Intent(this, UserActivity.class);
                startActivity(intent);

                break;
        }
        return true;
    }

    public void btn_add(View view) {

        // Obtenir el text de la caixa de text
        String nova = novaentrada.getText().toString();
        //Shared Preferences
        SharedPreferences prefs = getSharedPreferences("config", Context.MODE_PRIVATE);
        String username = prefs.getString("username", null);
        if (username == null) {
            // TODO: Demanar nom d'usuari.
        }
        // Afegir el nou ítem a la llista
        Resposta resp = new Resposta(nova, username , new Date());
        db.collection("fils").document("filtest").collection("respostes").add(resp); // .addOnSuccessListener
        // respostes.add(resp);
        // Notificar a l'adaptador dels canvis en el model
        // adapter.notifyItemInserted(respostes.size()-1);
        // Buidem la caixa de text perquè sigui més facil afegir ítems nous
        novaentrada.getText().clear();


    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_view, resposta_view, data_view;

        public ViewHolder(View itemView) {
            super(itemView);
            user_view = itemView.findViewById(R.id.user_view);
            resposta_view = itemView.findViewById(R.id.resposta_view);
            data_view = itemView.findViewById(R.id.data_view);
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.list_item_layout, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Resposta resp = respostes.get(position);
            holder.resposta_view.setText(resp.getText_resposta());
            holder.user_view.setText(resp.getUser());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            holder.data_view.setText(formatter.format(resp.getData()));
        }

        @Override
        public int getItemCount() {
            return respostes.size();
        }
    }
}
