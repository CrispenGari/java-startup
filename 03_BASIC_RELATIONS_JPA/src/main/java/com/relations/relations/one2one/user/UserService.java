package com.relations.relations.one2one.user;

import com.relations.relations.one2one.profile.Profile;
import com.relations.relations.one2one.profile.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserServiceInterface{
    private final UserRepository repository;
    private final ProfileRepository profileRepository;
    @Override
    public User createUser(User user) {
        Profile p = this.profileRepository.save(user.getProfile());
        user.setProfile(p);
        System.out.println("my user");
        System.out.println(user);
        System.out.println(p);
        return this.repository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return this.repository.save(user);
    }
}
