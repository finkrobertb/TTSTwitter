package com.tts.TechTalentTwitter.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.TechTalentTwitter.model.Tweet;
import com.tts.TechTalentTwitter.model.TweetDisplay;
import com.tts.TechTalentTwitter.model.User;
import com.tts.TechTalentTwitter.service.TweetService;
import com.tts.TechTalentTwitter.service.UserService;

@Controller
public class TweetController
{
    @Autowired
    private UserService userService;

    @Autowired
    private TweetService tweetService;
    
    // Mapping for when we visit main page or /tweets
    @GetMapping(value = { "/tweets", "/" })
    public String getFeed(@RequestParam(value = "filter", required = false) String filter, Model model)
    {
        User loggedInUser = userService.getLoggedInUser();
        List<TweetDisplay> tweets = new ArrayList<>();
        if (filter == null) 
        {
            filter = "all";
        }
        if (filter.equalsIgnoreCase("following")) 
        {
            List<User> following = loggedInUser.getFollowing();
            tweets = tweetService.findAllByUsers(following);
            model.addAttribute("filter", "following");
        } 
        else 
        {
            tweets = tweetService.findAll();
            model.addAttribute("filter", "all");
        }
        model.addAttribute("tweetList", tweets);
        return "feed";
//        User user = userService.getLoggedInUser();
//        List<User> following = user.getFollowing();
//        List<TweetDisplay> tweets = tweetService.findAllByUsers(following);
//        model.addAttribute("tweetList", tweets);
//        return "feed";
    }

    // Returns a new tweet when visiting /tweets/new
    @GetMapping(value = "/tweets/new")
    public String getTweetForm(Model model)
    {
        model.addAttribute("tweet", new Tweet());
        return "newTweet";
    }

    // Pass in tweet from 
    @PostMapping(value = "/tweets")
    public String submitTweetForm(@Valid Tweet tweet, BindingResult bindingResult, Model model)
    {
        User user = userService.getLoggedInUser();
        if(!bindingResult.hasErrors())
        {
            tweet.setUser(user);
            tweetService.save(tweet);
            model.addAttribute("successMessage", "Tweet successfully created!");
            model.addAttribute("tweet", new Tweet());
        }
        return "newTweet";
    }
    
    // Creates links out of hashtags
    // Creates page /tweets/hashtagname
    @GetMapping(value = "/tweets/{tag}")
    public String getTweetsByTag(@PathVariable(value="tag") String tag, Model model) {
        List<TweetDisplay> tweets = tweetService.findAllWithTag(tag);
        model.addAttribute("tweetList", tweets);
        model.addAttribute("tag", tag);
        return "taggedTweets";
    }
}