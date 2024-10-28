package rest.controller.interfaces;
import rest.controller.classes.BTree;

import java.util.List;

public interface IIndex {
    BTree index = null;
    String name = "";

    void setName(String name);

    String getName();

    void setBtree(ITable tableName, List<IColumn> columns);

    BTree getBtree();
}