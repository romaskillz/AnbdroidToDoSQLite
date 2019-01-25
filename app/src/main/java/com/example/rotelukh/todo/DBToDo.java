package com.example.rotelukh.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DBToDo extends DBSQLite {

    /* Private field for store SQL WHERE for one element (by id) */
    private static final String SQL_WHERE_BY_ID = BaseColumns._ID + "=?";

    /* Public constant that store a name of data base */
    public static final String DB_NAME = "DBToDo.db";

    /* Public constant that store a version of data base */
    public static final int DB_VERSION = 2;

    /**
     * Constructor with one parameter that describe a link
     * to the Context object.
     *
     * @param context The context object.
     */
    public DBToDo(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is
     * where the creation of tables and the initial population of the
     * tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /* Create tables */
        DBSQLite.execSQL(db, TableToDo.SQL_CREATE);

        /* Fill table tDep */

        // load data from application resources
        /*String[] toDo = getContext().getResources().getStringArray(R.array.todo_items);

        // create object for store couples of names and values
        ContentValues values = new ContentValues(toDo.length);

        // Fill table tDep
        for (int i = 0; i < toDo.length; i++) {

            // parse information about department
            String[] dep = toDo[i].split("-");

            // fill values
            values.put(TableToDo.C_TODO, dep[0]);

            // add record to a data base
            db.insert(TableToDo.T_NAME, null, values);
        }*/
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything
     * else it needs to upgrade to the new schema version.
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /* Drop tables */
        DBSQLite.dropTable(db, TableToDo.T_NAME);
        /* Invoke onCreate method */
        this.onCreate(db);

    }

    /**
     * Add information about department to a data base
     *
     * @param name     department name
     * @return id of new element
     */
    public long addDep(String name) {

        /* Create a new map of values, where column names are the keys */
        ContentValues v = new ContentValues();

        /* Fill values */
        v.put(TableToDo.C_TODO, name);

        /* Add item to a data base */
        return this.getWritableDatabase().insert(TableToDo.T_NAME, null, v);
    }

    /**
     * Update information about department into a data base.
     *
     * @param name     new department name
     * @return True, if was been updated only one element
     */
    public boolean updateToDo(String name, long id) {

        /* Create a new map of values, where column names are the keys */
        ContentValues v = new ContentValues();

        /* Fill values */
        v.put(TableToDo.C_TODO, name);

        /* Update information */
        return 1 == this.getWritableDatabase().update(TableToDo.T_NAME, v,
                SQL_WHERE_BY_ID, new String[]{String.valueOf(id)});
    }


    /**
     * Delete department from a data base.
     *
     * @param id of element that will be deleted
     * @return True, if was been deleted only one element
     */
    public boolean deleteToDo(long id) {
        return 1 == this.getWritableDatabase().delete(
                TableToDo.T_NAME, SQL_WHERE_BY_ID,
                new String[]{String.valueOf(id)});
    }
    public void deleteAll() {
        this.getWritableDatabase().execSQL(TableToDo.SQL_DELETE);
    }

    /**
     * Public static class that contains information about table tDep
     */
    public static class TableToDo implements BaseColumns {

        /**
         * Name of this table.
         */
        public static final String T_NAME = "tTODO";

        /**
         * The name of department.
         * <P>Type: TEXT</P>
         */
        public static final String C_TODO = "TODO";

        /**
         * SQL query for a create this table.
         */
        public static final String SQL_CREATE = "CREATE TABLE " + T_NAME +
                " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                C_TODO + " TEXT)";

        public static final String SQL_DELETE = "DELETE FROM tTODO;";
    }

    /**
     * Delete a warehouse specified by the id
     *
     * @param id
     */
    public void delete(int id) {
/*        String sql = "DELETE FROM " + TableToDo.T_NAME + "WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
    }

}
