package co.il.nmh.easy.http.proxy.core;

import java.util.List;
import java.util.Map;

import co.il.nmh.easy.utils.EasyInputStream;
import co.il.nmh.easy.utils.exceptions.RestException;
import co.il.nmh.easy.utils.rest.EasyRestClient;
import co.il.nmh.easy.utils.rest.data.RestClientResponse;

/**
 * @author Maor Hamami
 *
 */

public class RestClient
{
	public RestClientResponse execute(String url, String method, Map<String, List<String>> headers, EasyInputStream payload) throws RestException
	{
		return EasyRestClient.execute(url, method, headers, payload);
	}

}
