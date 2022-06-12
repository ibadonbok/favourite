package hymn.book.kakotjingrwai01;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import hymn.book.kakotjingrwai01.databinding.ActivityChorus1Binding;

public class chorus1 extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = "chorus1";


    ArrayList<String> mainFavList = new ArrayList<>();
    ActivityChorus1Binding chorus1Binding;
    DocumentReference favouriteref;
    FirebaseFirestore chorusDB = FirebaseFirestore.getInstance();

    // creating variables for
    // our ui components.
    private RecyclerView chorusRV;
    // variable for our adapter
    // class and array list
    private static ArrayList<chorus1Model> chorus1ModelArrayList;
    private chorus1Adapter chorus1Adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chorus1);


        chorus1Binding = ActivityChorus1Binding.inflate(getLayoutInflater());
        setContentView(chorus1Binding.getRoot());
        loadFavList(chorusDB);

        // initializing our variables.
        chorusRV = findViewById(R.id.chorusRecyclerView);

        // calling method to
        // build recycler view.
        buildRecyclerView();

        chorus1Binding.chorusBackBtn1.setOnClickListener(V -> {
            onBackPressed();
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        chorus1Binding.chorusRecyclerView.setLayoutManager(linearLayoutManager);

        chorus1ModelArrayList = new ArrayList<>();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<chorus1Model> chorus1ModelArrayList1=new ArrayList<>();
                chorusDB.collection("Chorus").addSnapshotListener((value, error) -> {
                    chorus1ModelArrayList.clear();
                    for (DocumentSnapshot snapshot : value) {

                        chorus1ModelArrayList.add(new chorus1Model(
                                snapshot.getString("id"),
                                snapshot.getString("lyric"), "0"));

                    }
                    for (int i = 0; i < chorus1ModelArrayList.size(); i++) {
                        for(int a =0; a<mainFavList.size();a++){

                            if (mainFavList.get(a).contains(chorus1ModelArrayList.get(a).getId())) {
                                chorus1ModelArrayList1.add(new chorus1Model(
                                        chorus1ModelArrayList.get(a).getId(),
                                        chorus1ModelArrayList.get(a).getLyric(), "1"));
                            }else{
                                chorus1ModelArrayList1.add(new chorus1Model(
                                        chorus1ModelArrayList.get(a).getId(),
                                        chorus1ModelArrayList.get(a).getLyric(), "0"));
                            }

                        }

                    }
                    chorus1ModelArrayList.clear();
                    chorus1ModelArrayList.addAll(chorus1ModelArrayList1);
                    chorus1Adapter = new chorus1Adapter(chorus1ModelArrayList, getApplicationContext());
                    chorus1Binding.chorusRecyclerView.setAdapter(chorus1Adapter);
                    chorus1Adapter.notifyDataSetChanged();
                });
            }
        }, 2000);


        chorus1Binding.chorusSearch.setOnQueryTextListener(this);
    }


    private void buildRecyclerView() {
        // below line we are creating a new array list
        chorus1ModelArrayList = new ArrayList<>();
        // below line is to add data to our array list.
        // initializing our adapter class.
        chorus1Adapter = new chorus1Adapter(chorus1ModelArrayList, chorus1.this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        chorusRV.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        chorusRV.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        chorusRV.setAdapter(chorus1Adapter);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String input_query = newText.toLowerCase();
        ArrayList<chorus1Model> searchList
                = new ArrayList<>();
        for (chorus1Model item : chorus1ModelArrayList) {
            if (item.getId().toLowerCase().contains(input_query) || item.getLyric().toLowerCase().contains(input_query)) {
                searchList.add(item);
            }
        }
        chorus1Adapter.updatelist(searchList);
        chorus1Adapter.notifyDataSetChanged();
        return true;
    }

    public void loadFavList(FirebaseFirestore userdb) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        userdb.collection("USERS").document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            String Favlist = task.getResult().get("FAVCHORUS").toString();
                            String[] arrayFav = Favlist.split(",");
                            List<String> fixLength = Arrays.asList(arrayFav);
                            mainFavList = new ArrayList<String>(fixLength);
                            Log.i("Favlist", Favlist.toString());

                        }
                    }


                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(chorus1.this, "Access to Data failed" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
