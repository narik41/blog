-- Create user table
CREATE TABLE IF NOT EXISTS users(
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

-- create user profile table
CREATE TABLE IF NOT EXISTS user_profiles(
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT(10) UNSIGNED NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),

    CONSTRAINT `user_profiles_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

-- Create post table
CREATE TABLE IF NOT EXISTS posts(
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT(10) UNSIGNED NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT  NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),

    CONSTRAINT `user_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

-- Create comment table
CREATE TABLE IF NOT EXISTS post_comments(
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT(10) UNSIGNED NOT NULL,
    post_id INT(10) UNSIGNED NOT NULL,
    `comment` TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (`id`),

    CONSTRAINT `post_comments_post_id_foreign` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
    CONSTRAINT `post_comments_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

-- Create image table
CREATE TABLE IF NOT EXISTS images(
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    path TEXT  NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

-- post and image
CREATE TABLE post_images  (
    post_id INT(10) UNSIGNED NOT NULL,
    image_id INT(10) UNSIGNED NOT NULL,

    CONSTRAINT fk_user_images_post_id FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_images__image_id FOREIGN KEY (image_id) REFERENCES images(id) ON DELETE CASCADE
);

-- Create role table
CREATE TABLE IF NOT EXISTS roles(
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`)
);

-- user and role
CREATE TABLE user_roles  (
    user_id INT(10) UNSIGNED NOT NULL ,
    role_id INT(10) UNSIGNED NOT NULL,

    CONSTRAINT fk_user_roles_user_id FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role_id FOREIGN KEY(role_id) REFERENCES roles(id) ON DELETE CASCADE
);
