package com.tts.TechTalentTwitter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.TechTalentTwitter.model.Tweet;
import com.tts.TechTalentTwitter.model.User;

//Stores tweets
@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {
    // List of tweets by day, becomes sql query...
    List<Tweet> findAllByOrderByCreatedAtDesc();
    // List of tweets created by user, becomes sql query...
    List<Tweet> findAllByUserOrderByCreatedAtDesc(User user);
    // List of users, finds all tweets, ordered by creation date in descending order...
    List<Tweet> findAllByUserInOrderByCreatedAtDesc(List<User> users);
    // List of users, finds all phrases by order created...
    List<Tweet> findByTags_PhraseOrderByCreatedAtDesc(String phrase);
}