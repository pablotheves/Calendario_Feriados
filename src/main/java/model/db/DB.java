package model.DB;

import java.sql.*;

/**
 *
 * @author aluno
 */
public class DB {

    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/calendario",
                    "root",
                    ""
            );
            System.out.println("Conexão bem-sucedida!");
            
            return con;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro na conexão: " + e.getMessage());
            return null;
        }
                
             
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
