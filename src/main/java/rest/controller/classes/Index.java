package rest.controller.classes;
import rest.controller.interfaces.IColumn;
import rest.controller.interfaces.IDataStorage;
import rest.controller.interfaces.IIndex;
import rest.controller.interfaces.ITable;

import java.util.ArrayList;
import java.util.List;

public class Index implements IIndex {
    BTree index = null;
    String name = "";
    List<Column> list;

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setBtree(ITable tableName, List<IColumn> columns)
    {
        BTree tree = new BTree(5);
        this.index = tree;
    }

    public BTree getBtree()
    {
        return this.index;
    }
}