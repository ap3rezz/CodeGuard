package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.RegisterService;
import es.tfg.codeguard.util.PasswordNotValidException;
import es.tfg.codeguard.util.UsernameAlreadyExistException;
import es.tfg.codeguard.util.UsernameNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class RegisterServiceImp implements RegisterService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserPassRepository userPassRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserPassDTO registerUser(AuthDTO authDTO) {

        if (userPassRepository.findById(authDTO.username()).isPresent()) {
            throw new UsernameAlreadyExistException("Username is already in use [" +authDTO.username() +"]");
        }

        UserPass userPass = new UserPass();
        userPass.setAdmin(false);

        String decodedPassword;

        //Decodificar contraeña en Base64
        try{
            decodedPassword = new String(Base64.getDecoder().decode(authDTO.password().getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw new PasswordNotValidException("Problem decoding password");
        }

        if(!decodedPassword.matches("(?=.*\\d).{8,}")) throw new PasswordNotValidException("Password not valid");

        try {
            userPass.setUsername(authDTO.username());
            userPass.setHashedPass(passwordEncoder.encode(decodedPassword));
        } catch (UsernameNotValidException e) {
            throw new UsernameNotValidException("Username not valid [" +authDTO.username() +"]");
        } catch (PasswordNotValidException i){
            throw new PasswordNotValidException("Password not valid");
        }

        userPassRepository.save(userPass);

        userRepository.save(new User(authDTO.username()));

        return new UserPassDTO(userPass);

    }

}
