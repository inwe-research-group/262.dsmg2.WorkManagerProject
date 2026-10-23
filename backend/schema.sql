CREATE TABLE IF NOT EXISTS public.sync_items (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT UNIQUE, -- unique if you want idempotency by client id
    title TEXT NOT NULL,
    body TEXT NOT NULL,
    timestamp BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);
