package com.order.liberalist.repository.persistence;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.order.liberalist.entity.domain.PairSleuth;
import com.order.liberalist.repository.PairSleuthRepository;
import com.order.liberalist.repository.mapper.PairSleuthMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PairSleuthRepositoryImpl extends ServiceImpl<PairSleuthMapper, PairSleuth> implements PairSleuthRepository {

}
