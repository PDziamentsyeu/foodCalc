package main.common.rest.userrest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import main.common.CustomEJBUtil;
import main.common.model.GenericEntityWithMeta;
import main.common.service.GenericService;
import main.common.service.userservice.UserBean;
import main.java.model.User;

@Stateless
@Path("/users")
public class UserRest extends GenericService {

    private static final Logger log = Logger.getLogger(UserRest.class);

    @EJB
    private UserBean userBean = (UserBean) CustomEJBUtil.getSessionBean(UserBean.class);
    
    /**
     * A method that allows to create a user and persist it.
     * It uses an object of {@link MneUserParameter} for users details. It does not have next fields:
     * 1. uuid/createDate - they are generated automatically in {@link GenericEntityWithMeta}
     * at the @PrePersist stage;
     * 2. updateDate - is changed in {@link GenericEntityWithMeta} at the @PrePersist
     * and @PreUpdate stages;
     * 3. createPrincipal/updatePrincipal - are changed in {@link ManagedCRUDBean} during
     * persisting the user {@link ManagedCRUDBean#create(Object)};
     * 4. lastLogon - should be changed during a login process only.
     *
     * @param userParameter a bean of {@link MneUserParameter}, that contains initial user's info
     * @return a response with persisted user.
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response create(
       
            User userParameter
    ) {
        log.trace("params: '" + userParameter.toString() + "'");
        try {
            User user = userBean.createFromEntity(userParameter);
            return respond(user);
        } catch (Exception e) {
            return respondException(e);
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAll() {

        dump();
        try {
            List<User> userList = userBean.getList();
            log.trace("Result: " + userList);
            return respond(userList);
        } catch (Exception e) {
            return respondException(e);
        }

    }

    @GET
    @Path("count")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response count() {

        dump();

        try {
            int count = userBean.getListCount();
            log.trace("Result: " + count);
            return respond(count);
        } catch (Exception e) {
            return respondException(e);
        }
    }


    // Operations by uuid
    @GET
    @Path("{uuid}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getByUUID(
        @PathParam("uuid") String uuid
    ) {
        try {
            User user = userBean.getByUuid(uuid);
            return respond(user);
        } catch (Exception e) {
            return respondException(e);
        }
    }

    @PUT
    @Path("{uuid}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response update(
        @PathParam("uuid") String uuid,
            User parameter
    ) {
        try {
            parameter.setUuid(uuid);
            User user = userBean.updateFromEntity(parameter);
            return respond(user);
        } catch (Exception e) {
            return respondException(e);
        }

    }
    
   

    @DELETE
    @Path("{uuid}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response delete(
        @PathParam("uuid") String uuid
    ) {
        try {

            log.trace("deleting by uuid: '" + uuid + "'");
            User user = userBean.getByUuid(uuid);
            userBean.delete(user);
            return respond(user);
        } catch (Exception e) {
            return respondException(e);
        }
    }    
    
}
