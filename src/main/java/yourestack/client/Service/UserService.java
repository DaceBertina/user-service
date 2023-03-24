package yourestack.client.Service;

import org.springframework.data.crossstore.ChangeSetPersister;
import yourestack.client.Model.Response;
import yourestack.client.Model.User;

public interface UserService {

    User registerNewUser(User user);

    User findById(Long userId);

    Response getUser(Long userId) throws ChangeSetPersister.NotFoundException;


}
