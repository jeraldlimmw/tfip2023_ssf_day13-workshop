package sg.edu.nus.iss.app.day13workshop;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static sg.edu.nus.iss.app.day13workshop.util.IOUtil.*;

@SpringBootApplication
public class Day13WorkshopApplication {
	// class for logging information during the execution of the program
	private static final Logger logger = LoggerFactory.getLogger(Day13WorkshopApplication.class);

	public static void main(String[] args) {
		// SpringApplication.run(Day13WorkshopApplication.class, args);
		SpringApplication app = new SpringApplication(Day13WorkshopApplication.class);
		
		// mvn clean spring-boot:run -Dspring-boot.run.arguments=--dataDir=<YOUR PATH>
        // mvn clean spring-boot:run -Dspring-boot.run.arguments=--dataDir=./data

		// to parse cmd-line arguments to the program
		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
		// finds dataDir in cmd line arguments using key "dataDir"
		// returns a list of String values
		List<String> opsVal = appArgs.getOptionValues("dataDir");
		System.out.println("before dir");
		
		// if data directory given in args, use helper createDir to check & create dir
		if(null != opsVal){
			// since we did import static, we can assume createDir method is in here
			logger.info("" + (String)opsVal.get(0)); //get first value
			System.out.println("inside dir");
			createDir((String)opsVal.get(0));
		} else { //no dir path provided, exit
			System.out.println("exit");
			System.exit(1);
		}

		app.run(args);
	}

}
