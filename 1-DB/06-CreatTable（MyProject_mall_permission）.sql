create table `permission`
(
    `id`           int(11)      not null auto_increment,
    `source_name`  varchar(20)  not null comment '权限名字',
    `url`          varchar(200) not null comment '权限访问地址，支持通配符',
    `url_match`    int(1)       not null comment '匹配方式：0 完全匹配   1 通配符匹配',
    `service_name` varchar(100) not null comment '服务名字',
    `method`       varchar(10)  not null comment 'get/post/put/options/delete/*',
    primary key (`id`)
) engine = innodb
  default charset = utf8 comment ='权限资源表';


create table `role_info`
(
    `id`          int(11)     not null auto_increment,
    `role_name`   varchar(20) not null,
    `description` varchar(500) default null,
    primary key (`id`)
) engine = innodb
  default charset = utf8 comment ='角色信息表';


create table `role_permission`
(
    `pid` int(11) not null,
    `rid` int(11) not null,
    primary key (`pid`, `rid`)
) engine = innodb
  default charset = utf8 comment ='角色授权表';
