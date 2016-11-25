package co.simplon.reserve.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class User {

    public enum Role {
	USER, ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private Long facebookId;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations = new HashSet<Reservation>();

    public User() {
    }

    public User(String name, String surname, String email, String password, Role role, boolean enabled) {
	this.name = name;
	this.surname = surname;
	this.email = email;
	this.password = password;
	this.role = role;
	this.enabled = enabled;
    }

    public Integer getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public String getSurname() {
	return surname;
    }

    public String getEmail() {
	return email;
    }

    public String getPassword() {
	return password;
    }

    public boolean isEnabled() {
	return enabled;
    }

    public Role getRole() {
	return role;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        //make everyone ROLE_USER
    	final User user = this;
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        GrantedAuthority grantedAuthority = new GrantedAuthority() {
            //anonymous inner type
            public String getAuthority() {
                return user.getRole().toString();
            }
        }; 
        grantedAuthorities.add(grantedAuthority);
        return grantedAuthorities;
    }
    
    public void updateUserStatus(boolean enabled) {
	this.enabled = enabled;
    }

    public void setPassword(String password) {
	this.password = password;
    }
}
