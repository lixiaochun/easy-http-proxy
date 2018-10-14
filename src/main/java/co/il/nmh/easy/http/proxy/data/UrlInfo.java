package co.il.nmh.easy.http.proxy.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Maor Hamami
 *
 */
@Getter
@ToString
@EqualsAndHashCode
public class UrlInfo
{
	private List<String> path;
	private Map<String, String> query;

	public UrlInfo(String url)
	{
		path = new ArrayList<>();
		query = new HashMap<>();

		if (null != url)
		{
			String[] splitUrl = url.split("\\?");

			String path = splitUrl[0];
			String query = splitUrl.length > 1 ? splitUrl[1] : null;

			if (null != path)
			{
				StringTokenizer stringTokenizer = new StringTokenizer(path, "/");

				while (stringTokenizer.hasMoreTokens())
				{
					this.path.add(stringTokenizer.nextToken());
				}
			}

			if (null != query)
			{
				StringTokenizer stringTokenizer = new StringTokenizer(query, "&");

				while (stringTokenizer.hasMoreTokens())
				{
					String[] data = stringTokenizer.nextToken().split("=");

					if (data.length > 1)
					{
						String key = data[0];
						String value = data[1];

						this.query.put(key, value);
					}
				}
			}
		}
	}
}
