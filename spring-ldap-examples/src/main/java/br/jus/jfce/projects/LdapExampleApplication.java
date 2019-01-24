package br.jus.jfce.projects;

import java.util.List;

import javax.annotation.PostConstruct;

import org.jfree.util.Log;
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
        log.info("Tenta autenticar ... ");
        try {
        	LDAPUser user = ldapService.login("username", "123456");
        	log.info("**** USUÁRIO AUTENTICADO COM SUCESSO ****");
	        log.info("Login: " + user.getUsername());
	        log.info("Nome: " + user.getNomeCompleto());
	        log.info("E-mail: " + user.getEmail());
        } catch(Exception ex) {
        	log.warn("Login ou Senha inválidos");
        	Log.error("Erro ao tentar autenticar usuário",ex);
        }
        
        
        // NOK
        LDAPUser user = ldapService.findByUsername("username");
        
        if( user!=null ) {
	        log.info("**** ENCONTROU O USUÁRIO ****");
	        log.info("Login: " + user.getUsername());
	        log.info("Nome: " + user.getNomeCompleto());
	        log.info("E-mail: " + user.getEmail());
        } else {
        	log.info("**** NÃO ENCONTROU O USUÁRIO ****");        	
        }

        System.exit(-1);
    }
    
    
    public static void main(String[] args) {
		SpringApplication.run(LdapExampleApplication.class, args);
	}



}
