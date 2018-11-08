package com.sling.api.builder.core;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sling.api.builder.core.utils.Constants;
import com.sling.api.builder.core.utils.RestResourceUtil;
import com.sling.api.builder.core.utils.ServletMappingStorage;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.StringJoiner;

@SlingServlet(paths = {"/bin/models"})
public class TestedModelServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response){
        StringJoiner modelsObject = new StringJoiner(",", "[", "]");
        ServletMappingStorage.getServletsStorage().forEach((key, value) -> {
            try {
                String responseJson = RestResourceUtil.toJson(value.getModelClass().getConstructor().newInstance());
                modelsObject.add("{\"className\":\"" + value.getModelClass().getSimpleName() + "\",\"fields\":" + responseJson + "}");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
       });
        response.setContentType(Constants.RESPONSE_JSON_SETTING);
        try {
            response.getWriter().write(modelsObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
