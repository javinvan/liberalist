package com.order.liberalist.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.order.liberalist.entity.domain.AllPair;
import com.order.liberalist.entity.domain.PairSleuth;
import com.order.liberalist.repository.AllPairRepository;
import com.order.liberalist.repository.PairSleuthRepository;
import com.order.liberalist.service.PairService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 功能概述：值班业务实现层
 * Date: 2021/11/22
 * Copyright: Copyright© 2021 alpha All Rights Reserved
 *
 * @author Javin
 * @version 1.0
 * @since JDK 1.8
 */
@Service
@Slf4j
public class PairServiceImpl implements PairService {

    public static final String USDT = "0x55d398326f99059ff775485246999027b3197955";

    @Resource
    AllPairRepository allPairRepository;

    @Resource
    PairSleuthRepository pairSleuthRepository;

    @Override
    public void doSleuth() {
        QueryWrapper<AllPair> wrapper = new QueryWrapper<>();
        wrapper.eq("is_sleuth", 0);
        wrapper.eq("path0", USDT).or().eq("path1", USDT);
        List<AllPair> allPairs = allPairRepository.list(wrapper);
        List<AllPair> executePairs1 = allPairs.subList(0, 1000);
        List<AllPair> executePairs2 = allPairs.subList(1000, 2000);
        List<AllPair> executePairs3 = allPairs.subList(2000, 3000);
        List<AllPair> executePairs4 = allPairs.subList(3000, 4000);
        List<AllPair> executePairs5 = allPairs.subList(4000, 5000);
        List<AllPair> executePairs6 = allPairs.subList(5000, 6000);
        List<AllPair> executePairs7 = allPairs.subList(6000, 7000);
        List<AllPair> executePairs8 = allPairs.subList(7000, 8000);
        List<AllPair> executePairs9 = allPairs.subList(8000, 9000);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(() -> {
            for (AllPair allPair : executePairs1) {
                doSleuthById(allPair.getId());
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs1);
        });
        executor.submit(() -> {
            for (AllPair allPair : executePairs2) {
                doSleuthById(allPair.getId());
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs2);
        });
        executor.submit(() -> {
            for (AllPair allPair : executePairs3) {
                doSleuthById(allPair.getId());
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs3);
        });
        executor.submit(() -> {
            for (AllPair allPair : executePairs4) {
                doSleuthById(allPair.getId());
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs4);
        });
        executor.submit(() -> {
            for (AllPair allPair : executePairs5) {
                doSleuthById(allPair.getId());
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs5);
        });
        executor.submit(() -> {
            for (AllPair allPair : executePairs6) {
                doSleuthById(allPair.getId());
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs6);
        });
        executor.submit(() -> {
            for (AllPair allPair : executePairs7) {
                doSleuthById(allPair.getId());
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs7);
        });
        executor.submit(() -> {
            for (AllPair allPair : executePairs8) {
                doSleuthById(allPair.getId());
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs8);
        });
        executor.submit(() -> {
            for (AllPair allPair : executePairs9) {
                doSleuthById(allPair.getId());
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs9);
        });
    }

    @Override
    public void doSleuthById(Integer id) {
        List<PairSleuth> pairSleuths = new ArrayList<>();
        AllPair allPair = allPairRepository.getById(id);
        String address = allPair.getPath1();
        QueryWrapper<AllPair> wrapperPair = new QueryWrapper<>();
        if (USDT.equals(allPair.getPath1())) {
            address = allPair.getPath0();
        }
        wrapperPair.eq("path1", address);
        List<AllPair> pairsLeft = allPairRepository.list(wrapperPair);
        for (AllPair pair : pairsLeft) {
            String iPath0 = pair.getPath0();
            searchSleuth(id, pairSleuths, address, wrapperPair, iPath0);
        }
        wrapperPair.clear();
        wrapperPair.eq("path0", address);
        List<AllPair> pairsRight = allPairRepository.list(wrapperPair);
        for (AllPair pair : pairsRight) {
            String iPath1 = pair.getPath1();
            searchSleuth(id, pairSleuths, address, wrapperPair, iPath1);
        }
        log.info("id:{}链路扫描完毕, 新增链路数:{}", id, pairSleuths.size());
        pairSleuthRepository.saveBatch(pairSleuths);
    }

    public void doSleuth2ById(Integer id) {
        List<PairSleuth> pairSleuths = new ArrayList<>();
        AllPair allPair = allPairRepository.getById(id);
        String address = allPair.getPath1();
        QueryWrapper<AllPair> wrapperPair = new QueryWrapper<>();
        if (USDT.equals(allPair.getPath1())) {
            address = allPair.getPath0();
        }
        wrapperPair.eq("path1", address);
        List<AllPair> pairsLeft = allPairRepository.list(wrapperPair);
        List<AllPair> allPairs = allPairRepository.list();
        for (AllPair pair : pairsLeft) {
            String iPath0 = pair.getPath0();
            searchSleuth(id, pairSleuths, address, wrapperPair, iPath0, allPairs);
        }
        wrapperPair.clear();
        wrapperPair.eq("path0", address);
        List<AllPair> pairsRight = allPairRepository.list(wrapperPair);
        for (AllPair pair : pairsRight) {
            String iPath1 = pair.getPath1();
            searchSleuth(id, pairSleuths, address, wrapperPair, iPath1, allPairs);
        }
        log.info("id:{}链路扫描完毕, 新增链路数:{}", id, pairSleuths.size());
        pairSleuthRepository.saveBatch(pairSleuths);
    }

    private void searchSleuth(Integer id, List<PairSleuth> pairSleuths, String address, QueryWrapper<AllPair> wrapperPair, String iPath1) {
        wrapperPair.clear();
        wrapperPair.eq("path0", iPath1).or().eq("path1", iPath1);
        List<AllPair> iPairsAll = allPairRepository.list(wrapperPair);
        for (AllPair pairAll : iPairsAll) {
            String allPath0 = pairAll.getPath0();
            String allPath1 = pairAll.getPath1();
            if (allPath0.equals(USDT) && allPath1.equals(iPath1)) {
                StringBuilder sb = new StringBuilder();
                sb.append(USDT).append(",").append(iPath1).append(",").append(address).append(",").append(USDT);
                addSleuth(id, pairSleuths, sb);
            } else if (allPath1.equals(USDT) && allPath0.equals(iPath1)) {
                StringBuilder sb = new StringBuilder();
                sb.append(USDT).append(",").append(iPath1).append(",").append(address).append(",").append(USDT);
                addSleuth(id, pairSleuths, sb);
            }
        }
    }

    private void searchSleuth(Integer id, List<PairSleuth> pairSleuths, String address, QueryWrapper<AllPair> wrapperPair, String iPath1, List<AllPair> allPairs) {
        wrapperPair.clear();
        wrapperPair.eq("path0", iPath1).or().eq("path1", iPath1);
        List<AllPair> collectList = allPairs.stream().filter(p -> p.getPath0().equals(iPath1) || p.getPath1().equals(iPath1)).collect(Collectors.toList());
//        List<AllPair> iPairsAll = allPairRepository.list(wrapperPair);
        for (AllPair pairAll : collectList) {
            String allPath0 = pairAll.getPath0();
            String allPath1 = pairAll.getPath1();
            if (allPath0.equals(USDT) && allPath1.equals(iPath1)) {
                StringBuilder sb = new StringBuilder();
                sb.append(USDT).append(",").append(iPath1).append(",").append(address).append(",").append(USDT);
                addSleuth(id, pairSleuths, sb);
            } else if (allPath1.equals(USDT) && allPath0.equals(iPath1)) {
                StringBuilder sb = new StringBuilder();
                sb.append(USDT).append(",").append(iPath1).append(",").append(address).append(",").append(USDT);
                addSleuth(id, pairSleuths, sb);
            }
        }
    }

    private void addSleuth(Integer id, List<PairSleuth> pairSleuths, StringBuilder sb) {
        PairSleuth pairSleuth = new PairSleuth();
        pairSleuth.setPairId(id);
        pairSleuth.setSleuth(sb.toString());
        pairSleuths.add(pairSleuth);
    }

}
