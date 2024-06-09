package arboles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ArbolBinario {

    public NodoArbol raiz;

    public ArbolBinario() {
        raiz = null;
    }

    //Metodo para insertar nodo en el arbol
    public void AgregarNodo(String nom, long cui) {
        if(raiz == null){
            raiz = new NodoArbol(nom, cui);
            return;
        }
        if(existenciaNodo(cui)){
            return;
        }
        
        NodoArbol nuevo = new NodoArbol(nom, cui);

        if (raiz == null) {
            raiz = nuevo;
        } else {
            NodoArbol auxiliar = raiz;
            NodoArbol padre;

            while (true) {
                padre = auxiliar;
                if (cui < auxiliar.dpi) {
                    auxiliar = auxiliar.HijoIzquierdo;
                    if (auxiliar == null) {
                        padre.HijoIzquierdo = nuevo;
                        return;
                    }
                } else {
                    auxiliar = auxiliar.HijoDerecho;
                    if (auxiliar == null) {
                        padre.HijoDerecho = nuevo;
                        return;
                    }
                }
            }
        }
    }
    
    public boolean existenciaNodo(long cui){
        return BuscarNodo(cui) != null;
    }

    public boolean EstaVacio() {
        return raiz == null;
    }

    //Metodo para buscar un Nodo en el arbol
    public NodoArbol BuscarNodo(long d) {
        NodoArbol aux = raiz;
        while (aux.dpi != d) {
            if (d < aux.dpi) {
                aux = aux.HijoIzquierdo;
            } else {
                aux = aux.HijoDerecho;
            }
            if (aux == null) {
                return null;
            }
        }
        return aux;
    }

    public boolean EliminarNodo(long d) {
        NodoArbol auxiliar = raiz;
        NodoArbol padre = raiz;
        boolean EsHijoIzq = true;

        while (auxiliar.dpi != d) {
            padre = auxiliar;
            if (d < auxiliar.dpi) {
                EsHijoIzq = true;
                auxiliar = auxiliar.HijoIzquierdo;
            } else {
                EsHijoIzq = false;
                auxiliar = auxiliar.HijoDerecho;
            }
            if (auxiliar == null) {
                return false; //nunca lo encontro
            }
        }//fin del while

        if (auxiliar.HijoIzquierdo == null && auxiliar.HijoDerecho == null) { //caso para una hoja
            if (auxiliar == raiz) {
                raiz = null;
            } else if (EsHijoIzq) {
                padre.HijoIzquierdo = null;
            } else {
                padre.HijoDerecho = null;
            }
        } else if (auxiliar.HijoDerecho == null) {
            if (auxiliar == raiz) {
                raiz = auxiliar.HijoIzquierdo;
            } else if (EsHijoIzq) {
                padre.HijoIzquierdo = auxiliar.HijoIzquierdo;
            } else {
                padre.HijoDerecho = auxiliar.HijoIzquierdo;
            }
        } else if (auxiliar.HijoIzquierdo == null) {
            if (auxiliar == raiz) {
                raiz = auxiliar.HijoDerecho;
            } else if (EsHijoIzq) {
                padre.HijoIzquierdo = auxiliar.HijoDerecho;
            } else {
                padre.HijoDerecho = auxiliar.HijoDerecho;
            }
        } else {
            NodoArbol reemplazo = ObtenerNodoReemplazo(auxiliar);
            if (auxiliar == raiz) {
                raiz = reemplazo;
            } else if (EsHijoIzq) {
                padre.HijoIzquierdo = reemplazo;
            } else {
                padre.HijoDerecho = reemplazo;
            }
            reemplazo.HijoIzquierdo = auxiliar.HijoIzquierdo;
        }
        return true;
    }

    public NodoArbol ObtenerNodoReemplazo(NodoArbol nodoreemp) {
        NodoArbol reemplazopadre = nodoreemp;
        NodoArbol reemplazo = nodoreemp;
        NodoArbol auxiliar = nodoreemp.HijoDerecho;

        while (auxiliar != null) {
            reemplazopadre = reemplazo;
            reemplazo = auxiliar;
            auxiliar = auxiliar.HijoIzquierdo;
        }
        if (reemplazo != nodoreemp.HijoDerecho) {
            reemplazopadre.HijoIzquierdo = reemplazo.HijoDerecho;
            reemplazo.HijoDerecho = nodoreemp.HijoDerecho;

        }
        System.out.println("\nEl nodo reemplazo es: " + reemplazo.dpi);
        return reemplazo;
    }

    public void cargarArchivo(File archivo) {
        try {
            BufferedReader leer = new BufferedReader(new FileReader(archivo));
            //Salto de Linea para Encabezado del documento NOMBRE "TABULADOR" DPI
            //leer.readLine();
            String linea;
            while ((linea = leer.readLine()) != null) {
                //Separa el documento mediante tabulador
                String[] partes = linea.split("\t");

                //Guarda nombre y dpi en arreglos aparte
                String nombre = partes[0];
                String dpi = partes[1];

                //
                Pattern pt = Pattern.compile("\\d+");
                Matcher mt = pt.matcher(dpi);

                String ndpi = "";

                while (mt.find()) {
                    ndpi += mt.group();
                }

                Long dpiEntero = Long.parseLong(ndpi);
                AgregarNodo(nombre, dpiEntero);
            }
            leer.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en lectura del archivo");
        }
    }

    public void imprimir(NodoArbol r, FileWriter guardar) {
        try {
            if (r != null) {
                imprimir(r.HijoIzquierdo, guardar);
                imprimir(r.HijoDerecho, guardar);
                String datos = r.nombre + "\t" + r.dpi + "\t"
                        + r.departamento + "\t" + r.municipio + "\t" + r.cantidadDosis + "\t"
                        + r.fechaPrimera + "\t" + r.fechaSegunda + "\t" + r.fechaTercera + "\t"
                        + r.lugarVacunacion + "\n";
                guardar.write(datos);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en recorrido");
        }
    }

    public void guardarArchivo() {
        try {
            FileWriter guardar = new FileWriter("C:\\Users\\luis-\\Documents\\NetBeansProjects\\Proyecto_Progra3\\src\\Archivo\\PacientesGuardadosABB.txt");
            //Agrega Encabezado a documento
            //guardar.write("NOMBRE DE PERSONA VACUNADA" + "\t" + "NÚMERO DE IDENTIFICACIÓN" + "\n");
            imprimir(raiz, guardar);
            guardar.close();
            JOptionPane.showMessageDialog(null, "Lineas guardadas");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en escritura del archivo");
        }
    }

    //CODIGO PARA GRAPHVIZ
    //ASIGNA RELACIONES Y DECLARA VARIABLES
    public void imprimirGraphviz(NodoArbol r, StringBuilder texto) {
        if (r != null) {
            imprimirGraphviz(r.HijoIzquierdo, texto);
            imprimirGraphviz(r.HijoDerecho, texto);

            texto.append(r.getDpi()).append("[label = \"").append(r.getNombre()).append("\"]\n");

            if (r.HijoIzquierdo != null) {
                texto.append(r.getDpi()).append("->").append(r.HijoIzquierdo.getDpi()).append("\n");
            }
            if (r.HijoDerecho != null) {
                texto.append(r.getDpi()).append("->").append(r.HijoDerecho.getDpi()).append("\n");
            }
        }
    }

    //UNE INFORMACION DE CADA NODO Y CONVIERTE A TEXTO
    public String getTextoGraphviz() {
        StringBuilder texto = new StringBuilder();
        imprimirGraphviz(raiz, texto);
        return texto.toString();
    }

    //COMPLEMENTA CODIGO PARA GRAPHVIZ Y AGREGA FORMATO
    public String codigoGraphviz() {
        String texto = "digraph G\n" + "{\n" + "node [shape = circle]\n"
                + "node [style = filled]\n" + "node [fillcolor = \"#EEEEE\"]\n" + "node [color = \"#EEEEE\"]\n"
                + "edge [color = \"#31CEFO\"]\n";
        if (raiz != null) {
            texto += getTextoGraphviz();
        }
        texto += "\n}";
        return texto;
    }

    //CAPTURA EL CODIGO COMPLETO DE GRAPHVIZ
    public void archivoGraphviz(String ruta, String contenido) {
        FileWriter archivo = null;
        PrintWriter pw = null;
        try {
            archivo = new FileWriter(ruta);
            pw = new PrintWriter(archivo);
            pw.write(contenido);
            pw.close();
            archivo.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //PROCESA Y CREA EL ARCHIVO PNG
    public void dibujarGraphviz() {
        try {
            archivoGraphviz("archivoABB.dot", codigoGraphviz());
            ProcessBuilder proceso = new ProcessBuilder("dot", "-Tpng", "-o", "arbolABB.png", "archivoABB.dot");
            proceso.redirectErrorStream(true);
            proceso.start();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CODIFICACION DE INFORMACION DE ARBOL ABB
    //REALIZA RECORRIDO Y DEFINE LOS CARACTERES PARA CODIFICAR
    public void datosCodificar(NodoArbol r) {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZÑÁÉÍÓÚ/1234567890";
        if (r != null) {
            datosCodificar(r.HijoIzquierdo);
            datosCodificar(r.HijoDerecho);
            r.nombre = codificarLetras(letras, r.nombre);
            r.dpi = codificarNumero(r.dpi);
            r.departamento = codificarLetras(letras, r.departamento);
            r.municipio = codificarLetras(letras, r.municipio);
            r.cantidadDosis = codificarLetras(letras, r.cantidadDosis);
            r.fechaPrimera = codificarLetras(letras, r.fechaPrimera);
            r.fechaSegunda = codificarLetras(letras, r.fechaSegunda);
            r.fechaTercera = codificarLetras(letras, r.fechaTercera);
            r.lugarVacunacion = codificarLetras(letras, r.lugarVacunacion);
        }
    }

    //REALIZA RECORRIDO Y DEFINE CARACTERES PARA DECODIFICAR
    public void datosDecodificar(NodoArbol r) {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZÑÁÉÍÓÚ/1234567890";
        if (r != null) {
            datosDecodificar(r.HijoIzquierdo);
            datosDecodificar(r.HijoDerecho);
            r.nombre = decodificarLetras(letras, r.nombre);
            r.dpi = decodificarNumero(r.dpi);
            r.departamento = decodificarLetras(letras, r.departamento);
            r.municipio = decodificarLetras(letras, r.municipio);
            r.cantidadDosis = decodificarLetras(letras, r.cantidadDosis);
            r.fechaPrimera = decodificarLetras(letras, r.fechaPrimera);
            r.fechaSegunda = decodificarLetras(letras, r.fechaSegunda);
            r.fechaTercera = decodificarLetras(letras, r.fechaTercera);
            r.lugarVacunacion = decodificarLetras(letras, r.lugarVacunacion);
        }
    }

    public static String codificarLetras(String letras, String texto) {
        String letrasMinusculas = letras.toLowerCase();
        StringBuilder textoCodificado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {
            char caracter = texto.charAt(i);

            boolean esMayuscula = Character.isUpperCase(caracter);
            char caracterMayuscula = Character.toUpperCase(caracter);
            int pos = letras.indexOf(caracterMayuscula);

            if (pos == -1) {
                textoCodificado.append(caracter);
            } else {
                char nuevoCaracter = letras.charAt((pos + 3) % letras.length());
                if (esMayuscula) {
                    textoCodificado.append(nuevoCaracter);
                } else {
                    textoCodificado.append(Character.toLowerCase(nuevoCaracter));
                }
            }
        }

        return textoCodificado.toString();
    }

    public long codificarNumero(long numero) {
        String numeroTexto = String.valueOf(numero);
        StringBuilder numeroCodificado = new StringBuilder();

        for (int i = 0; i < numeroTexto.length(); i++) {
            char digito = numeroTexto.charAt(i);
            if (Character.isDigit(digito)) {
                int nuevoDigito = (Character.getNumericValue(digito) + 3) % 10;
                numeroCodificado.append(nuevoDigito);
            } else {
                numeroCodificado.append(digito);
            }
        }

        return Long.parseLong(numeroCodificado.toString());
    }

    //USAR RECORRIDO PARA GUARDAR INFORMACION CIFRADA
    public void txtCrifrado(NodoArbol raiz) {
        StringBuilder contenido = new StringBuilder();

        recorrerYCodificar(raiz, contenido);

        try (BufferedWriter escribir = new BufferedWriter(new FileWriter("C:\\Users\\luis-\\Documents\\NetBeansProjects\\Proyecto_Progra3\\src\\Archivo\\PacientesGuardadosABB.txt"))) {
            escribir.write(contenido.toString());
            JOptionPane.showMessageDialog(null, "Datos codificados guardados correctamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo codificado: " + e.getMessage());
        }
    }

    private void recorrerYCodificar(NodoArbol r, StringBuilder contenido) {
        if (r != null) {
            //contenido.append(r.nombre).append("\t").append(r.dpi).append("\n");
            contenido.append(r.nombre).append("\t").append(r.dpi).append("\t").append(r.departamento).append("\t").append(r.municipio).append("\t").append(r.cantidadDosis).append("\t").append(r.fechaPrimera).append("\t").append(r.fechaSegunda).append("\t").append(r.lugarVacunacion).append("\n");
            recorrerYCodificar(r.HijoIzquierdo, contenido);
            recorrerYCodificar(r.HijoDerecho, contenido);
        }
    }

    public static String decodificarLetras(String letras, String texto) {
        String letrasMinusculas = letras.toLowerCase();
        StringBuilder textoDecodificado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {
            char caracter = texto.charAt(i);

            boolean esMayuscula = Character.isUpperCase(caracter);
            char caracterMayuscula = Character.toUpperCase(caracter);
            int pos = letras.indexOf(caracterMayuscula);

            if (pos == -1) {
                textoDecodificado.append(caracter);
            } else {
                int nuevaPos = (pos - 3 + letras.length()) % letras.length();
                char nuevoCaracter = letras.charAt(nuevaPos);
                if (esMayuscula) {
                    textoDecodificado.append(nuevoCaracter);
                } else {
                    textoDecodificado.append(Character.toLowerCase(nuevoCaracter));
                }
            }
        }

        return textoDecodificado.toString();
    }

    public static long decodificarNumero(long numero) {
        String numeroStr = String.valueOf(numero);
        StringBuilder numeroDecodificado = new StringBuilder();

        for (int i = 0; i < numeroStr.length(); i++) {
            char digito = numeroStr.charAt(i);
            if (Character.isDigit(digito)) {
                int nuevoDigito = (Character.getNumericValue(digito) - 3 + 10) % 10;
                numeroDecodificado.append(nuevoDigito);
            } else {
                numeroDecodificado.append(digito);
            }
        }

        return Long.parseLong(numeroDecodificado.toString());
    }

    public void mostrarDatos(JTable informacion, int numero) {
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Nombre");
        modelo.addColumn("DPI");
        modelo.addColumn("Departamento");
        modelo.addColumn("Municipio");
        modelo.addColumn("Cantidad Dosis");
        modelo.addColumn("1ra Vacuna");
        modelo.addColumn("2da Vacuna");
        modelo.addColumn("3ra Vacuna");
        modelo.addColumn("Lugar Vacunacion");

        switch (numero) {
            case 1:
                imprimirTablaPreorden(raiz, modelo);
                break;
            case 2:
                imprimirTablaInorden(raiz, modelo);
                break;
            case 3:
                imprimirTablaPostorden(raiz, modelo);
                break;
        }

        informacion.setModel(modelo);
    }

    public void imprimirTablaPreorden(NodoArbol r, DefaultTableModel modelo) {
        if (r != null) {
            Object[] dTabla = {r.nombre, r.dpi, r.departamento, r.municipio, r.cantidadDosis, r.fechaPrimera, r.fechaSegunda, r.fechaTercera, r.lugarVacunacion};
            modelo.addRow(dTabla);

            imprimirTablaPreorden(r.HijoIzquierdo, modelo);
            imprimirTablaPreorden(r.HijoDerecho, modelo);
        }
    }

    public void imprimirTablaInorden(NodoArbol r, DefaultTableModel modelo) {
        if (r != null) {
            imprimirTablaInorden(r.HijoIzquierdo, modelo);

            Object[] dTabla = {r.nombre, r.dpi, r.departamento, r.municipio, r.cantidadDosis, r.fechaPrimera, r.fechaSegunda, r.fechaTercera, r.lugarVacunacion};
            modelo.addRow(dTabla);
            imprimirTablaInorden(r.HijoDerecho, modelo);
        }
    }

    public void imprimirTablaPostorden(NodoArbol r, DefaultTableModel modelo) {
        if (r != null) {
            imprimirTablaPostorden(r.HijoIzquierdo, modelo);
            imprimirTablaPostorden(r.HijoDerecho, modelo);

            Object[] dTabla = {r.nombre, r.dpi, r.departamento, r.municipio, r.cantidadDosis, r.fechaPrimera, r.fechaSegunda, r.fechaTercera, r.lugarVacunacion};
            modelo.addRow(dTabla);
        }
    }

    public void limpiarTabla(JTable pTbDatos) {
        DefaultTableModel modelo = new DefaultTableModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i--;
        }

        modelo.addColumn("Nombre");
        modelo.addColumn("DPI");
        modelo.addColumn("Departamento");
        modelo.addColumn("Municipio");
        modelo.addColumn("Cantidad Dosis");
        modelo.addColumn("1ra Vacuna");
        modelo.addColumn("2da Vacuna");
        modelo.addColumn("3ra Vacuna");
        modelo.addColumn("Lugar Vacunacion");

        pTbDatos.setModel(modelo);
    }
}
