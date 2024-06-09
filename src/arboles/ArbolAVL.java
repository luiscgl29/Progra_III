/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author luis-
 */
public class ArbolAVL {

    public NodoArbolAVL raiz;

    public ArbolAVL() {
        raiz = null;
    }

    public NodoArbolAVL buscar(long d) {
        NodoArbolAVL aux = raiz;
        while (aux.dpi != d) {
            if (d < aux.dpi) {
                aux = aux.hijoIzquierdoAVL;
            } else {
                aux = aux.hijoDerechoAVL;
            }
            if (aux == null) {
                JOptionPane.showMessageDialog(null, "El registro del paciente no existe");
                return null;
            }
        }
        JOptionPane.showMessageDialog(null, "Paciente encontrado");
        return aux;
    }

    public int obtenerFE(NodoArbolAVL x) {
        if (x == null) {
            return -1;
        } else {
            return x.fe;
        }
    }

    public NodoArbolAVL rotacionIzquierda(NodoArbolAVL c) {
        NodoArbolAVL auxiliar = c.hijoIzquierdoAVL;
        c.hijoIzquierdoAVL = auxiliar.hijoDerechoAVL;
        auxiliar.hijoDerechoAVL = c;
        c.fe = Math.max(obtenerFE(c.hijoIzquierdoAVL), obtenerFE(c.hijoDerechoAVL)) + 1;
        auxiliar.fe = Math.max(obtenerFE(auxiliar.hijoIzquierdoAVL), obtenerFE(auxiliar.hijoDerechoAVL)) + 1;
        return auxiliar;
    }

    public NodoArbolAVL rotacionDerecha(NodoArbolAVL c) {
        NodoArbolAVL auxiliar = c.hijoDerechoAVL;
        c.hijoDerechoAVL = auxiliar.hijoIzquierdoAVL;
        auxiliar.hijoIzquierdoAVL = c;
        c.fe = Math.max(obtenerFE(c.hijoIzquierdoAVL), obtenerFE(c.hijoDerechoAVL)) + 1;
        auxiliar.fe = Math.max(obtenerFE(auxiliar.hijoIzquierdoAVL), obtenerFE(auxiliar.hijoDerechoAVL)) + 1;
        return auxiliar;
    }

    public NodoArbolAVL rotacionDobleIzquierda(NodoArbolAVL c) {
        NodoArbolAVL temporal;
        c.hijoIzquierdoAVL = rotacionDerecha(c.hijoIzquierdoAVL);
        temporal = rotacionIzquierda(c);
        return temporal;
    }

    public NodoArbolAVL rotacionDobleDerecha(NodoArbolAVL c) {
        NodoArbolAVL temporal;
        c.hijoDerechoAVL = rotacionIzquierda(c.hijoDerechoAVL);
        temporal = rotacionDerecha(c);
        return temporal;
    }

    public NodoArbolAVL insertarAVL(NodoArbolAVL nuevo, NodoArbolAVL subAr) {
        NodoArbolAVL nuevoPadre = subAr;
        if (nuevo.dpi < subAr.dpi) {
            if (subAr.hijoIzquierdoAVL == null) {
                subAr.hijoIzquierdoAVL = nuevo;
            } else {
                subAr.hijoIzquierdoAVL = insertarAVL(nuevo, subAr.hijoIzquierdoAVL);
                if ((obtenerFE(subAr.hijoIzquierdoAVL) - obtenerFE(subAr.hijoDerechoAVL)) == 2) {
                    if (nuevo.dpi < subAr.hijoIzquierdoAVL.dpi) {
                        nuevoPadre = rotacionIzquierda(subAr);
                    } else {
                        nuevoPadre = rotacionDobleIzquierda(subAr);
                    }
                }
            }
        } else if (nuevo.dpi > subAr.dpi) {
            if (subAr.hijoDerechoAVL == null) {
                subAr.hijoDerechoAVL = nuevo;
            } else {
                subAr.hijoDerechoAVL = insertarAVL(nuevo, subAr.hijoDerechoAVL);
                if ((obtenerFE(subAr.hijoDerechoAVL) - obtenerFE(subAr.hijoIzquierdoAVL)) == 2) {
                    if (nuevo.dpi > subAr.hijoDerechoAVL.dpi) {
                        nuevoPadre = rotacionDerecha(subAr);
                    } else {
                        nuevoPadre = rotacionDobleDerecha(subAr);
                    }
                }
            }
            //Nodo repetido
        } else {
            System.out.println("Nodo Duplicado");
        }

        //Actualizacion de altura
        if ((subAr.hijoIzquierdoAVL == null) && (subAr.hijoDerechoAVL != null)) {
            subAr.fe = subAr.hijoDerechoAVL.fe + 1;
        } else if ((subAr.hijoDerechoAVL == null) && (subAr.hijoIzquierdoAVL != null)) {
            subAr.fe = subAr.hijoIzquierdoAVL.fe + 1;
        } else {
            subAr.fe = Math.max(obtenerFE(subAr.hijoIzquierdoAVL), obtenerFE(subAr.hijoDerechoAVL)) + 1;
        }
        return nuevoPadre;
    }

    public void insertar(String nom, long d) {
        NodoArbolAVL nuevo = new NodoArbolAVL(nom, d);
        if (raiz == null) {
            raiz = nuevo;
        } else {
            raiz = insertarAVL(nuevo, raiz);
        }
    }

    public NodoArbolAVL eliminarAVL(NodoArbolAVL nodo, long dpi) {
        if (nodo == null) {
            JOptionPane.showMessageDialog(null, "El registro del paciente no existe");
            return null;
        }

        if (dpi < nodo.dpi) {
            nodo.hijoIzquierdoAVL = eliminarAVL(nodo.hijoIzquierdoAVL, dpi);
            if ((obtenerFE(nodo.hijoDerechoAVL) - obtenerFE(nodo.hijoIzquierdoAVL)) == 2) {
                NodoArbolAVL hijoDerecho = nodo.hijoDerechoAVL;
                if (obtenerFE(hijoDerecho.hijoIzquierdoAVL) > obtenerFE(hijoDerecho.hijoDerechoAVL)) {
                    nodo = rotacionDobleDerecha(nodo);
                } else {
                    nodo = rotacionDerecha(nodo);
                }
            }
        } else if (dpi > nodo.dpi) {
            nodo.hijoDerechoAVL = eliminarAVL(nodo.hijoDerechoAVL, dpi);
            if ((obtenerFE(nodo.hijoIzquierdoAVL) - obtenerFE(nodo.hijoDerechoAVL)) == 2) {
                NodoArbolAVL hijoIzquierdo = nodo.hijoIzquierdoAVL;
                if (obtenerFE(hijoIzquierdo.hijoDerechoAVL) > obtenerFE(hijoIzquierdo.hijoIzquierdoAVL)) {
                    nodo = rotacionDobleIzquierda(nodo);
                } else {
                    nodo = rotacionIzquierda(nodo);
                }
            }
        } else {
            if (nodo.hijoIzquierdoAVL == null || nodo.hijoDerechoAVL == null) {
                nodo = (nodo.hijoIzquierdoAVL == null) ? nodo.hijoDerechoAVL : nodo.hijoIzquierdoAVL;
            } else {
                NodoArbolAVL temp = nodoMenor(nodo.hijoDerechoAVL);
                nodo.dpi = temp.dpi;
                nodo.nombre = temp.nombre;
                nodo.hijoDerechoAVL = eliminarAVL(nodo.hijoDerechoAVL, temp.dpi);
                if ((obtenerFE(nodo.hijoIzquierdoAVL) - obtenerFE(nodo.hijoDerechoAVL)) == 2) {
                    NodoArbolAVL hijoIzquierdo = nodo.hijoIzquierdoAVL;
                    if (obtenerFE(hijoIzquierdo.hijoDerechoAVL) > obtenerFE(hijoIzquierdo.hijoIzquierdoAVL)) {
                        nodo = rotacionDobleIzquierda(nodo);
                    } else {
                        nodo = rotacionIzquierda(nodo);
                    }
                }
            }
        }

        if (nodo != null) {
            nodo.fe = Math.max(obtenerFE(nodo.hijoIzquierdoAVL), obtenerFE(nodo.hijoDerechoAVL)) + 1;
        }

        return nodo;
    }

    public NodoArbolAVL nodoMenor(NodoArbolAVL nodo) {
        while (nodo.hijoIzquierdoAVL != null) {
            nodo = nodo.hijoIzquierdoAVL;
        }
        return nodo;
    }

    public void eliminar(long dpi) {
        raiz = eliminarAVL(raiz, dpi);
    }

    public void cargarArchivo(File archivo) {
        try {
            BufferedReader leer = new BufferedReader(new FileReader(archivo));
            //PARA SALTARSE LINEA DE ENCABEZADO
            //leer.readLine();
            String linea;
            while ((linea = leer.readLine()) != null) {
                String[] partes = linea.split("\t");
                String nombre = partes[0];
                String dpi = partes[1];

                Pattern pt = Pattern.compile("\\d+");
                Matcher mt = pt.matcher(dpi);

                String ndpi = "";

                while (mt.find()) {
                    ndpi += mt.group();
                }

                Long dpiEntero = Long.parseLong(ndpi);
                insertar(nombre, dpiEntero);
            }
            leer.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en lectura del archivo");
        }
    }

    public void imprimir(NodoArbolAVL r, FileWriter guardar) {
        try {
            if (r != null) {
                imprimir(r.hijoIzquierdoAVL, guardar);
                imprimir(r.hijoDerechoAVL, guardar);
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
            FileWriter guardar = new FileWriter("C:\\Users\\luis-\\Documents\\NetBeansProjects\\Proyecto_Progra3\\src\\Archivo\\PacientesGuardadosAVL.txt");

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
    public void imprimirGraphviz(NodoArbolAVL r, StringBuilder texto) {
        if (r != null) {
            imprimirGraphviz(r.hijoIzquierdoAVL, texto);
            imprimirGraphviz(r.hijoDerechoAVL, texto);

            texto.append(r.getDpi()).append("[label = \"").append(r.getNombre()).append("\"]\n");

            if (r.hijoIzquierdoAVL != null) {
                texto.append(r.getDpi()).append("->").append(r.hijoIzquierdoAVL.getDpi()).append("\n");
            }
            if (r.hijoDerechoAVL != null) {
                texto.append(r.getDpi()).append("->").append(r.hijoDerechoAVL.getDpi()).append("\n");
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
            archivoGraphviz("archivoAVL.dot", codigoGraphviz());
            ProcessBuilder proceso;
            proceso = new ProcessBuilder("dot", "-Tpng", "-o", "arbolAVL.png", "archivoAVL.dot");
            proceso.redirectErrorStream(true);
            proceso.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CODIFICACION DE DATOS EN ARBOL AVL
    //REALIZA RECORRIDO Y DEFINE LOS CARACTERES PARA CODIFICAR
    public void datosCodificar(NodoArbolAVL r) {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZÑÁÉÍÓÚ/1234567890";
        if (r != null) {
            datosCodificar(r.hijoIzquierdoAVL);
            datosCodificar(r.hijoDerechoAVL);
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
    public void datosDecodificar(NodoArbolAVL r) {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZÑÁÉÍÓÚ/1234567890";
        if (r != null) {
            datosDecodificar(r.hijoIzquierdoAVL);
            datosDecodificar(r.hijoDerechoAVL);
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
    public void txtCrifrado(NodoArbolAVL raiz) {
        StringBuilder contenido = new StringBuilder();

        recorrerYCodificar(raiz, contenido);

        try (BufferedWriter escribir = new BufferedWriter(new FileWriter("C:\\Users\\luis-\\Documents\\NetBeansProjects\\Proyecto_Progra3\\src\\Archivo\\PacientesGuardadosAVL.txt"))) {
            escribir.write(contenido.toString());
            JOptionPane.showMessageDialog(null, "Datos codificados guardados correctamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo codificado: " + e.getMessage());
        }
    }

    private void recorrerYCodificar(NodoArbolAVL r, StringBuilder contenido) {
        if (r != null) {
            contenido.append(r.nombre).append("\t").append(r.dpi).append("\t").append(r.departamento).append("\t").append(r.municipio).append("\t").append(r.cantidadDosis).append("\t").append(r.fechaPrimera).append("\t").append(r.fechaSegunda).append("\t").append(r.lugarVacunacion).append("\n");
            recorrerYCodificar(r.hijoIzquierdoAVL, contenido);
            recorrerYCodificar(r.hijoDerechoAVL, contenido);
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

    //EDICION DE TABLA PARA AVL
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

    public void imprimirTablaPreorden(NodoArbolAVL r, DefaultTableModel modelo) {
        if (r != null) {
            Object[] dTabla = {r.nombre, r.dpi, r.departamento, r.municipio, r.cantidadDosis, r.fechaPrimera, r.fechaSegunda, r.fechaTercera, r.lugarVacunacion};
            modelo.addRow(dTabla);

            imprimirTablaPreorden(r.hijoIzquierdoAVL, modelo);
            imprimirTablaPreorden(r.hijoDerechoAVL, modelo);
        }
    }

    public void imprimirTablaInorden(NodoArbolAVL r, DefaultTableModel modelo) {
        if (r != null) {
            imprimirTablaInorden(r.hijoIzquierdoAVL, modelo);

            Object[] dTabla = {r.nombre, r.dpi, r.departamento, r.municipio, r.cantidadDosis, r.fechaPrimera, r.fechaSegunda, r.fechaTercera, r.lugarVacunacion};
            modelo.addRow(dTabla);
            imprimirTablaInorden(r.hijoDerechoAVL, modelo);
        }
    }

    public void imprimirTablaPostorden(NodoArbolAVL r, DefaultTableModel modelo) {
        if (r != null) {
            imprimirTablaPostorden(r.hijoIzquierdoAVL, modelo);
            imprimirTablaPostorden(r.hijoDerechoAVL, modelo);

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
