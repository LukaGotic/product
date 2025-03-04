CREATE
    TABLE
        users(
            id BIGSERIAL PRIMARY KEY,
            username VARCHAR(255) NOT NULL UNIQUE,
            password VARCHAR(255) NOT NULL
        );

INSERT
    INTO
        users(
            username,
            password
        )
    VALUES(
        'user',
        '$2b$12$Z/Mz/GVylWorDRcD7GJM4unVY1UWh0bRUnC0t/jMVaNCrxWfKH9/G'
    );
