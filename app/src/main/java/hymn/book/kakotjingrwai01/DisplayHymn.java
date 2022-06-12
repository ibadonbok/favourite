package hymn.book.kakotjingrwai01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import hymn.book.kakotjingrwai01.databinding.ActivityDisplayHymnBinding;

public class DisplayHymn extends AppCompatActivity {


    private FirebaseUser firebaseAuth;
    private FirebaseFirestore userdb;
    private ActivityDisplayHymnBinding displayHymnBinding;
    private ArrayList<HymnModel> arrayList = new ArrayList<>();

    Intent intent;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayHymnBinding = ActivityDisplayHymnBinding.inflate(getLayoutInflater());
        setContentView(displayHymnBinding.getRoot());

        displayHymnBinding.HBackBtn.setOnClickListener(V->{
            onBackPressed();
            finish();
        });

      displayHymnBinding.Hnumber.setText(getIntent().getStringExtra("ID"));
      displayHymnBinding.Htitle.setText(getIntent().getStringExtra("TITLE"));
      displayHymnBinding.Hauthor.setText(getIntent().getStringExtra("AUTHOR"));
     String lyrics=getIntent().getStringExtra("LYRIC");
      lyrics=lyrics.replace("_b","\n");
      displayHymnBinding.Hlyric.setText(lyrics);

        displayHymnBinding.hymnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Now share text only function will be called
                // here we  will be passing the text to share
                shareTextOnly(displayHymnBinding.Htitle.getText().toString());
                // shareTextOnly(displayHymnBinding.Hnumber.getText().toString(),displayHymnBinding.Htitle.getText().toString(),displayHymnBinding.Hlyric.getText().toString(),displayHymnBinding.Hauthor.getText().toString());
            }


            private void shareTextOnly(String title) {

                // String sharenumber = number;
                String sharetitle = title;
                // String sharelyric = lyric;
                // String shareauthor = author;

                // The value which we will sending through data via
                // other applications is defined
                // via the Intent.ACTION_SEND
                Intent intentt = new Intent(Intent.ACTION_SEND);

                // setting type of data shared as text
                intentt.setType("text/plain");
                intentt.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");

                // Adding the text to share using putExtra
                //intentt.putExtra(Intent.EXTRA_TEXT, sharenumber);
                final Intent intent = intentt.putExtra(Intent.EXTRA_TEXT, sharetitle);
                // intentt.putExtra(Intent.EXTRA_TEXT, sharelyric);
                // intentt.putExtra(Intent.EXTRA_TEXT, shareauthor);
                startActivity(Intent.createChooser(intent, "Share Via"));
            }
        });
    }
}