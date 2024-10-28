package rest.controller.interfaces;

import rest.controller.classes.Instruction;
import rest.controller.classes.Query;

import java.util.List;

public interface IParser {

    List<Instruction> parse(Query query);
}