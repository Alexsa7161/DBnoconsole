package rest.controller.interfaces;

public interface IColumn {
    String name = "";
    String dataType = "";

    String getName();

    void setName(String name);

    String getDataType();

    void setDataType(String dataType);
}