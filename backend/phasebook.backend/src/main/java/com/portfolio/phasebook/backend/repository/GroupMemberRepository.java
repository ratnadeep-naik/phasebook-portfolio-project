package com.portfolio.phasebook.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.phasebook.backend.entity.GroupMember;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {


}
