package model.classes;

import model.DB.DB;
import java.sql.Connection;

public class Principal {
    public static void main(String[] args) {
        Connection con = DB.getConnection();
        if (con != null) {
            System.out.println("Conexão está OK e funcionando!");
        }
    }
}
