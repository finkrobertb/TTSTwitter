package com.tts.TechTalentTwitter.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tweet {
    
    // Tweets consist of...
    // Auto generated Long id
    // Column forces it to be renamed tweet_id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tweet_id")
    private Long id;

    // User associated with each tweet
    // ManyToOne mapping - many tweets to one user
    // Fetch type - when user is fetched, all tweets will be automatically populated once get user is called
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    // Storing one entity in an entity - mapping
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "tweet_tag", joinColumns = @JoinColumn(name = "tweet_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    // Allow for message that holds a tweet
    // Annotation for when we don't want message to be empty...
    @NotEmpty(message = "Tweet cannot be empty")
    // Annotation for when we don't want more than 280 characters
    @Length(max = 280, message = "Tweet cannot have more than 280 characters")
    private String message;

    // Creation map update to auto set up date created
    @CreationTimestamp
    private Date createdAt;

}
