package api.ergast.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public class CsvUtils {

	public static void writeCsvResponse(String[] header, String csvFileName, HttpServletResponse response,
			List<?> objects) {
		try {
			response.setContentType("text/csv");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
			response.setHeader(headerKey, headerValue);
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			csvWriter.writeHeader(header);
			for (Object object : objects) {
				csvWriter.write(object, header);
			}
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
