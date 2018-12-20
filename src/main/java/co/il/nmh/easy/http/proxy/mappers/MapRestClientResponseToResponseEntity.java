package co.il.nmh.easy.http.proxy.mappers;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import co.il.nmh.easy.utils.EasyInputStream;
import co.il.nmh.easy.utils.rest.data.RestClientResponse;

/**
 * @author Maor Hamami
 *
 */

public class MapRestClientResponseToResponseEntity
{
	public ResponseEntity<Object> map(RestClientResponse restClientResponse)
	{
		EasyInputStream responseBody = restClientResponse.getResponseBody();
		Object body = responseBody;

		try
		{
			body = responseBody.getAllBytes();
		}
		catch (IOException e)
		{
		}

		return map(body, restClientResponse);
	}

	public ResponseEntity<Object> map(Object customBody, RestClientResponse restClientResponse)
	{
		HttpHeaders responseHeaders = new HttpHeaders();

		if (null != restClientResponse.getHeaderFields())
		{
			for (Entry<String, List<String>> entry : restClientResponse.getHeaderFields().getHeaders().entrySet())
			{
				String key = entry.getKey();

				if (null == key)
				{
					continue;
				}

				responseHeaders.addAll(key, entry.getValue());
			}
		}

		return new ResponseEntity<>(customBody, responseHeaders, HttpStatus.valueOf(restClientResponse.getHttpStatus()));
	}
}
