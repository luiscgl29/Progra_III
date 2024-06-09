/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arboles;

/**
 *
 * @author luis-
 */
public class NodoArbolAVL {

    long dpi;
    int fe;
    String nombre;
    String departamento;
    String municipio;
    String cantidadDosis;
    String fechaPrimera;
    String fechaSegunda;
    String fechaTercera;
    String lugarVacunacion;

    String textoGraphviz;

    NodoArbolAVL hijoIzquierdoAVL, hijoDerechoAVL;

    public NodoArbolAVL() {

    }

    public NodoArbolAVL(String nom, long cui) {
        this.nombre = nom;
        this.dpi = cui;
        this.departamento = "";
        this.municipio = "";
        this.cantidadDosis = "";
        this.fechaPrimera = "";
        this.fechaSegunda = "";
        this.fechaTercera = "";
        this.lugarVacunacion = "";
        this.fe = 0;
        this.hijoIzquierdoAVL = null;
        this.hijoDerechoAVL = null;
    }

    //METODOS GETTERS
    public long getDpi() {
        return dpi;
    }

    public int getFe() {
        return fe;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getCantidadDosis() {
        return cantidadDosis;
    }

    public String getFechaPrimera() {
        return fechaPrimera;
    }

    public String getFechaSegunda() {
        return fechaSegunda;
    }

    public String getFechaTercera() {
        return fechaTercera;
    }

    public String getLugarVacunacion() {
        return lugarVacunacion;
    }

    public String getTextoGraphviz() {
        return textoGraphviz;
    }

    public NodoArbolAVL getHijoIzquierdoAVL() {
        return hijoIzquierdoAVL;
    }

    public NodoArbolAVL getHijoDerechoAVL() {
        return hijoDerechoAVL;
    }

    //METODOS SETTERS

    public void setDpi(long dpi) {
        this.dpi = dpi;
    }

    public void setFe(int fe) {
        this.fe = fe;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setCantidadDosis(String cantidadDosis) {
        this.cantidadDosis = cantidadDosis;
    }

    public void setFechaPrimera(String fechaPrimera) {
        this.fechaPrimera = fechaPrimera;
    }

    public void setFechaSegunda(String fechaSegunda) {
        this.fechaSegunda = fechaSegunda;
    }

    public void setFechaTercera(String fechaTercera) {
        this.fechaTercera = fechaTercera;
    }

    public void setLugarVacunacion(String lugarVacunacion) {
        this.lugarVacunacion = lugarVacunacion;
    }

    public void setTextoGraphviz(String textoGraphviz) {
        this.textoGraphviz = textoGraphviz;
    }

    public void setHijoIzquierdoAVL(NodoArbolAVL hijoIzquierdoAVL) {
        this.hijoIzquierdoAVL = hijoIzquierdoAVL;
    }

    public void setHijoDerechoAVL(NodoArbolAVL hijoDerechoAVL) {
        this.hijoDerechoAVL = hijoDerechoAVL;
    }
    
}
