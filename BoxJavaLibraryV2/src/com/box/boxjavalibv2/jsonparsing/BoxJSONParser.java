package com.box.boxjavalibv2.jsonparsing;

import java.io.IOException;
import java.io.InputStream;

import com.box.boxjavalibv2.dao.IBoxType;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;

/**
 * The json parser class wrapping Jackson JSON parser. For now, if user wants to remove jackson dependency(jackson jars), all the overriden methods and
 * constructor of this class needs to be rewritten against the cusomized json parser. An alternative approach (not taken yet) requires user to implement a new
 * IBoxJSONParser, in the meantime make all the jackson library related calls in this class reflection calls. However this is error prone if we need to update
 * jackson. Since jackson is still the recommended way. We are not doing the reflection way yet.
 */
public class BoxJSONParser implements IBoxJSONParser {

    private final ObjectMapper mObjectMapper;

    public BoxJSONParser(final IBoxResourceHub hub) {
        mObjectMapper = new ObjectMapper();
        mObjectMapper.setSerializationInclusion(Include.NON_NULL);
        mObjectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        mObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        for (IBoxType type : hub.getAllTypes()) {
            mObjectMapper.registerSubtypes(new NamedType(hub.getClass(type), type.toString()));
        }
    }

    protected ObjectMapper getObjectMapper() {
        return mObjectMapper;
    }

    @Override
    public String convertBoxObjectToJSONStringQuietly(final Object object) {
        try {
            return convertBoxObjectToJSONString(object);
        }
        catch (BoxJSONException e) {
            return null;
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public <T> T parseIntoBoxObjectQuietly(final InputStream inputStream, final Class<T> theClass) {
        try {
            return parseIntoBoxObject(inputStream, theClass);
        }
        catch (BoxJSONException e) {
            return null;
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public <T> T parseIntoBoxObjectQuietly(final String jsonString, final Class<T> theClass) {
        try {
            return parseIntoBoxObject(jsonString, theClass);
        }
        catch (BoxJSONException e) {
            return null;
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public String convertBoxObjectToJSONString(Object object) throws BoxJSONException, IOException {
        try {
            return getObjectMapper().writeValueAsString(object);
        }
        catch (JsonGenerationException e) {
            throw new BoxJSONException(e);
        }
        catch (JsonMappingException e) {
            throw new BoxJSONException(e);
        }
    }

    @Override
    public <T> T parseIntoBoxObject(InputStream inputStream, Class<T> theClass) throws BoxJSONException, IOException {
        try {
            JsonFactory jsonFactory = new JsonFactory();
            JsonParser jp = jsonFactory.createJsonParser(inputStream);
            return getObjectMapper().readValue(jp, theClass);
        }
        catch (JsonGenerationException e) {
            throw new BoxJSONException(e);
        }
        catch (JsonMappingException e) {
            throw new BoxJSONException(e);
        }
        catch (JsonParseException e) {
            throw new BoxJSONException(e);
        }
    }

    @Override
    public <T> T parseIntoBoxObject(String jsonString, Class<T> theClass) throws BoxJSONException, IOException {
        try {
            JsonFactory jsonFactory = new JsonFactory();
            JsonParser jp = jsonFactory.createJsonParser(jsonString);
            return getObjectMapper().readValue(jp, theClass);
        }
        catch (JsonGenerationException e) {
            throw new BoxJSONException(e);
        }
        catch (JsonMappingException e) {
            throw new BoxJSONException(e);
        }
        catch (JsonParseException e) {
            throw new BoxJSONException(e);
        }
    }
}
