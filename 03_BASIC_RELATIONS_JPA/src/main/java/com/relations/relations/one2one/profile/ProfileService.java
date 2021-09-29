package com.relations.relations.one2one.profile;

import com.relations.relations.one2one.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService implements  ProfileServiceInterface{
    private  final ProfileRepository repository;
    @Override
    public Profile createProfile(Profile profile) {
        return this.repository.save(profile);
    }
    @Override
    public Profile updateProfile(Profile profile) {
        return this.repository.save(profile);
    }
}
