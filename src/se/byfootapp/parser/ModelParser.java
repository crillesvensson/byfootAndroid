package se.byfootapp.parser;

import org.json.JSONObject;

public interface ModelParser<T> {
    T doParse(JSONObject jssonObject) throws Exception;
}
