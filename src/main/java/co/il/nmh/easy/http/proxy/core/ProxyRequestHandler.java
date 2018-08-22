package co.il.nmh.easy.http.proxy.core;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
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

	@PostConstruct
	private void init()
	{
		if (null != proxyRequestHandlers)
		{
			proxyRequestHandlers.sort(new Comparator<BaseProxyRequestHandler>()
			{
				@Override
				public int compare(BaseProxyRequestHandler arg1, BaseProxyRequestHandler arg0)
				{
					Pattern pattern0 = arg0.urlPattern;
					Pattern pattern1 = arg1.urlPattern;

					boolean matches0 = pattern0.matcher(pattern1.pattern()).matches();
					boolean matches1 = pattern1.matcher(pattern0.pattern()).matches();

					if (!matches0 && !matches1)
					{
						return pattern1.pattern().compareTo(pattern0.pattern());
					}

					if (matches0 && matches1)
					{
						return 0;
					}

					if (matches0)
					{
						return -1;
					}

					return 1;
				}
			});
		}
	}

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
