package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderSubmitService;
import com.sky.service.ShoppingCartService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/order")
@Slf4j
@Api(tags = "用户订单接口")
public class OrderController {

    @Autowired
    private OrderSubmitService orderSubmitService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> orderSubmit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单：{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderSubmitService.submit(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderSubmitService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        return Result.success(orderPaymentVO);
    }

    /**
     * 历史订单查询
     * @param page,pageSize,status
     * @return
     */
    @GetMapping("/historyOrders")
    @ApiOperation("历史订单查询")
    public Result<PageResult> page(int page, int pageSize, Integer status) {
        log.info("历史订单查询，page={},pageSize={},status={}", page, pageSize, status);
        PageResult pageResult = orderSubmitService.page(page, pageSize, status);
        return Result.success(pageResult);
    }

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    @GetMapping("/orderDetail/{id}")
    @ApiOperation("根据订单id查看订单详情")
    public Result<OrderVO> showOrderDetail(@PathVariable("id") Long id) {
        log.info("根据订单id查看订单详情：{}", id);
        OrderVO orderVO = orderSubmitService.showOrderDetail(id);
        return Result.success(orderVO);
    }

    /**
     * 取消订单
     * @param id
     * @return
     */
    @PutMapping("/cancel/{id}")
    @ApiOperation("取消订单")
    public Result<String> cancel(@PathVariable("id") Long id) throws Exception {
        log.info("取消订单：{}", id);
        orderSubmitService.cancel(id);
        return Result.success();
    }

//    /**
//     * 再来一单
//     * @param id
//     * @return
//     */
//    @PostMapping("/repetition/{id}")
//    @ApiOperation("再来一单")
//    public Result<String> repetition(@PathVariable("id") Long id) {
//        log.info("再来一单：{}", id);
//        // 构造出shoppingCartDTO
//        List<ShoppingCartDTO> shoppingCartDTOList = orderSubmitService.repetition(id);
//
//        for (ShoppingCartDTO shoppingCartDTO : shoppingCartDTOList) {
//            // 调用之前的添加购物车接口
//            shoppingCartService.add(shoppingCartDTO);
//        }
//
//        return Result.success();
//    }

    /**
     * 再来一单
     * @param id
     * @return
     */
    @PostMapping("/repetition/{id}")
    @ApiOperation("再来一单")
    public Result<String> repetition(@PathVariable Long id) {
        orderSubmitService.repetition(id);
        return Result.success();
    }
}
