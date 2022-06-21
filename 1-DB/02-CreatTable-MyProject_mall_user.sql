-- 用户收件地址
CREATE TABLE `address`
(
    id          int auto_increment primary key,
    user_name   varchar(50) null comment '用户名',
    province_id varchar(20) null comment '省',
    city_id     varchar(20) null comment '市',
    area_id     varchar(20) null comment '县/区',
    phone       varchar(20) null comment '电话',
    address     varchar(200) null comment '详细地址',
    contact     varchar(50) null comment '联系人',
    is_default  int(1) null comment '是否是默认 1默认 0否'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT ='用户收件地址';

INSERT INTO MyProject_mall_user.address (id, user_name, province_id, city_id, area_id, phone, address, contact, is_default) VALUES (1, 'TD', '1', '1', '2', '12345678912', '北京市西大望路xxxx号', '蓝田_Loto', 1);
INSERT INTO MyProject_mall_user.address (id, user_name, province_id, city_id, area_id, phone, address, contact, is_default) VALUES (2, 'CJ', '2', '1', '1', '12123456789', '北京市潘家园街道xxxx号', 'CJ', 1);
INSERT INTO MyProject_mall_user.address (id, user_name, province_id, city_id, area_id, phone, address, contact, is_default) VALUES (3, 'TD', '2', '3', '4', '12345678912', '北京市潘家园街道xxxx号', '蓝田_Loto', 0);
