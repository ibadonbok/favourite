package hymn.book.kakotjingrwai01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class chorus1Adapter extends RecyclerView.Adapter<chorus1Adapter.viewHolder> {
    // creating a variable for array list and context.
    private static ArrayList<chorus1Model> chorus1ModelArrayList;
    Context context;

    // creating a constructor for our variables.
    public chorus1Adapter(ArrayList<chorus1Model> chorus1ModelArrayList, Context context) {
        chorus1Adapter.chorus1ModelArrayList = chorus1ModelArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public chorus1Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chorus1_itemlist, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        // setting data to our views of recycler view.
        chorus1Model chorus1Model = chorus1ModelArrayList.get(position);

        holder.chorusid.setText(chorus1ModelArrayList.get(position).getId());

        if (!chorus1ModelArrayList.get(position).getRmvfav().contains("1")) {
            holder.removefav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
        } else {
            holder.removefav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_24));

        }

        holder.chorusHymncard.setOnClickListener(V -> {
            Intent intent = new Intent(context, DisplayChorus.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ID", chorus1ModelArrayList.get(position).getId());
            intent.putExtra("LYRIC", chorus1ModelArrayList.get(position).getLyric());
            intent.putExtra("Favourite", chorus1ModelArrayList.get(position).getRmvfav());
            context.startActivity(intent);
        });

        holder.removefav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chorus1ModelArrayList.get(position).getRmvfav().contains("1")) {
                    addToFavourite(chorus1ModelArrayList.get(position).getId(), "1", position);
                } else {
                    addToFavourite(chorus1ModelArrayList.get(position).getId(), "0", position);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return chorus1ModelArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views.
        private TextView chorusid;
        private MaterialCardView chorusHymncard;
        private ImageView removefav;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            chorusid = itemView.findViewById(R.id.chorusNumberitem);
            chorusHymncard = itemView.findViewById(R.id.chorusHymncard);
            removefav = itemView.findViewById(R.id.removeFav);

        }
    }

    public void updatelist(ArrayList<chorus1Model> newlist) {
        if (newlist.isEmpty()) {
            return;
        }

        chorus1ModelArrayList = new ArrayList<>();
        chorus1ModelArrayList.addAll(newlist);
        notifyDataSetChanged();


    }
    //To add Favourite

    public void addToFavourite(String Id, String isFav, int i) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore chorusDB = FirebaseFirestore.getInstance();
        long timestamp = System.currentTimeMillis();

        //setup data to add in firebase db of  current user for favourite hymn
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ID", "" + Id);
        hashMap.put("timestamp", "" + timestamp);

        //Save to dp
        ArrayList<chorus1Model> updateChorusItem = new ArrayList<>();
        ArrayList<USERmodel> userdata = new ArrayList<>();

        //fetching user details to update
        chorusDB.collection("USERS").document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            String name = task.getResult().get("NAME").toString();
                            String age = task.getResult().get("AGE").toString();
                            String pro_url = task.getResult().get("PROFILE_URL").toString();
                            String use_type = task.getResult().get("USERTYPE").toString();
                            String gender = task.getResult().get("GENDER").toString();
                            String Favlist = task.getResult().get("FAVCHORUS").toString();
                            userdata.add(new USERmodel(age, Favlist, gender, name, pro_url, use_type));
                        }
                    }


                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Access to Data failed" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        String name = userdata.get(0).getNAME();
        String age = userdata.get(0).getAGE();
        String pro_url = userdata.get(0).getPROFILE_URI();
        String use_type = userdata.get(0).getUSERTYPE();
        String gender = userdata.get(0).getGENDER();
        String CurrentFavList = userdata.get(0).getFAVCHORUS();
        if (isFav.contains("1")) {
            userdata.clear();
            userdata.add(new USERmodel(age, CurrentFavList+","+chorus1ModelArrayList.get(i).getId(), gender, name, pro_url, use_type));
        }
        else
        {

            StringBuilder CurrentFavList1 = new StringBuilder();


            String Favlist = CurrentFavList;
            String[] arrayFav = Favlist.split(",");
            List<String> fixLength= Arrays.asList(arrayFav);
            ArrayList<String> favUpdatedList =new ArrayList<String>(fixLength);
            for (int p=0;p<=favUpdatedList.size();p++)
            {
                if (favUpdatedList.get(p).contains(chorus1ModelArrayList.get(i).getId())){
                    favUpdatedList.remove(p);
                }
                else
                {
                    if(favUpdatedList.size()!=p)
                    {
                        CurrentFavList1.append(favUpdatedList.get(p)).append(",");

                    }
                    else
                    {
                        CurrentFavList1.append(favUpdatedList.get(p));

                    }

                }
            }
                userdata.clear();
                userdata.add(new USERmodel(age, CurrentFavList1.toString(), gender, name, pro_url, use_type));

        }

        Map<String,String> updatedUserData=new HashMap<>();
        updatedUserData.put("AGE",userdata.get(0).getAGE());
        updatedUserData.put("FAVCHORUS",userdata.get(0).getFAVCHORUS());
        updatedUserData.put("GENDER",userdata.get(0).getGENDER());
        updatedUserData.put("NAME",userdata.get(0).getNAME());
        updatedUserData.put("PROFILE_URI",userdata.get(0).getPROFILE_URI());
        updatedUserData.put("USERTYPE",userdata.get(0).getUSERTYPE());

        DocumentReference FavRef=chorusDB.collection("USERS").document(FirebaseAuth.getInstance().getUid());
        FavRef.update("AGE",userdata.get(0).getAGE(),"FAVCHORUS",userdata.get(0).getFAVCHORUS(),"GENDER",userdata.get(0).getGENDER(),
                "NAME",userdata.get(0).getNAME(),"PROFILE_URI",userdata.get(0).getPROFILE_URI(),"USERTYPE",userdata.get(0).getUSERTYPE()
                ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(context, "Added in Favourite List", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Failed to Added in Favourite List", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    class USERmodel {
        String AGE, FAVCHORUS, GENDER, NAME, PROFILE_URI, USERTYPE;

        public USERmodel(String AGE, String FAVCHORUS, String GENDER, String NAME, String PROFILE_URI, String USERTYPE) {
            this.AGE = AGE;
            this.FAVCHORUS = FAVCHORUS;
            this.GENDER = GENDER;
            this.NAME = NAME;
            this.PROFILE_URI = PROFILE_URI;
            this.USERTYPE = USERTYPE;
        }

        public String getAGE() {
            return AGE;
        }

        public void setAGE(String AGE) {
            this.AGE = AGE;
        }

        public String getFAVCHORUS() {
            return FAVCHORUS;
        }

        public void setFAVCHORUS(String FAVCHORUS) {
            this.FAVCHORUS = FAVCHORUS;
        }

        public String getGENDER() {
            return GENDER;
        }

        public void setGENDER(String GENDER) {
            this.GENDER = GENDER;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getPROFILE_URI() {
            return PROFILE_URI;
        }

        public void setPROFILE_URI(String PROFILE_URI) {
            this.PROFILE_URI = PROFILE_URI;
        }

        public String getUSERTYPE() {
            return USERTYPE;
        }

        public void setUSERTYPE(String USERTYPE) {
            this.USERTYPE = USERTYPE;
        }
    }
}
