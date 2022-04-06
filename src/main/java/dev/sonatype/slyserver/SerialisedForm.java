package dev.sonatype.slyserver;

import com.unboundid.ldap.sdk.Attribute;

import java.util.List;

public class SerialisedForm {

    public String dn;
    public String[] objectClass;
    public String[] javaClassNames;
    public String javaClassName;
    public String cn;
    public byte[] javaSerialisedData;
    public List<Attribute> attributes;
}
