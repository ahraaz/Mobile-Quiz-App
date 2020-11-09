package com.na6ix.quizapp;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionAnswers implements Parcelable {

    private String _question;
    private String _option1;
    private String _option2;
    private String _option3;
    private String _option4;
    private int _answer;
    private int _id;

    public QuestionAnswers(){

    }

    public QuestionAnswers(String _question, String _option1, String _option2,
                           String _option3, String _option4, int _answer) {
        this._question = _question;
        this._option1 = _option1;
        this._option2 = _option2;
        this._option3 = _option3;
        this._option4 = _option4;
        this._answer = _answer;
    }

    protected QuestionAnswers(Parcel in) {
        _question = in.readString();
        _option1 = in.readString();
        _option2 = in.readString();
        _option3 = in.readString();
        _option4 = in.readString();
        _answer = in.readInt();
        _id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_question);
        dest.writeString(_option1);
        dest.writeString(_option2);
        dest.writeString(_option3);
        dest.writeString(_option4);
        dest.writeInt(_answer);
        dest.writeInt(_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuestionAnswers> CREATOR = new Creator<QuestionAnswers>() {
        @Override
        public QuestionAnswers createFromParcel(Parcel in) {
            return new QuestionAnswers(in);
        }

        @Override
        public QuestionAnswers[] newArray(int size) {
            return new QuestionAnswers[size];
        }
    };

    public String get_question() {
        return _question;
    }

    public void set_question(String _question) {
        this._question = _question;
    }

    public String get_option1() {
        return _option1;
    }

    public void set_option1(String _option1) {
        this._option1 = _option1;
    }

    public String get_option2() {
        return _option2;
    }

    public void set_option2(String _option2) {
        this._option2 = _option2;
    }

    public String get_option3() {
        return _option3;
    }

    public void set_option3(String _option3) {
        this._option3 = _option3;
    }

    public String get_option4() {
        return _option4;
    }

    public void set_option4(String _option4) {
        this._option4 = _option4;
    }

    public int get_answer() {
        return _answer;
    }

    public void set_answer(int _answer) {
        this._answer = _answer;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
