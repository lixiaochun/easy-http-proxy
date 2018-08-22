package co.il.nmh.easy.http.proxy.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.il.nmh.easy.http.proxy.core.ProxyRequestHandler;

/**
 * @author Maor Hamami
 *
 */
@RestController
public class ProxyRes
{
	@Autowired
	protected ProxyRequestHandler proxyRequestHandler;

	@RequestMapping(value = "**")
	public ResponseEntity<Object> proxy(HttpServletRequest httpServletRequest) throws IOException
	{
		String method = httpServletRequest.getMethod();
		String requestURI = httpServletRequest.getRequestURI();

		String queryString = httpServletRequest.getQueryString();

		if (null != queryString)
		{
			requestURI += "?" + queryString;
		}

		Map<String, List<String>> headers = getHeaders(httpServletRequest);
		byte[] payload = getPayload(httpServletRequest);

		return proxyRequestHandler.handle(httpServletRequest, method, requestURI, headers, payload);
	}

	private Map<String, List<String>> getHeaders(HttpServletRequest httpServletRequest)
	{
		Map<String, List<String>> headers = new HashMap<>();

		Enumeration<String> headerNames = httpServletRequest.getHeaderNames();

		while (headerNames.hasMoreElements())
		{
			String header = headerNames.nextElement();
			Enumeration<String> values = httpServletRequest.getHeaders(header);

			while (values.hasMoreElements())
			{
				String value = values.nextElement();

				if (!headers.containsKey(header))
				{
					headers.put(header, new ArrayList<>());
				}

				headers.get(header).add(value);
			}
		}
		return headers;
	}

	private byte[] getPayload(HttpServletRequest httpServletRequest) throws IOException
	{
		ServletInputStream inputStream = httpServletRequest.getInputStream();
		BufferedInputStream reader = new BufferedInputStream(inputStream);

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = reader.read(data, 0, data.length)) != -1)
		{
			buffer.write(data, 0, nRead);
		}

		buffer.flush();
		reader.close();

		byte[] response = buffer.toByteArray();

		return response;
	}
}
