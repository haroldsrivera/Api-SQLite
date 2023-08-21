package model;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import classes.User;

public class UserDAO {
    private ManagerDB managerDB;
    Context context;
    View view;
    User user;

    public UserDAO(Context context, View view){
        this.context=context;
        this.view=view;
        managerDB= new ManagerDB(context);
    }

    public void insertUser(User myUser){
        try {
            SQLiteDatabase db = managerDB.getWritableDatabase();
            if (db != null){
                ContentValues values = new ContentValues();
                values.put("usu_document",myUser.getDocument ());
                values.put("usu_user",myUser.getUser() );
                values.put("usu_names", myUser.getNames());
                values.put("usu_last_names", myUser.getLastNames());
                values.put("usu_pass",myUser.getPass());
                values.put("usu_status",myUser.getStatus());
                long cod = db.insert ("users", null, values);
                Snackbar.make (this.view, "The user register success:"+cod, Snackbar.LENGTH_LONG). show();
            }else{
                Snackbar.make (this.view, "The user not register ERROR:", Snackbar. LENGTH_LONG).show();
            }
        }catch (SQLException e) {
            Log.i( "ERROR", " "+e);
        }
    }

    public ArrayList<User> getUserList() {
        SQLiteDatabase db = managerDB.getReadableDatabase();

        String query = "SELECT * FROM users WHERE usu_status = 1";

        ArrayList<User> userList = new ArrayList<User>();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setDocument(cursor.getInt(0));
                user.setUser(cursor.getString(1));
                user.setNames(cursor.getString(2));
                user.setLastNames(cursor.getString(3));
                user.setPass(cursor.getString(4));
                user.setStatus(cursor.getInt(5));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    @SuppressLint("Range")
    public ArrayList<User> getFilteredUserList(String filter) {
        SQLiteDatabase db = managerDB.getReadableDatabase();
        String query = "SELECT * FROM users WHERE (usu_names LIKE ? OR usu_user LIKE ?) AND usu_status = 1";
        ArrayList<User> filteredUserList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, new String[]{"%" + filter + "%", "%" + filter + "%"});

        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setDocument(cursor.getInt(cursor.getColumnIndex("usu_document")));
                user.setUser(cursor.getString(cursor.getColumnIndex("usu_user")));
                user.setNames(cursor.getString(cursor.getColumnIndex("usu_names")));
                user.setLastNames(cursor.getString(cursor.getColumnIndex("usu_last_names")));
                user.setPass(cursor.getString(cursor.getColumnIndex("usu_pass")));
                user.setStatus(cursor.getInt(cursor.getColumnIndex("usu_status")));
                filteredUserList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return filteredUserList;
    }

    public void deleteUser(User userToUpdate) {
        SQLiteDatabase db = managerDB.getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put("usu_status", 0);

            int rowsAffected = db.update("users", values, "usu_document = ?", new String[]{String.valueOf(userToUpdate.getDocument())});

            if (rowsAffected > 0) {
                Snackbar.make(this.view, "Usuario eliminado correctamente.", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(this.view, "Error al eliminar el usuario.", Snackbar.LENGTH_LONG).show();
            }

            db.close();
        } else {
            Snackbar.make(this.view, "Error al acceder a la base de datos.", Snackbar.LENGTH_LONG).show();
        }
    }

    public void updateUser(User userToUpdate) {
        SQLiteDatabase db = managerDB.getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put("usu_names", userToUpdate.getNames());
            values.put("usu_last_names", userToUpdate.getLastNames());
            values.put("usu_user", userToUpdate.getUser());
            values.put("usu_pass", userToUpdate.getPass());

            int rowsAffected = db.update("users", values, "usu_document = ?", new String[]{String.valueOf(userToUpdate.getDocument())});

            if (rowsAffected > 0) {
                Snackbar.make(this.view, "Datos actualizados correctamente.", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(this.view, "Error al actualizar los datos.", Snackbar.LENGTH_LONG).show();
            }

            db.close();
        } else {
            Snackbar.make(this.view, "Error al acceder a la base de datos.", Snackbar.LENGTH_LONG).show();
        }
    }


}

