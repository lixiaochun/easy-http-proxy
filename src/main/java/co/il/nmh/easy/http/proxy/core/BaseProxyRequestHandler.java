package co.il.nmh.easy.http.proxy.core;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import co.il.nmh.easy.http.proxy.data.ProxyRequest;
import co.il.nmh.easy.http.proxy.mappers.MapRestClientResponseToResponseEntity;
import co.il.nmh.easy.utils.EasyInputStream;
import co.il.nmh.easy.utils.exceptions.RestException;
import co.il.nmh.easy.utils.rest.EasyRestClient;
import co.il.nmh.easy.utils.rest.data.RestClientResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Maor Hamami
 *
 */
@Slf4j
public abstract class BaseProxyRequestHandler
{
	protected MapRestClientResponseToResponseEntity mapRestClientResponseToResponseEntity;
	protected Pattern methodPattern;
	protected Pattern urlPattern;

	public BaseProxyRequestHandler(MapRestClientResponseToResponseEntity mapRestClientResponseToResponseEntity, Pattern methodPattern, Pattern urlPattern)
	{
		this.mapRestClientResponseToResponseEntity = mapRestClientResponseToResponseEntity;
		this.methodPattern = methodPattern;
		this.urlPattern = urlPattern;
	}

	public final ResponseEntity<Object> handle(HttpServletRequest httpServletRequest, String method, String requestURI, Map<String, List<String>> headers, EasyInputStream payload)
	{
		ProxyRequest proxyRequest = new ProxyRequest(httpServletRequest, method, requestURI, headers, payload);

		try
		{
			preExecute(proxyRequest);
			RestClientResponse response = execute(proxyRequest);
			Object responseBody = postExecute(proxyRequest, response);

			return mapRestClientResponseToResponseEntity.map(responseBody, response);
		}
		catch (Exception e)
		{
			log.error("error occured", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	protected void preExecute(ProxyRequest proxyRequest) throws Exception
	{
	}

	protected RestClientResponse execute(ProxyRequest proxyRequest) throws RestException
	{
		String realHost = getRealHost();

		if (realHost.endsWith("/"))
		{
			realHost = realHost.substring(0, realHost.length() - 1);
		}

		RestClientResponse restClientResponse = EasyRestClient.execute(realHost + proxyRequest.getRequestURI(), proxyRequest.getMethod(), proxyRequest.getHeaders(), proxyRequest.getPayload());
		return restClientResponse;
	}

	protected Object postExecute(ProxyRequest proxyRequest, RestClientResponse response) throws Exception
	{
		return response.getResponseBody().getAllBytes();
	}

	public abstract String getRealHost();
}
