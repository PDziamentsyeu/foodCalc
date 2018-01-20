package main.common.service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import javax.ws.rs.OPTIONS;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import main.common.LookupUtil;
import main.common.exceptions.EntityCreateException;
import main.common.exceptions.EntityDeleteException;
import main.common.exceptions.EntityNotAllowedException;
import main.common.exceptions.EntityNotFoundException;
import main.common.exceptions.EntityUpdateException;
import main.common.exceptions.ErrorResponse;


public abstract class GenericService {

    private Logger logger = Logger.getLogger(GenericService.class);

    private static final String FQCN = GenericService.class.getName();

    @Context
    public UriInfo uriInfo;

    public UriInfo getUriInfo() {
        return uriInfo;
    }

    public URL getServicePath(String module) {
        try {
            return LookupUtil.getUrl(module);
        } catch (EntityNotFoundException e) {
            logger.error("Error", e);
        }
        return null;
    }

    protected Response build(ResponseBuilder responseBuilder) {
        return responseBuilder
                .header("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Authorization").header("Access-Control-Allow-Credentials", "true")
                .links(getTransitionalLinks()).build();
    }

    protected Link[] getTransitionalLinks() {
        Link self = Link.fromResource(getClass()).build();
        return new Link[] { self };
    }

    @OPTIONS
    public Response options() {
        ResponseBuilder responseBuilder = Response.ok();
        return build(responseBuilder);
    }

    

    protected static final String NOTFOUND = "not found";
    protected static final String NOTAUTHORIZED = "not authorized";
    protected static final String INVALIDREQUEST = "request is not valid";
    protected static final String NOTPROCESSED = "error occurred while processing request";
    protected static final String NOTMODIFIED_CREATE = "not created";
    protected static final String NOTMODIFIED_UPDATE = "not updated";
    protected static final String NOTMODIFIED_DELETE = "not deleted";

    protected <E> String getResponseText(String text, Exception e, Class<E> clazz) {
        String s = text;
        if (clazz != null)
            s = clazz.getSimpleName() + " " + s;
        s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        if (e != null) {
            Throwable t = e;
            while (t.getCause() != null)
                t = t.getCause();
            s += (" - " + t.getMessage());
        }
        return s;
    }

    @Context
    protected UriInfo uri;

    @Context
    protected Request request;

    protected void dump() {
        logger.log(FQCN, Level.DEBUG,
                "[" + request.getMethod() + ":" + uri.getRequestUri().getPath()
                        + (uri.getRequestUri().getQuery() != null ? "?" + uri.getRequestUri().getQuery() : "") + "] ",
                null);
    }

    protected void dump(UriInfo uri, Request request) {
        logger.log(FQCN, Level.DEBUG,
                "["+ request.getMethod() + ":" + uri.getRequestUri().getPath()
                        + (uri.getRequestUri().getQuery() != null ? "?" + uri.getRequestUri().getQuery() : "") + "] ",
                null);
    }

    protected void debug(String s) {
        logger.log(FQCN, Level.DEBUG,
                "[" + request.getMethod() + ":" + uri.getRequestUri().getPath()
                        + (uri.getRequestUri().getQuery() != null ? "?" + uri.getRequestUri().getQuery() : "") + "] "
                        + (s != null ? s : ""),
                null);
    }

    protected void error(Exception e) {
        logger.log(FQCN, Level.ERROR,
                "[" + request.getMethod() + ":" + uri.getRequestUri().getPath()
                        + (uri.getRequestUri().getQuery() != null ? "?" + uri.getRequestUri().getQuery() : "") + "] " + "Error",
                e);
    }

    protected Response respond() {
        ResponseBuilder responseBuilder;
        responseBuilder = Response.ok();
        return build(responseBuilder);
    }

    protected Response respond(Object entity) {
        return respond(entity, entity.getClass());
    }

    protected Response respond(Object entity, Class<?> clazz) {
        ResponseBuilder responseBuilder;
        Object value = entity;
        if (value instanceof Boolean || value instanceof Integer || value instanceof Long) {
            value = String.valueOf(value);
        } else {
            if (value instanceof Vector) {
                value = ((Vector<?>) value).toArray();
            }
        }
        debug(clazz.getSimpleName().toLowerCase() + " = " + value + " : done");
        responseBuilder = Response.ok(value);
        return build(responseBuilder);
    }

    @SuppressWarnings("rawtypes")
    protected GenericEntity listToGenericEntity(List list, Class<?> clazz) {
        logger.debug("No entity list found for class " + clazz);
        return new GenericEntity<List>(list) {
        };
    }


    protected Response respondException(Exception e) {
        return respondException(e, null);
    }

    protected <E> Response respondException(Exception e, Class<E> clazz) {
        error(e);
        ResponseBuilder responseBuilder = null;
        if (e instanceof EntityNotFoundException) {
            responseBuilder = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(), getResponseText(NOTFOUND, e, clazz)));
        }
        if (e instanceof EntityNotAllowedException) {
            responseBuilder = Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse(Response.Status.FORBIDDEN.getStatusCode(), getResponseText(NOTAUTHORIZED, e, clazz)));
        }
        if (e instanceof EntityCreateException) {
            responseBuilder = Response.status(Response.Status.BAD_REQUEST).entity(
                    new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), getResponseText(NOTMODIFIED_CREATE, e, clazz)));
        }
        if (e instanceof EntityUpdateException) {
            if(e.getCause() != null && e.getCause() instanceof EntityNotFoundException) {
                responseBuilder = Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(), getResponseText(NOTFOUND, e, clazz)));
            }
            else {
                responseBuilder = Response.status(Response.Status.BAD_REQUEST).entity(
                        new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), getResponseText(NOTMODIFIED_UPDATE, e, clazz)));
            }
        }
        if (e instanceof EntityDeleteException) {
            responseBuilder = Response.status(Response.Status.BAD_REQUEST).entity(
                    new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), getResponseText(NOTMODIFIED_DELETE, e, clazz)));
        }
        if (e instanceof IOException) {
            responseBuilder = Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), getResponseText(NOTPROCESSED, e, clazz)));
        }
        if (responseBuilder == null) {
            responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), getResponseText(NOTPROCESSED, e, clazz)));
        }
        return build(responseBuilder);
    }

    protected Response respondBadRequest() {
        ResponseBuilder responseBuilder = Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), getResponseText(INVALIDREQUEST, null, null)));
        return build(responseBuilder);
    }

    protected Response respondBadRequest(String message) {
        ResponseBuilder responseBuilder = Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), message));
        return build(responseBuilder);
    }

}
