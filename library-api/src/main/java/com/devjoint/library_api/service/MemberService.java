package com.devjoint.library_api.service;

import com.devjoint.library_api.dto.MemberDto;
import com.devjoint.library_api.entity.Member;
import com.devjoint.library_api.exception.ResourceNotFoundException;
import com.devjoint.library_api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // CREATE
    public MemberDto create(MemberDto dto) {
        Member member = new Member();
        member.setFullName(dto.getFullName());
        member.setEmail(dto.getEmail());
        member.setRegistrationDate(dto.getRegistrationDate());

        Member saved = memberRepository.save(member);
        return toDto(saved);
    }


    public List<MemberDto> getAll() {
        return memberRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    public MemberDto getById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
        return toDto(member);
    }

    // UPDATE
    public MemberDto update(Long id, MemberDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));

        member.setFullName(dto.getFullName());
        member.setEmail(dto.getEmail());
        member.setRegistrationDate(dto.getRegistrationDate());

        Member updated = memberRepository.save(member);
        return toDto(updated);
    }

    // DELETE
    public void delete(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Member not found with id: " + id);
        }
        memberRepository.deleteById(id);
    }


    private MemberDto toDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getFullName(),
                member.getEmail(),
                member.getRegistrationDate()
        );
    }
}