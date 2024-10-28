package rest.controller.interfaces;

import rest.controller.classes.Query;

import java.io.IOException;

public interface ITransaction {


    void startTransaction() throws IOException;

}