package yourestack.client.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yourestack.client.Model.Response;
import yourestack.client.Model.User;
import yourestack.client.Service.UserService;


@Log4j2
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

    @PostMapping("/registerClient")
    @Operation(summary = "Creating new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A new client has been registered",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content) })
    public String registerNewClient(@ModelAttribute("user") @Valid final User user, @NotNull Model model,
                                    final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

        model.addAttribute("user", user);
        if (bindingResult.hasErrors()) {
            log.error("New client cannot be registered: error in {}", bindingResult);
        }

        userService.registerNewUser(user);
        return "profile";
    }

//    @PostMapping("/delete/{clientId}")
////    @Operation(summary = "Deleting client's account")
////    @ApiResponses(value = {
////            @ApiResponse(responseCode = "201", description = "The account has been deleted",
////                    content = { @Content(mediaType = "application/json",
////                            schema = @Schema(implementation = User.class)) }),
////            @ApiResponse(responseCode = "400", description = "Invalid data supplied",
////                    content = @Content),
////            @ApiResponse(responseCode = "404", description = "Resource not found",
////                    content = @Content) })
////    public String delete(@PathVariable final Long clientId,
////                         final RedirectAttributes redirectAttributes) {
////
////            userService.delete(clientId);
////
////        }
////        return "redirect:/clients";
////    }

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
