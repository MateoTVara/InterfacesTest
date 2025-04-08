/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VO;

/**
 *
 * @author Mateo Torres
 */
public class MedicoVo {
    int idmedico;
    String nombremedico;
    String especialidad;

    public MedicoVo(int idmedico, String nombremedico, String especialidad) {
        this.idmedico = idmedico;
        this.nombremedico = nombremedico;
        this.especialidad = especialidad;
    }

    public int getIdmedico() {
        return idmedico;
    }

    public void setIdmedico(int idmedico) {
        this.idmedico = idmedico;
    }

    public String getNombremedico() {
        return nombremedico;
    }

    public void setNombremedico(String nombre) {
        this.nombremedico = nombremedico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return nombremedico;
    }
    
}
