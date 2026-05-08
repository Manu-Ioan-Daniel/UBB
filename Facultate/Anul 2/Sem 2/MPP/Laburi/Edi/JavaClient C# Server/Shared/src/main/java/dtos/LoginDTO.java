package dtos;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;

public class LoginDTO implements Serializable {

    @SerializedName("Username")
    private final String username;
    @SerializedName("Password")
    private final String password;

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
