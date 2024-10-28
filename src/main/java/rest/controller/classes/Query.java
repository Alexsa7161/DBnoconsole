package rest.controller.classes;
import rest.controller.interfaces.IQuery;
public class Query implements IQuery {

    private String queryText;
    private Parser parser;

    public Query(String queryText) {
        this.parser = new Parser();
        this.queryText =  queryText;
    }

    @Override
    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    @Override
    public String getQueryText() {
        return queryText;
    }
}