import dev.sonatype.slyserver.Realated;

import javax.naming.Context;
import javax.naming.Name;
import java.util.Hashtable;

public class ExternalObject implements javax.naming.spi.ObjectFactory {
    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        Realated r=new Realated();
        return ""+r+ "XYX"+System.getProperty("java.version")+" "+System.getProperty("com.sun.jndi.ldap.object.trustURLCodebase")+"??"+System.getProperties().keySet();
    }

    public static void main(String[] args) {
        System.out.println("hello I'm a real object");
    }
}


