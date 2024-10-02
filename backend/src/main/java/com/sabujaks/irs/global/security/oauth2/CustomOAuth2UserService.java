package com.sabujaks.irs.global.security.oauth2;

import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.auth.repository.SeekerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final SeekerRepository seekerRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = null;
        if(provider.equals("kakao")){
            oAuth2UserInfo = new KakaoUserDetails(oAuth2User.getAttributes());
        }
        else if (provider.equals("naver")) {
            oAuth2UserInfo = new NaverUserDetails(oAuth2User.getAttributes());
        }
        else if (provider.equals("google")) {
            oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());
        }
        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();
        String role = "ROLE_SEEKER";
        Optional<Seeker> result = seekerRepository.findBySeekerEmail(email);
        Seeker seeker = null;
        if(result.isEmpty()){
            seeker = Seeker.builder()
                    .role(role)
                    .nickname(name)
                    .email(email)
                    .emailAuth(true)
                    .socialType(provider)
                    .build();
            seekerRepository.save(seeker);
        } else {
            seeker = result.get();
        }
        return new CustomOAuth2UserDetails(seeker, oAuth2User.getAttributes(), (Set<SimpleGrantedAuthority>) oAuth2User.getAuthorities());
    }
}
