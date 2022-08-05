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
        List<AllPair> allList = allPairRepository.list();
        List<AllPair> executePairs1 = allPairs.subList(0, 3000);
        List<AllPair> executePairs2 = allPairs.subList(3000, 6000);
        List<AllPair> executePairs3 = allPairs.subList(6000, 9000);
        List<AllPair> executePairs4 = allPairs.subList(9000, 12000);
        List<AllPair> executePairs5 = allPairs.subList(12000, 15000);
        List<AllPair> executePairs6 = allPairs.subList(15000, 18000);
        List<AllPair> executePairs7 = allPairs.subList(18000, 21000);
        List<AllPair> executePairs8 = allPairs.subList(21000, allPairs.size());
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(() -> {
            for (AllPair allPair : executePairs1) {
                doSleuth2ById(allPair.getId(), allList);
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs1);
            log.info("executePairs1链路执行完毕！");
        });
        executor.submit(() -> {
            for (AllPair allPair : executePairs2) {
                doSleuth2ById(allPair.getId(), allList);
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs2);
            log.info("executePairs2链路执行完毕！");
        });
        executor.submit(() -> {
            for (AllPair allPair : executePairs3) {
                doSleuth2ById(allPair.getId(), allList);
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs3);
            log.info("executePairs3链路执行完毕！");
        });
        executor.submit(() -> {
            for (AllPair allPair : executePairs4) {
                doSleuth2ById(allPair.getId(), allList);
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs4);
            log.info("executePairs4链路执行完毕！");
        });
        executor.submit(() -> {
            for (AllPair allPair : executePairs5) {
                doSleuth2ById(allPair.getId(), allList);
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs5);
            log.info("executePairs5链路执行完毕！");
        });
        executor.submit(() -> {
            for (AllPair allPair : executePairs6) {
                doSleuth2ById(allPair.getId(), allList);
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs6);
            log.info("executePairs6链路执行完毕！");
        });
        executor.submit(() -> {
            for (AllPair allPair : executePairs7) {
                doSleuth2ById(allPair.getId(), allList);
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs7);
            log.info("executePairs7链路执行完毕！");
        });
        executor.submit(() -> {
            for (AllPair allPair : executePairs8) {
                doSleuth2ById(allPair.getId(), allList);
                allPair.setIsSleuth(1);
            }
            allPairRepository.updateBatchById(executePairs8);
            log.info("executePairs7链路执行完毕！");
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

    public void doSleuth2ById(Integer id, List<AllPair> allList) {
        List<PairSleuth> pairSleuths = new ArrayList<>();
//        AllPair allPair = allPairRepository.getById(id);
        AllPair allPair = allList.stream().filter(p -> p.getId().equals(id)).collect(Collectors.toList()).get(0);
        String address = allPair.getPath1();
        QueryWrapper<AllPair> wrapperPair = new QueryWrapper<>();
        if (USDT.equals(allPair.getPath1())) {
            address = allPair.getPath0();
        }
//        wrapperPair.eq("path1", address);
//        List<AllPair> pairsLeft = allPairRepository.list(wrapperPair);
        String finalAddress = address;
        List<AllPair> pairsLeft = allList.stream().filter(p -> p.getPath1().equals(finalAddress)).collect(Collectors.toList());
        for (AllPair pair : pairsLeft) {
            String iPath0 = pair.getPath0();
            searchSleuth(id, pairSleuths, address, wrapperPair, iPath0, allList);
        }
//        wrapperPair.clear();
//        wrapperPair.eq("path0", address);
//        List<AllPair> pairsRight = allPairRepository.list(wrapperPair);
        List<AllPair> pairsRight = allList.stream().filter(p -> p.getPath0().equals(finalAddress)).collect(Collectors.toList());
        for (AllPair pair : pairsRight) {
            String iPath1 = pair.getPath1();
            searchSleuth(id, pairSleuths, address, wrapperPair, iPath1, allList);
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
//        wrapperPair.clear();
//        wrapperPair.eq("path0", iPath1).or().eq("path1", iPath1);
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
