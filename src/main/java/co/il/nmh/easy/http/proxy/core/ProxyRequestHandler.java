package co.il.nmh.easy.http.proxy.core;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Maor Hamami
 *
 */
public class ProxyRequestHandler
{
	@Autowired(required = false)
	protected List<BaseProxyRequestHandler> proxyRequestHandlers;

	public ResponseEntity<Object> handle(HttpServletRequest httpServletRequest, String method, String requestURI, Map<String, List<String>> headers, byte[] payload)
	{
		if (null != proxyRequestHandlers)
		{
			for (BaseProxyRequestHandler baseProxyRequestHandler : proxyRequestHandlers)
			{
				Pattern methodPattern = baseProxyRequestHandler.methodPattern;
				Pattern urlPattern = baseProxyRequestHandler.urlPattern;

				if (methodPattern.matcher(method).matches() && urlPattern.matcher(requestURI).matches())
				{
					return baseProxyRequestHandler.handle(httpServletRequest, method, requestURI, headers, payload);
				}
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
