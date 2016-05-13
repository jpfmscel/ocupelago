package br.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;

public class Util {

	public static int getIntAleatorio(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}

	public static boolean isDataOk(Date dataInicio, Date dataFim) {
		return dataInicio.before(dataFim);
	}
	
	public static String fixExternalURL(String url) {
		if (url != null && !url.isEmpty()) {
			if (url.contains("https://")) {
				return url;
			} else if (!url.contains("http://")) {
				url = "http://" + url;
			}
		}
		return url;
	}

	public static float getFloatAleatorio(int min, int max) {
		Random rand = new Random();
		float r = rand.nextFloat();
		while (r > 5000) {
			r = rand.nextFloat();
		}
		return r;
	}

	public static String getTimestampFromDate(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(data);
	}

	public static String getTimestampFromDateForFile(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss");
		return sdf.format(data);
	}

	public static Date getDateFromTimestamp(Timestamp ts) {
		return new Date(ts.getTime());
	}

	public static String getFormattedTimestamp(Timestamp ts) {
		return getTimestampFromDate(getDateFromTimestamp(ts));
	}

	public static Double getRandom(Double limit, Double min) {
		Double valor = new Double(0);
		do {
			valor = Math.random() * 100;
		} while (valor > limit || valor < min.intValue());
		return valor;
	}

	public static Date getMesPassadoPeriodo() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT -03:00"));
		cal.add(Calendar.DAY_OF_MONTH, -30);
		return cal.getTime();
	}

	public static Date getHoraPassadaPeriodo() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT -03:00"));
		cal.add(Calendar.HOUR_OF_DAY, -1);
		return cal.getTime();
	}

	public static String getFilePath() {
		if (System.getProperty("os.name").contains("Mac")) {
			return "/Users/jpfms/imagensOcupeLago/";
		} else if (System.getProperty("os.name").contains("Windows")) {
			return "C:\\imagens";
		}
		return null;
	}

	public static String getNomeArquivo(String nomeArquivo) {
		return getFilePath().concat(getTimestampFromDateForFile(new Date()).concat(nomeArquivo));
	}

	// Inserir:
	// get file > get bytes > insert bytes into blob

	// Consultar:
	// get bytes > write temp file > use temp file

	public static byte[] readBytesFromFile(String filePath) throws IOException {
		File inputFile = new File(filePath);
		FileInputStream inputStream = new FileInputStream(inputFile);

		byte[] fileBytes = new byte[(int) inputFile.length()];
		inputStream.read(fileBytes);
		inputStream.close();

		return fileBytes;
	}

	public static String saveBytesToFile(String nomeArquivo, byte[] fileBytes) throws IOException {
		String nomeArquivo2 = getNomeArquivo(nomeArquivo);
		FileOutputStream outputStream = new FileOutputStream(nomeArquivo2);
		outputStream.write(fileBytes);
		outputStream.close();
		return nomeArquivo2;
	}

	public static String encodeBase64(byte[] bytes) {
		return DatatypeConverter.printBase64Binary(bytes);
	}

	public static Object encodeBase64(String encoded) {
		return DatatypeConverter.parseBase64Binary(encoded);
	}
}
