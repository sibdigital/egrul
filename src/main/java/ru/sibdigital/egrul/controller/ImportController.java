package ru.sibdigital.egrul.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.sibdigital.egrul.model.ModelTypes;
import ru.sibdigital.egrul.service.ImportService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class ImportController {

    @Autowired
    private ImportService importService;

//    @GetMapping("/import")
//    public ResponseEntity<String> importData() {
//        importService.importData();
//        return ResponseEntity.ok().body("Ok");
//    }

    @GetMapping("/import")
    public String import1() {
        return "import";
    }


    @GetMapping("/queue_up_all")
    public @ResponseBody
    String queueUpEgrul(@RequestParam("time") String time) {
        try {
            Date date = new Date(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time).getTime());
            importService.addToScheduleAll(date);
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
            return "Не удалось задать расписание запуска";
        }
        return "Ok";
    }

    @GetMapping("/queue_up_egrul")
    public @ResponseBody
    String queueUpEgrul(@RequestParam("loadVersion") Long loadVersion, @RequestParam("time") String time) {
        try {
            Date date = new Date(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time).getTime());
            importService.addToSchedule(ModelTypes.EGRUL_LOAD, Integer.parseInt(""+loadVersion), date);
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
            return "Не удалось задать расписание запуска ЕГРЮЛа";
        }
        return "Ok";
    }

    @GetMapping("/queue_up_egrip")
    public @ResponseBody
    String queueUpEgrip(@RequestParam("loadVersion") Long loadVersion, @RequestParam("time") String time) {
        try {
            Date date = new Date(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time).getTime());
            importService.addToSchedule(ModelTypes.EGRIP_LOAD, Integer.parseInt(""+loadVersion), date);
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
            return "Не удалось задать расписание запуска ЕГРИПа";
        }
        return "Ok";
    }

    @GetMapping("/delete_queue")
    public @ResponseBody
    String queueUpEgrip(@RequestParam("type") String type) {
        try {
            if (type.equals("egrul"))
                importService.deleteFromSchedule(ModelTypes.EGRUL_LOAD);
            else
                importService.deleteFromSchedule(ModelTypes.EGRIP_LOAD);
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
            return "Не удалось задать расписание запуска ЕГРИПа";
        }
        return "Ok";
    }


}
