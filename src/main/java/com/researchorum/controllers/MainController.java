package com.researchorum.controllers;

import com.researchorum.models.Record;
import com.researchorum.services.RecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author pbathala
 */
@Controller
@RequestMapping("/")
public class MainController extends AbstractController {

    private static final Logger logger = Logger.getLogger(MainController.class);

    @Autowired
    RecordService recordService;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(method = RequestMethod.GET)
    public String homePage(HttpServletRequest request) {
        logger.info("New request - " + request.getRemoteAddr() + "\t" + request.getLocale());
        return "index";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String searchResultsPage(HttpServletRequest request, ModelMap modelMap) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        logger.info("Parameters map: " + parameterMap.toString());
        int ipp = 2;
        int page = 1;
        String keyword = parameterMap.get("keyword")[0];
        String[] ippParam = parameterMap.get("ipp");
        if (ippParam != null && ippParam.length > 0) {
            ipp = Integer.parseInt(ippParam[0]);
        }
        String[] pageParam = parameterMap.get("pg");
        if (pageParam != null && pageParam.length > 0) {
            page = Integer.parseInt(pageParam[0]);
        }

        logger.info("ipp: " + ipp + "; pg: " + page);

        List<Record> records = recordService.getRecordsByTitle(keyword, ipp, page);

        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("records", records);

        for (Record p : records) {
            logger.info(p.toString());
        }

        logger.info("Posts returned: " + records.size());

        return "search";
    }
}
