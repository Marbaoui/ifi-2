

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/*", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private Map<String, Method> uriMappings = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("Getting request for " + req.getRequestURI());
        // TODO

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // on enregistre notre controller au démarrage de la servlet
        try {
            this.registerController(HelloController.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This methods checks the following rules :
     * - The controllerClass is annotated with @Controller
     * Then all methods are scanned and processed by the registerMethod method
     * @param controllerClass the controller to scan
     */
    protected void registerController(Class controllerClass){
        System.out.println("Analysing class " + controllerClass.getName());
        // TODO
        if(controllerClass.getAnnotation(Controller.class)!=null){
            for (Method method: controllerClass.getMethods()
                 ) {
                registerMethod(method);

            }
        }else{
            throw new IllegalArgumentException();
        }




    }

    /**
     * This methods checks the following rules :
     * - The method is annotated with @RequestMapping
     * - The @RequestMapping annotation has a URI
     * - The method does not return void
     * If these rules are followed, the method and its URI are added to the uriMapping map.
     * @param method the method to scan
     */
    protected void registerMethod(Method method) {
        System.out.println("Registering method " + method.getName());
        // TODO
        if (method.getAnnotation(RequestMapping.class)!=null){
            if (!method.getAnnotation(RequestMapping.class).uri().isEmpty()){
                if (method.getReturnType()!=Void.TYPE){
                    uriMappings.put(method.getAnnotation(RequestMapping.class).uri(), method);
                }

            }
        }
    }

    protected Map<String, Method> getMappings(){
        return this.uriMappings;
    }

    protected Method getMappingForUri(String uri){
        return this.uriMappings.get(uri);
    }
}