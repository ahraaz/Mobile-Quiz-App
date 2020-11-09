package com.na6ix.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SciDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME = "sci.db";
    private static final String TABLE_NAME = "Science";
    private static final String  COL_1= "ID";
    private static final String COL_2 = "Questions";
    private static final String  COL_3= "Option1";
    private static final String COL_4 = "Option2";
    private static final String  COL_5= "Option3";
    private static final String COL_6 = "Option4";
    private static final String COL_7 = "Answer_Number";

    private SQLiteDatabase db;


    public SciDbHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;

        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_1 + " Integer PRIMARY KEY AUTOINCREMENT," +
                COL_2 + " Text NOT NULL," +
                COL_3 + " TEXT NOT NULL," +
                COL_4 + " TEXT NOT NULL," +
                COL_5 + " TEXT NOT NULL," +
                COL_6 + " TEXT NOT NULL," +
                COL_7 +  " Text NOT NULL" +")" + ";" ;

        //Create Table with four columns
        db.execSQL(query);
        addQuestion();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    private void addQuestion(){
        QuestionAnswers q1 = new QuestionAnswers("asd","a","b",
                "c","d",1);
        addQuestionToDB(q1);
    }

    private void addQuestionToDB(QuestionAnswers qa){
        ContentValues cv = new ContentValues();

        cv.put(COL_2, qa.get_question());
        cv.put(COL_3, qa.get_option1());
        cv.put(COL_4,qa.get_option2());
        cv.put(COL_5,qa.get_option3());
        cv.put(COL_6,qa.get_option4());
        cv.put(COL_7,qa.get_answer());
        db.insert(TABLE_NAME, null, cv);
    }

    public ArrayList getQuestions() {

        ArrayList<QuestionAnswers> qaList = new ArrayList<>();
        String query = "Select * FROM " + TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            do{
                QuestionAnswers qa = new QuestionAnswers();
                qa.set_question(cursor.getString(cursor.getColumnIndex(COL_2)));
                qa.set_option1(cursor.getString(cursor.getColumnIndex(COL_3)));
                qa.set_option2(cursor.getString(cursor.getColumnIndex(COL_4)));
                qa.set_option3(cursor.getString(cursor.getColumnIndex(COL_5)));
                qa.set_option4(cursor.getString(cursor.getColumnIndex(COL_6)));
                qa.set_answer(cursor.getInt(cursor.getColumnIndex(COL_7)));

                qaList.add(qa);
            } while(cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return qaList;
    }
}
