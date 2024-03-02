package sql;

import java.sql.*;
import java.util.*;

/**
 * <p>Description:	<br>Die Klasse ConnectionProvider kapselt die Informnationen zum Zugriff
 *									auf die Tabellen der Scheinverwaltung in der MySQL-Datenbank</p>
 * <p>Company:     	Universität Marburg</p>
 * <p>Copyright:   	Copyright (c) 2001</p>
 * @title:       		ConnectionProvider.java
 * @author      		Olaf Siefart
 * @version     		1.0
 */

class ConnectionProvider {
    
    // Konstanten
    private static final String db_driver_name = "org.gjt.mm.mysql.Driver";
    private static final String db_url         = "jdbc:mysql://localhost/praktikanten";
    private static final String db_user        = "root";
    private static final String db_pw          = "";
    private static final Properties db_prop    = db_properties();

    // Konstruktoren
    private ConnectionProvider(){}
		
		/**
		Setzt die Eigenschaften der Datenbank
		@return Properties
		*/
    private static Properties db_properties () {
        Properties prop = new Properties();
        prop.put("user", db_user);
        prop.put("password", db_pw);
        prop.put ("autoReconnect", "true");
        return prop;
    }

		/**
		Fordert eine Connection zur Datenbank an
		@return Connection
		*/
    public static Connection getConnection() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
        Driver drv = (Driver) Class.forName(db_driver_name).newInstance();
        Connection conn = java.sql.DriverManager.getConnection(db_url, db_prop);
        return conn;
    }
}