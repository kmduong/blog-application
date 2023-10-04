package com.example.blogapplication.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.blogapplication.models.Account;
import com.example.blogapplication.models.Authority;
import com.example.blogapplication.models.Post;
import com.example.blogapplication.repositories.AuthorityRepository;
import com.example.blogapplication.services.AccountService;
import com.example.blogapplication.services.PostService;

@Component
public class SeedData implements CommandLineRunner {
    
    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public  void run(String... arg) throws  Exception {
        List<Post> posts = postService.getAll();

        if (posts.size() == 0){

            Authority user = new Authority();
            user.setName("ROLE_USER");
            authorityRepository.save(user);

            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepository.save(admin);


            Account account1 = new Account();
            Account account2 = new Account();

            account1.setFirstName("user");
            account1.setLastName("user");
            account1.setEmail("user.user@domain.com");
            account1.setPassword("password");
            Set<Authority> authorities1 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities1::add);
            account1.setAuthorities(authorities1);
            
            account2.setFirstName("admin_bro");
            account2.setLastName("admin_last");
            account2.setEmail("admin.admin@domain.com");
            account2.setPassword("password");

            Set<Authority> authorities2 = new HashSet<>();
            authorityRepository.findById("ROLE_ADMIN").ifPresent(authorities2::add);
            authorityRepository.findById("ROLE_USER").ifPresent(authorities2::add);
            account2.setAuthorities(authorities2);

            accountService.save(account1);
            accountService.save(account2);

            Post post1 = new Post();
            post1.setTitle("Advice of the day");
            post1.setBody("Prioritize self-care as a non-negotiable daily ritual; it's the foundation for a balanced and fulfilling life.");
            post1.setAccount(account1);

            Post post2 = new Post();
            post2.setTitle("Rant");
            post2.setBody("Why do we keep hitting the snooze button on our dreams? Life's too short for that! Let's break free from the mundane and chase our passions with relentless determination. Embrace the chaos, make mistakes, and build the extraordinary life you deserve.");
            post2.setAccount(account2);

            postService.save(post1);
            postService.save(post2);
        }
    }
}
