# Manual Técnico
## Programa de Árboles Binarios
El programa ha sido desarrollado con los siguientes requisitos:
* Lenguaje de programación Java
* Entorno de desarrollo (IDE) Apache Neatbeans
* Versión de JDK 18

# Descripción
Durante el inicio del desarrollo se tuvo la premisa de trabajar con un archivo de aproximadamente 3,485,256 de registros. La consigna del proyecto ha sido el desarrollo de un programa que mediante la estructura de datos de árboles binarios fuese capaz de cargar el número de registros mencionados y optimizar la búsqueda, eliminación, modificación y represetnación gráfica de la estructura. Para hacerlo posible se necesitó automatizar el proceso de inserción de nodo, utilizar elementos gráficos que provee el IDE para mostrar los datos y crear botones de acción para la ejecución del programa.

# Codificación
El proyecto dispone de las siguientes clases:
* NodoArbol
* ArbolBinario
* ArbolBinarioBusqueda (JForm)
* NodoArbolAVL
* ArbolAVL
* ArbolAVLInterfaz (JForm)

Para ambos tipos de árboles se definieron los siguientes atributos y constructor: 
~~~
    long dpi;
    String nombre;
    String departamento;
    String municipio;
    String cantidadDosis;
    String fechaPrimera;
    String fechaSegunda;
    String fechaTercera;
    String lugarVacunacion;
    
    NodoArbol HijoIzquierdo, HijoDerecho;
~~~

~~~
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
~~~

Para la creación de ambos árboles se inicializó la raíz principal de cada uno en nullo para que posteriormente se le agregue la información

~~~
public ArbolBinario() {
        raiz = null;
    }
~~~

## Codificación Arbol ABB
El método principal que hace posible la creación del árbol es el método de "agregarNodo" que recibe como parámetros el nombre de la persona y el número de identificación DPI. Su funcionamiento es comprobar que no haya nodos duplicados a través del valor booleano de "existenciaNodo" el cual se apoya del método "buscarNodo" para regresar un nodo encontrado y decidir mediante condición agregarlo o descartarlo.

Seguidamente cumple con una serie de comprobaciones, una vez creada la instancia NodoArbol, para definir la posición del nodo, siendo raíz, o algún subárbol; Esto se realiza mediante la comprobación de menor o mayor que la raíz principal utilizando DPI como valor numérico de comprobación. El método utiliza una padre y un auxiliar para hacer un breve recorrido y no perder las posiciones de los nodos agregados anteriormente.

~~~
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
~~~
~~~
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
~~~

Uno de los métodos que más comprobaciones requirió fue el método EliminarNodo, ya que consideraba posibilidades de borrar hojas, subárboles y sus movimientos para encontrar el reemplazo, incluso se comprobó la posibilidad de eliminar la raíz colocando un reemplazo en su posición.

~~~
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
~~~

Para la parte de cargarArchivo se utilizó la clase BufferedReader un ciclo while y la declaración de un array para obtener las partes separadas por medio de "tabulación" y así separar la parte del nombre de la persona y su identificación personal. El número de identificación tuvo que pasar una comprobación que permitió convertir los valores String en datos de tipo long, ya que el tipo de parámetros indicados en el metodo agregarnodo requiere de esos tipos de datos.
~~~
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
~~~

Se programaron los recorridos en Preorden, Inorden y PostOrden para verificar la consecución de los nodos y obtener sus datos que  se utilizaron para: 
* Guardar y cifrar información en documento
* Creación del documento "dot" de Graphviz.

~~~
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
            FileWriter guardar = new FileWriter("C:\\Users\\luis-\\Documents\\NetBeansProjects\\Proyecto_Progra3\\src\\Archivo\\PacientesGuardados.txt");

            imprimir(raiz, guardar);
            guardar.close();
            JOptionPane.showMessageDialog(null, "Lineas guardadas");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en escritura del archivo");
        }
    }
~~~

Para la codificación se utilizó el algoritmo de Cesar y para la decodificación se aplicó contrariedad al número de aumento de letras y números para evitar pérdida de información.
~~~
    public void datosCodificar(NodoArbol r) {
        if (r != null) {
            datosCodificar(r.HijoIzquierdo);
            datosCodificar(r.HijoDerecho);
            r.nombre = codificarLetras("ABCDEFGHIJKLMNOPQRSTUVWXYZÑÁÉÍÓÚ", r.nombre);
            r.dpi = codificarNumero(r.dpi);
        }
    }

    //REALIZA RECORRIDO Y DEFINE CARACTERES PARA DECODIFICAR
    public void datosDecodificar(NodoArbol r) {
        if (r != null) {
            datosDecodificar(r.HijoIzquierdo);
            datosDecodificar(r.HijoDerecho);
            r.nombre = decodificarLetras("ABCDEFGHIJKLMNOPQRSTUVWXYZÑÁÉÍÓÚ", r.nombre);
            r.dpi = decodificarNumero(r.dpi);
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
~~~

Por lo demás, se utilizaron elementos visuales de Netbeans para desarrollar la interfaz gráfica y se les aplicó lógica para la funcionalidad.

## Coficiación Arboles AVL
Para los árboles AVL se preservaron los siguientes elementos de los árboles binarios:
* Método de búsqueda
* Métodos de recorridos
* Métodos de obtención y graficación de Graphviz
* Método para codificar y decodificar

En el arbol AVL se cambió y añadió nuevos métodos para considerar la parte de las rotaciones. Se inició con el factor de equilibrio y definiendo las rotaciones, considerando auxiliares, subárboles como hijos y realizando la diferencia del factor como se detalla a continuación: 

~~~
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
~~~

Para el proceso de inserción se establecieron los recorridos principales de comparación a la raíz principal para posicionar cada nuevo elemento, con la diferencia de abarcar más posibilidades de desequilibrio en cada inserción. Se utilizaron las rotaciones dentro de la inserción para que fuera un proceso automático, se verificó la posibilidad de nodos repetidos y también una actualización del factor de equilibrio luego de cada nodo agregado.

~~~
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
~~~