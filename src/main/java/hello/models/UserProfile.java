package hello.models;

import lombok.Data;

/**
 * Created by yangyu on 2/10/17.
 */
@Data
public class UserProfile {
    private int pid;

    private String profileName;

    public UserProfile(int id, String name) {
        this.pid = id;
        this.profileName = name;
    }
}
