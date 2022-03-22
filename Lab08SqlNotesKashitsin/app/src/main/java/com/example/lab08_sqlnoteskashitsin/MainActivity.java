package com.example.lab08_sqlnoteskashitsin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lstctl;
    ArrayList<Note> lst = new ArrayList<Note>();
    ArrayAdapter<Note> adp;

    Context ctx;

    void updateList()
    {
        lst.clear();
        g.notes.getAllNotes(lst);
        adp.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)//Кашицын,393
    {
        updateList();
        super.onActivityResult(requestCode, resultCode,data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;

        g.notes = new DB(this, "notes.db", null, 1);

        lstctl = findViewById(R.id.listvw_notes);
        adp = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, lst);
        lstctl.setAdapter(adp);

        lstctl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note n = adp.getItem(position);
                Intent i = new Intent(ctx, Main2Activity.class);
                i.putExtra("noteId", n.id);
                i.putExtra("noteTxt", n.txt);
                startActivityForResult(i, 1);
            }
        });
        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//Кашицын,393
        int id = item.getItemId();
        switch (id)
        {
            case R.id.item_new:{
                int nid = g.notes.getMaxId() + 1;
                g.notes.addNote(nid,"Hello, world!");
                updateList();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}