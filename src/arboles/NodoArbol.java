package arboles;

public class NodoArbol {

    long dpi;
    String nombre;
    String departamento;
    String municipio;
    String cantidadDosis;
    String fechaPrimera;
    String fechaSegunda;
    String fechaTercera;
    String lugarVacunacion;
    
    String textoGraphviz;
    
    NodoArbol HijoIzquierdo, HijoDerecho;

    public NodoArbol() {

    }

    public NodoArbol(String nom,long cui) {
        this.nombre = nom;
        this.dpi = cui;
        this.departamento = "";
        this.municipio = "";
        this.cantidadDosis = "";
        this.fechaPrimera = "";
        this.fechaSegunda = "";
        this.fechaTercera = "";
        this.lugarVacunacion = "";
        this.HijoIzquierdo = null;
        this.HijoDerecho = null;
    }

    //METODOS GETTERS
    public long getDpi() {
        return dpi;
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

    public NodoArbol getHijoIzquierdo() {
        return HijoIzquierdo;
    }

    public NodoArbol getHijoDerecho() {
        return HijoDerecho;
    }

    //METODOS SETTERS
    public void setDpi(long dpi) {
        this.dpi = dpi;
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

    public void setHijoIzquierdo(NodoArbol HijoIzquierdo) {
        this.HijoIzquierdo = HijoIzquierdo;
    }

    public void setHijoDerecho(NodoArbol HijoDerecho) {
        this.HijoDerecho = HijoDerecho;
    }
}
