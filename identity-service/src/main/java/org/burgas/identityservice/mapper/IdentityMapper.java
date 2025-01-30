package org.burgas.identityservice.mapper;

import org.burgas.identityservice.dto.AuthorityResponse;
import org.burgas.identityservice.dto.IdentityRequest;
import org.burgas.identityservice.dto.IdentityResponse;
import org.burgas.identityservice.entity.Identity;
import org.burgas.identityservice.repository.AuthorityRepository;
import org.burgas.identityservice.repository.IdentityRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class IdentityMapper {

    private final IdentityRepository identityRepository;
    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;
    private final PasswordEncoder passwordEncoder;

    public IdentityMapper(
            IdentityRepository identityRepository, AuthorityRepository authorityRepository,
            AuthorityMapper authorityMapper, PasswordEncoder passwordEncoder
    ) {
        this.identityRepository = identityRepository;
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
        this.passwordEncoder = passwordEncoder;
    }

    private <T> T getData(T requestData, T dbData) {
        return requestData == null ? dbData : requestData;
    }

    public Identity toIdentity(IdentityRequest identityRequest) {
        Long identityId = getData(identityRequest.id(), 0L);
        return identityRepository.findById(identityId)
                .map(
                        identity -> Identity.builder()
                                .id(identity.getId())
                                .email(getData(identityRequest.email(), identity.getEmail()))
                                .phone(getData(identityRequest.phone(), identity.getPhone()))
                                .password(getData(passwordEncoder.encode(identityRequest.password()), identity.getPassword()))
                                .authorityId(getData(identityRequest.authorityId(), identity.getAuthorityId()))
                                .created(getData(identityRequest.created(), identity.getCreated()))
                                .build()
                )
                .orElseGet(
                        () -> Identity.builder()
                                .id(identityRequest.id())
                                .email(identityRequest.email())
                                .phone(identityRequest.phone())
                                .password(passwordEncoder.encode(identityRequest.password()))
                                .authorityId(2L)
                                .created(LocalDateTime.now())
                                .build()
                );
    }

    public IdentityResponse toIdentityResponse(Identity identity) {
        return IdentityResponse.builder()
                .id(identity.getId())
                .email(identity.getEmail())
                .phone(identity.getPhone())
                .password(identity.getPassword())
                .authority(
                        authorityRepository.findById(identity.getAuthorityId())
                                .map(authorityMapper::toAuthorityResponse)
                                .orElseGet(AuthorityResponse::new)
                )
                .created(
                        identity.getCreated().format(
                                DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm")
                        )
                )
                .build();
    }
}
