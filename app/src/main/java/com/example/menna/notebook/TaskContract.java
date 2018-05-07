package com.example.menna.notebook;

import android.provider.BaseColumns;

/**
 * Created by menna on 4/23/2018.
 */

public class TaskContract {

    public static final String DB_NAME = "Menna";
    public static final int DB_VERSION =6 ;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";
        public static final String COL_TASK_TITLE = "title";
    }
}
