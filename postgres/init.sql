CREATE TABLE assets (
    symbol TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    kind TEXT NOT NULL CHECK (kind IN ('FIAT', 'CRYPTO')),
    scale INTEGER NOT NULL CHECK (scale >= 0)
);

CREATE TABLE trading_pairs (
    base_asset_symbol TEXT NOT NULL REFERENCES assets(symbol),
    quote_asset_symbol TEXT NOT NULL REFERENCES assets(symbol),
    PRIMARY KEY (base_asset_symbol, quote_asset_symbol),
    CHECK (base_asset_symbol <> quote_asset_symbol)
);

INSERT INTO assets(symbol, name, kind, scale) VALUES
    ('BTC', 'Bitcoin', 'CRYPTO', 8),
    ('USD', 'US Dollar', 'FIAT', 2),
    ('ETH', 'Ethereum', 'CRYPTO', 18),
    ('BRL', 'Brazilian Real', 'FIAT', 2)
ON CONFLICT (symbol) DO NOTHING;

INSERT INTO trading_pairs(base_asset_symbol, quote_asset_symbol) VALUES
    -- BTC <-> USD
    ('BTC', 'USD'),
    ('USD', 'BTC'),
    -- ETH <-> USD
    ('ETH', 'USD'),
    ('USD', 'ETH'),
    -- BTC <-> BRL
    ('BRL', 'BTC'),
    ('BTC', 'BRL'),
    -- BTC <-> ETH
    ('BTC', 'ETH'),
    ('ETH', 'BTC'),
    -- USD <-> BRL
    ('USD', 'BRL'),
    ('BRL', 'USD'),
    -- ETH <-> BRL
    ('ETH', 'BRL'),
    ('BRL', 'ETH')
ON CONFLICT (base_asset_symbol, quote_asset_symbol) DO NOTHING;