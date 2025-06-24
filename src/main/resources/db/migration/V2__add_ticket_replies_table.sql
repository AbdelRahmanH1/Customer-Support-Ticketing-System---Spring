CREATE TABLE ticket_replies (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                ticket_id BIGINT NOT NULL,
                                user_id BIGINT NOT NULL,
                                message TEXT NOT NULL,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE CASCADE,
                                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
