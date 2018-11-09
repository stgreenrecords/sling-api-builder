package com.sling.api.builder.core;

import com.sling.api.builder.core.utils.Constants;
import com.sling.api.builder.core.utils.RestResourceUtil;
import com.sling.api.builder.core.utils.ServletMappingStorage;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import java.io.IOException;
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
            } catch (Exception e) {
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
