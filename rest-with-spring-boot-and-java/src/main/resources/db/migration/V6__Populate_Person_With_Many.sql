INSERT INTO person (first_name, last_name, address, gender, enabled)
WITH RECURSIVE seq AS (
	SELECT 1 AS n
	UNION ALL
	SELECT n + 1
	FROM seq
	WHERE n < 1000
)
SELECT
	ELT(slot,
		'Brunhilda', 'Ellerey', 'Saleem', 'Briant', 'Juliette',
		'Kelsey', 'Claretta', 'Jedediah', 'Kane', 'Marti',
		'Albina', 'Osbert', 'Caralie', 'Carry', 'Matti',
		'Piotr', 'Melisse', 'Stephie', 'Kizzie', 'Laurie'
	) AS first_name,
	ELT(slot,
		'Cansdell', 'Kemm', 'Scraggs', 'Coaster', 'Demangeot',
		'Whimpenny', 'Matchell', 'Skirven', 'Elgood', 'Josse',
		'Sor', 'Witherby', 'Antyshev', 'Madre', 'Hellens',
		'Labeuil', 'Hallett', 'Derwin', 'Feehily', 'Treswell'
	) AS last_name,
	ELT(slot,
		'Room 1938', 'Suite 7', 'Apt 592', 'Apt 1271', '13th Floor',
		'4th Floor', 'Suite 14', 'Apt 1727', 'Suite 56', 'Suite 65',
		'14th Floor', 'Apt 71', 'Apt 1699', 'Apt 26', 'PO Box 86389',
		'Apt 660', 'Room 696', 'Apt 138', 'Suite 70', 'Room 1206'
	) AS address,
	ELT(slot,
		'Female', 'Male', 'Male', 'Male', 'Female',
		'Female', 'Female', 'Male', 'Male', 'Female',
		'Female', 'Male', 'Female', 'Female', 'Female',
		'Male', 'Female', 'Female', 'Female', 'Female'
	) AS gender,
	CASE slot
		WHEN 1 THEN b'1'
		WHEN 2 THEN b'1'
		WHEN 3 THEN b'0'
		WHEN 4 THEN b'0'
		WHEN 5 THEN b'1'
		WHEN 6 THEN b'1'
		WHEN 7 THEN b'0'
		WHEN 8 THEN b'1'
		WHEN 9 THEN b'0'
		WHEN 10 THEN b'0'
		WHEN 11 THEN b'1'
		WHEN 12 THEN b'1'
		WHEN 13 THEN b'0'
		WHEN 14 THEN b'1'
		WHEN 15 THEN b'1'
		WHEN 16 THEN b'1'
		WHEN 17 THEN b'1'
		WHEN 18 THEN b'1'
		WHEN 19 THEN b'1'
		ELSE b'1'
	END AS enabled
FROM (
	SELECT n, MOD(n - 1, 20) + 1 AS slot
	FROM seq
) AS seeded;