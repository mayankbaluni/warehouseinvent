package com.salor.ventgo.db.dao_user;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.salor.ventgo.obj.login.DataUser;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    long insertUser(DataUser User);

    @Insert
    void insertUserList(List<DataUser> UserList);

    @Query("SELECT * FROM " + Database.DB_NAME)
    List<DataUser> fetchAllUsers();


    @Query("SELECT * FROM " + Database.DB_NAME + " WHERE idLokal = :UserId")
    DataUser fetchUserListById(int UserId);

    @Update
    int updateUser(DataUser User);

    @Delete
    int deleteUser(DataUser User);

    @Query("DELETE FROM user_db")
    void deleteAll();

}

 //       userDatabase = Room.databaseBuilder(applicationContext, UserDatabase::class.java, Cons.DB_NAME_LOGIN_USER).fallbackToDestructiveMigration().build()


//    @SuppressLint("StaticFieldLeak")
//    private fun fetchTodoById(todo_id: Int) {
//        object : AsyncTask<Int, Void, DataLogin>() {
//            override fun doInBackground(vararg p0: Int?): DataLogin? {
//
//            return userDatabase.daoAccess().fetchUserListById(p0[0]!!)
//
//            }
//
//            override fun onPostExecute(user: DataLogin) {
//                super.onPostExecute(user)
//                loadImage.LoadImagePicasso(user.image,ivImage,pbLoading)
//
//                tvName.text = user.name
//                tvEmail.text = user.email
//
//                tvJabatan.text = user.departement + " | " + user.company
//
//
//                dataLogin = user
//            }
//        }.execute(todo_id)
//
//    }

//
//    @SuppressLint("StaticFieldLeak")
//    private void updateRow(User todo) {
//        new AsyncTask<User, Void, Integer>() {
//            @Override
//            protected Integer doInBackground(User... params) {
//                return userDatabase.daoAccess().updateUser(params[0]);
//            }
//
//            @Override
//            protected void onPostExecute(Integer number) {
//                super.onPostExecute(number);
//
//                Intent intent = getIntent();
//                setResult(Cons.RESULT_UPDATE_ROOM_DATA, intent);
//                finish();
//
//            }
//        }.execute(todo);
//
//    }

//    @SuppressLint("StaticFieldLeak")
//    private fun loadAllUsers() {
//        object : AsyncTask<String, Void, List<DataLogin>>() {
//            override fun doInBackground(vararg params: String): List<DataLogin> {
//                return userDatabase.daoAccess().fetchAllUsers()
//            }
//
//            override fun onPostExecute(userList: List<DataLogin>) {
//
//                if(userList.isNotEmpty()){
//                    deleteAllUser()
//                }else{
//
//                    loginServer(str_nik,str_password)
//
//                }
//
//            }
//        }.execute()
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private fun deleteAllUser() {
//        object : AsyncTask<String, Void, String>() {
//            override fun doInBackground(vararg params: String?): String? {
//
//                    userDatabase.daoAccess().deleteAll()
//
//
//            return ""
//            }
//
//            override fun onPostExecute(result: String?) {
//
//                loginServer(str_nik,str_password)
//            }
//        }.execute()
//
//    }
//
//
//
//    @SuppressLint("StaticFieldLeak")
//    private fun insertRow(user: DataLogin) {
//        object : AsyncTask<DataLogin, Void, Long>() {
//            override fun doInBackground(vararg params: DataLogin): Long? {
//            return userDatabase.daoAccess().insertUser(params[0])
//            }
//
//            override fun onPostExecute(id: Long?) {
//                super.onPostExecute(id)
//
//                DBS.with(this@LoginActivity).isLogin = true
//
//
//                val intent = Intent(this@LoginActivity, HomeSliderActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(intent)
//
//                finish()
//
//
//            }
//        }.execute(user)
//
//    }
