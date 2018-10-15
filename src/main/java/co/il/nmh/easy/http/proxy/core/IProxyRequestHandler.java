package co.il.nmh.easy.http.proxy.core;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import co.il.nmh.easy.utils.EasyInputStream;

/**
 * @author Maor Hamami
 */

public interface IProxyRequestHandler
{
	ResponseEntity<Object> handle(HttpServletRequest httpServletRequest, String method, String requestURI, Map<String, List<String>> headers, EasyInputStream payload);

	Pattern getUrlPattern();

	Pattern getMethodPattern();
}
