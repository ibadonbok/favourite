package hymn.book.kakotjingrwai01;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hymn.book.kakotjingrwai01.databinding.ActivitySignupBinding;
//
//import hymn.book.kakotjingrwai.database.DatabaseHelper;
//import hymn.book.kakotjingrwai.databinding.ActivitySignupBinding;

public class Signup extends AppCompatActivity {

    private ActivitySignupBinding signupBinding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore userDb;

    private static String GENDER = null;
    private static ArrayList<Integer> FieldsLength = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signupBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(signupBinding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        signupBinding.signupbutton.setEnabled(false);


        signupBinding.signupbutton.setOnClickListener(v -> {
            if (signupBinding.userName.getEditText().getText().length() >= 3 | !signupBinding.userName.getEditText().getText().toString().isEmpty()) {
                if (!signupBinding.email.getEditText().getText().toString().isEmpty() | signupBinding.email.getEditText().getText().toString().contains("@")) {
                    if (!signupBinding.age.getEditText().getText().toString().isEmpty() | signupBinding.age.getEditText().getText().toString().length() != 0) {
                        if (getGender()) {
                            if (password(signupBinding.password1.getEditText().getText().toString(), signupBinding.password2.getEditText().getText().toString())) {//        Button Click Action
                                signupBinding.signupbutton.setEnabled(true);
                                signup(signupBinding.userName.getEditText().getText().toString(),
                                        signupBinding.email.getEditText().getText().toString(),
                                        signupBinding.password1.getEditText().getText().toString(),
                                        signupBinding.age.getEditText().getText().toString(),
                                        GENDER);

                            } else {
                                Toast.makeText(this, "Password MissMatch", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Select a Gender!", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "Age can't be empty!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Enter A valid email ID!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "name can't be empty!", Toast.LENGTH_SHORT).show();
            }
        });


        signupBinding.userName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (FieldsLength.size() >= 5) {
                    signupBinding.signupbutton.setEnabled(true);
                } else {
                    FieldsLength.add(1);
                    signupBinding.signupbutton.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signupBinding.email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (FieldsLength.size() >= 5) {
                    signupBinding.signupbutton.setEnabled(true);
                } else {
                    FieldsLength.add(2);
                    signupBinding.signupbutton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signupBinding.age.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (FieldsLength.size() >= 5) {
                    signupBinding.signupbutton.setEnabled(true);
                } else {
                    signupBinding.signupbutton.setEnabled(false);
                    FieldsLength.add(3);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signupBinding.password1.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (FieldsLength.size() >= 5) {
                    signupBinding.signupbutton.setEnabled(true);

                } else {
                    FieldsLength.add(4);
                    signupBinding.signupbutton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signupBinding.password2.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (FieldsLength.size() >= 5) {
                    signupBinding.signupbutton.setEnabled(true);
                } else {
                    FieldsLength.add(5);
                    signupBinding.signupbutton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean getGender() {
        if (signupBinding.male.isChecked()) {
            GENDER = "Male";
            return true;

        }
        if (signupBinding.female.isChecked()) {
            GENDER = "Female";
            return true;
        }
        if (signupBinding.others.isChecked()) {
            GENDER = "Other";
            return true;
        } else {
            GENDER = null;
            return false;
        }

    }

    private boolean password(String text, String text1) {
        return text.equals(text1);
    }

    private void signup(String username, String email, String password, String age, String gender) {
        signupBinding.signupbutton.setEnabled(false);
        signupBinding.progressBar.setVisibility(View.VISIBLE);

//        for registration
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, response -> {
                   if(response.isSuccessful()) {
                       SavedUserDetails(username,age,gender);

                   }else{
                       Toast.makeText(this, response.getException().toString(), Toast.LENGTH_SHORT).show();
                   }
                    signupBinding.progressBar.setVisibility(View.INVISIBLE);
                    signupBinding.signupbutton.setEnabled(true);
                });
    }

    private void SavedUserDetails(String username, String age, String gender) {
        Map<String,String> userDetails = new HashMap<>();
        userDetails.put("NAME",username);
        userDetails.put("AGE",age);
        userDetails.put("GENDER",gender);
        userDetails.put("PROFILE_URI","null");
        userDetails.put("USERTYPE","NormalUser");

        userDb = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        userDb.collection("USERS")
                .document(firebaseAuth.getCurrentUser().getUid())
                .set(userDetails)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Signup.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Signup.this,Login.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Signup.this, "Internal Error "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}

