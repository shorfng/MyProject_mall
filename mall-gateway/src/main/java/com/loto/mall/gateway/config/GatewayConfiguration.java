package com.loto.mall.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author：蓝田_Loto<p>
 * Date：2022-07-04 15:48<p>
 * PageName：GatewayConfiguration.java<p>
 * Function：
 */

//@Configuration
public class GatewayConfiguration {
    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider, ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    /**
     * 熔断降级异常处理
     *
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    /**
     * 获取当前 Route路由，并按照当前 Sentinel 流量控制规则做处理
     *
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    /**
     * 初始化（采用 @PostConstruct 注解实现加载调用）
     */
    @PostConstruct
    public void doInit() {
        initCustomizedApis();  // 加载 Api 组
        initGatewayRules();    // 加载网关限流规则
    }

    /**
     * 定义 Api 组
     */
    private void initCustomizedApis() {
        // 定义集合存储要定义的 Api 组
        Set<ApiDefinition> definitions = new HashSet<>();

        // 创建 Api，并配置相关规律
        ApiDefinition cartApi = new ApiDefinition("mall_api_cart")
                .setPredicateItems(new HashSet<>() {{
                    add(new ApiPathPredicateItem().setPattern("/cart/list"));  // /cart/list
                    add(new ApiPathPredicateItem().setPattern("/cart/**")      // /cart/*/*
                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX)   // 匹配策略：根据前缀匹配
                    );
                }});

        // 将创建好的 Api 添加到 Api 集合中
        definitions.add(cartApi);

        // 手动加载 Api 到 Sentinel
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    /**
     * 定义网关限流规则
     */
    public void initGatewayRules() {
        // 创建集合存储所有规则
        Set<GatewayFlowRule> rules = new HashSet<>();

        // 创建新的规则，并添加到集合中
        // goods_route 为配置文件中的 spring.cloud.gateway.routes.id 值
        rules.add(new GatewayFlowRule("goods_route")
                .setCount(2)  // 请求的阈值
                //.setBurst(2)  // 突发流量，额外允许的并发数量

                // 限流行为
                // CONTROL_BEHAVIOR_RATE_LIMITER  匀速排队
                // CONTROL_BEHAVIOR_DEFAULT  直接失败
                .setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER)

                .setMaxQueueingTimeoutMs(10000)  // 排队时间
                .setIntervalSec(30) // 统计时间窗口，单位：秒，默认为1秒
        );

        // 创建新的规则，并添加到指定的 Api 集合中
        rules.add(new GatewayFlowRule("mall_api_cart")  // 84行定义的 Api 名称
                .setCount(2)  // 请求的阈值
                //.setBurst(2)  // 突发流量，额外允许的并发数量

                // 限流行为
                // CONTROL_BEHAVIOR_RATE_LIMITER  匀速排队
                // CONTROL_BEHAVIOR_DEFAULT  直接失败
                .setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT)

                .setMaxQueueingTimeoutMs(10000)  // 排队时间
                .setIntervalSec(30)  // 统计时间窗口，单位：秒，默认为1秒
        );

        // 手动加载规则配置
        GatewayRuleManager.loadRules(rules);
    }
}
