-- Drop tables if they exist
DROP TABLE IF EXISTS article_comment;
DROP TABLE IF EXISTS article_hashtag;
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS hashtag;
DROP TABLE IF EXISTS user_account;

-- Create user accounts table
CREATE TABLE user_account (
                               user_id VARCHAR(50) PRIMARY KEY,
                               user_password VARCHAR(255) NOT NULL,
                               email VARCHAR(100) NOT NULL UNIQUE,
                               nickname VARCHAR(100),
                               memo TEXT,
                               created_at TIMESTAMP,
                               modified_at TIMESTAMP,
                               created_by VARCHAR(50),
                               modified_by VARCHAR(50)
);

-- Create articles table
CREATE TABLE article (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          user_id VARCHAR(50) NOT NULL,
                          title VARCHAR(255) NOT NULL,
                          content TEXT NOT NULL,
                          created_at TIMESTAMP,
                          modified_at TIMESTAMP,
                          created_by VARCHAR(50),
                          modified_by VARCHAR(50),
                          FOREIGN KEY (user_id) REFERENCES user_account(user_id)
);

-- Create article comments table
CREATE TABLE article_comment (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  article_id BIGINT NOT NULL,
                                  user_id VARCHAR(50) NOT NULL,
                                  content TEXT NOT NULL,
                                  created_at TIMESTAMP,
                                  modified_at TIMESTAMP,
                                  created_by VARCHAR(50),
                                  modified_by VARCHAR(50),
                                  FOREIGN KEY (article_id) REFERENCES article(id),
                                  FOREIGN KEY (user_id) REFERENCES user_account(user_id)
);

-- Create hashtags table
CREATE TABLE hashtag (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          hashtag_name VARCHAR(255) NOT NULL UNIQUE,
                          created_at TIMESTAMP,
                          modified_at TIMESTAMP,
                          created_by VARCHAR(50),
                          modified_by VARCHAR(50)
);

-- Create junction table for articles and hashtags many-to-many relationship
CREATE TABLE article_hashtag (
                                  article_id BIGINT NOT NULL,
                                  hashtag_id BIGINT NOT NULL,
                                  PRIMARY KEY (article_id, hashtag_id),
                                  FOREIGN KEY (article_id) REFERENCES article(id),
                                  FOREIGN KEY (hashtag_id) REFERENCES hashtag(id)
);
