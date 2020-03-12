package com.example.spotifyapplication.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

public class OAuthRepository {
    private OAuthDao mOauthDao;

    public OAuthRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mOauthDao = db.oAuthDao();
    }

    public void insertOauth(OAuthInfo oAuthInfo) {
        new InsertAsyncTask(mOauthDao).execute(oAuthInfo);
    }

    public void deleteOauth(OAuthInfo oAuthInfo) {
        new DeleteAsyncTask(mOauthDao).execute(oAuthInfo);
    }

    public LiveData<OAuthInfo> getSingleOAuth() {
        return mOauthDao.getSingleOAuth();
    }

    public void deleteAllOauthEntries(){
//        mOauthDao.deleteAllOauthEntries();
        new DeleteAllAsyncTask(mOauthDao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<OAuthInfo, Void, Void> {
        private OAuthDao mDao;
        InsertAsyncTask(OAuthDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(OAuthInfo... oAuthInfos) {
            mDao.insert(oAuthInfos[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<OAuthInfo, Void, Void> {
        private OAuthDao mDao;
        DeleteAsyncTask(OAuthDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(OAuthInfo... oAuthInfos) {
            mDao.delete(oAuthInfos[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private OAuthDao mDao;
        DeleteAllAsyncTask(OAuthDao dao) {
            mDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.deleteAllOauthEntries();
            return null;
        }

    }

}
