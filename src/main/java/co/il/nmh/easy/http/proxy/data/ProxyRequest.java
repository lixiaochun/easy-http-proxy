package co.il.nmh.easy.http.proxy.data;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import co.il.nmh.easy.utils.EasyInputStream;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Maor Hamami
 *
 */

@Data
@AllArgsConstructor
public class ProxyRequest
{
	private HttpServletRequest httpServletRequest;
	private String method;
	private String requestURI;
	private Map<String, List<String>> headers;
	private EasyInputStream payload;
}
