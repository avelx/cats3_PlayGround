SELECT * FROM country;

CREATE TABLE country (
                         code       character(3)  NOT NULL,
                         name       text          NOT NULL,
                         population integer       NOT NULL,
                         gnp        numeric(10,2)
    -- more columns, but we won't use them here
)