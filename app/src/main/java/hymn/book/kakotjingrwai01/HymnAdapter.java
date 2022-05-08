package hymn.book.kakotjingrwai01;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

class HymnAdapter extends RecyclerView.Adapter<HymnAdapter.ViewHolder> {
    private ArrayList<HymnModel> hymnList;
    Context context;

    public HymnAdapter(ArrayList<HymnModel> hymnList, Context context) {
        this.hymnList = hymnList;
        this.context = context;
    }

    @NonNull
    @Override
    public HymnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hymn_itemlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HymnAdapter.ViewHolder holder, int position) {
        holder.hymnNumber.setText(hymnList.get(position).getNumber());
        holder.Author.setText("Author: "+hymnList.get(position).getAuthor());
        holder.Title.setText("Title: "+hymnList.get(position).getTitle());

        holder.hymncard.setOnClickListener(V->{
            Intent intent = new Intent(context,DisplayHymn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ID",hymnList.get(position).getNumber());
            intent.putExtra("TITLE",hymnList.get(position).getTitle());
            intent.putExtra("AUTHOR",hymnList.get(position).getAuthor());
            intent.putExtra("LYRIC",hymnList.get(position).getLyrics());
            context.startActivity(intent);

        });

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return hymnList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView hymnNumber,Title,Author;
        private MaterialCardView hymncard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hymnNumber = itemView.findViewById(R.id.hymnRNumbre);
            Title = itemView.findViewById(R.id.TitleR);
            Author = itemView.findViewById(R.id.AuthorR);
            hymncard = itemView.findViewById(R.id.hymncard);
        }
    }
}
