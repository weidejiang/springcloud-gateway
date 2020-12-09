package com.manulife.ap.filter;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;

public class TokenFilter implements GlobalFilter, Ordered {

    private static final Logger log= LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        URI uri = exchange.getRequest().getURI();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @SuppressWarnings("serial")
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                switch (getDelegate().getStatusCode().value()) {
                    case 302:
                    case 301:
                    case 303:
                        HttpHeaders headers = getDelegate().getHeaders();
                        String location = headers.getFirst(HttpHeaders.LOCATION);
                        int i = -1;
                        if (StringUtils.isEmpty(location) || (i = location.indexOf("/", 8)) == -1) {
                            log.error("异常URI{} .", location);
                            break;
                        }

                        String newLocation = uri.getScheme() + "://" + uri.getHost() + ":"
                                + (uri.getPort() > 0 ? uri.getPort() : 80) +"/"+uri.getPath().split("/")[1]+ location.substring(i);
                        log.debug("ASK {}  for RELP URL :{}  TO {} ", uri.toString(), location, newLocation);
                        headers.put(HttpHeaders.LOCATION, new ArrayList<String>() {
                            {
                                add(newLocation);
                            }
                        });
                        break;
                    default:
                        break;
                }

                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
    }
}
