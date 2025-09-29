/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.classes;

import java.sql.Date;

/**
 *
 * @author pablo
 */
public class Feriado {
    private int id;
    private String nome;
    private java.sql.Date dataFeriado;
    private String tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataFeriado() {
        return dataFeriado;
    }

    public void setDataFeriado(Date dataFeriado) {
        this.dataFeriado = dataFeriado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Feriados{" + "id=" + id + ", nome=" + nome + ", dataFeriado=" + dataFeriado + ", tipo=" + tipo + '}';
    }

    public Feriado(int id, String nome, Date dataFeriado, String tipo) {
        this.id = id;
        this.nome = nome;
        this.dataFeriado = dataFeriado;
        this.tipo = tipo;
    }
    
    public Feriado(){
        
    }
    
    
    
    
    
}
