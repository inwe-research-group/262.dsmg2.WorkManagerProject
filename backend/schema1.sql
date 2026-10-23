CREATE TABLE IF NOT EXISTS public.sync_items (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT UNIQUE, -- unique if you want idempotency by client id
    title TEXT NOT NULL,
    body TEXT NOT NULL,
    timestamp BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);
CREATE USER usuario1 WITH PASSWORD 'usuario1';
GRANT ALL PRIVILEGES ON DATABASE mycorporatedb TO usuario1;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO usuario1;
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO usuario1;