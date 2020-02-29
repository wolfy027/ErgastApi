package api.ergast.utils;

import java.util.Collection;

public class ErgastUtils {

	public static String getDriverStandingUrlByYear(String serviceURL, int year) {
		String url = serviceURL + "/api/f1/" + year + "/driverStandings.json";
		System.out.println(url);
		return url;
	}

	public static String getCsvOutput(Collection<?> collection) {
		String output = "";
		for (Object o : collection) {
			output += o + "<br>";
		}
		return output;

	}

}
