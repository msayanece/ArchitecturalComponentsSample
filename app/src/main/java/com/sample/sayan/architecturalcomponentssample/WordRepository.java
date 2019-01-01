package com.sample.sayan.architecturalcomponentssample;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class WordRepository {

    private Application application;

    public WordRepository(Application application) {
        this.application = application;
    }

    public LiveData<List<Word>> getAllWords(){
        WordDatabase database = WordDatabase.getINSTANCE(application);
        return database.getWordDAO().getAllAlphabetizedWords();
    }

    public void insertAWord(final Word word){
        InsertAsync insertAsync = new InsertAsync(application);
        insertAsync.execute(word);
    }

    public void deleteAll(){
        DeleteAllAsync insertAsync = new DeleteAllAsync(application);
        insertAsync.execute();
    }

    private static class InsertAsync extends AsyncTask<Word, Void, Void> {

        private Application application;

        public InsertAsync(Application application) {
            this.application = application;
        }

        @Override
        protected Void doInBackground(Word... words) {
            WordDatabase database = WordDatabase.getINSTANCE(application);
            database.getWordDAO().insert(words[0]);
            return null;
        }
    }

    private static class DeleteAllAsync extends AsyncTask<Void, Void, Void> {

        private Application application;

        public DeleteAllAsync(Application application) {
            this.application = application;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            WordDatabase database = WordDatabase.getINSTANCE(application);
            database.getWordDAO().deleteAll();
            return null;
        }
    }
}
