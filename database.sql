-- 创建管理员表（Admin）
CREATE TABLE `admin`
(
    `id`          bigint     NOT NULL AUTO_INCREMENT COMMENT '管理员ID，主键',
    `user_id`     bigint     NOT NULL COMMENT '关联用户ID',
    `group_name`  VARCHAR(50)          DEFAULT NULL COMMENT '组别名称，如技术组等',
    `score`       INT(11)     NOT NULL  DEFAULT 0 COMMENT '分数',
    `create_time` DATETIME             DEFAULT NULL COMMENT '创建时间，自动填充当前时间',
    `update_time` DATETIME             DEFAULT NULL  COMMENT '修改时间，每次更新时自动更新为当前时间',
    `create_user` bigint               DEFAULT NULL COMMENT '创建者ID',
    `update_user` bigint               DEFAULT NULL COMMENT '修改者ID',
    `is_deleted`  TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，1表示已删除，0表示未删除',
    `delete_time` DATETIME             DEFAULT NULL COMMENT '删除时间',
    `version`     INT(11)     NOT NULL DEFAULT 0 COMMENT '乐观锁版本号，用于并发控制',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_user_id` (`user_id`) COMMENT '唯一索引，确保一个用户只能对应一个管理员'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='管理员信息表';

-- 创建用户表（User）
CREATE TABLE `user`
(
    `id`                bigint     NOT NULL AUTO_INCREMENT COMMENT '用户ID，主键',
    `openid`            varchar(256) not null unique comment '用户的openid',
    `name`              VARCHAR(50)           COMMENT '姓名',
    `student_id`        VARCHAR(50)           COMMENT '学号',
    `campus`            VARCHAR(50)           COMMENT '所在校区',
    `department`        VARCHAR(100)          COMMENT '所在院系',
    `phone_number`      VARCHAR(20)           COMMENT '手机长号',
    `wechat_id`         VARCHAR(50)           DEFAULT NULL COMMENT '微信号',
    `address`           VARCHAR(100)          COMMENT '地址（精确到宿舍号）',
    `image`         varchar(255) COMMENT '用户头像',
    `create_time` DATETIME             DEFAULT NULL COMMENT '创建时间，自动填充当前时间',
    `update_time` DATETIME             DEFAULT NULL  COMMENT '修改时间，每次更新时自动更新为当前时间',
    `create_user` bigint               DEFAULT NULL COMMENT '创建者ID',
    `update_user` bigint               DEFAULT NULL COMMENT '修改者ID',
    `is_deleted`  TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，1表示已删除，0表示未删除',
    `delete_time` DATETIME             DEFAULT NULL COMMENT '删除时间',
    `version`     INT(11)     NOT NULL DEFAULT 0 COMMENT '乐观锁版本号，用于并发控制',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户信息表';

-- 创建维修单表（RepairOrder）
CREATE TABLE `repair_order`
(
    `id`                   bigint      NOT NULL AUTO_INCREMENT COMMENT '维修单ID，主键',
    `user_id`              bigint     NOT NULL COMMENT '关联用户ID',
    `name`              VARCHAR(50)           COMMENT '姓名',
    `student_id`        VARCHAR(50)           COMMENT '学号',
    `campus`            VARCHAR(50)           COMMENT '所在校区',
    `department`        VARCHAR(100)          COMMENT '所在院系',
    `phone_number`      VARCHAR(20)           COMMENT '手机长号',
    `wechat_id`         VARCHAR(50)           DEFAULT NULL COMMENT '微信号',
    `address`           VARCHAR(100)          COMMENT '地址（精确到宿舍号）',
    `computer_model`       VARCHAR(100) NOT NULL COMMENT '电脑机型',
    `computer_brand_model` VARCHAR(100) NOT NULL COMMENT '电脑品牌型号',
    `os_version`           VARCHAR(50)  NOT NULL COMMENT '操作系统版本',
    `fault_description`    TEXT         NOT NULL COMMENT '详细描述故障问题情况',
    `fault_images`         varchar(255) COMMENT '故障情况图片，可以存储图片路径或序列化后的数据',
    `is_accepted`          TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '是否被接单，1表示已接单，0表示未接单',
    `accpeted_user`  bigint               DEFAULT NULL COMMENT '接单者ID',
    `create_time` DATETIME             DEFAULT NULL COMMENT '创建时间，自动填充当前时间',
    `update_time` DATETIME             DEFAULT NULL  COMMENT '修改时间，每次更新时自动更新为当前时间',
    `create_user` bigint               DEFAULT NULL COMMENT '创建者ID',
    `update_user` bigint               DEFAULT NULL COMMENT '修改者ID',
    `is_deleted`  TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，1表示已删除，0表示未删除',
    `delete_time` DATETIME             DEFAULT NULL COMMENT '删除时间',
    `version`     INT(11)     NOT NULL DEFAULT 0 COMMENT '乐观锁版本号，用于并发控制',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='维修单信息表';


-- 创建反馈表（Feedback）
CREATE TABLE `feedback`
(
    `id`          bigint    NOT NULL AUTO_INCREMENT COMMENT '反馈表ID，主键',
    `user_id`     bigint    NOT NULL COMMENT '关联用户ID',
    `feedback_description`  TEXT         NOT NULL COMMENT '详细描述故障问题情况',
    `create_time` DATETIME             DEFAULT NULL COMMENT '创建时间，自动填充当前时间',
    `update_time` DATETIME             DEFAULT NULL  COMMENT '修改时间，每次更新时自动更新为当前时间',
    `create_user` bigint               DEFAULT NULL COMMENT '创建者ID',
    `update_user` bigint               DEFAULT NULL COMMENT '修改者ID',
    `is_deleted`  TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，1表示已删除，0表示未删除',
    `delete_time` DATETIME             DEFAULT NULL COMMENT '删除时间',
    `version`     INT(11)     NOT NULL DEFAULT 0 COMMENT '乐观锁版本号，用于并发控制',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户反馈信息表';

-- 创建订单评价表（OrderRating）
CREATE TABLE `order_rating`
(
    `id`          bigint     NOT NULL AUTO_INCREMENT COMMENT '评价ID，主键',
    `order_id`    bigint     NOT NULL COMMENT '关联维修单ID（逻辑外键，关联repair_order.id）',
    `user_id`     bigint     NOT NULL COMMENT '评价用户ID（逻辑外键，关联user.id）',
    `admin_id`    bigint     DEFAULT NULL COMMENT '接单管理员ID（逻辑外键，关联user.id，从repair_order.accepted_user派生）',
    `rating`     INT(11)     NOT NULL COMMENT '评分，1-5分',
    `comment`     VARCHAR(500) DEFAULT NULL COMMENT '评价评论，可选，最大500字符',
    `create_time` DATETIME             DEFAULT NULL COMMENT '创建时间，自动填充当前时间',
    `update_time` DATETIME             DEFAULT NULL  COMMENT '修改时间，每次更新时自动更新为当前时间',
    `create_user` bigint               DEFAULT NULL COMMENT '创建者ID',
    `update_user` bigint               DEFAULT NULL COMMENT '修改者ID',
    `is_deleted`  TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '是否删除，1表示已删除，0表示未删除',
    `delete_time` DATETIME             DEFAULT NULL COMMENT '删除时间',
    `version`     INT(11)     NOT NULL DEFAULT 0 COMMENT '乐观锁版本号，用于并发控制',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_order_user` (`order_id`, `user_id`) COMMENT '唯一索引，确保每个用户对每个订单只能评价一次',
    KEY `idx_order_id` (`order_id`) COMMENT '订单ID索引，用于查询订单的评价',
    KEY `idx_user_id` (`user_id`) COMMENT '用户ID索引，用于查询用户的评价',
    KEY `idx_admin_id` (`admin_id`) COMMENT '管理员ID索引，用于查询管理员收到的评价'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单评价信息表';