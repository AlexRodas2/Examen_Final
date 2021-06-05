
package Conexion_BD;

import java.sql.*;
import javax.swing.*;
import java.sql.DriverManager;

public class Conectar {
    Connection conect= null;
    
    public Connection conexion(){
       try{
           // carga del Driver de MySQL
           Class.forName("com.mysql..jdbc.Driver");
           conect=(Connection) DriverManager.getConnection("jdbc:mysql://localhost/examenfinal");
       }catch(Exception e){
           
       }
       return conect;
   }
    
}
