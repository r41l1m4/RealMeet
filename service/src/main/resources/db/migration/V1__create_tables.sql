CREATE TABLE `room` (
    `id`        BIGINT          NOT NULL    AUTO_INCREMENT,
    `name`      VARCHAR(20)     NOT NULL,
    `seats`     INT NOT         NULL,
    `active`    TINYINT         NOT NULL,
    PRIMARY KEY (`id`)
);
