package com.alipeach.restapi.script;

import com.alipeach.restapi.script.ScriptServlet;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import javax.servlet.ServletException;

/**
 * @author chenhaoming
 */
public class ScriptServletTest {

    public static final String ENTIRE_WORKFLOW_DIR = "./RestAPI/src/test/java/com/alipeach/restapi/script/entireworkflow/";

    @Test
    public void testEntireWorkflow () throws Exception {
        ScriptServlet servlet = getScriptServlet (ENTIRE_WORKFLOW_DIR);

        MockHttpServletRequest request = getMockHttpServletRequest ();
        MockHttpServletResponse response = new MockHttpServletResponse ();

        servlet.service (request, response);

        Assert.assertTrue ((Boolean) request.getAttribute ("pre_all"));
        Assert.assertTrue ((Boolean) request.getAttribute ("pre_get"));
        Assert.assertTrue ((Boolean) request.getAttribute ("test/pre_all"));
        Assert.assertTrue ((Boolean) request.getAttribute ("test/pre_get"));
        Assert.assertTrue ((Boolean) request.getAttribute ("get"));
        Assert.assertTrue ((Boolean) request.getAttribute ("test/post_get"));
        Assert.assertTrue ((Boolean) request.getAttribute ("test/post_all"));
        Assert.assertTrue ((Boolean) request.getAttribute ("post_get"));
        Assert.assertTrue ((Boolean) request.getAttribute ("post_all"));
    }

    private ScriptServlet getScriptServlet (String scriptDir) throws ServletException {
        MockServletConfig mockServletConfig = new MockServletConfig ();
        mockServletConfig.addInitParameter (ScriptServlet.PARAM_SCRIPT_DIR, scriptDir);
        mockServletConfig.addInitParameter (ScriptServlet.PARAM_SCRIPT_EXTENSION, "js");

        ScriptServlet servlet = new ScriptServlet ();
        servlet.init (mockServletConfig);
        return servlet;
    }

    private MockHttpServletRequest getMockHttpServletRequest () {
        MockHttpServletRequest request = new MockHttpServletRequest ();
        request.setMethod ("get");
        request.setQueryString ("/restapi/test");
        return request;
    }

    @Test
    public void testPartWorkflow () throws Exception {
        ScriptServlet servlet = getScriptServlet ("./RestAPI/src/test/java/com/alipeach/restapi/script/partworkflow/");

        MockHttpServletRequest request = getMockHttpServletRequest ();
        MockHttpServletResponse response = new MockHttpServletResponse ();

        servlet.service (request, response);

        Assert.assertTrue ((Boolean) request.getAttribute ("pre_all"));
        Assert.assertTrue ((Boolean) request.getAttribute ("test/pre_get"));
        Assert.assertTrue ((Boolean) request.getAttribute ("get"));
        Assert.assertTrue ((Boolean) request.getAttribute ("test/post_get"));
        Assert.assertTrue ((Boolean) request.getAttribute ("post_all"));
    }

    @Ignore
    @Test
    public void test100ThousandTimes () throws Exception {
        long startTime = System.currentTimeMillis ();
        ScriptServlet servlet = getScriptServlet (ENTIRE_WORKFLOW_DIR);
        System.out.println ("It takes " + (System.currentTimeMillis () - startTime) + " milliseconds to initialize a servlet.");

        for (int i = 0; i < 10000; ++ i) {
            MockHttpServletRequest request = getMockHttpServletRequest ();
            MockHttpServletResponse response = new MockHttpServletResponse ();
            servlet.service (request, response);
        }

        System.out.print ("It takes " + (System.currentTimeMillis () - startTime) / 1000 + " seconds to finished 10 thousand times.");
    }
}
