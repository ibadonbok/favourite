package hymn.book.kakotjingrwai01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;

import hymn.book.kakotjingrwai01.databinding.ActivityAddHymnBinding;

public class addHymn extends AppCompatActivity {
    ActivityAddHymnBinding addHymnBinding;
    private FirebaseFirestore userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addHymnBinding=ActivityAddHymnBinding.inflate(getLayoutInflater());
        setContentView(addHymnBinding.getRoot());

        addHymnBinding.addBackButton.setOnClickListener(V -> {
            onBackPressed();
        });

    }
}