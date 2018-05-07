package com.example.menna.notebook;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ReminderActivity";
    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        mHelper = new TaskDbHelper(this);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            Log.d(TAG, "Task: " + cursor.getString(idx));
        }
        cursor.close();
        db.close();
        mTaskListView = (ListView) findViewById(R.id.list_todo);
        updateUI();
        registerForContextMenu(mTaskListView);

    }


    //da 7lwww
       /*   mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int i, final long l) {
            Log.d("on item click","on click item "+i+" "+l);
            final EditText taskEditText = new EditText(view.getContext());
            AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                    .setTitle("Edit the Task")
                    .setMessage("Change your task to ....?")
                    .setView(taskEditText)
                    .setPositiveButton("Commit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("on item click","on click item "+which);
                            String task = String.valueOf(taskEditText.getText());
                            Log.d("on item click","the edit value is = "+task);
                            SQLiteDatabase db = mHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                            db.update(TaskContract.TaskEntry.TABLE,values,"_id" + "= "+ (i+1) ,null);
                            db.close();
                            Log.d("on item click","updateeeed");
                            updateUI();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();

        }
    });

}*/


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_add_task: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_dialog, null);
                builder.setView(dialogView)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final EditText txt= (EditText) dialogView.findViewById(R.id.task);
                                String task = txt.getText().toString();
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                builder.show();
                break;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Add: //mn8er custom
                /*final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();*/

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_dialog, null);
                builder.setView(dialogView)

                        .setPositiveButton("Add Task", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                final EditText txt= (EditText) dialogView.findViewById(R.id.task);
                                String task = txt.getText().toString();
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .create();
                builder.show();
                return true;

            case R.id.action_Logout:
                finish();


            default:
                return super.onOptionsItemSelected(item);

        }

    }


    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }


        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.reminder_row,
                    R.id.task_title,
                    taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
    }


    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_edit:

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        LayoutInflater inflater = this.getLayoutInflater();
                        final View dialogView = inflater.inflate(R.layout.edit_dialog, null);
                        builder.setView(dialogView)
                                .setPositiveButton("Commit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("on item click","on click item "+(int)(info.id+1));
                                        final EditText txt= (EditText) dialogView.findViewById(R.id.Etask);
                                        String task = txt.getText().toString();
                                        Log.d("on item click","the edit value is = "+task);
                                        SQLiteDatabase db = mHelper.getWritableDatabase();
                                        ContentValues values = new ContentValues();
                                        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                        int r= db.update(TaskContract.TaskEntry.TABLE,values,TaskContract.TaskEntry._ID +"= "+ (info.id+1) ,null);
                                        Log.d("return value = "," "+r);
                                        db.close();
                                        Log.d("on item click","updateeeed");
                                        updateUI();
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .create();
                        builder.show();
                updateUI();
                return true;
            case R.id.action_Delete:
                View parent = (View) info.targetView;
                TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
                String task = String.valueOf(taskTextView.getText());
                Log.d("on item click","the delete value "+task);
                SQLiteDatabase db = mHelper.getWritableDatabase();
                db.delete(TaskContract.TaskEntry.TABLE,
                        TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                        new String[]{task});
                db.close();
                updateUI();
                return true;

            default:
                return super.onContextItemSelected(item);
        }


    }
}
