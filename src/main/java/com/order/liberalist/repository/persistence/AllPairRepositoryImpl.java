package com.order.liberalist.repository.persistence;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.order.liberalist.entity.domain.AllPair;
import com.order.liberalist.repository.AllPairRepository;
import com.order.liberalist.repository.mapper.AllPairMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AllPairRepositoryImpl extends ServiceImpl<AllPairMapper, AllPair> implements AllPairRepository {

}
