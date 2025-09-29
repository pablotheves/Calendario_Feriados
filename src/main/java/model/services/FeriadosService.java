/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.services;

import cadm.feriados.dao.FeriadosDao;
import java.util.List;
import model.DB.DB;
import model.classes.Feriado;

/**
 *
 * @author pablo
 */
public class FeriadosService {
    private FeriadosDao dao = new FeriadosDao(DB.getConnection()); 
    
    public List<Feriado> getAll(){
        return dao.getAll(); 
    }
    
    public boolean salvarOuAtualizar(Feriado feriado) {
        if (feriado.getId()<= 0) {
            return dao.inserir(feriado);
        } else {
            return dao.editar(feriado);
        }
    }
    
    public boolean excluir(Feriado feriado){
        return dao.excluir(feriado);
    
    }
}
