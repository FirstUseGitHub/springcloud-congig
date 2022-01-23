package com.atguigu.springcloud.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @author 张明伟
 * @version 1.0
 **/
//如果没有添加这个就没有注入到ioc容器中
@Component
public class MyLogGateWayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("time:" + new Date() + "\t 执行了自定义的全局过滤器: " + "MyLogGateWayFilter" + "hello");
        //获取url中的第一个参数
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        //此时就表示不能再向下继续执行
        if (uname == null) {
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        //放行，让其他的过滤器继续执行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
