package sg.edu.nus.iss.app.day13workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import sg.edu.nus.iss.app.day13workshop.model.Contact;
import sg.edu.nus.iss.app.day13workshop.util.Contacts;

@Controller
@RequestMapping(path="/contact")
public class AddressBookController {

    @Autowired
    private Contacts ctcs;

    @Autowired
    private ApplicationArguments appArgs;

    @Value("${day13workshop.default.data.dir}")
    private String defaultDataDir;

    // Step 1: Use get to add the model object to the form
    @GetMapping
    public String showAddressBookForm(Model model){
        model.addAttribute("contact", new Contact());
        return "addressbook";
    }

    // Step 2: Take in data from form (param contact = object ${contact})
    @PostMapping
    public String saveContact(@Valid Contact contact, BindingResult binding
        , Model model) {
        // Use syntactic validation (jakarta validation)
        // If there are errors, return to the form page
        if (binding.hasErrors()){
            return "addressbook";
        }

        ctcs.saveContact(contact, model, appArgs, defaultDataDir);
        return "showcontact";
    }

    // Get the contact with the contact ID in the url
    @GetMapping(path="{contactId}")
    public String getContactId(Model model, @PathVariable String contactId){
        ctcs.getContactById(model, contactId, appArgs, defaultDataDir);
        return "showcontact";
    }
    
    @GetMapping(path="/list")
    public String getAllContacts(Model model) {
        ctcs.getAllContacts(model, appArgs, defaultDataDir);
        return "contacts";
    }
}