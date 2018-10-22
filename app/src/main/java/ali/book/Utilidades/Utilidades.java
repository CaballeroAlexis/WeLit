package ali.book.Utilidades;

/**
 * Created by Javier on 15/11/2017.
 */

public class Utilidades {

    public static final String TABLA_LISTA_LIBROS="libro";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_NOMBRE="nombre";


    public static final String CREAR_TABLA_LIBROS = "CREATE TABLE "+TABLA_LISTA_LIBROS+ "("+CAMPO_ID+" INTEGER, "+CAMPO_NOMBRE+" TEXT )";
}