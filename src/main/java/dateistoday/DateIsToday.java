package dateistoday;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateIsToday {

    // Метод получения текущей даты.
    public static String todayDateAndTime() {
        Calendar calendar = new GregorianCalendar();
        java.util.Date date = calendar.getTime();
        return date.toString();
    }

    // Метод для поля 'Дата создания шаблона', которое заполняется автоматически текущей датой и временем.
    public static String formatDate() {

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        Calendar calendar = new GregorianCalendar();
        java.util.Date date = calendar.getTime();
        String outputDate = outputFormat.format(date);
        return outputDate;
    }
}