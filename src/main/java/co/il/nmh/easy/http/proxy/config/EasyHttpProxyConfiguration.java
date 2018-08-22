package co.il.nmh.easy.http.proxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.il.nmh.easy.http.proxy.core.ProxyRequestHandler;
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
	public ProxyRes proxyRes()
	{
		return new ProxyRes();
	}
}
