package com.example.sqlitedb_itp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn_insert,btn_read,btn_update,btn_delete;
    EditText et_id,et_name,et_qual,et_desig;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_insert=findViewById(R.id.btn_insert);
        btn_read=findViewById(R.id.btn_read);
        btn_update=findViewById(R.id.btn_update);
        btn_delete=findViewById(R.id.btn_delete);

        et_id=findViewById(R.id.et_eid);
        et_name=findViewById(R.id.et_ename);
        et_qual=findViewById(R.id.et_qual);
        et_desig=findViewById(R.id.et_desig);

        listView=findViewById(R.id.list_view);

        //step 1: create database
      SQLiteDatabase sqLiteDatabase= openOrCreateDatabase("ITP", Context.MODE_PRIVATE,null);

      //step 2: create table

        sqLiteDatabase.execSQL("create table if not exists Employees(_id integer primary key autoincrement,id integer,name varchar2(20),qual varchar2(20),desig varhar2(20))");

        btn_insert.setOnClickListener((view) ->{

           String id= et_id.getText().toString();
           String name=  et_name.getText().toString();
           String qual=  et_qual.getText().toString();
           String desig= et_desig.getText().toString();

            ContentValues cv=new ContentValues();
            cv.put("id",id);
            cv.put("name",name);
            cv.put("qual",qual);
            cv.put("desig",desig);

           Long status=sqLiteDatabase.insert("Employees",null,cv);
           if(status==-1)
           {
               Toast.makeText(this, "Failed to insert data", Toast.LENGTH_SHORT).show();
           }else
           {
               Toast.makeText(this, "data inserted succesfily!!", Toast.LENGTH_SHORT).show();
           }
            });

        btn_read.setOnClickListener((v) ->{


         Cursor cursor=  sqLiteDatabase.query("Employees",null,null,null,null,null,"name=?");

         String[] from=new String[]{"_id","name","qual","desig"};
         int[] to=new int[]{R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4};
         SimpleCursorAdapter cursorAdapter=new SimpleCursorAdapter(this,R.layout.my_design,cursor,from,to);
         listView.setAdapter(cursorAdapter);

        });

        btn_update.setOnClickListener((v)->{

            String id= et_id.getText().toString();
            String name=  et_name.getText().toString();
            String qual=  et_qual.getText().toString();
            String desig= et_desig.getText().toString();

            ContentValues cv=new ContentValues();
            cv.put("id",id);
            cv.put("name",name);
            cv.put("qual",qual);
            cv.put("desig",desig);


            int count=sqLiteDatabase.update("Employees",cv,"id=?",new String[]{et_id.getText().toString()});
            if(count>0)
                Toast.makeText(this, "updated Succesfully!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"failed to update",Toast.LENGTH_SHORT).show();
        });

        btn_delete.setOnClickListener((v)->{
            int count=sqLiteDatabase.delete("Employees","id=?",new String[]{et_id.getText().toString()});
            if(count>0)
                Toast.makeText(this, "deleted Succesfully!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"failed to delete",Toast.LENGTH_SHORT).show();
        });
    }
}