INSERT INTO `operation` (`id_operation`, `type`, `cost`)
VALUES
	(1, 'ADDITION', 1),
	(2, 'SUBSTRACTION', 2),
	(3, 'MULTIPLICATION', 2.5),
	(4, 'DIVISION', 3),
	(5, 'SQUARE_ROOT', 5),
	(6, 'RANDOM_STRING', 4);

INSERT INTO `user_info` (`id_user`, `username`, `name`, `password`, `status`)
VALUES
	(1, 'marcusandrade816@gmail.com', 'Marcus Andrade', '$2a$10$VcN1jjgEEmarV8WnAZdJD.0FOpYmdSTH5.I/WL0RpkpEQXXFE8Jgi', 1);

INSERT INTO `user_record` (`id_user`, `id_user_deleted`, `id_operation`, `amount`, `user_balance`, `operation_response`, `date_inserted`, `date_inserted_uts`, `date_deleted`, `date_deleted_uts`, `deleted`, `success`)
VALUES
	(1, NULL, NULL, 0, 100, 'Initial balance', '2023-03-02 21:00:00', 1677790800, NULL, NULL, 0, 1);


