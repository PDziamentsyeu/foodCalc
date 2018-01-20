package main.common.service.userservice;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@Startup
@Singleton
@DependsOn("CommonBootstrap")
public class Bootstrap implements Serializable {

    private static final long serialVersionUID = 1L;

    private Log logger = LogFactory.getLog(Bootstrap.class);

    @EJB
    private DataStoreBean dataStoreBean;

    public Bootstrap() {
        super();
    }

    @PostConstruct
    public void boot() {
        logger.debug("init:start");

        // Bootstrap data
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                logger.debug("init:start setup");
                dataStoreBean.bootstrap();
                logger.debug("init:done setup");
            }
        });

        logger.debug("init:done");
    }

    @PreDestroy
    public void destroy() {
        logger.debug("destroy");
    }

}
