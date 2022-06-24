create table `order_info`
(
    `id`                    varchar(50) collate utf8_bin not null comment '订单id',
    `total_num`             int(11) default null comment '数量合计',
    `moneys`                int(11) default null comment '金额合计',
    `pay_type`              varchar(1) collate utf8_bin   default null comment '支付类型，1、在线支付、0 货到付款',
    `create_time`           datetime                      default null comment '订单创建时间',
    `update_time`           datetime                      default null comment '订单更新时间',
    `pay_time`              datetime                      default null comment '付款时间',
    `consign_time`          datetime                      default null comment '发货时间',
    `end_time`              datetime                      default null comment '交易完成时间',
    `username`              varchar(50) collate utf8_bin  default null comment '用户名称',
    `recipients`            varchar(50) collate utf8_bin  default null comment '收货人',
    `recipients_mobile`     varchar(12) collate utf8_bin  default null comment '收货人手机',
    `recipients_address`    varchar(200) collate utf8_bin default null comment '收货人地址',
    `weixin_transaction_id` varchar(30) collate utf8_bin  default null comment '交易流水号',
    `order_status`          int(1) default null comment '订单状态,0:未完成,1:已完成，2：已退货',
    `pay_status`            int(1) default null comment '支付状态,0:未支付，1：已支付，2：支付失败',
    `is_delete`             int(1) default null comment '是否删除',
    primary key (`id`),
    key                     `create_time` (`create_time`),
    key                     `status` (`order_status`),
    key                     `payment_type` (`pay_type`)
) engine=innodb default charset=utf8 comment ='订单表';


create table `order_sku`
(
    `id`       varchar(50) collate utf8_bin not null comment 'id',
    `sku_id`   varchar(60) collate utf8_bin  default null comment 'sku_id',
    `order_id` varchar(50) collate utf8_bin not null comment '订单id',
    `name`     varchar(200) collate utf8_bin default null comment '商品名称',
    `price`    int(20) default null comment '单价',
    `num`      int(10) default null comment '数量',
    `money`    int(20) default null comment '总金额',
    `image`    varchar(200) collate utf8_bin default null comment '图片地址',
    primary key (`id`),
    key        `item_id` (`sku_id`),
    key        `order_id` (`order_id`)
) engine=innodb default charset=utf8 comment ='订单明细表';
