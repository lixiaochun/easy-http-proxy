package co.il.nmh.easy.http.proxy.core;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import co.il.nmh.easy.http.proxy.data.ProxyRequest;
import co.il.nmh.easy.http.proxy.exceptions.EasyHttpProxyExecption;
import co.il.nmh.easy.http.proxy.mappers.MapRestClientResponseToResponseEntity;
import co.il.nmh.easy.utils.EasyInputStream;
import co.il.nmh.easy.utils.exceptions.RestException;
import co.il.nmh.easy.utils.rest.data.RestClientResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Maor Hamami
 *
 */
@Slf4j
public abstract class TemplateProxyRequestHandler<T>
{
	protected RestClient restClient;
	protected MapRestClientResponseToResponseEntity mapRestClientResponseToResponseEntity;
	protected Pattern methodPattern;
	protected Pattern urlPattern;

	public TemplateProxyRequestHandler(RestClient restClient, MapRestClientResponseToResponseEntity mapRestClientResponseToResponseEntity, Pattern methodPattern, Pattern urlPattern)
	{
		this.restClient = restClient;
		this.mapRestClientResponseToResponseEntity = mapRestClientResponseToResponseEntity;
		this.methodPattern = methodPattern;
		this.urlPattern = urlPattern;
	}

	public final ResponseEntity<Object> handle(HttpServletRequest httpServletRequest, String method, String requestURI, Map<String, List<String>> headers, EasyInputStream payload)
	{
		ProxyRequest proxyRequest = new ProxyRequest(httpServletRequest, method, requestURI, headers, payload);

		try
		{
			T context = buildContext(proxyRequest);
			preExecute(proxyRequest, context);
			RestClientResponse response = execute(proxyRequest, context);
			Object responseBody = postExecute(proxyRequest, response, context);

			return mapRestClientResponseToResponseEntity.map(responseBody, response);
		}
		catch (EasyHttpProxyExecption e)
		{
			return mapRestClientResponseToResponseEntity.map(e.getRestClientResponse());
		}
		catch (Exception e)
		{
			log.error("error occured", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	protected abstract T buildContext(ProxyRequest proxyRequest);

	protected void preExecute(ProxyRequest proxyRequest, T context) throws Exception
	{
	}

	protected RestClientResponse execute(ProxyRequest proxyRequest, T context) throws RestException
	{
		String realHost = getRealHost();

		if (realHost.endsWith("/"))
		{
			realHost = realHost.substring(0, realHost.length() - 1);
		}

		RestClientResponse restClientResponse = restClient.execute(realHost + proxyRequest.getRequestURI(), proxyRequest.getMethod(), proxyRequest.getHeaders(), proxyRequest.getPayload());
		return restClientResponse;
	}

	protected Object postExecute(ProxyRequest proxyRequest, RestClientResponse response, T context) throws Exception
	{
		return response.getResponseBody().getAllBytes();
	}

	public abstract String getRealHost();
}
