package com.example.strona;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/")
public class Contr {

    @Autowired
    private ExcelService excelService;


    @GetMapping("/")
    public String welcome(){
        return "welcome";
    }

    @PostMapping("/")
    public String sd(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        excelService.addHoliday();
        long roznica = 0;
        Date date1;
        Date date2;
        if(!(request.getParameter("line1_1").isEmpty() || request.getParameter("line1_2").isEmpty())) {
            date1 = excelService.getDateFromString(request.getParameter("line1_1"));
            date2 = excelService.getDateFromString(request.getParameter("line1_2"));
            roznica += excelService.roznicaCzasu(date1, date2);
        }
        if(!(request.getParameter("line2_1").isEmpty() || request.getParameter("line2_2").isEmpty())) {
            date1 = excelService.getDateFromString(request.getParameter("line2_1"));
            date2 = excelService.getDateFromString(request.getParameter("line2_2"));
            roznica += excelService.roznicaCzasu(date1, date2);
        }
        if(!(request.getParameter("line3_1").isEmpty() || request.getParameter("line3_2").isEmpty())) {
            date1 = excelService.getDateFromString(request.getParameter("line3_1"));
            date2 = excelService.getDateFromString(request.getParameter("line3_2"));
            roznica += excelService.roznicaCzasu(date1, date2);
        }
        if(!(request.getParameter("line4_1").isEmpty() || request.getParameter("line4_2").isEmpty())) {
            date1 = excelService.getDateFromString(request.getParameter("line4_1"));
            date2 = excelService.getDateFromString(request.getParameter("line4_2"));
            roznica += excelService.roznicaCzasu(date1, date2);
        }
        if(!(request.getParameter("line5_1").isEmpty() || request.getParameter("line5_2").isEmpty())) {
            date1 = excelService.getDateFromString(request.getParameter("line4_1"));
            date2 = excelService.getDateFromString(request.getParameter("line4_2"));
            roznica += excelService.roznicaCzasu(date1, date2);
        }
        if(!(request.getParameter("line6_1").isEmpty() || request.getParameter("line6_2").isEmpty())) {
            date1 = excelService.getDateFromString(request.getParameter("line4_1"));
            date2 = excelService.getDateFromString(request.getParameter("line4_2"));
            roznica += excelService.roznicaCzasu(date1, date2);
        }
        if(!(request.getParameter("line7_1").isEmpty() || request.getParameter("line7_2").isEmpty())) {
            date1 = excelService.getDateFromString(request.getParameter("line4_1"));
            date2 = excelService.getDateFromString(request.getParameter("line4_2"));
            roznica += excelService.roznicaCzasu(date1, date2);
        }
        if(!(request.getParameter("line8_1").isEmpty() || request.getParameter("line8_2").isEmpty())) {
            date1 = excelService.getDateFromString(request.getParameter("line4_1"));
            date2 = excelService.getDateFromString(request.getParameter("line4_2"));
            roznica += excelService.roznicaCzasu(date1, date2);
        }
        if(!(request.getParameter("line9_1").isEmpty() || request.getParameter("line9_2").isEmpty())) {
            date1 = excelService.getDateFromString(request.getParameter("line4_1"));
            date2 = excelService.getDateFromString(request.getParameter("line4_2"));
            roznica += excelService.roznicaCzasu(date1, date2);
        }
        if(!(request.getParameter("line10_1").isEmpty() || request.getParameter("line10_2").isEmpty())) {
            date1 = excelService.getDateFromString(request.getParameter("line4_1"));
            date2 = excelService.getDateFromString(request.getParameter("line4_2"));
            roznica += excelService.roznicaCzasu(date1, date2);
        }
        System.out.println(excelService.czasZMinut(roznica));
        model.addAttribute("line4_3", excelService.czasZMinut(roznica));

        return "welcome";
    }
}
