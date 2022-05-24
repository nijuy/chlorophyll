package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Error TAG
    protected static String TAG = "DatabaseHelper";

    private static String databasePath = ""; // 데이터베이스 경로
    private static String databaseName = "plantDB.db"; // 데이터베이스 이름


    private final Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context){
        super(context, databaseName, null, 1);


        databasePath = context.getApplicationInfo().dataDir + "/databases/";

        this.mContext = context;
    }

    // 데이터베이스 파일 열기
    public boolean OpenDatabaseFile() throws SQLException {
        if(!CheckDatabaseFileExist()){
            CreateDatabase();
        }

        String mPath = databasePath + databaseName;
        try{
            mDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            Log.e(TAG,  "[SUCCESS] " + databaseName + " are Opened");
        }
        catch(SQLException sqlException){
            Log.e(TAG, "[ERROR]" + "Can't Open Database");
        }
        return mDatabase != null;
    }

    // 데이터베이스 파일 존재 여부 확인
    public boolean CheckDatabaseFileExist(){
        File file = new File(databasePath + databaseName);
        return file.exists();
    }

    // Database 만들기
    public void CreateDatabase() throws SQLException{

        this.getReadableDatabase();
        this.close();

        try{
            CopyDatabaseFile();
            Log.e(TAG,  "[SUCCESS] " + databaseName + " are Created");
        }
        catch(IOException ioException){
            // Error Message
            Log.e(TAG, "[ERROR] " + "Unable to create " + databaseName);
            throw new Error(TAG);
        }
    }

    // 데이터베이스 복사
    public void CopyDatabaseFile() throws IOException{

        InputStream inputStream  = mContext.getAssets().open(databaseName);
        String outputFileName = databasePath + databaseName;
        OutputStream outputStream = new FileOutputStream(outputFileName);

        byte[] buffer = new byte[1024];
        int length;
        while((length = inputStream.read(buffer)) > 0){
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }


    // 테이블 정보 가져오기
    public ArrayList<Plant> getTableData(String sql) {

        try{
            // 테이블 정보를 저장할 List
            ArrayList<Plant> mList = new ArrayList<>();

            // 테이블 데이터를 읽기 위한 Cursor
            Cursor mCursor = mDatabase.rawQuery(sql, null);

            // 테이블 끝까지 읽기
            if (mCursor != null){

                // 다음 Row로 이동
                while(mCursor.moveToNext()){
                    // 해당 Row 저장
                    Plant plant = new Plant();

                    plant.setId(mCursor.getString(0));
                    plant.setName(mCursor.getString(1));
                    plant.setUse(mCursor.getString(2));
                    plant.setSize(mCursor.getString(3));
                    plant.setFlowering(mCursor.getString(4));
                    plant.setDifficulty(mCursor.getString(5));
                    plant.setSunshine(mCursor.getString(6));
                    plant.setImage(mCursor.getString(7));
                    // List에 해당 Row 추가
                    mList.add(plant);
                }
                mCursor.close();
            }
            return mList;

        }
        catch (SQLException mSQLException){
            // Error Message
            Log.e(TAG, mSQLException.toString());
            throw mSQLException;
        }

    }

    // 데이터베이스 닫기
    public void CloseDatabaseFile(){
        if (mDatabase != null){
            mDatabase.close();
        }
    }

    @Override
    public synchronized void close(){
        CloseDatabaseFile();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

