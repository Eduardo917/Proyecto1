package sample.Models;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    private static String server="127.0.0.1";
    private static String usuario="topicos2020";
    private static String pwd="12345";
    private static String db="restaurante";

    public static Connection conexion;
    public static void crearConexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion=DriverManager.getConnection("jdbc:mysql://"+server+":3306/"+db,usuario,pwd);
        }
        catch (Exception e){e.printStackTrace();}
    }
}
