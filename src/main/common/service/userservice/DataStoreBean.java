package main.common.service.userservice;



import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@Singleton
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DataStoreBean {

    private Log logger = LogFactory.getLog(DataStoreBean.class);

    @EJB
    private UserBean userBean;

 //   @EJB
 //   private RoleBean roleBean;

    private static final String PORTAL_ADMIN = "MnePortalAdministrator";
    private static final String USER = "User";
    private static final String ACCOUNT_ADMIN = "AccountAdministrator";

    public void bootstrap() {

        // functional id
        createUser(" initial user", "test");

        // real id
       

        createRole(PORTAL_ADMIN);
        createRole(USER);
        createRole(ACCOUNT_ADMIN);
        
        //TODO assigne role

        
    }

   public void createUser(String userDesc, String userName){
	   
   }
   
   public void createRole(String role){
	   
   }

}
