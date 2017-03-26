package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.OverridesAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value="search")
public class SearchController {

    @RequestMapping(value = "", method=RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results
    @RequestMapping(value="results" ,method= RequestMethod.POST)
    public String results(Model model, @RequestParam("searchType") String column ,
                          @RequestParam("searchTerm") String value ){
        if(value==null||value.equals("")){
            ArrayList<HashMap<String, String>> jobs = JobData.findAll();
            model.addAttribute("title", "All " +
                                ListController.columnChoices.get(column) + " Values");
            model.addAttribute("jobs", jobs);

        }
        else if(ListController.columnChoices.get(column).equals("All")){
            ArrayList<HashMap<String, String>> jobs=JobData.findByValue(value);
            model.addAttribute("title", "Jobs with"+ value);
            model.addAttribute("jobs", jobs);
        }
        else{
            ArrayList<HashMap<String, String>> jobs = JobData.findByColumnAndValue(column, value);
            model.addAttribute("title", "Jobs with " +
                                ListController.columnChoices.get(column) + ": " + value);
            model.addAttribute("jobs", jobs);

        }

        return "search";
    }

}
