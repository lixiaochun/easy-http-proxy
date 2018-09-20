package co.il.nmh.easy.http.proxy.core;

import java.util.regex.Pattern;

import co.il.nmh.easy.http.proxy.mappers.MapRestClientResponseToResponseEntity;

/**
 * @author Maor Hamami
 */

public class DefaultProxyRequestHandler extends BaseProxyRequestHandler
{
	private String realServerURL;

	public DefaultProxyRequestHandler(RestClient restClient, MapRestClientResponseToResponseEntity mapRestClientResponseToResponseEntity, String realServerURL, Pattern methodPattern, Pattern urlPattern)
	{
		super(restClient, mapRestClientResponseToResponseEntity, methodPattern, urlPattern);

		this.realServerURL = realServerURL;
	}

	@Override
	public String getRealHost()
	{
		return realServerURL;
	}
}
