create table `pay_log`
(
    `id`         varchar(50) not null comment 'id',
    `status`     int(20)      default null comment '状态',
    `content`    varchar(200) default null comment '内容',
    `pay_id`      varchar(200) default null comment '支付id',
    `create_time` timestamp comment '创建时间',
    primary key (`id`)
) engine = innodb
  default charset = utf8 comment ='支付日志表';
