package br.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class Util {

	public static String[] locais = { "Quarto", "Sala", "Cozinha", "Garagem" };

	public static int getIntAleatorio(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
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
			return "C:/imagens";
		}
		return null;
	}

}
