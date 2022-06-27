create table `seckill_goods`
(
    `id`            varchar(60) not null,
    `sup_id`        varchar(60)   default null comment 'spu id',
    `sku_id`        varchar(60)   default null comment 'sku id',
    `name`          varchar(100)  default null comment '标题',
    `images`        varchar(150)  default null comment '商品图片',
    `price`         int(20) default null comment '原价格',
    `seckill_price` int(20) default null comment '秒杀价格',
    `num`           int(11) default null comment '秒杀商品数',
    `store_count`   int(11) default null comment '剩余库存数',
    `content`       varchar(2000) default null comment '描述',
    `activity_id`   varchar(60)   default null comment '活动id',
    primary key (`id`)
) engine=innodb default charset=utf8 comment ='秒杀商品表';



create table `seckill_order`
(
    `id`                    varchar(60) not null comment '主键',
    `seckill_goods_id`      varchar(60) default null comment '秒杀商品id',
    `money`                 int(10) default null comment '支付金额',
    `username`              varchar(50) default null comment '用户',
    `create_time`           datetime    default null comment '创建时间',
    `pay_time`              datetime    default null comment '支付时间',
    `status`                int(1) default null comment '状态（0未支付，1已支付）',
    `weixin_transaction_id` varchar(30) default null comment '交易流水',
    primary key (`id`)
) engine=innodb default charset=utf8 comment ='秒杀订单表';


create table `seckill_activity`
(
    `id`            varchar(60) not null,
    `activity_name` varchar(60) not null comment '活动名字',
    `type`          int(1) not null comment '活动分类（0 shop秒杀、1每日特价、2大牌闪购、 3品类秒杀、4节日活动）',
    `start_time`    datetime    not null comment '开始时间',
    `end_time`      datetime    not null comment '结束时间',
    primary key (`id`)
) engine=innodb default charset=utf8 comment ='秒杀活动表';
