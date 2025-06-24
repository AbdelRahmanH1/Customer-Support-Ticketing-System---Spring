CREATE TABLE audit_logs (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            action VARCHAR(255) NOT NULL,
                            actor_id BIGINT NOT NULL,
                            metadata JSON,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                            FOREIGN KEY (actor_id) REFERENCES users(id) ON DELETE CASCADE
);
