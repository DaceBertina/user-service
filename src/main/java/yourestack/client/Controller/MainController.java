package yourestack.client.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import yourestack.client.Model.Response;
import yourestack.client.Model.User;
import yourestack.client.Service.UserService;

import java.util.List;


//@Controller
@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class MainController {

    private final RestTemplate restTemplate;

    private final UserService userService;

//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    @ResponseBody
//    public String updateUser(@PathVariable("id") String id, @RequestBody User user) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
//        HttpEntity<User> entity = new HttpEntity<>(user, headers);
//
//        return restTemplate.exchange(
//                "http://localhost:8083/users/"+id, HttpMethod.GET, entity, String.class).getBody();
//    }

    @GetMapping("/usersEpack/{id}")
    public ResponseEntity<Response> getUserAndEpack(@PathVariable("id") Long userId) throws ChangeSetPersister.NotFoundException {
        Response response = userService.getUser(userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("profile")
    @ResponseBody
    public String showProfile() {
        return "profile";
    }

}
