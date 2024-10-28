package rest.controller.interfaces;

public interface IDBConnection {

    Boolean connect(String connectionString);


    void disconnect();

}