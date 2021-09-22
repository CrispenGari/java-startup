package com.rest.rest.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public List<Users> getUsers() {
        return userRepository.findAll();
    }
    public Users createUser(Users user){
        Optional<Users> userFound = this.userRepository.findByEmail(user.getEmail());
        if(userFound.isPresent()) {
            throw new IllegalStateException("the email is already taken.");
        }
        return this.userRepository.save(user);
    }
    public Boolean deleteUser(Long id){
        Optional<Users> usersOptional = this.userRepository.findUserById(id);
        if(!usersOptional.isPresent()){
           throw new IllegalStateException("the user with that id does not exists");
        }
        this.userRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Users updateUser(Long id, String email, String name){
        Users user = this.userRepository.findUserById(id).orElseThrow(()-> new IllegalStateException(
                "the user with that id does not exist"));
        if(!user.getName().equals(name) && name != null){
            user.setName(name);
        }
        if(!user.getName().equals(email) && email != null){
            Optional<Users> emailFound = this.userRepository.findByEmail(email);
            if(emailFound.isPresent()){
                throw new IllegalStateException("the email is already taken");
            }else {
                user.setEmail(email);
            }
        }
        return user;
    }
}
