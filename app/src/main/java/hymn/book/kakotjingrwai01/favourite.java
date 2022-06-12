package hymn.book.kakotjingrwai01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import hymn.book.kakotjingrwai01.databinding.ActivityFavouriteBinding;

public class favourite extends AppCompatActivity {
    private static final String TAG = "favourite";

    ArrayList<String> mainFavList = new ArrayList<>();

    ActivityFavouriteBinding favouriteBinding;
    FirebaseFirestore hymnDB = FirebaseFirestore.getInstance();
    private FirebaseUser firebaseAuth;

    private static ArrayList<HymnModel> hymn_numberArrayList;
    private HymnAdapter adapter;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        favouriteBinding=ActivityFavouriteBinding.inflate(getLayoutInflater());
        setContentView(favouriteBinding.getRoot());

        favouriteBinding.FavBackBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        favouriteBinding.FavRecyclerView.setLayoutManager(linearLayoutManager);

        favouriteBinding.FavRecyclerView.setHasFixedSize(true);
        favouriteBinding.FavRecyclerView.setLayoutManager(new LinearLayoutManager(favourite.this));
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();


      hymnDB.collection("USERS")
                .addSnapshotListener((value, error) -> {
            hymn_numberArrayList.clear();
            for (DocumentSnapshot snapshot: value) {
                hymn_numberArrayList.add(new HymnModel(
                        snapshot.getString("id"),
                        snapshot.getString("title"),
                        snapshot.getString("author"),
                        snapshot.getString("lyric")));
            }
            adapter = new HymnAdapter(hymn_numberArrayList,getApplicationContext());
            favouriteBinding.FavRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });

        readFav();

    }

    private void readFav() {

        hymn_numberArrayList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("FAVOURITE").child(firebaseAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hymn_numberArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HymnModel hymnModel = snapshot.getValue(HymnModel.class);

                    assert hymnModel != null;
                    assert firebaseAuth != null;
                    if (hymnModel.getId().equals(firebaseAuth.getUid())) {
                        hymn_numberArrayList.add(hymnModel);
                    }
                }
                adapter = new HymnAdapter( hymn_numberArrayList,getApplicationContext());
                favouriteBinding.FavRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}