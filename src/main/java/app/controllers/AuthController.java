package app.controllers;

import io.javalin.http.Context;
import app.utils.JwtUtil;

import java.util.Map;

public class AuthController {

    //adding user/admin functionality for quick implementation and testing
    // Hardcoded users: username -> password
    private final Map<String, String> users = Map.of(
            "admin", "admin123",
            "user", "user123"
    );

    // Hardcoded roles: username -> role
    private final Map<String, String> roles = Map.of(
            "admin", "admin",
            "user", "user"
    );

    public void login(Context ctx) {
        var body = ctx.bodyAsClass(Map.class);
        String username = (String) body.get("username");
        String password = (String) body.get("password");

        if (!users.containsKey(username) || !users.get(username).equals(password)) {
            ctx.status(401).result("Invalid username or password");
            return;
        }

        String role = roles.get(username);
        String token = JwtUtil.generateToken(username, role);

        ctx.json(Map.of("token", token));
    }
}
