package rest.controller.interfaces;

import rest.controller.classes.Query;

import java.util.List;

public interface IQueryParcer {
    List<Query> parce(String queries);
}
