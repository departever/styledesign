package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.ContactPerson;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactPersonMapper extends BaseMapper<ContactPerson> {
}
