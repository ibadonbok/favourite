package hymn.book.kakotjingrwai01;

import android.content.ClipData;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import hymn.book.kakotjingrwai01.databinding.ActivityHymnNumberBinding;

public class hymn_number extends AppCompatActivity {

    ActivityHymnNumberBinding hymnNumberBinding;
    private FirebaseFirestore hymnDb = FirebaseFirestore.getInstance();

    private HymnAdapter adapter;
    private static ArrayList<HymnModel> hymn_numberArrayList = new ArrayList<>();

    DocumentReference reference;
    DatabaseReference databaseReference, favREf, favListRef;
    Boolean Favchecker = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hymnNumberBinding = ActivityHymnNumberBinding.inflate(getLayoutInflater());
        setContentView(hymnNumberBinding.getRoot());


        hymnNumberBinding.hymnBackBtn.setOnClickListener(V -> {
            onBackPressed();
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        hymnNumberBinding.hymnRecyclerView.setLayoutManager(linearLayoutManager);


        hymnDb = FirebaseFirestore.getInstance();


        hymnDb.collection("Hymn_Number").addSnapshotListener((value, error) -> {
            hymn_numberArrayList.clear();
            for (DocumentSnapshot snapshot : value) {
                hymn_numberArrayList.add(new HymnModel(
                        snapshot.getString("id"),
                        snapshot.getString("title"),
                        snapshot.getString("author"),
                        snapshot.getString("lyric")));
            }
            adapter = new HymnAdapter(hymn_numberArrayList, getApplicationContext());
            hymnNumberBinding.hymnRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });

    }
}