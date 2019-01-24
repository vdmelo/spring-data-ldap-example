package br.jus.jfce.projects.service;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import br.jus.jfce.projects.domain.LDAPUser;
import br.jus.jfce.projects.repository.JFCELdapRepository;

@Service
public class JFCELDAPService {

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private JFCELdapRepository ldapRepository;

    
    /**
     * Search using spring-data-ldap's LdapRepository
     * 
     * @param username
     * @return
     */
    public LDAPUser findByUsername(String username) {
    	return this.ldapRepository.findByUsername(username);
    }

    
    /**
     * Authenticate with ldapTemplate
     * 
     * @param username
     * @param password
     */
    public void login(String username, String password) {
    	ldapTemplate.authenticate(query().where("sAMAccountName").is(username), password);
    }

    /**
     * Search using LDAPTemplate
     *  
     * @return list of person names
     */
    public List<String> getAllPersonNames() {
        return ldapTemplate.search(
                query().where("objectclass").is("person").and("sAMAccountName").is("vinicius.dantas"),
                new AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attrs)
                            throws NamingException {
                    	
                    	printAttributes(attrs);
                    	
                        return (String) attrs.get("sn").get();
                    }
                });
    }

    
    /**
     * Auxiliary method to print attributes 
     * 
     * @param attrs
     */
    public void printAttributes( Attributes attrs ) {
    	System.out.println(">> printAttrs()");
        if (attrs == null)
        {
            System.out.println("No attributes");
        }
        else
        {
            // Print every single attribute
            try
            {
                for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();)
                {
                    Attribute attr = (Attribute) ae.next();
                    System.out.println("Attribute ID: " + attr.getID());
     
                    // Print values of current attribute
                    NamingEnumeration e = attr.getAll();
                    while(e.hasMore())
                    {
                        String value = e.next().toString();
                        System.out.println("Value: " + value);
                    }
                }
            } catch (NamingException e) { e.printStackTrace(); }
        }
        System.out.println("<< printAttrs()");
    }
}
