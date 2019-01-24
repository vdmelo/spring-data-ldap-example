package br.jus.jfce.projects.repository;

import java.util.List;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import br.jus.jfce.projects.domain.LDAPUser;

@Repository
public interface JFCELdapRepository extends LdapRepository<LDAPUser> {

	LDAPUser findByUsername(String username);
	//LDAPUser findByUsernameAndPassword(String username, String password);
    List<LDAPUser> findByUsernameLikeIgnoreCase(String username);

}
