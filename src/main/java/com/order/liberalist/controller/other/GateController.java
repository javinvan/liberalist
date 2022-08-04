package com.order.liberalist.controller.other;

import io.gate.gateapi.ApiClient;
import io.gate.gateapi.ApiException;
import io.gate.gateapi.Configuration;
import io.gate.gateapi.GateApiException;
import io.gate.gateapi.api.SpotApi;
import io.gate.gateapi.models.Order;
import io.gate.gateapi.models.Ticker;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static java.math.RoundingMode.DOWN;

/**
 * 功能概述：GateController
 * Date: 2021/11/22
 * Copyright: Copyright© 2021 alpha All Rights Reserved
 *
 * @author Javin
 * @version 1.0
 * @since JDK 1.8
 */
@RestController
public class GateController {

    @PostMapping("order")
    @ResponseBody
    public String order(@RequestBody Order order) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.gateio.ws/api/v4");
        defaultClient.setApiKeySecret("b5fde06975c1d044cee9cc364b769fe3", "d425fa387570be0cd70f15061d204f9af760be024c49d239f0af823b7f9cc6f1");

        SpotApi apiInstance = new SpotApi(defaultClient);
        try {
            String currencyPair = order.getCurrencyPair();
            List<Ticker> tickers = apiInstance.listTickers().currencyPair(currencyPair).execute();
//            BigDecimal lastPrice = new BigDecimal(Objects.requireNonNull(tickers.get(0).getLast()));
            BigDecimal lastPrice = new BigDecimal("0.37");
//            BigDecimal price = lastPrice.multiply(new BigDecimal("5"));
            BigDecimal price = new BigDecimal("0.37");
            order.setAmount(String.valueOf(new BigDecimal(order.getAmount()).divide(lastPrice, 2, DOWN)));
            order.setPrice(String.valueOf(price));
            Order result = apiInstance.createOrder(order);
            System.out.println(result);
        } catch (GateApiException e) {
            System.err.printf("Gate api exception, label: %s, message: %s%n", e.getErrorLabel(), e.getMessage());
            e.printStackTrace();
            return String.format("Gate api exception, label: %s, message: %s", e.getErrorLabel(), e.getMessage());
        } catch (ApiException e) {
            System.err.println("Exception when calling SpotApi#createOrder");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

}
