import GUI.SqliteDB;

public class Main4 {

    public static void main(String[] args){
        SqliteDB db = new SqliteDB();
        db.conectar();
        db.crearTablas();
        db.insertarEjemplos();
        db.procesosDeTabla(1);
        db.nuevaTabla();
        db.selectTablas();
        db.desconectar();
    }


}
