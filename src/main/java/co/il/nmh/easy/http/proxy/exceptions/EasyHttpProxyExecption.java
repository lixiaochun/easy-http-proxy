package co.il.nmh.easy.http.proxy.exceptions;

import co.il.nmh.easy.utils.rest.data.RestClientResponse;

/**
 * @author Maor Hamami
 *
 */

public class EasyHttpProxyExecption extends RuntimeException
{
	private static final long serialVersionUID = 386247924509953809L;

	private RestClientResponse restClientResponse;

	public EasyHttpProxyExecption(RestClientResponse restClientResponse)
	{
		this.restClientResponse = restClientResponse;
	}

	public RestClientResponse getRestClientResponse()
	{
		return restClientResponse;
	}
}
