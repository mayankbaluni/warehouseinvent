package com.salor.ventgo.helper.date;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yudho utomo on 6/6/17.
 */

public class DateTimeMasker {

    private static Locale ID = new Locale("id", "ID", "ID");

    public static String rawPretty(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            //DateFormat formatter_show = new SimpleDateFormat("d MMMM yyyy, HH:mm", Locale.ENGLISH);
            //DateFormat formatter_show = new SimpleDateFormat("EEEE, d MMM yyyy", ID);
            DateFormat formatter_show = new SimpleDateFormat("d MMMM yyyy", ID);
            return formatter_show.format(date);
        }

        return "";
    }

    public static String labelBilangan(Double price) {

        DecimalFormat formatter = new DecimalFormat("#,###");
        String harga = formatter.format(price);

        return harga.replace(",", ".");
    }


    public static String changeToNameMonth(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("dd-MM-yyyy HH:mm", ID);
            return formatter_show.format(date);
        }

        return "";
    }

    public static String changeToNameMonthCustom(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("dd-MM-yyyy, HH:mm", ID);
            return formatter_show.format(date);
        }

        return "";

    }


    public static String changeDetailMonthCustom(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("dd-MM-yyyy, HH:mm", ID);
            return formatter_show.format(date);
        }

        return "";

    }

    public static String changeToDatePublishStockOpname(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("dd MMMM yyyy", ID);
            return formatter_show.format(date);
        }

        return "";
    }

    public static String changeToDate(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("dd-M-yyyy", ID);
            return formatter_show.format(date);
        }

        return "";
    }

    public static String changeToHour(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("HH:mm", ID);
            return formatter_show.format(date);
        }

        return "";
    }

    public static String changeToFullName(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("dd MMMM yyyy", ID);
            return formatter_show.format(date);
        }

        return "";
    }

    public static String changeToDayNumber(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("d", ID);
            return formatter_show.format(date);
        }

        return "";
    }

    public static String changeToDayName(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("EE", ID);
            return formatter_show.format(date);
        }

        return "";
    }

    public static String getDateFormatByDateMonthDialog(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("d MMM yyyy HH:mm", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("yyyy-MM-dd", ID);
            return formatter_show.format(date);
        }

        return "";
    }

    public static String changeHourtoHour(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("HH:mm", ID);
            return formatter_show.format(date);
        }

        return "";
    }


    public static String getDateFormatByDateJamDialog(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("HH:mm", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("HH:mm:ss", ID);
            return formatter_show.format(date);
        }

        return "";
    }

    public static String getDateFormatByJam(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("HH:mm", ID);
            return formatter_show.format(date);
        }

        return "";
    }

    public static String getDateFormatByBulan(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("MMM", ID);
            return formatter_show.format(date);
        }

        return "";
    }

    public static String getDateFormatByTahun(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("MMM", ID);
            return formatter_show.format(date);
        }

        return "";
    }

    public static String getDateFormatByDefaultFormat(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("d MMMM yyyy", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {

            DateFormat formatter_show = new SimpleDateFormat("yyyy-MM-dd", ID);
            return formatter_show.format(date);
        }

        return "";
    }

    // TODO: 25/05/18 format yang diubah yyyy-MM-dd
    public static String changeToDay(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            //DateFormat formatter_show = new SimpleDateFormat("d MMMM yyyy, HH:mm", Locale.ENGLISH);
            //DateFormat formatter_show = new SimpleDateFormat("EEEE, d MMM yyyy", ID);
            DateFormat formatter_show = new SimpleDateFormat("EEEE", ID);
            return formatter_show.format(date);
        }

        return "";
    }


    // TODO: 25/05/18 format yang diubah MMMM yyyy
    public static String changeFromMonthYeartoYear(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("MMMM yyyy", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            DateFormat formatter_show = new SimpleDateFormat("yyyy", ID);
            return formatter_show.format(date);
        }

        return "";
    }

    public static String createAt() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", ID);
        Date date = Calendar.getInstance().getTime();
        return formatter.format(date);
    }

    public static String createAtHour() {
        DateFormat formatter = new SimpleDateFormat("HH:mm", ID);
        Date date = Calendar.getInstance().getTime();
        return formatter.format(date);
    }

    public static String createAtTime() {
        DateFormat formatter = new SimpleDateFormat("yyyy MM dd", ID);
        Date date = Calendar.getInstance().getTime();
        return formatter.format(date);
    }

    public static String createAtTimeDayFirst() {
        DateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", ID);
        Date date = Calendar.getInstance().getTime();
        return formatter.format(date);
    }

    public static String createAtDay() {
        DateFormat formatter = new SimpleDateFormat("dd", ID);
        Date date = Calendar.getInstance().getTime();
        return formatter.format(date);
    }

    public static int bulanNow() {
        DateFormat formatter = new SimpleDateFormat("MM", ID);
        Date date = Calendar.getInstance().getTime();
        return Integer.parseInt(formatter.format(date));
    }

    public static String bulanNowFull() {
        DateFormat formatter = new SimpleDateFormat("MMMM", ID);
        Date date = Calendar.getInstance().getTime();
        return formatter.format(date);
    }

    public static String tahunNow() {
        DateFormat formatter = new SimpleDateFormat("yyyy", ID);
        Date date = Calendar.getInstance().getTime();
        return formatter.format(date);
    }


    // ----------- // TODO: 08/06/18 untuk pemformatan ke api


    // / TODO: 25/05/18 format yang diubah MMMM
    public static String changeToMonthformat2(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("MMMM", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            DateFormat formatter_show = new SimpleDateFormat("MM", ID);

            int dateConvert = Integer.parseInt(formatter_show.format(date));

            int hasil = dateConvert - 1;

            return String.valueOf(hasil);
        }

        return "";
    }

    // / TODO: 25/05/18 format yang diubah MMMM
    // januari = 1
    public static String changeToMonthformat2Default(String dateRaw) {
        DateFormat formatter = new SimpleDateFormat("MMMM", ID);
        Date date = null;
        try {
            date = formatter.parse(dateRaw);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            DateFormat formatter_show = new SimpleDateFormat("MM", ID);

            return formatter_show.format(date);

        }

        return "";
    }

//    // TODO: 13/07/18 get Date Now menggunakan param
//    public static String getCurrentDateJustMonth(int month) {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.add(Calendar.MONTH, month);
//        Date newDate = calendar.getTime();
//        String str_date = df.format(newDate);
//        SimpleDateFormatter simpleDateFormatter = new SimpleDateFormatter();
//        Date format_tanggal_start = simpleDateFormatter.stringToDateInd(str_date, Cons.DateFormat);
//        return simpleDateFormatter.dateToStringInd(format_tanggal_start, "MMMM");
//    }
//
//    public static String getCurrentDateDayMonthYear(int day)
//    {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.add(Calendar.DATE, day);
//        Date newDate = calendar.getTime();
//        String str_date = df.format(newDate);
//        SimpleDateFormatter simpleDateFormatter = new SimpleDateFormatter();
//        Date format_tanggal_start = simpleDateFormatter.stringToDateInd(str_date, Cons.DateFormat);
//        return simpleDateFormatter.dateToStringInd(format_tanggal_start, "dd MMMM yyyy");
//    }

}
