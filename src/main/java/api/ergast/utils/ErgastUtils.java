package api.ergast.utils;

import java.util.Collection;

public class ErgastUtils {

	public static String getDriverStandingUrlByYear(String serviceURL, int year) {
		String url = serviceURL + "/api/f1/" + year + "/driverStandings.json";
		System.out.println(url);
		return url;
	}

	public static String getSeasonsUrlByYear(String serviceURL, int year) {
		return serviceURL + "/api/f1/" + year + ".json";
	}

	public static String getPitStopsUrlByYear(String serviceURL, int year, int round) {
		return serviceURL + "/api/f1/" + year + "/" + round + "/pitstops.json";
	}

	public static String getConstructorsUrlByYear(String serviceURL, int year) {
		return serviceURL + "/api/f1/" + year + "/constructors.json";
	}

	public static String getDriversUrlByConstructor(String serviceURL, int year, String constructor) {
		return serviceURL + "/api/f1/" + year + "/constructors/" + constructor + "/drivers.json";
	}

	public static String getCsvOutput(Collection<?> collection) {
		String output = "";
		for (Object o : collection) {
			output += o + "<br>";
		}
		return output;

	}

}
