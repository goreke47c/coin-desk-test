CREATE TABLE currency_mapping (
    currency_code VARCHAR(3) PRIMARY KEY,
    currency_name VARCHAR(50),
    currency_ch_name VARCHAR(50),
    rate_text VARCHAR(20),
    rate_float DECIMAL(10, 4),CURRENCY_MAPPING
    symbol VARCHAR(10),
    last_updated TIMESTAMP
);