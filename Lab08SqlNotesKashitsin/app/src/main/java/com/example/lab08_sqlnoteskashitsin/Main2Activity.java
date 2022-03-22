package com.example.lab08_sqlnoteskashitsin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    EditText txtctl;
    int nid;
    String ntxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//Кашицын,393
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtctl = findViewById(R.id.input_content);
        Intent i = getIntent();
        nid = i.getIntExtra("noteId", 0);
        ntxt = i.getStringExtra("noteTxt");
        txtctl.setText(ntxt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//Кашицын,393
        int id = item.getItemId();
        switch (id)
        {
            case R.id.item_save:{
                g.notes.saveNote(nid, txtctl.getText().toString());
                Toast.makeText(this, "Заметка сохранена", Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.item_delete:{
                makeDialog("Вы действительно хотите удалить эту заметку?");
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void makeDialog(String str)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dlg = builder.create();
        dlg.setTitle(str);
        dlg.setView(dialogWithButtons(dlg));
        dlg.show();
    }

    LinearLayout dialogWithButtons(AlertDialog dlg)
    {
        LinearLayout linearLayout = new LinearLayout(getBaseContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Button butYes = addButton("Да");
        butYes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                g.notes.deleteNote(nid);
                dlg.cancel();
                finish();
            }
        });
        Button butNo = addButton("Нет");
        butNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.cancel();
            }
        });
        linearLayout.addView(butYes);
        linearLayout.addView(butNo);
        return linearLayout;
    }

    Button addButton (String str)//Кашицын,393
    {
        Button button = new Button(this);
        button.setText(str);
        return button;
    }
}