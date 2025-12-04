-- 図書館管理システム データベース初期化スクリプト

-- データベース作成
CREATE DATABASE IF NOT EXISTS `library-touroku` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `library-touroku`;

-- ユーザーテーブル作成
CREATE TABLE IF NOT EXISTS `user` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(100) NOT NULL,
  `pass` VARCHAR(100) NOT NULL,
  UNIQUE KEY `unique_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 図書テーブル作成
CREATE TABLE IF NOT EXISTS `list` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `book` VARCHAR(255) NOT NULL,
  `number` INT NOT NULL DEFAULT 0,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- サンプルデータ（オプション）
-- テスト用ユーザー
INSERT INTO `user` (`name`, `pass`) VALUES 
('admin', 'admin'),
('test', 'test')
ON DUPLICATE KEY UPDATE `name`=`name`;

-- サンプル図書データ
INSERT INTO `list` (`book`, `number`) VALUES 
('Java入門', 10),
('データベース設計', 5),
('Webアプリケーション開発', 8)
ON DUPLICATE KEY UPDATE `book`=`book`;

