package rest.controller.classes;

import rest.controller.interfaces.IQueryParcer;

import java.util.List;

public class QueryParcer implements IQueryParcer {
    public List<Query> parce(String queries)
    {
        String[] mas = queries.split(";\\n");
        List<Query> list = new java.util.ArrayList<>(List.of());
        for (int i=0;i<mas.length;i++)
        {
            Query q = new Query(mas[i]);
            list.add(q);
        }
        return list;
    }
}
