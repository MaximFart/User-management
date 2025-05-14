DROP TABLE IF EXISTS users, subscriptions, subscription_user CASCADE;

-- Создание таблицы users
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL
);

-- Создание таблицы subscriptions
CREATE TABLE subscriptions (
                               id SERIAL PRIMARY KEY,
                               name VARCHAR(100) NOT NULL
);

ALTER TABLE subscriptions ADD CONSTRAINT subscription_name_unique UNIQUE (name);

-- Создание промежуточной таблицы user_subscriptions для связи many-to-many
CREATE TABLE subscription_user (
                                    user_id INT NOT NULL,
                                    subscription_id INT NOT NULL,
                                    PRIMARY KEY (user_id, subscription_id),
                                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                    FOREIGN KEY (subscription_id) REFERENCES subscriptions(id) ON DELETE CASCADE
);
