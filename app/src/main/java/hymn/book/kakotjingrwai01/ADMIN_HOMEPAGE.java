package hymn.book.kakotjingrwai01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import hymn.book.kakotjingrwai01.databinding.ActivityAdminHomepageBinding;
import hymn.book.kakotjingrwai01.databinding.ActivityUserHomeBinding;

public class ADMIN_HOMEPAGE extends AppCompatActivity {

    ActivityAdminHomepageBinding adminHomepageBinding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adminHomepageBinding = ActivityAdminHomepageBinding.inflate(getLayoutInflater());
        setContentView(adminHomepageBinding.getRoot());

        adminHomepageBinding.add.setOnClickListener(view -> {
            Intent intent = new Intent(ADMIN_HOMEPAGE.this, addHymn.class);
            startActivity(intent);
        });

        auth = FirebaseAuth.getInstance();
        adminHomepageBinding.adminLogout.setOnClickListener(V -> {
            auth.signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        });

        adminHomepageBinding.ViewHymn.setOnClickListener(view -> {
            Intent intent = new Intent(ADMIN_HOMEPAGE.this, user_home.class);
            startActivity(intent);
        });

        auth = FirebaseAuth.getInstance();
        adminHomepageBinding.ViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getCurrentUser().getUid();
                //UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
// See the UserRecord reference doc for the contents of userRecord.
                System.out.println("Successfully fetched user data: " + auth.getUid());

            }
        });
    }
}