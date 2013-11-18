package com.researchorum.controllers;

import com.researchorum.context.ResearchorumContext;
import com.researchorum.context.ResearchorumContextFactory;
import com.researchorum.models.SearchPageModel;
import com.researchorum.services.RecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
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
    public String searchResultsPage(ModelMap modelMap) {
        ResearchorumContext ctx = ResearchorumContextFactory.getContext();
        Map<String, String[]> parameterMap = ctx.getUrlParams();
        String keyword = parameterMap.get("kw")[0];

        SearchPageModel searchPageModel = recordService.getRecordsByTitle(keyword);

        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("records", searchPageModel.getRecords());

        return "search";
    }
}
