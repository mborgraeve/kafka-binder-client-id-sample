# kafka-binder-client-id-sample
This project is made to highlight what we are trying to achieve.

Our application is setup in a multicluster kafka setup. When a cluster is not available at start up, we need to spot quickly which cluster is down.
Currently, the log that tells us something is wrong is using the `client-id` field of the `AdminClient` object. This field's value is automatically generated, so does not points to the cluster directly.

While we can setup this property as a whole using the `spring.kafka.admin.client-id` property, this sets the value for *all* admin clients, which defeats the solution.

It would be nice to have a place to set this up, i guess the binder configuration would be a suitable place.

You can reproduce this with a one-cluster setup using this app by doing the following. Note that the value of having this property in a one-cluster setup is low, but these are just steps to show what we would need. Also note that I had to disable JMX Agent.
- First start up a terminal and run `nc -l -k 9092` so we have timeouts at startup that will trigger the logs
- Start the application using the `localdev` profile
- After a few seconds you'll see the following log:
```log
2021-05-12 11:21:05.169  INFO 60657 --- [| adminclient-1] o.a.k.c.a.i.AdminMetadataManager         : [AdminClient clientId=adminclient-1] Metadata update failed

org.apache.kafka.common.errors.TimeoutException: Call(callName=fetchMetadata, deadlineMs=1620832865163) timed out at 1620832865164 after 1 attempt(s)
Caused by: org.apache.kafka.common.errors.TimeoutException: Timed out waiting to send the call.
```

If you want to check using a multicluster, adapt and use the `localdev-multicluster` profile (you'll need to find a way to run two different Kafka clusters, which was a pain for me - I ended up spinning two VMs in network bridge mode to ensure clusters are really independant).
