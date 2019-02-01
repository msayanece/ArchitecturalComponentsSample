package com.sample.sayan.architecturalcomponentssample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sample.sayan.architecturalcomponentssample.databinding.ActivityMainBinding;

import java.util.List;

//https://developer.android.com/topic/libraries/architecture/adding-components#room
public class MainActivity extends AppCompatActivity {

    //https://medium.com/androiddevelopers/viewmodels-persistence-onsaveinstancestate-restoring-ui-state-and-loaders-fc7cc4a6c090
    private WordViewModel mWordViewModel;
    private WordAdapter wordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null){
            wordAdapter = new WordAdapter(null, layoutInflater);
            viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            viewDataBinding.recyclerView.setAdapter(wordAdapter);
        }

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
}
