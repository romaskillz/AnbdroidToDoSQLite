package com.example.rotelukh.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends Activity implements /*View.OnClickListener,*/ ToDoAdapter.OnItemClickListener {
    @BindView(R.id.bAdd) Button bAdd;
    @BindView(R.id.bDelete) Button bDelete;
    @BindView(R.id.bUpdate) Button bUpdate;
    @BindView(R.id.et_todo) EditText etToDo;

    @BindView (R.id.rvList) RecyclerView mRecyclerView;

    private Cursor cr;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<ToDo> mArrayToDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Invoke a parent method */
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
        checkDataBaseEntity();
        mAdapter.notifyDataSetChanged();
    }


    void init() {
//        mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
//        mArrayToDo = new ArrayList<>();
        mArrayToDoList = new ArrayList<>();
        /* Use this setting to improve performance if you know that changes
         in content do not change the layout size of the RecyclerView*/
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        /*Set layout manager*/
        mRecyclerView.setLayoutManager(mLayoutManager);
        /*Init Adapter*/
        mAdapter = new ToDoAdapter(mArrayToDoList, this, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    void checkDataBaseEntity() {
        if (getDBLinesCount() == 0) {
            mArrayToDoList.clear();
            mAdapter.notifyDataSetChanged();
            emptyAlert();
        } else {
            mArrayToDoList.clear();
            cr = MyApp.getDB().getReadableCursor(DBToDo.TableToDo.T_NAME);
            if (cr.moveToFirst()) {
                int col_id = cr.getColumnIndex(DBToDo.TableToDo._ID);
                int col_name = cr.getColumnIndex(DBToDo.TableToDo.C_TODO);
                do {
                    String id = cr.getString(col_id);
                    String name = cr.getString(col_name);
                    ToDo toDo = new ToDo();
                    toDo.setId(Integer.parseInt(id));
                    toDo.setTodo(name);
                    mArrayToDoList.add(toDo);
                    mAdapter.notifyDataSetChanged();
                } while (cr.moveToNext());
            }
            cr.close();
        }
    }

    @OnClick({R.id.bAdd, R.id.bUpdate, R.id.bDelete})
    public void onClick(View button) {
        switch (button.getId()) {
            case R.id.bAdd:
                mArrayToDoList.clear();
                if (!etToDo.getText().toString().isEmpty()) {

                    /*Add to DataBase*/
                    ToDo d = new ToDo();
                    String toDo = etToDo.getText().toString();
                    d.setTodo(toDo);
                    etToDo.getText().clear();
                    MyApp.getDB().addDep(toDo);

                    /*Check and add to ArrayList for RecyclerView*/
                    checkDataBaseEntity();
                    mAdapter.notifyDataSetChanged();
                } else {
                    emptyToDoText();
                }
                break;

            case R.id.bUpdate:
                mArrayToDoList.clear();
                checkDataBaseEntity();
                break;


            case R.id.bDelete:
                MyApp.getDB().deleteAll();
                checkDataBaseEntity();
                mAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }

    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bAdd:
                mArrayToDoList.clear();
                if (!etToDo.getText().toString().isEmpty()) {

                    *//*Add to DataBase*//*
                    ToDo d = new ToDo();
                    String toDo = etToDo.getText().toString();
                    d.setTodo(toDo);
                    etToDo.getText().clear();
                    MyApp.getDB().addDep(toDo);

                    *//*Check and add to ArrayList for RecyclerView*//*
                    checkDataBaseEntity();
                    mAdapter.notifyDataSetChanged();
                } else {
                    emptyToDoText();
                }
                break;

            case R.id.bUpdate:
                mArrayToDoList.clear();
                checkDataBaseEntity();
                break;


            case R.id.bDelete:
                MyApp.getDB().deleteAll();
                checkDataBaseEntity();
                mAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }*/


    @Override
    public void onItemClick(ToDo item) {
        MyApp.getDB().deleteToDo(item.getId());

        Iterator<ToDo> itr = mArrayToDoList.iterator();
        int i = item.getId();
        while (itr.hasNext()) {
            if (i == itr.next().getId()) {
                itr.remove();
            }
            mAdapter.notifyDataSetChanged();
        }

        if (getDBLinesCount() == 0) {
            mArrayToDoList.clear();
            emptyAlert();
        }


    }

    public long getDBLinesCount() {
        SQLiteDatabase db = MyApp.getDB().getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, "tTODO");
        db.close();
        return count;
    }

    private void emptyAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Важное сообщение!")
                .setMessage("Ваш список дел пуст")
                .setPositiveButton("OK", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void emptyToDoText() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Важное сообщение!")
                .setMessage("Вы не ввели текст дела.")
                .setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
