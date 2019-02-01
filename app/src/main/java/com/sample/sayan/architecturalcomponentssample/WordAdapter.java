package com.sample.sayan.architecturalcomponentssample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder>  {
    private Context context;
    private List<Word> words;
    private LayoutInflater layoutInflater;

    public WordAdapter(Context context, List<Word> words, LayoutInflater layoutInflater) {
        this.context = context;
        this.words = words;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.word_child, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.setData(words.get(position));
    }

    @Override
    public int getItemCount() {
        if (words != null){
            return words.size();
        }else {
            return 0;
        }
    }

    public void refresh(List<Word> words) {
        this.words = words;
        notifyDataSetChanged();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {

        private final TextView wordTextView;

        public WordViewHolder(View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.wordTextView);
        }

        public void setData(Word word) {
            wordTextView.setText(word.getWord());
        }
    }
}
