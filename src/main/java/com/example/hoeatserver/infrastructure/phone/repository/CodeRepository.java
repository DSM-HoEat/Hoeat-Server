package com.example.hoeatserver.infrastructure.phone.repository;

import com.example.hoeatserver.infrastructure.phone.entity.PhoneCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRepository extends CrudRepository<PhoneCode, String> {

    Optional<PhoneCode> findPhoneCodeByPhoneNumber(String phoneNumber);
}
