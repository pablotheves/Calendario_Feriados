package addon;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import cadm.feriados.dao.FeriadosDao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import model.classes.Feriado;

/**
 *
 * @author https://www.guj.com.br/t/contar-somente-dias-uteis/33294
 *
 */
public class DateUtils {

    public static Date stringToDate(String stringDate) {

        Date date = null;

        try {
            SimpleDateFormat sdf
                    = new SimpleDateFormat("dd/MM/yyyy", new Locale.Builder().setLanguage("pt").setRegion("BR").build());
            date = sdf.parse(stringDate);
        } catch (ParseException e) {
        }

        return date;
    }

    public static int deductDates(Date initialDate, Date finalDate) {

        if (initialDate == null || finalDate == null) {
            return 0;
        }

        int days = (int) ((finalDate.getTime() - initialDate.getTime()) / (24 * 60 * 60 * 1000));

        return (days > 0 ? days : 0);
    }

    public static String dateToString(Date date) {

        SimpleDateFormat sdf
                = new SimpleDateFormat("dd/MM/yyyy", new Locale.Builder().setLanguage("pt").setRegion("BR").build());

        String dateFormated = sdf.format(date);

        return dateFormated;
    }

    public static Date clearHour(Date date) {
        return (stringToDate(dateToString(date)));
    }
    
    
    private static boolean isFeriado(Date dia, List<Feriado> feriados){
        Date diaLimpo = clearHour(dia); // dia atual com a hora limpa para ñ dar incompatibilidade c/ sql
        
        for (Feriado feriado : feriados) {
            Date dataFeriado = clearHour(feriado.getDataFeriado());// passa por cada dia de feriado
            
            if(diaLimpo.equals(dataFeriado)){
                return true; // true p/ feriado
            }
        }
        return false;
        
    }
    
    
    public static int getWorkingDays(Date initialDate, Date finalDate, List<Feriado> feriados) {

        int workingDays = 0;
        int totalDays = deductDates(initialDate, finalDate);
        
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd", new Locale.Builder().setLanguage("pt").setRegion("BR").build());
        String day = sdf.format(initialDate);

        sdf = new SimpleDateFormat("MM", new Locale.Builder().setLanguage("pt").setRegion("BR").build());
        String month = sdf.format(initialDate);

        sdf = new SimpleDateFormat("yyyy", new Locale.Builder().setLanguage("pt").setRegion("BR").build());
        String year = sdf.format(initialDate);

        Calendar calendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
        
        
        for (int i = 0; i <= totalDays; i++) {
            int diaDaSemana = calendar.get(Calendar.DAY_OF_WEEK);
            
            if (diaDaSemana != Calendar.SATURDAY && diaDaSemana != Calendar.SUNDAY && !isFeriado(calendar.getTime(), feriados)) {
                workingDays++;
            }
            calendar.add(Calendar.DATE, 1);
        }

        return workingDays;
    }

}
