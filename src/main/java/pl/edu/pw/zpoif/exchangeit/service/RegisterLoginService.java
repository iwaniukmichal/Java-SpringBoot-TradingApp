package pl.edu.pw.zpoif.exchangeit.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.zpoif.exchangeit.exceptions.register.EmailTakenException;
import pl.edu.pw.zpoif.exchangeit.model.user.User;

@Service
public class RegisterLoginService {

    private final DataBaseService dataBaseService;

    @Autowired
    public RegisterLoginService(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    public void processRegister(@NotNull User user) throws EmailTakenException {
        User user1 = dataBaseService.getUserRepository().findByEmail(user.getEmail());
        if (user1 != null) {
            throw new EmailTakenException("Email already taken");
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            dataBaseService.getUserRepository().save(user);
            dataBaseService.createAndSaveUserTrading(user.getUserId(), 100000);
        }
    }

    public Long getCurrentlyLoggedUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = dataBaseService.getUserRepository().findByEmail(email);
        return user.getUserId();
    }
}
