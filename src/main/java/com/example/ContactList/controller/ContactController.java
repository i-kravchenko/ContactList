package com.example.ContactList.controller;

import com.example.ContactList.exception.ContactNotFoundException;
import com.example.ContactList.model.Contact;
import com.example.ContactList.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ContactController
{
    private final ContactService service;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", service.findAll());
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("title", "Create contact");
        model.addAttribute("contact", new Contact());
        return "edit";
    }

    @PostMapping("/create")
    public String add(@ModelAttribute Contact contact) {
        contact.setId(System.currentTimeMillis());
        service.save(contact);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("title", "Edit contact");
            Contact contact = service.findById(id);
            model.addAttribute("contact", contact);
            return "edit";
        } catch (ContactNotFoundException e) {
            return "redirect:/";
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Contact contact) {
        if(service.findById(id) != null) {
            contact.setId(id);
            service.update(contact);
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/";
    }
}
