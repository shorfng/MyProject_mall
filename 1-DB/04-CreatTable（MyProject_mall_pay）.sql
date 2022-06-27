create table `pay_log`
(
    `id`          varchar(60) not null comment '支付唯一标识符',
    `status`      int(1) not null comment '状态 1未支付（notpay），2支付成功（success），3转入退款（refund），4已关闭（closed），5 已撤销（revoked） ，6 用户支付中（userpaying），7 支付失败（payerror），8 系统错误（systemerror），9 已经关闭（orderclosed），10 签名错误（signerror），11 请使用post方法（require_post_method），12 操作错误（error）',
    `content`     varchar(2000)        default null comment '支付凭证信息',
    `pay_id`      varchar(60)          default null comment '查询唯一标识符',
    `create_time` timestamp   not null default current_timestamp on update current_timestamp comment '创建时间、修改时间',
    primary key (`id`)
) engine = innodb
  default charset = utf8 comment ='支付日志表';

