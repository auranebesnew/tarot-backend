-- Users
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    display_name VARCHAR(255),
    role VARCHAR(50) NOT NULL DEFAULT 'USER',
    subscription_type VARCHAR(50) NOT NULL DEFAULT 'FREE',
    bonus_ai_readings INTEGER NOT NULL DEFAULT 3,
    subscription_expires_at TIMESTAMPTZ
);

-- Refresh tokens
CREATE TABLE refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(256) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    expires_at TIMESTAMPTZ NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_refresh_tokens_user_id ON refresh_tokens(user_id);

-- Tarot readings
CREATE TABLE tarot_readings (
    id BIGSERIAL PRIMARY KEY,
    question TEXT,
    context TEXT,
    spread_type VARCHAR(50),
    cards_json TEXT,
    interpretation TEXT,
    used_ai BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);
CREATE INDEX idx_tarot_readings_user_id ON tarot_readings(user_id);
