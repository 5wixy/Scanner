package com.example.scannerapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;

public class ViewItems extends AppCompatActivity implements View.OnClickListener {

    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<ItemModal> itemModalArrayList;
    private DBHandler dbHandler;
    private ItemRVAdapter itemsRVAdapter;
    ItemModal delItem;
    private RecyclerView itemsRV;
    public FloatingActionButton btnMenu;
    TextView tvScanContent, tvScanFormat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        // initializing our all variables.
        itemModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ViewItems.this);
        MainActivity scanAdd = new MainActivity();
        tvScanContent = findViewById(R.id.tvScanContent);
        tvScanFormat = findViewById(R.id.tvScanFormat);
        btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(this);


        // getting our course array
        // list from db handler class.
        itemModalArrayList = dbHandler.readItems();

        // on below line passing our array lost to our adapter class.
        itemsRVAdapter = new ItemRVAdapter(itemModalArrayList, ViewItems.this);
        itemsRV = findViewById(R.id.idRVItems);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewItems.this, RecyclerView.VERTICAL, false);
        itemsRV.setLayoutManager(linearLayoutManager);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(itemsRV);
        // setting our adapter to recycler view.
        itemsRV.setAdapter(itemsRVAdapter);
    }



    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            ItemModal x = dbHandler.readItem(viewHolder.getAdapterPosition());
            dbHandler.deleteItem(x.getitemName());
            Toast.makeText(ViewItems.this,"Item " + x.getitemName() + "was deleted", Toast.LENGTH_SHORT).show();
            itemsRVAdapter.notifyItemRemoved(x.getId());
            Intent i = new Intent(ViewItems.this,ViewItems.class);
            startActivity(i);



        }
    };


    @Override
    public void onClick(View view) {
        dbHandler.getData();

    }
    public void onClickCode(){
        delItem.getItemCode();
    }
    public void onClickName(){
        delItem.getitemName();
    }
}


