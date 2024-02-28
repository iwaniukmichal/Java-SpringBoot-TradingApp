package pl.edu.pw.zpoif.exchangeit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.pw.zpoif.exchangeit.exceptions.register.EmailTakenException;
import pl.edu.pw.zpoif.exchangeit.model.user.User;
import pl.edu.pw.zpoif.exchangeit.service.RegisterLoginService;

@Controller
@Tag(name = "Login Controller", description = "Endpoints related to user login and registration")
public class LoginController extends pl.edu.pw.zpoif.exchangeit.controller.Controller {

    private final RegisterLoginService registerLoginService;

    @Autowired
    public LoginController(RegisterLoginService registerLoginService) {
        this.registerLoginService = registerLoginService;
    }

    @Operation(summary = "Home page for login and register.", description = "This page contains login and register options.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema =
            @Schema(type = "html"), examples = {
                    @ExampleObject(summary = "Simple response", value = "html")}))})
    @GetMapping(value = "", produces = "text/html")
    public String viewHomePage() {
        return "index";
    }

    @Operation(summary = "Registration form.", description = "This page contains registration form.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema =
            @Schema(type = "html"), examples = {
                    @ExampleObject(summary = "Simple response", value = "html")}))})
    @GetMapping(value = "/register", produces = "text/html")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @Operation(summary = "Registration confirmation.", description = "This page contains confirmation of successful registration and gives link to login page.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema =
            @Schema(type = "html"), examples = {
                    @ExampleObject(summary = "Simple response", value = "html")}))})
    @PostMapping(value = "/process_register", produces = "text/html")
    public String processRegister(User user) throws EmailTakenException {
        registerLoginService.processRegister(user);
        return "register_success";
    }

    @Operation(summary = "App page.", description = "This page contains our app page.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema =
            @Schema(type = "html"), examples = {
                    @ExampleObject(summary = "Simple response", value = "html")}))})
    @GetMapping(value = "/app", produces = "text/html")
    public String showAppPage() {
        return "app";
    }

}
