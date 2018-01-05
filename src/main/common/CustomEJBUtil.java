package main.common;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CustomEJBUtil {

    private static Log logger = LogFactory.getLog(CustomEJBUtil.class);

    public static Object getSessionBean(Class<?> clazz) {
        Object bean = getSessionBean(LookupUtil.getApplicationName(), clazz.getName(),
                clazz.getSimpleName().replaceAll("Local", "").replaceAll("Remote", ""));
        return bean;
    }

	@SuppressWarnings("unused")
	private static Object getSessionBean(String module, String className, String simpleClassName) {
        Object bean = null;
        try {
            InitialContext ctx = new InitialContext();
            try {
                bean = ctx
                        .lookup("java:global/" + module + "/" + simpleClassName + "!" + className);
            } catch (Exception e) {
                logger.error("EJB lookup error : " + className);
            }
        } catch (NamingException e) {
            logger.error("EJB context error : " + e.getMessage());
        }
        return bean;
    }

}
