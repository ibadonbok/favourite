package hymn.book.kakotjingrwai01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import hymn.book.kakotjingrwai01.databinding.ActivityDisplayChorusBinding;

public class DisplayChorus extends AppCompatActivity {

    private ActivityDisplayChorusBinding displayChorus1Binding;
    private ArrayList<chorus1Model> chorus1ModelArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        displayChorus1Binding=ActivityDisplayChorusBinding.inflate(getLayoutInflater());
        setContentView(displayChorus1Binding.getRoot());

        displayChorus1Binding.chorus1BackBtn.setOnClickListener(V->{
            onBackPressed();
            finish();
        });

        displayChorus1Binding.chorus1Number.setText(getIntent().getStringExtra("ID"));

        String lyric=getIntent().getStringExtra("LYRIC");
        lyric=lyric.replace("_b","\n");
        displayChorus1Binding.chorus1lyric.setText(lyric);

        displayChorus1Binding.chorusShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Now share text only function will be called
                // here we  will be passing the text to share
                //shareTextOnly(displayChorus1Binding.chorus1lyric.getText().toString());
                 shareTextOnly(displayChorus1Binding.chorus1Number.getText().toString(),displayChorus1Binding.chorus1lyric.getText().toString());
            }
        });

    }

    private void shareTextOnly( String number,String lyric){

         String sharenumber = number;
         String sharelyric = lyric;


        // The value which we will sending through data via
        // other applications is defined
        // via the Intent.ACTION_SEND
        Intent intentt = new Intent(Intent.ACTION_SEND);

        // setting type of data shared as text
        intentt.setType("text/plain");
        intentt.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        intentt.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");

        // Adding the text to share using putExtra
        intentt.putExtra(Intent.EXTRA_TEXT,sharenumber);
        intentt.putExtra(Intent.EXTRA_TEXT, sharelyric);

        startActivity(Intent.createChooser(intentt, "Share Via"));
    }
}