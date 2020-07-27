(https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html)
All actuator endpoints are disabled by default. You can enable them by using the following command:

management.endpoints.web.exposure.include=* 

or you could be selective for specific endpoints (e.g. info and health):

management.endpoints.web.exposure.include=info,health

For prometheus, download the required dependencies actuator and prometheus, enable the following properties:

management.endpoint.metrics.enabled=true
management.endpoints.web.exposure.include=*
management.endpoint.prometheus.enabled=true
management.metrics.export.prometheus.enabled=true

and then you should have access to prometheus metrics via the following endpoints:

http://localhost:8080/actuator
http://localhost:8080/actuator/prometheus


==========================================

List all metric keys:

http://localhost:8080/actuator/metrics

Choose a metric to view using a metric key (e.g. system.cpu.usage):

http://localhost:8080/actuator/metrics/system.cpu.usage


===

(https://www.baeldung.com/micrometer)
when you create a custom metric, you need to define a key-value tag:

it will show up in prometheus as the following:

counter_query_logs_total{counter_key="counter_value",} 1.0

....where the name of the metric is "counter_query_logs_total"
	the name of the tag key = "counter_key"
	the name of the tag value = "counter_value"
	
	
	
	
===========

view the log file:

http://localhost:8080/actuator/logfile

(case sensitive URL)


===============

good article for creating RestControllerAdvice (for all rest controllers)
https://thepracticaldeveloper.com/2019/09/09/custom-error-handling-rest-controllers-spring-boot/


================

kafka-producer-consumer
https://dzone.com/articles/kafka-producer-and-consumer-example