package com.example.strona;


import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class ExcelService {

        private final static int CZAS_REAKCJI_SLA = 480;
        private final static int CZAS_NAPRAWY_SLA = 32*60;

    List<Date> holidays = new LinkedList<>();

    Scanner scanner = new Scanner(System.in);




    public long czasOczekiwaniaWMinutach(String utworzono, String reakcja) throws Exception{
        SimpleDateFormat formatter6=new SimpleDateFormat("dd/MMM/yy hh:mm a", Locale.ENGLISH);
        Date utworzonoDate = formatter6.parse(utworzono);
        Date reakcjaDate = formatter6.parse(reakcja);
        long minutes = TimeUnit.MINUTES.convert(Math.abs(reakcjaDate.getTime() - utworzonoDate.getTime()), TimeUnit.MILLISECONDS);
        return minutes;
    }

    public long roznicaCzasuMiedzyGodzinami(Date date, Date date1){

        if(isFreeDay(date) && isFreeDay(date1))
            return 0;

        return TimeUnit.MINUTES.convert(Math.abs(date1.getTime() - date.getTime()), TimeUnit.MILLISECONDS);
    }

    public long roznicaCzasuMiedzyDatami(Date date, Date date1){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR,1);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        long czas = 0;
        if(!isFreeDay(date))
            czas += czasDoKoncaDnia(date);
        while (!isTheSameDay(calendar.getTime(), calendar1.getTime())){
            if(!isFreeDay(calendar.getTime()))
                czas += 8*60;
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        if(!isFreeDay(date1))
        czas += czasOdPoczatkuDnia(date1);
        return czas;
    }


    public long czasDoKoncaDnia(Date date){
        if(isFreeDay(date))
            return 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 4);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.AM_PM, Calendar.PM);
        //c.set(Calendar.DAY_OF_MONTH, date.getDay());
//
//        if(date.getHours()>=16){
//            c.add(Calendar.DAY_OF_MONTH,-1);
//        }


        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.set(Calendar.HOUR, 8);
        c1.set(Calendar.MINUTE,0);
        c1.set(Calendar.AM_PM, Calendar.AM);

        if(date.before(c1.getTime()))
            date.setTime(c1.getTime().getTime());



        long minuty = TimeUnit.MINUTES.convert(Math.abs(c.getTime().getTime() - date.getTime()), TimeUnit.MILLISECONDS);
        if(date.after(c.getTime()))
            return 0;
        return minuty;

    }

    public long czasOdPoczatkuDnia(Date date){
        if(isFreeDay(date))
            return 0;


        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 8);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.AM_PM, Calendar.AM);

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.set(Calendar.HOUR, 4);
        c1.set(Calendar.MINUTE,0);
        c1.set(Calendar.AM_PM, Calendar.PM);

        if(date.before(c.getTime()))
            date.setTime(c.getTime().getTime());
        if(date.after(c1.getTime()))
            date.setTime(c1.getTime().getTime());
        long minuty = TimeUnit.MINUTES.convert(Math.abs(date.getTime() - c.getTime().getTime() ), TimeUnit.MILLISECONDS);
        return minuty;

    }

    public Date getDateFromString(String data) throws Exception{
        SimpleDateFormat formatter6=new SimpleDateFormat("dd/MMM/yy hh:mm a", Locale.ENGLISH);
        return formatter6.parse(data);
    }



    public long roznicaCzasu(Date date1, Date date2){
        if(isTheSameDay(date1, date2)){
            return roznicaCzasuMiedzyGodzinami(dataiGodzinaOoKtorejLiczymy(date1), dataIGodzinaDoKtórejLiczymy(date2));
        } else if(isTheNextDay(date1, date2)){
            return czasDoKoncaDnia(date1) + czasOdPoczatkuDnia(date2);
        } else {
            return roznicaCzasuMiedzyDatami(date1, date2);
        }
    }

    public boolean isTheNextDay(Date date, Date date1){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        if(calendar.get(Calendar.DAY_OF_YEAR) == calendar1.get(Calendar.DAY_OF_YEAR) &&
                calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR))
                    return true;
                return false;
    }


    public boolean isTheSameDay(Date date, Date date1){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        if(calendar.get(Calendar.DAY_OF_YEAR) == calendar1.get(Calendar.DAY_OF_YEAR) &&
                calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR))
                    return true;
                return false;
    }


    public Date dataiGodzinaOoKtorejLiczymy(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 8);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.AM_PM, Calendar.AM);

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.set(Calendar.HOUR, 4);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.AM_PM, Calendar.PM);

        if(date.after(c1.getTime()))
            return c1.getTime();
        if(date.before(c.getTime())) {
            return c.getTime();
        }
         else if (date.after(c.getTime())){
            return date;
        } else if (date.equals(c.getTime())){
            return date;
        }

        return null;

    }


    public Date dataIGodzinaDoKtórejLiczymy(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 4);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.AM_PM, Calendar.PM);
        if(date.after(c.getTime())){
            return c.getTime();
        } else if (date.before(c.getTime())){
            return date;
        } else if (date.equals(c.getTime())){
            return date;
        }
        return null;

    }


    public boolean isFreeDay(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || isHoliday(date))
            return true;
        return false;
    }

    public List<Date> addHoliday(){
        List<Date> dates = new LinkedList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020,0,1);
        dates.add(calendar.getTime());
        calendar.set(2020, 0,6);
        dates.add(calendar.getTime());
        calendar.set(2020,3,12);
        dates.add(calendar.getTime());
        calendar.set(2020,3,13);
        dates.add(calendar.getTime());
        calendar.set(2020, 4,1);
        dates.add(calendar.getTime());
        calendar.set(2020, 4,3);
        dates.add(calendar.getTime());
        calendar.set(2020,4,31);
        dates.add(calendar.getTime());
        calendar.set(2020,5,11);
        dates.add(calendar.getTime());
        calendar.set(2020,7,15);
        dates.add(calendar.getTime());
        calendar.set(2020,10,1);
        dates.add(calendar.getTime());
        calendar.set(2020,10,11);
        dates.add(calendar.getTime());
        calendar.set(2020,11,25);
        dates.add(calendar.getTime());
        calendar.set(2020,11,26);

        calendar.set(2019,11,25);
        dates.add(calendar.getTime());
        calendar.set(2019,11,26);
        dates.add(calendar.getTime());
        this.holidays = dates;
        return dates;
    }


    public boolean isHoliday(Date date){
        for(Date date1 : getHolidays()){
            if(isTheSameDay(date, date1))
                return true;
        }
        return false;
    }


    public List<Date> getHolidays() {
        return this.holidays;
    }

    public String czasZMinut(long minuty){
        long minutyString = 0;
        long godziny = minuty/60;
            if(godziny==0){
                minutyString = minuty;
            } else {
                minutyString = minuty - (godziny*60);
            }
        StringBuilder stringBuilder = new StringBuilder();
            if(minutyString==0){
                if(godziny==0){
                    return "0min";
                } else {
                    stringBuilder.append(godziny).append("h");
                    return stringBuilder.toString();
                }

            }
            if(godziny==0){
                String minutyOut = stringBuilder.append(minuty).append("min").toString();
                return minutyOut;
            }
            String minutyOut = String.valueOf(minutyString).length()==1 ? stringBuilder.append("0").append(minutyString).toString() : String.valueOf(minutyString);
            stringBuilder = new StringBuilder();
            stringBuilder.append(godziny).append("h ").append(minutyOut);
            stringBuilder.append("min");
        return stringBuilder.toString();
    }


    public File openFile(){
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }



    public String setStatus(String taksName){
        if(taksName.toLowerCase().startsWith("[CASHE]".toLowerCase())) {
            return "NAPRAWA KOSZYKA";
        } else if(taksName.toLowerCase().startsWith("[ZAMÓWIENIE]".toLowerCase())){
            if(taksName.toLowerCase().contains("Błędna anulacja".toLowerCase())){
                return "AKTUALIZACJA DOSTĘPNOŚCI";
            } else if (taksName.toLowerCase().contains("Błąd dotyczący wysyłki".toLowerCase())){
                return "AKTUALIZACJA STATUSÓW";
            }
        } else if(taksName.toLowerCase().startsWith("[INNE]".toLowerCase())){
            return "INNE";
        } else if(taksName.toLowerCase().startsWith("[FORMY DOSTAWY]".toLowerCase())){
            return "WERYFIKACJA FORM DOSTAWY";
        } else if (taksName.toLowerCase().startsWith("[ZWROTY]".toLowerCase())){
            return "ZWROT";
        } else if(taksName.toLowerCase().startsWith("[OPIS/ZDJĘCIE]".toLowerCase())){
            return "AKTUALIZACJA DANYCH";
        } else if(taksName.toLowerCase().startsWith("[RABAT]".toLowerCase())){
            return "RABAT";
        } else if(taksName.toLowerCase().startsWith("[FORMY PŁATNOŚCI]".toLowerCase())){
            return "FORMY PŁATNOŚCI";
        } else if(taksName.toLowerCase().startsWith("[STATUS]".toLowerCase())) {
            if(taksName.toLowerCase().contains("Zamówienie zablokowane – inne".toLowerCase())){
                return "ODBLOKOWANIE ZAMÓWIEŃ";
            } else if (taksName.toLowerCase().contains("Zamówienie zablokowane w statusie".toLowerCase())){
                return "UDROŻNIANIE PROCESÓW";
            } else if (taksName.toLowerCase().contains("Błędny status".toLowerCase())){
                return "AKTUALIZACJA STATUSÓW";
            }
        }

        return "INNE";
    }
}
