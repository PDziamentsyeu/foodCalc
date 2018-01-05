package main.common;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import main.common.exceptions.EntityNotFoundException;


public class LookupUtil {

    private static Logger logger = Logger.getLogger(LookupUtil.class);

    public static void dumpUrls() {
        try {
            InitialContext ic = new InitialContext();
            logger.debug("url:");
            NamingEnumeration<NameClassPair> e = ic.list("url");
            while (e.hasMoreElements()) {
                NameClassPair p = e.next();
                logger.debug("url = " + p.getName());
            }
        } catch (NamingException e) {
            logger.error("Error", e);
        }
    }

    public static URL getUrl(String name) throws EntityNotFoundException {
        try {
            InitialContext ic = new InitialContext();
            Object value = ic.lookup("url/" + name);
            if (value instanceof URL) {
                return (URL) value;
            } else {
                try {
                    return new URL(value.toString());
                } catch (MalformedURLException e) {
                    throw new EntityNotFoundException("Invalid URL " + value.toString());
                }
            }
        } catch (NamingException e) {
            dumpUrls();
            throw new EntityNotFoundException(e);
        }
    }

    public static String getUrlString(String name) throws EntityNotFoundException {
        try {
            InitialContext ic = new InitialContext();
            String url = (String) ic.lookup("url/" + name);
            return url;
        } catch (NamingException e) {
            dumpUrls();
            throw new EntityNotFoundException(e);
        }
    }

    public static String getString(String name) throws EntityNotFoundException {
        try {
            InitialContext ic = new InitialContext();
            String url = (String) ic.lookup(name);
            return url;
        } catch (NamingException e) {
            dumpUrls();
            throw new EntityNotFoundException(e);
        }
    }

    public static String getModuleName() {
        try {
            InitialContext ic = new InitialContext();
            String name = (String) ic.lookup("java:module/ModuleName");
            return name;
        } catch (NamingException e) {
            dumpUrls();
            return "default";
        }
    }

    public static String getApplicationName() {
        try {
            InitialContext ic = new InitialContext();
            String name = (String) ic.lookup("java:app/AppName");
            return name;
        } catch (NamingException e) {
            dumpUrls();
            return "default";
        }
    }

    public static int getVcapServicePropertyInt(String name) throws EntityNotFoundException {
        return Integer.valueOf(getVcapServicePropertyString(name));
    }

    public static boolean getVcapServicePropertyBoolean(String name) throws EntityNotFoundException {
        return Boolean.valueOf(getVcapServicePropertyString(name));
    }

    public static String getVcapServicePropertyString(String name) throws EntityNotFoundException {
        String vcapServices = System.getenv("VCAP_SERVICES");
        if (vcapServices != null) {
            JsonReader jsonReader = Json.createReader(new ByteArrayInputStream(vcapServices.getBytes()));
            try {
                JsonObject jsonObject = jsonReader.readObject();
                return jsonObject.getString(name);
            } catch (Exception e) {
                throw new EntityNotFoundException("Property " + name + " not found in VCAP Services");
            } finally {
                jsonReader.close();
            }
        } else {
            throw new EntityNotFoundException("VCAP Services can not be located");
        }
    }

}
