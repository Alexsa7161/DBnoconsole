package rest.controller.classes;
import rest.controller.interfaces.IColumn;
public class Column implements IColumn {
	
    private String name;
    private String dataType;

    public Column(String name, String dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}