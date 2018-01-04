package com.example.android.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    static Set<String> set;
    ListView listView;
    static ArrayList<String> notes = new ArrayList<String>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Are you sure dude, this is serious")
                        .setPositiveButton("Yup", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position) {
                                notes.remove(position);
                                SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("com.example.android.todolist", Context.MODE_PRIVATE);
                                if (set == null) {
                                    set = new HashSet<String>();
                                } else {
                                    set.clear();
                                }
                                set.addAll(notes);
                                sharedPreferences.edit().remove("notes").apply();
                                sharedPreferences.edit().putStringSet("notes", set).apply();
                                    arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Nah", null)
                        .show();


            }
        });
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.android.todolist", Context.MODE_PRIVATE);
        set = sharedPreferences.getStringSet("notes", null);

        notes.clear();
        if (set != null) {
            notes.addAll(set);
        } else {
            notes.add("Example note");
            set = new HashSet<String>();
            set.addAll(notes);
            sharedPreferences.edit().putStringSet("notes", set).apply();
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(getApplicationContext(), EditYourNotes.class);
                i.putExtra("noteId", position);
                startActivity(i);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
            notes.add("");
            SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.android.todolist", Context.MODE_PRIVATE);
            if (set == null) {
                set = new HashSet<String>();
            } else {
                set.clear();
            }
            set.addAll(notes);
            sharedPreferences.edit().putStringSet("notes", set).apply();
            arrayAdapter.notifyDataSetChanged();
            sharedPreferences.edit().remove("notes").apply();
            Intent i = new Intent(getApplicationContext(), EditYourNotes.class);
            i.putExtra("noteId", notes.size() - 1);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
