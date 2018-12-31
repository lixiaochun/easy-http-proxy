package co.il.nmh.easy.http.proxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import co.il.nmh.easy.http.proxy.core.ProxyRequestHandler;
import co.il.nmh.easy.http.proxy.core.RestClient;
import co.il.nmh.easy.http.proxy.mappers.MapRestClientResponseToResponseEntity;
import co.il.nmh.easy.http.proxy.rest.ProxyRes;

/**
 * @author Maor Hamami
 *
 */
@Configuration
public class EasyHttpProxyConfiguration
{
	@Bean
	public MapRestClientResponseToResponseEntity mapRestClientResponseToResponseEntity()
	{
		return new MapRestClientResponseToResponseEntity();
	}

	@Bean
	public ProxyRequestHandler proxyRequestHandler()
	{
		return new ProxyRequestHandler();
	}

	@Bean
	public RestClient restClient()
	{
		return new RestClient();
	}

	@RestController
	public class ProxyResController extends ProxyRes
	{
	}
}
