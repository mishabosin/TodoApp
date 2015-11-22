package com.mishabosin.todoapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditItemDialog.EditItemDialogListener {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    FileStorage fileStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fileStorage = new FileStorage(getFilesDir());
        items = fileStorage.readItems();
        lvItems = (ListView)findViewById(R.id.lvItems);
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTaskDialog("New task");
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishEditDialog(String itemText) {
        addTask(itemText);
    }

    private void showTaskDialog(String title) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialog editNameDialog = EditItemDialog.newInstance(title);
        editNameDialog.show(fm, "fragment_edit_item");
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                final String taskText = items.get(pos);
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                fileStorage.writeItems(items);

                Snackbar.make(item, "Task completed!", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                addTask(taskText);
                            }
                        }).show();
                return true;
            }
        });
    }

    private void addTask(String taskText) {
        itemsAdapter.add(taskText);
        fileStorage.writeItems(items);
    }
}
