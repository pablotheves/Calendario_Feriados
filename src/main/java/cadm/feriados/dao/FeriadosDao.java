/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadm.feriados.dao;

import static java.nio.file.Files.list;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import model.DB.DB;
import model.classes.Feriado;

/**
 *
 * @author pablo
 */
public class FeriadosDao {
    private Connection con;
    
    public FeriadosDao(Connection con){
        this.con = con;
        
    }
    
    public List<Feriado> getAll(){
        List<Feriado> List = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try{
            String sql = "SELECT * FROM Feriados";
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();

            while (res.next()) {
                Feriado feriado = new Feriado(
                        res.getInt("id"),
                        res.getString("nome"),
                        res.getDate("dataFeriado"),
                        res.getString("tipo")
                );
                System.out.println(feriado);
                List.add(feriado);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            
        }
        return List;
        
    }
    
    public boolean inserir(Feriado feriado){
        PreparedStatement stmt = null;
        boolean result = false;
        
        try {
            String sql = "INSERT INTO feriados (nome, dataFeriado, tipo)" +
                    "VALUES (?,?,?)";
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, feriado.getNome());
            stmt.setDate(2, feriado.getDataFeriado());
            stmt.setString(3, feriado.getTipo());
            
            int rowsAffected = stmt.executeUpdate();
            
            if(rowsAffected > 0){
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()) {
                    int id = rs.getInt(1);
                    feriado.setId(id);
                    result = true;
                }
            } else{
                throw new SQLException("Nao foi possivel inserir");
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();

        }finally{
            DB.closeStatement(stmt);
            return result;
        }
        
    }
    
    public boolean editar(Feriado feriado) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "update feriados set nome = ?, "+
                         " dataFeriado = ?, "+
                         " tipo = ? "+
                         " where id = ?";
            
            stmt = con.prepareStatement(sql);

            stmt.setString(1, feriado.getNome());
            stmt.setDate(2, feriado.getDataFeriado());
            stmt.setString(3, feriado.getTipo());
            stmt.setInt(4, feriado.getId());

            stmt.executeUpdate();
            result = true;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean excluir(Feriado feriado) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from feriados where id = ?";
            stmt = con.prepareStatement(sql);

            stmt.setInt(1, feriado.getId());

            stmt.executeUpdate();
            result = true;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }   
    
    
}
