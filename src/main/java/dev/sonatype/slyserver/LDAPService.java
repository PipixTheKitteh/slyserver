package dev.sonatype.slyserver;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPException;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;


import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.naming.NamingException;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Singleton

public class LDAPService {

    private static final Logger log = Logger.getLogger(LDAPService.class);

    public static final String BASEDNS="dc=example,dc=org";
    public static final int port=1389;

    @Inject
    Interceptor i;

    private  InMemoryDirectoryServer ds;


void onStop(@Observes ShutdownEvent ss ) {

    ds.shutDown(true);
    log.info("ldap service ended");
}

  void onStart(@Observes StartupEvent event) throws UnknownHostException, LDAPException, NamingException {

    log.info("ldap service started");

    InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(BASEDNS);

  config.setListenerConfigs(new InMemoryListenerConfig(
          "listen",
          InetAddress.getByName("0.0.0.0"),
          port,
          ServerSocketFactory.getDefault(),
          SocketFactory.getDefault(),
          (SSLSocketFactory) SSLSocketFactory.getDefault()));

  String server=InetAddress.getLocalHost().getHostAddress();

  log.infof("interceptor server host %s",server);

  config.addInMemoryOperationInterceptor(i);

    ds = new InMemoryDirectoryServer(config);

    ds.startListening();

    LdapServerUploader l=new LdapServerUploader();
    l.addObjects();

      }

}
