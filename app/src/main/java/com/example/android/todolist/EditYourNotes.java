package com.example.android.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class EditYourNotes extends AppCompatActivity implements TextWatcher {
    EditText editText;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_your_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        editText = (EditText) findViewById(R.id.editText);
        Intent i = getIntent();
        noteId = i.getIntExtra("noteId", -1);
        if (noteId != -1) {
            editText.setText(MainActivity.notes.get(noteId));
        }

        editText.addTextChangedListener(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {


    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        MainActivity.notes.set(noteId, String.valueOf(charSequence));
        MainActivity.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.android.todolist", Context.MODE_PRIVATE);
        if (MainActivity.set == null) {
            MainActivity.set = new HashSet<String>();
        } else {
            MainActivity.set.clear();
        }
        MainActivity.set.addAll(MainActivity.notes);
        sharedPreferences.edit().remove("notes").apply();
        sharedPreferences.edit().putStringSet("notes", MainActivity.set).apply();

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
