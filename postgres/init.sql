CREATE TABLE assets (
    symbol TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    kind TEXT NOT NULL CHECK (kind IN ('FIAT', 'CRYPTO')),
    scale INTEGER NOT NULL CHECK (scale >= 0)
);

CREATE TABLE trading_pairs (
    base_asset_symbol TEXT NOT NULL REFERENCES assets(symbol),
    quote_asset_symbol TEXT NOT NULL REFERENCES assets(symbol),
    last_known_price DECIMAL(38, 18),
    price_updated_at TIMESTAMPTZ,
    PRIMARY KEY (base_asset_symbol, quote_asset_symbol),
    CHECK (base_asset_symbol <> quote_asset_symbol)
);

INSERT INTO assets(symbol, name, kind, scale) VALUES
    ('BTC', 'Bitcoin', 'CRYPTO', 8),
    ('USD', 'US Dollar', 'FIAT', 2),
    ('ETH', 'Ethereum', 'CRYPTO', 18),
    ('BRL', 'Brazilian Real', 'FIAT', 2)
ON CONFLICT (symbol) DO NOTHING;

INSERT INTO trading_pairs(base_asset_symbol, quote_asset_symbol, last_known_price, price_updated_at) VALUES
    -- BTC <-> USD
    ('BTC', 'USD', 114223.17, now()),
    ('USD', 'BTC', 0.0000088, now()),
    -- ETH <-> USD
    ('ETH', 'USD', 4127.52, now()),
    ('USD', 'ETH', 0.00024, now()),
    -- BTC <-> BRL
    ('BRL', 'BTC', 0.0000016, now()),
    ('BTC', 'BRL', 613705.41, now()),
    -- BTC <-> ETH
    ('BTC', 'ETH', 27.66, now()),
    ('ETH', 'BTC', 0.036, now()),
    -- USD <-> BRL
    ('USD', 'BRL', 5.37, now()),
    ('BRL', 'USD', 0.19, now()),
    -- ETH <-> BRL
    ('ETH', 'BRL', 22164.8324, now()),
    ('BRL', 'ETH', 0.00004512, now())
ON CONFLICT (base_asset_symbol, quote_asset_symbol) DO NOTHING;