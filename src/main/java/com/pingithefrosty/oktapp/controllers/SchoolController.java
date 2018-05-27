package com.pingithefrosty.oktapp.controllers;

import com.pingithefrosty.oktapp.collections.Telepulesek;
import com.pingithefrosty.oktapp.collections.Years;
import com.pingithefrosty.oktapp.models.School;
import com.pingithefrosty.oktapp.services.DataImportService;
import com.pingithefrosty.oktapp.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SchoolController {

  final
  SchoolService schoolService;

  final
  Years years;

  final
  Telepulesek telepulesek;

  @Autowired
  public SchoolController(SchoolService schoolService, Years years, Telepulesek telepulesek) {
    this.schoolService = schoolService;
    this.years = years;
    this.telepulesek = telepulesek;
  }

  @GetMapping("/")
  public String showStartPage(Model model) {
    return "startPage";
  }

  @GetMapping("/schools")
  public String showSchools(@RequestParam(value = "search", required = false, defaultValue = "0") int search,
                            @RequestParam(value = "matek", required = false, defaultValue = "0") int matek,
                            @RequestParam(value = "matek", required = false, defaultValue = "0") int szoveg, Model model) {
    ArrayList<School> allSchools = schoolService.getAllSchoolsAsArray();
    List<School> schools = new ArrayList<>();
    if (search == 10) {
      schools.addAll(schoolService.sortByAbc(allSchools));
    } else if (search == 11) {
      schools.addAll(schoolService.reverseSortByAbc(allSchools));
    } else if (search == 20) {
      schools.addAll(schoolService.sortByNumber(allSchools, "matek"));
    } else if (search == 21) {
      schools.addAll(schoolService.reverseSortByNumber(allSchools, "matek"));
    } else if (search == 30) {
      schools.addAll(schoolService.sortByNumber(allSchools, "szoveg"));
    } else if (szoveg == 31) {
      schools.addAll(schoolService.reverseSortByNumber(allSchools, "szoveg"));
    } else {
      schools.addAll(allSchools);
    }
    model.addAttribute("schools", schools);
    return "schoolList";
  }

  @PostMapping("/schools/{iskolaId}")
  public String displaySchool() {

    return null;
  }

  @GetMapping("/schools/{ev}")
  public String searchSchools(@PathVariable String ev, Model model) {
    List<School> schools = schoolService.getAllSchoolsByEv(Integer.parseInt(ev));
    model.addAttribute("schools", schools);
    return "schoolList";
  }

  @GetMapping("/search")
  public String showSearch(Model model) {
    model.addAttribute("yearTypes", years.getYearTypes());
    model.addAttribute("telepulesTypes", telepulesek.getTelepulesTypes());
    return "searchPage";
  }



}