package sg.edu.nus.iss.app.day13workshop.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import sg.edu.nus.iss.app.day13workshop.model.Contact;

// Component class was created because repository not required in this app
@Component("contacts")
public class Contacts {
    
    // method to save contact info into a text file
    public void saveContact(Contact ctc, Model model, ApplicationArguments appArgs,
            String defaultDataDir) {
        String dataFileName = ctc.getId();
        
        PrintWriter pw = null;
        try {
            FileWriter fw = new FileWriter(getDataDir(appArgs, defaultDataDir) 
                    + "/" + dataFileName); //dir from cmd line + ctc id
            pw = new PrintWriter(fw);
            pw.println(ctc.getName());
            pw.println(ctc.getEmail());
            pw.println(ctc.getPhoneNumber());
            pw.println(ctc.getDateOfBirth());
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // make sure the model object has the contact info
        model.addAttribute("contact", new Contact(ctc.getId()
            , ctc.getName(), ctc.getEmail(), ctc.getPhoneNumber()
            , ctc.getDateOfBirth()));
        // also model.addAttribute("contact", ctc);
    }

    // method to get contact with contact ID in url
    public void getContactById(Model model, String contactId
            , ApplicationArguments appArgs, String defaultDataDir) {
        Contact ctc = new Contact();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        try {
            Path filePath = new File(getDataDir(appArgs, defaultDataDir) + "/" + contactId)
                                .toPath();
            Charset charset = Charset.forName("UTF-8");
            
            // store each line of the file as a value in a list
            List<String> stringValues = Files.readAllLines(filePath, charset);
            ctc.setId(contactId);
            ctc.setName(stringValues.get(0));
            ctc.setEmail(stringValues.get(1));
            ctc.setPhoneNumber(stringValues.get(2));
            LocalDate dob = LocalDate.parse(stringValues.get(3), formatter);
            
            ctc.setDateOfBirth(dob);
            model.addAttribute("contact", ctc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // method to store the files in the Data dir as a set and add to model obj "contacts"
    public void getAllContacts(Model model, ApplicationArguments appArgs
            , String defaultDataDir){
        Set<String> dataFiles = listFiles(getDataDir(appArgs,defaultDataDir));
        model.addAttribute("contacts", dataFiles);
    }

    // method to create a set of file names in the directory
    public Set<String> listFiles(String dir){
        return Stream.of(new File(dir).listFiles()) //returns an array of files
            .filter(file -> !file.isDirectory()) //if the file is not a dir
            .map(File::getName)
            .collect(Collectors.toSet()); //put file names in a set
    }

    // method to create a fully qualified path to the data folder
    private String getDataDir(ApplicationArguments appArgs, String defaultDataDir) {
        String dataDirResult = "";
        List<String> optValues = null;
        String[] optValuesArr = null;

        // retrieve option names from appArgs into a set
        Set<String> opsNames = appArgs.getOptionNames();
        // convert set to array
        String[] opsNamesArr = opsNames.toArray(new String[opsNames.size()]);

        if (opsNamesArr.length > 0) {
            // retrieves the values associated with the first option name
            // which should be the values of --dataDir
            optValues = appArgs.getOptionValues(opsNamesArr[0]);
            optValuesArr = optValues.toArray(new String[optValues.size()]);
            // sets the dataDir as the first value in the array of values
            dataDirResult = optValuesArr[0];
        } else {
            dataDirResult = defaultDataDir;
        }

        return dataDirResult;
    }
}
