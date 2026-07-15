package com.devjoint.library_api.repository;

import com.devjoint.library_api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

}
