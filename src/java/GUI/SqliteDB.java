package GUI;

import clases.Proceso;
import javafx.fxml.FXMLLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SqliteDB {

    Connection c = null;
    Statement stmt = null;

    public void conectar(){

        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:simulador");

        }catch (Exception e){
            System.out.println("Error:" + e.getMessage());
            System.exit(1);
        }
    }

    public void crearTablas(){
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS TablaProcesos"+
                    "(idTabla integer PRIMARY KEY"+
                    ")";
            stmt.executeUpdate(sql);
            //System.out.println("Creada la tabla TablaProcesos");
            sql = "CREATE TABLE IF NOT EXISTS Procesos"+
                    "(idProceso integer PRIMARY KEY,"+
                    "tamanoProceso integer,"+
                    "tiempoCreacion integer,"+
                    "prioridad integer,"+
                    "cpu1 integer,"+
                    "cpu2 integer,"+
                    "cpu3 integer,"+
                    "es1 integer,"+
                    "es2 integer"+
                    ")";
            stmt.executeUpdate(sql);
            //System.out.println("Creada la tabla Procesos");
            sql = "CREATE TABLE IF NOT EXISTS ProcTabl"+
                    "(idTabla integer,"+
                    "idProceso integer,"+
                    "PRIMARY KEY(idTabla,idProceso),"+
                    "FOREIGN KEY(idTabla) REFERENCES TablaProcesos(idTabla),"+
                    "FOREIGN KEY(idProceso) REFERENCES TablaProcesos(idProceso)"+
                    ")";
            stmt.executeUpdate(sql);
            //System.out.println("Creada la tabla ProcTabl");

            //// MARTIN HACE ESTO, PENSALO
            sql = "CREATE TABLE IF NOT EXISTS EntradaSimulador"+
                    "(idEntrada integer PRIMARY KEY,"+
                    "idMemoria integer)";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Planificador"+
                    "(idPlanificador integer PRIMARY KEY,"+
                    "idMemoria integer)";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS EntradaPlanificador"+
                    "(idEntrada integer,"+
                    "idPlanificador integer,"+
                    "PRIMARY KEY(idEntrada,idPlanificador),"+
                    "FOREIGN KEY(idEntrada) REFERENCES EntradaSimulador(idEntrada),"+
                    "FOREIGN KEY(idPlanificador) REFERENCES Planificador(idPlanificador))";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Memoria"+
                    "(idMemoria integer PRIMARY KEY)";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Fija"+
                    "(idMemoria integer PRIMARY KEY,"+
                    "p1 integer, p2 integer, p3 integer, p4 integer, p5 integer, p6 integer,"+
                    "FOREIGN KEY (idMemoria) REFERENCES Memoria(idMemoria))";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Variable"+
                    "(idMemoria integer PRIMARY KEY,"+
                    "tamano integer, porcSO integer,"+
                    "FOREIGN KEY (idMemoria) REFERENCES Memoria(idMemoria))";
            stmt.executeUpdate(sql);
        }catch (Exception e){
            System.out.println("Error:" + e.getMessage());
        }
    }

    public void desconectar(){
        try{
            c.close();
        }catch (Exception e){
            System.out.println("Error:" + e.getMessage());
            System.exit(1);
        }
    }

    public void insertarEjemplos(){
        try{
            //stmt.executeUpdate("INSERT INTO TablaProcesos(idTabla) SELECT MAX(idTabla)+1 FROM TablaProcesos");
            stmt.executeUpdate("INSERT INTO TablaProcesos(idTabla) VALUES(1) ON CONFLICT(idTabla) DO NOTHING");
            stmt.executeUpdate("INSERT INTO TablaProcesos(idTabla) VALUES(2) ON CONFLICT(idTabla) DO NOTHING");
            stmt.executeUpdate("INSERT INTO TablaProcesos(idTabla) VALUES(3) ON CONFLICT(idTabla) DO NOTHING");

            stmt.executeUpdate("INSERT INTO Procesos(idProceso,tamanoProceso,tiempoCreacion,prioridad,cpu1,cpu2,cpu3,es1,es1)"+
                                    "VALUES(1,15,0,3,5,0,0,0,0) ON CONFLICT(idProceso) DO NOTHING");
            stmt.executeUpdate("INSERT INTO Procesos(idProceso,tamanoProceso,tiempoCreacion,prioridad,cpu1,cpu2,cpu3,es1,es1)"+
                    "VALUES(2,20,0,3,4,0,0,0,0) ON CONFLICT(idProceso) DO NOTHING");
            stmt.executeUpdate("INSERT INTO Procesos(idProceso,tamanoProceso,tiempoCreacion,prioridad,cpu1,cpu2,cpu3,es1,es1)"+
                    "VALUES(3,12,0,3,10,0,0,0,0) ON CONFLICT(idProceso) DO NOTHING");
            stmt.executeUpdate("INSERT INTO Procesos(idProceso,tamanoProceso,tiempoCreacion,prioridad,cpu1,cpu2,cpu3,es1,es1)"+
                    "VALUES(4,5,1,3,3,0,0,0,0) ON CONFLICT(idProceso) DO NOTHING");
            stmt.executeUpdate("INSERT INTO Procesos(idProceso,tamanoProceso,tiempoCreacion,prioridad,cpu1,cpu2,cpu3,es1,es1)"+
                    "VALUES(5,3,2,3,2,0,0,0,0) ON CONFLICT(idProceso) DO NOTHING");
            stmt.executeUpdate("INSERT INTO Procesos(idProceso,tamanoProceso,tiempoCreacion,prioridad,cpu1,cpu2,cpu3,es1,es1)"+
                    "VALUES(6,70,3,3,10,0,0,0,0) ON CONFLICT(idProceso) DO NOTHING");
            stmt.executeUpdate("INSERT INTO Procesos(idProceso,tamanoProceso,tiempoCreacion,prioridad,cpu1,cpu2,cpu3,es1,es1)"+
                    "VALUES(7,25,4,3,5,0,0,0,0) ON CONFLICT(idProceso) DO NOTHING");
            stmt.executeUpdate("INSERT INTO Procesos(idProceso,tamanoProceso,tiempoCreacion,prioridad,cpu1,cpu2,cpu3,es1,es1)"+
                    "VALUES(8,10,5,3,5,0,0,0,0) ON CONFLICT(idProceso) DO NOTHING");

            for (int i = 1; i < 9; i++) {
                stmt.executeUpdate("INSERT INTO ProcTabl(idTabla,idProceso)"+
                        "VALUES(1,"+i+") ON CONFLICT(idTabla,idProceso) DO NOTHING");
            }

            stmt.executeUpdate("INSERT INTO Procesos(idProceso,tamanoProceso,tiempoCreacion,prioridad,cpu1,cpu2,cpu3,es1,es1)"+
                    "VALUES(9,10,0,3,8,0,0,0,0) ON CONFLICT(idProceso) DO NOTHING");
            stmt.executeUpdate("INSERT INTO Procesos(idProceso,tamanoProceso,tiempoCreacion,prioridad,cpu1,cpu2,cpu3,es1,es1)"+
                    "VALUES(10,7,0,3,6,0,0,0,0) ON CONFLICT(idProceso) DO NOTHING");
            stmt.executeUpdate("INSERT INTO Procesos(idProceso,tamanoProceso,tiempoCreacion,prioridad,cpu1,cpu2,cpu3,es1,es1)"+
                    "VALUES(11,15,0,3,4,0,0,0,0) ON CONFLICT(idProceso) DO NOTHING");
            stmt.executeUpdate("INSERT INTO Procesos(idProceso,tamanoProceso,tiempoCreacion,prioridad,cpu1,cpu2,cpu3,es1,es1)"+
                    "VALUES(12,11,0,3,2,0,0,0,0) ON CONFLICT(idProceso) DO NOTHING");

            for (int i = 9; i < 12; i++) {
                stmt.executeUpdate("INSERT INTO ProcTabl(idTabla,idProceso)"+
                        "VALUES(2,"+i+") ON CONFLICT(idTabla,idProceso) DO NOTHING");
            }


        }catch (Exception e){
            System.out.println("Error:" + e.getMessage());
        }
    }

    public List<String> selectTablas() {
        List<String> stringsList = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM TablaProcesos");
            while (rs.next()) {
                int id = rs.getInt("idTabla");
                stringsList.add(String.valueOf(id));
            }

            rs = stmt.executeQuery("SELECT * FROM Procesos");
            while (rs.next()) {
                int id = rs.getInt("idProceso");
            }

        }catch (Exception e){
            System.out.println("Error:" + e.getMessage());
        }
        return stringsList;
    }

    public ArrayList<Proceso> procesosDeTabla(int idTabla){
        ArrayList<Proceso> listaProcesos = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Procesos "+
                    "WHERE idProceso IN(SELECT idProceso FROM ProcTabl "+
                    "WHERE idTabla="+idTabla+")");
            int idActual = Proceso.count;
            while (rs.next()) {
                //int id = rs.getInt("idProceso");
                int tamano = rs.getInt("tamanoProceso");
                int tiempoCreacion = rs.getInt("tiempoCreacion");
                int prioridad = rs.getInt("prioridad");

                int cpu1 = rs.getInt("cpu1");
                int cpu2 = rs.getInt("cpu2");
                int cpu3 = rs.getInt("cpu3");
                int es1 = rs.getInt("es1");
                int es2 = rs.getInt("es2");

                Proceso proceso = new Proceso();
                proceso.setId(Proceso.count);
                proceso.setTamano(tamano);
                proceso.setTiempoCreacion(tiempoCreacion);
                proceso.setPrioridad(prioridad);
                proceso.setCpu0(cpu1);
                proceso.setCpu1(cpu2);
                proceso.setCpu2(cpu3);
                proceso.setEs1(es1);
                proceso.setEs2(es2);

                TablaProcesos.procesosList.add(proceso);
                //listaProcesos.add(proceso);

                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("TablaController.fxml")));
            }

            return listaProcesos;

        }catch (Exception e){
            System.out.println("Error:" + e.getMessage());
        }
        return null;
    }

    public int nuevaTabla(){
        int idProceso= 0;
        int idTabla = 0;
        int tamano;
        int tiempoCreacion;
        int prioridad;
        int cpu0;
        int cpu1;
        int cpu2;
        int es1;
        int es2;

        try {
            ResultSet rs = stmt.executeQuery("SELECT MAX(idTabla)+1 FROM TablaProcesos");
            while (rs.next()) {
                idTabla = rs.getInt(1);
            }
            stmt.executeUpdate("INSERT INTO TablaProcesos(idTabla) VALUES("+idTabla+")");
            for (Proceso proceso: TablaProcesos.procesosList) {
                tamano = proceso.getTamano();
                tiempoCreacion = proceso.getTiempoCreacion();
                prioridad = proceso.getPrioridad();
                cpu0 = proceso.getCpu0();
                cpu1 = proceso.getCpu1();
                cpu2 = proceso.getCpu2();
                es1 = proceso.getEs1();
                es2 = proceso.getEs2();
                rs = stmt.executeQuery("SELECT MAX(idProceso)+1 FROM Procesos");
                while (rs.next()) {
                    idProceso = rs.getInt(1);
                }
                stmt.executeUpdate("INSERT INTO Procesos(idProceso,tamanoProceso,tiempoCreacion,prioridad,cpu1,cpu2,cpu3,es1,es2) " +
                        "SELECT MAX(idProceso)+1,"+tamano+","+tiempoCreacion+","+prioridad+","+cpu0+","+cpu1+
                        ","+cpu2+","+es1+","+es2+" FROM Procesos");
                stmt.executeUpdate("INSERT INTO ProcTabl(idTabla,idProceso) VALUES("+idTabla+","+idProceso+")");
            }
            System.out.println(idTabla);
            return idTabla;

        }catch (Exception e){
            System.out.println("Error:" + e.getMessage());
            System.exit(1);
        }

        return idTabla;
    }

}
