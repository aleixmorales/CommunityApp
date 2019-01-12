package aleix.morales.communityapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForumActivity extends AppCompatActivity {
    private Adapter adapter;
    private RecyclerView LlistaFils;
    private List<Fil> fils;
    private String title = "null";
    public static final int EDIT_TITLE = 0;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject1_activity);

        fils = new ArrayList<>();

        // fils.add(new Fil("Enginyeria Tèrmica", 12));

        adapter = new Adapter();
        LlistaFils = findViewById(R.id.LlistaFils);
        LlistaFils.setLayoutManager(new LinearLayoutManager(this));
        LlistaFils.setAdapter(adapter);
        ImageView postervire = findViewById(R.id.posterview);
        Glide.with(this)
                .load("file:///android_asset/gearview.png").into(postervire);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListenerRegistration registration = db.collection("fils").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                ForumActivity.this.fils.clear();
                for (DocumentSnapshot doc : documentSnapshots) {
                    Fil fil = doc.toObject(Fil.class);
                    fil.setId(doc.getId());
                    ForumActivity.this.fils.add(fil);
                }
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void onClickFil(int pos) {
        Fil fil = fils.get(pos);
        Intent intent = new Intent(this, FilActivity.class);
        intent.putExtra("filId", fil.getId());
        startActivity(intent);
    }

    public void onAddClick(View view) {
        //afegir fil
        // Paso 1: Llamamos a la otra actividad pasando un texto para editar
        Intent intent = new Intent(this, NewFilActivity.class);
        intent.putExtra("text", title);
        startActivityForResult(intent, EDIT_TITLE);


        //Resposta resp = new Resposta(nova, username , new Date());
       // db.collection("fils").document(filId).collection("respostes").add(resp); // .addOnSuccessListener

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Paso 4: Recogemos el resultado
        switch (requestCode) {
            case EDIT_TITLE:
                if (resultCode == RESULT_OK) {
                    // Actualizamos el model
                    title = data.getStringExtra("text");
                    Integer numlikes = 0;
                    // Refrescar els fils amb un de nou
                    Fil noufil = new Fil(title, numlikes);  //AQUESTA ES LA FUNCIONALITAT QUE FALLA!!
                    db.collection("fils").add(noufil); //
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void btnSumalike (View view){

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView likes_view, titol_view;

        public ViewHolder(View itemView) {
            super(itemView);
            likes_view = itemView.findViewById(R.id.likes_view);
            titol_view = itemView.findViewById(R.id.titol_view);
            titol_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    onClickFil(pos);
                }
            });
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.pastilla, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Fil resp = fils.get(position);
            holder.titol_view.setText(resp.getTitol());
            holder.likes_view.setText(Integer.toString(resp.getLikes()));
        }

        @Override
        public int getItemCount() {
            return fils.size();
        }
    }

}








