package com.example.todoapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private ArrayList<String> items;
private ArrayAdapter<String> itemsAdapter;
private ListView listView;
private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,items);
        listView.setAdapter(itemsAdapter);
        setuplistViewListener();
    }

    private void setuplistViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Item Removed",Toast.LENGTH_LONG).show();

                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showEditDialog(position);
            }
        });
    }

    private void showEditDialog(int position) {
        String currentText = items.get(position);

        EditText editText = new EditText(this);
        editText.setText(currentText);
        editText.setSelection(currentText.length());

        new android.app.AlertDialog.Builder(this)
                .setTitle("Edit Item")
                .setView(editText)
                .setPositiveButton("Save", (dialog, which) -> {
                    String editedText = editText.getText().toString().trim();
                    if (!editedText.isEmpty()) {
                        items.set(position, editedText);
                        itemsAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Item updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Text can't be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


    private void addItem(View view) {
        EditText input = findViewById(R.id.editTextText);
        String itemText = input.getText().toString();

        if(!(itemText.equals(""))){
            itemsAdapter.add(itemText);
            input.setText("");
        }
        else{
            Toast.makeText(getApplicationContext(), "Please Enter Text....", Toast.LENGTH_LONG).show();
        }
    }
}
