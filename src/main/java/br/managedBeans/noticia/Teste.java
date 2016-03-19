package br.managedBeans.noticia;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Teste {

	public static void main (String[] args) {
		System.out.println(new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(Calendar.getInstance(new Locale( System.getProperty("user.language.format"),  System.getProperty("user.country.format"))).getTime()) +"h de Brasília");
    }
}
