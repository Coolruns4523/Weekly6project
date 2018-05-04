package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class MainController {

    @Autowired
    EventRepository eventRepository;

    @RequestMapping("/")
    public String listEvents(Model model)
    {
        model.addAttribute("events", eventRepository.findAll());
        return "list";}

    @GetMapping("/add")
    public String eventForm (Model model)
    {
        model.addAttribute("aEvent", new Event());
        return "eventForm";
    }

    @PostMapping("/saveevent")
    public String saveEvent(@Valid Event event, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "eventForm";
        }
        eventRepository.save(event);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showEvent (@PathVariable("id") long id, Model model)
    {
        model.addAttribute("aEvent", eventRepository.findEventById(id));
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateEvent (@PathVariable("id") long id, Model model)
    {
        model.addAttribute("aEvent",eventRepository.findEventById(id));
        return "eventForm";
    }

    @RequestMapping("/delete/{id}")
    public String delEvent(@PathVariable("id") long id, Model model)
    {
        eventRepository.deleteById(id);
        return "redirect:/";
    }
}
