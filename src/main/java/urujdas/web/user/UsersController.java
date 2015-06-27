package urujdas.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import urujdas.model.users.User;
import urujdas.service.UserService;
import urujdas.service.exception.UserAlreadyExistsException;
import urujdas.web.common.WebCommons;
import urujdas.web.common.exception.ValidationException;
import urujdas.web.common.model.error.ErrorResponse;
import urujdas.web.common.model.error.ErrorType;
import urujdas.web.user.model.RegisterUserRequest;
import urujdas.web.user.model.UpdateUserRequest;

import javax.validation.Valid;

@RestController
@RequestMapping(WebCommons.VERSION_PREFIX + "/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("userId") Long userId) {
        return userService.getById(userId);
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(@Valid @RequestBody RegisterUserRequest registerUserRequest,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        userService.register(registerUserRequest.toUser());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> update(@Valid @RequestBody UpdateUserRequest updateUserRequest,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        userService.update(updateUserRequest.toUser());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandler(UserAlreadyExistsException e) {
        return new ErrorResponse(ErrorType.USER_ALREADY_EXISTS, "User with the same username already exists");
    }
}
