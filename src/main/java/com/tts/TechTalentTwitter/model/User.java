package com.tts.TechTalentTwitter.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    
    // Puts a constraint to make sure they are valid; checks before written to database
    @Email(message = "Please provide a valid email")
    @NotEmpty(message = "Please provide an email")
    private String email;
        
    @Length(min = 3, message = "Your username must have at least 3 characters")
    @Length(max = 15, message = "Your username cannot have more than 15 characters")
    @Pattern(regexp="[^\\s]+", message="Your username cannot contain spaces")
    private String username;
        
    @Length(min = 5, message = "Your password must have at least 5 characters")
    private String password;
        
    @NotEmpty(message = "Please provide your first name")
    private String firstName;
        
    @NotEmpty(message = "Please provide your last name")
    private String lastName;
    private int active;

    @CreationTimestamp
    private Date createdAt;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_follower", joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private List<User> followers;
    
    // Looks at table in reverse direction
    @ManyToMany(mappedBy="followers")
    private List<User> following;
    
    // Can't just store a set in a  JPA entity-- we are actually defining a relationship
    // between User and Role.  This relationship will be many-to-many in database terms
    // Makes set of IDs to hold the rolls
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name= "user_role", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles;
    
    

}
