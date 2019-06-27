package com.wcs.doctorwho;

import org.hibernate.validator.cfg.context.ReturnValueConstraintMappingContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
@SpringBootApplication
public class DoctorwhoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoctorwhoApplication.class, args);
	}
    @RequestMapping("/doctor/{numero}")
    @ResponseBody
    public Doctor myDoctor(@PathVariable int numero,@RequestParam(value = "details", required=false) boolean details) {
       
        String[] id = {"9","10","11","12","13"};
        String[] acteurs = {"Christopher Eccleston", "David Tennant","Matt Smith","Peter Capaldi","Jodie Whittaker"};
        String[] episode = {"13","47","44","40","11"};
        String[] age = {"41","34","27","55","35"};
       
        if(numero>8 && numero<14)
        {
            if(details)
            {
                return new ExtendedDoctor(acteurs[numero-9],Integer.parseInt(id[numero-9]),Integer.parseInt(episode[numero-9]), Integer.parseInt(age[numero-9]));
            }
            return new Doctor(acteurs[numero-9], Integer.parseInt(id[numero-9]));   
        }
        else if(numero>=1 && numero<=8)
        {
            throw new ResponseStatusException(HttpStatus.SEE_OTHER, "Question Not Found");
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Impossible de récupérer l'incarnation "+numero);
        }
}

    class Doctor {

        private String name;
        private int number;

        public Doctor(String name, int number) {
            this.name = name;
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public int getNumber() {
            return number;
        }
    }
    class ExtendedDoctor extends Doctor {

        private int numberOfEpisodes;
        private int ageAtStart;
    
        public ExtendedDoctor(String name, int number,int numberOfEpisodes, int ageAtStart) {
            super(name, number);
            this.numberOfEpisodes = numberOfEpisodes;
            this.ageAtStart = ageAtStart;
        }
    
        

        public int getNumberOfEpisodes() {
            return numberOfEpisodes;
        }

        public void setNumberOfEpisodes(int numberOfEpisodes) {
            this.numberOfEpisodes = numberOfEpisodes;
        }

        public int getAgeAtStart() {
            return ageAtStart;
        }

        public void setAgeAtStart(int ageAtStart) {
            this.ageAtStart = ageAtStart;
        }
    }
}

