# easy-http-proxy
proxy for http based on spring-web, allow the user to give custom handles for method+url patterns

if you wish to have pure proxy, just define DefaultProxyRequestHandler as @Bean in your configuration class and provide in the constructor
the real server url, accept all methods and urls using pattern Pattern.compile(".*").

for example:
```xml
@Bean
public DefaultProxyRequestHandler defaultProxyRequestHandler(MapRestClientResponseToResponseEntity mapRestClientResponseToResponseEntity)
{
	return new DefaultProxyRequestHandler(mapRestClientResponseToResponseEntity, "http://google.com", Pattern.compile(".*"), Pattern.compile(".*"));
}
```
