package br.jus.jfce.projects;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;

import br.jus.jfce.projects.domain.LDAPUser;
import br.jus.jfce.projects.service.JFCELDAPService;

@SpringBootApplication
@EnableLdapRepositories
public class LdapExampleApplication {
    private static Logger log = LoggerFactory.getLogger(LdapExampleApplication.class);

    @Autowired
    private JFCELDAPService ldapService;


    @PostConstruct
    public void setup(){
        log.info("Spring LDAP + Spring Boot Configuration Example");

        // OK
        //List<String> names = ldapService.getAllPersonNames();
        //log.info("names: " + names);

        
        // OK
        //log.info("Authenticate ... ");
        //ldapService.login("vinicius.dantas", "xyz1234");
        
        
        // NOK
        LDAPUser user = ldapService.findByUsername("vinicius.dantas");
        
        if( user!=null ) {
	        System.out.println("**** ENCONTROU O USUÁRIO ****");
	        System.out.println("Login: " + user.getUsername());
	        System.out.println("Nome: " + user.getNomeCompleto());
	        System.out.println("E-mail: " + user.getEmail());
        } else {
        	System.out.println("**** NÃO ENCONTROU O USUÁRIO ****");        	
        }

        System.exit(-1);
    }
    
    
    public static void main(String[] args) {
		SpringApplication.run(LdapExampleApplication.class, args);
	}



}
