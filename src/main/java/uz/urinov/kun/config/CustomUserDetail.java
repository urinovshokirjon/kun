package uz.urinov.kun.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.urinov.kun.entity.ProfileEntity;
import uz.urinov.kun.enums.ProfileStatus;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CustomUserDetail implements UserDetails {
//    private Integer id;
//    private String name;
//    private String surname;
//    private String email;
//    private String password;
//    private Boolean visible;
//    private ProfileStatus status;
    private ProfileEntity profile;

    public CustomUserDetail(ProfileEntity profile) {
//        this.id = profile.getId();
//        this.name = profile.getName();
//        this.surname = profile.getSurname();
//        this.email = profile.getEmail();
//        this.password = profile.getPassword();
//        this.visible = profile.getVisible();
//        this.status = profile.getStatus();
        this.profile = profile;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> roleList = new LinkedList<>();
        roleList.add(new SimpleGrantedAuthority(profile.getRole().name()));
        return roleList;
    }

    @Override
    public String getPassword() {
        return profile.getPassword();
    }

    @Override
    public String getUsername() {
        return profile.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return profile.getStatus().equals(ProfileStatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return profile.getVisible();
    }

    public ProfileEntity getProfile() {
        return profile;
    }
}
