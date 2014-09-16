package com.alipeach.restapi;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenhoming
 */
public class ScriptServlet extends HttpServlet {

    public static final String PRE_ALL = "pre_all";
    public static final String SPLITTER = "/";
    public static final String PRE = "pre_";
    public static final String POST_ALL = "post_all";
    public static final String POST = "post_";

    private static final transient Logger LOG = LoggerFactory.getLogger (ScriptServlet.class);

    private static final transient Logger SCRIPT_LOG = LoggerFactory.getLogger (ScriptServlet.class + ".EXECUTED_SCRIPT");
    public static final String PARAM_SCRIPT_DIR = ScriptServlet.class + ".SCRIPT_DIR";
    public static final String PARAM_SCRIPT_EXTENSION = ScriptServlet.class + ".SCRIPT_EXTENSION";
    public static final String PARAM_ENCODING = ScriptServlet.class + ".ENCODING";

    private String scriptDir = "../controllers/";
    private String scriptExtension = "js";
    private String scriptFileSuffix = "." + scriptExtension;
    private ScriptEngine scriptEngine;

    private WeakReference<Map<String, String>> cacheMapRef;
    private String encoding = "utf-8";

    /**
     * /controllers/
     * |
     * -- pre_all.scriptExtension
     * |
     * -- pre_get.scriptExtension
     * |
     * -- post_get.scriptExtension
     * |
     * -- pre_post.scriptExtension
     * |
     * -- exception_all.scriptExtension
     * |
     * -- /users/
     * |
     * -- pre_all.scriptExtension
     * |
     * -- post_all.scriptExtension
     *
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod ();
        String modelName = getModelName (req);
        if (StringUtils.isBlank (modelName)) {
            resp.sendError (HttpServletResponse.SC_NOT_FOUND);
        }

        File targetScript = new File (scriptDir + modelName + SPLITTER + method + scriptFileSuffix);
        if (! targetScript.canRead ()) {
            resp.sendError (HttpServletResponse.SC_NOT_FOUND);
        }

        Bindings bindings = scriptEngine.createBindings ();
        bindings.put ("request", req);
        bindings.put ("response", resp);
        bindings.put ("parameter", req.getParameterMap ());

        executeScript (scriptEngine, PRE_ALL, bindings);
        String preMethod = PRE + method;
        executeScript (scriptEngine, preMethod, bindings);
        executeScript (scriptEngine, modelName + SPLITTER + PRE_ALL, bindings);
        executeScript (scriptEngine, modelName + SPLITTER + preMethod, bindings);
        executeScript (scriptEngine, modelName + SPLITTER + method, bindings);

        String postMethod = POST + method;
        executeScript (scriptEngine, modelName + SPLITTER + postMethod, bindings);
        executeScript (scriptEngine, modelName + SPLITTER + POST_ALL, bindings);
        executeScript (scriptEngine, postMethod, bindings);
        executeScript (scriptEngine, POST_ALL, bindings);
    }

    private void executeScript (ScriptEngine scriptEngine, String scriptFilename, Bindings bindings) throws ServletException {
        String scriptFilePath = scriptDir + scriptFilename + scriptFileSuffix;

        Map<String, String> cacheMap = cacheMapRef.get ();
        if (null == cacheMap) {
            synchronized (this) {
                if (null == (cacheMap = cacheMapRef.get ())) {
                    cacheMap = new HashMap<> ();
                    cacheMapRef = new WeakReference<> (cacheMap);
                }
            }
        }

        try {
            String scriptContent = getScriptContent (scriptFilePath, cacheMap);
            if (null != scriptContent) {
                scriptEngine.eval (scriptContent, bindings);
            }
        } catch (Exception e) {
            throw new ServletException (e);
        }
    }

    private String getScriptContent (String scriptFilePath, Map<String, String> cacheMap) throws IOException {
        String scriptContent = cacheMap.get (scriptFilePath);

        if (null == scriptContent) {
            //noinspection SynchronizationOnLocalVariableOrMethodParameter
            synchronized (cacheMap) {
                if (null == (scriptContent = cacheMap.get (scriptFilePath))) {
                    File scriptFile = new File (scriptFilePath);
                    if (scriptFile.canRead ()) {
                        scriptContent = FileUtils.readFileToString (scriptFile, encoding);
                        cacheMap.put (scriptFilePath, scriptContent);
                    }
                }
            }

        }
        return scriptContent;
    }

    /**
     * The query string should be in pattern of <strong>/restapi/model/parameter/...</strong>
     *
     * @return model name.
     */
    private String getModelName (HttpServletRequest req) {
        String queryString = req.getQueryString ();
        if (StringUtils.isBlank (queryString)) {
            return null;
        }

        if (queryString.startsWith (SPLITTER)) {
            queryString = queryString.substring (SPLITTER.length ());
        }

        if (queryString.endsWith (SPLITTER)) {
            queryString = queryString.substring (0, queryString.length () - SPLITTER.length ());
        }

        String[] paths = queryString.split (SPLITTER);
        if (paths.length > 1) {
            return paths[1];
        }

        return null;
    }

    @Override
    public void init (ServletConfig config) throws ServletException {
        LOG.info ("Start initializing servlet [{}]...", ScriptServlet.class);
        super.init (config);
        String scriptDir = config.getInitParameter (PARAM_SCRIPT_DIR);
        this.scriptDir = StringUtils.isNotBlank (scriptDir) ? scriptDir : this.scriptDir;
        LOG.info ("Configured script directory:{}", this.scriptDir);

        String scriptExtension = config.getInitParameter (PARAM_SCRIPT_EXTENSION);
        this.scriptExtension = StringUtils.isNotBlank (scriptExtension) ? scriptExtension : this.scriptExtension;
        scriptFileSuffix = "." + this.scriptExtension;
        LOG.info ("Configured script extension:{}", this.scriptExtension);

        String encoding = config.getInitParameter (PARAM_ENCODING);
        this.encoding = (null == encoding) ? this.encoding : encoding;
        LOG.info ("Configured script encoding:{}", this.encoding);

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager ();
        scriptEngine = scriptEngineManager.getEngineByExtension (this.scriptExtension);
        if (null == scriptEngine) {
            throw new ServletException (scriptExtension + " is not registered as an executable script file.");
        }

        scriptEngine.getBindings (ScriptContext.ENGINE_SCOPE).put ("LOG", SCRIPT_LOG);
        cacheMapRef = new WeakReference<> (new HashMap<> ());
    }

}
