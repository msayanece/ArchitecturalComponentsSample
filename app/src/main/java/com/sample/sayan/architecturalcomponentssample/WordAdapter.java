package com.sample.sayan.architecturalcomponentssample;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sample.sayan.architecturalcomponentssample.databinding.WordChildBinding;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder>  {
    private List<Word> words;
    private LayoutInflater layoutInflater;

    public WordAdapter(List<Word> words, LayoutInflater layoutInflater) {
        this.words = words;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WordChildBinding wordChildBinding = DataBindingUtil.inflate(layoutInflater, R.layout.word_child, parent, false);
        return new WordViewHolder(wordChildBinding);
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

        private WordChildBinding wordChildBinding;

        WordViewHolder(WordChildBinding wordChildBinding) {
            super(wordChildBinding.getRoot());
            this.wordChildBinding = wordChildBinding;
        }

        void setData(Word word) {
            wordChildBinding.setWordData(word);
            wordChildBinding.executePendingBindings();
        }
    }
}
