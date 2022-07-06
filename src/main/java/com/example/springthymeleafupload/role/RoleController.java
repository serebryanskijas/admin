package com.example.springthymeleafupload.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class RoleController {

    @Autowired
    private RoleService firmService;

    @GetMapping(value ="/add_firm")
    public String index(Model model) {
        //model.addAttribute("firm",new Firm()); //Если не добавить, то не будет выполняться парсинг шаблона исходной страницы
        return "add_firm";
    }

    @GetMapping(value ="/list_firms" )
    public String listFirms(Model model) {
        model.addAttribute("firms", firmService.findAll());
        return "list_firms";
    }

    @PostMapping(value="/add_firm")
    public String saveFirm(Role firm, Model model, HttpServletResponse response) {
        System.out.println(firm);
        //Передать id в заголовке ответа
        //Firm newFirm = firmService.save(firm);
        //long id = newFirm.getId();
        //response.addHeader("id", String.valueOf(id));
        model.addAttribute("firms", firmService.findAll());
        return "redirect:/list_firms";
    }

    @DeleteMapping(value = "/delete_firm")
    public String deleteFirm(@RequestParam(name="id")Long id) {
        firmService.deleteById(id);
        return "redirect:/list_firms";
    }

    @GetMapping(value ="/edit_firm")
    public String editFirm(Model model, @RequestParam(name="id")Long id) {
        //Firm firm = firmService.findById(id);
        //model.addAttribute("firm",firm);
        return "edit_firm";
    }

    @PutMapping(value="/update_firm")
    public String updateFirm(Role firm, Model model) {
        //Firm firmDb = firmService.findById(firm.getId());
        //firmDb.setName(firm.getName());
        //firmService.save(firmDb);
        model.addAttribute("firms", firmService.findAll());
        return "redirect:/list_firms";
    }
}
