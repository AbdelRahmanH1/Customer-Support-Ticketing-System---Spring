CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(100),
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL DEFAULT 'USER' CHECK (role IN ('USER', 'ADMIN')),
                       deleted BOOLEAN DEFAULT FALSE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE tickets (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         title VARCHAR(255),
                         description TEXT,
                         status VARCHAR(20) NOT NULL DEFAULT 'OPEN'
                             CHECK (status IN ('OPEN', 'IN_PROGRESS', 'CLOSED')),
                         user_id BIGINT,
                         admin_responder_id BIGINT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         end_time TIMESTAMP,

                         FOREIGN KEY (user_id) REFERENCES users(id),
                         FOREIGN KEY (admin_responder_id) REFERENCES users(id)
);

