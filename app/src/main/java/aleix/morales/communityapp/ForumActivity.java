package aleix.morales.communityapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends AppCompatActivity {
    private Adapter adapter;
    private RecyclerView LlistaFils;
    private List<Fil> fils;

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        db.collection("fils").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                fils.clear();
                for (DocumentSnapshot doc : documentSnapshots) {
                    Fil fil = doc.toObject(Fil.class);
                    fil.setId(doc.getId());
                    fils.add(fil);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void onClickFil(int pos) {
        Fil fil = fils.get(pos);
        fil.getId();
    }

    public void onAddClick(View view) {
        //afegir fil
        Intent intent = new Intent(this, NewFilActivity.class);
        startActivity(intent);

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








