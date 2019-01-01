package com.sample.sayan.architecturalcomponentssample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

//https://developer.android.com/topic/libraries/architecture/adding-components#room
public class MainActivity extends AppCompatActivity {

    //https://medium.com/androiddevelopers/viewmodels-persistence-onsaveinstancestate-restoring-ui-state-and-loaders-fc7cc4a6c090
    private WordViewModel mWordViewModel;
    private WordAdapter wordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordAdapter = new WordAdapter(MainActivity.this, null);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(wordAdapter);
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
//        mWordViewModel.deleteAll();
        mWordViewModel.getmAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                //refresh adapter
                wordAdapter.refresh(words);
            }
        });
    }

    public void onClickFAB(View view) {
        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(layoutParams);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newWord = input.getText().toString();
                        Word word = new Word();
                        word.setWord(newWord);
                        mWordViewModel.addAWord(word);
                    }
                })
                .create();
        alertDialog.setView(input);
        alertDialog.setTitle("Enter a Word");
        alertDialog.show();
    }

    private class WordAdapter extends RecyclerView.Adapter<WordViewHolder> {

        private Context context;
        private List<Word> words;

        public WordAdapter(Context context, List<Word> words) {
            this.context = context;
            this.words = words;
        }

        @NonNull
        @Override
        public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.word_child, null, false);
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
    }

    private class WordViewHolder extends RecyclerView.ViewHolder {

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
