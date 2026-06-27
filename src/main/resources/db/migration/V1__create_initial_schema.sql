------------------------------------------------------------
-- TABLE: category
------------------------------------------------------------
CREATE TABLE categories (
    id UUID PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE
);

------------------------------------------------------------
-- TABLE: transaction
------------------------------------------------------------
CREATE TABLE transactions (
    id UUID PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    date DATE NOT NULL,
    description VARCHAR(80) NOT NULL,
    category_id UUID,
    created_date TIMESTAMP NOT NULL,

    CONSTRAINT fk_transaction_category
            FOREIGN KEY (category_id)
            REFERENCES categories(id)
);