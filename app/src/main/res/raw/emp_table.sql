CREATE TABLE IF NOT EXISTS `emp` (
    `id` INTEGER PRIMARY KEY AUTOINCREMENT,
    `name` TEXT,
    `displayname` TEXT,
    `buyPrice` TEXT,
    `sellPrice` TEXT,
    `buyVolume` INT,
    `sellVolume` INT,
    `time` DATETIME
)