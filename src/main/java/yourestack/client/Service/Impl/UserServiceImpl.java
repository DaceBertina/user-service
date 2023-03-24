package yourestack.client.Service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import yourestack.client.Model.Epack;
import yourestack.client.Model.Response;
import yourestack.client.Model.User;
import yourestack.client.Model.UserRepository;
import yourestack.client.Service.UserService;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    @Override
    public User findById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            log.error("Exception {} is thrown. User with ID " + userId + " does not exist.", HttpStatus.CONFLICT);
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "User with ID " + userId + " does not exist.");
        }
    }

    @Override
    public User registerNewUser(User user) {

        if (userExists(user.getEmail())) {
            log.error("Exception {} is thrown. User with email entered already exists.", HttpStatus.CONFLICT);
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "User with email " + user.getEmail() + " already has account.");
        }

        userRepository.save(user);
        log.info("New user registered: {}", user);

        return user;

    }

    public boolean userExists(String username) {
        return userRepository.findByEmail(username).isPresent();
    }
    @Override
    public Response getUser(Long userId) throws ChangeSetPersister.NotFoundException {

        Response response = new Response();

        final User user = userRepository.findById(userId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "ClientApplication");
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        Epack epack = restTemplate.exchange(
                "http://localhost:8084/api/v1/epacks/"+user.getEpackId(), HttpMethod.GET, entity, Epack.class).getBody();


            response.setUser(user);
            response.setEpack(epack);
            return response;
    }
}
