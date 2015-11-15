package com.android.gestiondesbiens;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import android.util.Log;

public class MySQLConnector {
	
	public void testDB() {
        //TextView tv = (TextView) this.findViewById(R.id.tv_data);
        try {

        	Class.forName("com.mysql.jdbc.Driver");
        	Connection conn = null;
        	conn = DriverManager.getConnection("jdbc:mysql://198.71.225.54:3306/mysql_android?user=mysql_android&password=Lebanon_2015");
        
        	
      
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from tablename ");
            
            while(rs.next()){
            	Log.w("id", rs.getString("id"));
            	Log.w("nane", rs.getString("name"));
            	
            }
            conn.close();
        	//Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
           // tv.setText(e.toString());
            //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }

}
