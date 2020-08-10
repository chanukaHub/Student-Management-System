package com.WizGuys.eStudent;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {

    private static  final String SP_NAME ="userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase= context.getSharedPreferences(SP_NAME,0);
    }
    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name",user.name);
        spEditor.putInt("age",user.age);
        spEditor.putString("userName",user.userName);
        spEditor.putString("password",user.password);
         spEditor.commit();
    }
    public User getLoggedInUser(){
        String name = userLocalDatabase.getString("name","");
        int age = userLocalDatabase.getInt("age",-1);
        String userName = userLocalDatabase.getString("userName","");
        String password = userLocalDatabase.getString("password","");

        User user = new User(name,age,userName,password);
        return user;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn",loggedIn);
        spEditor.commit();
    }
    public void  clearUserDetails(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
