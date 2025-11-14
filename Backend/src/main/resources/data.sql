-- ===========================================
-- LIMPIAR TABLAS SIN BORRARLAS
-- ===========================================
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE comida;
TRUNCATE TABLE ingrediente_receta;
TRUNCATE TABLE ingrediente;
TRUNCATE TABLE dia;
TRUNCATE TABLE receta;

SET FOREIGN_KEY_CHECKS = 1;

-- ===========================================
-- LIMPIAR TABLAS (si deseas reiniciar)
-- ===========================================
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE ingrediente_receta;
TRUNCATE TABLE ingrediente;
TRUNCATE TABLE receta;
SET FOREIGN_KEY_CHECKS = 1;

-- ============================
-- RECETAS
-- ============================
INSERT INTO receta (nombre, descripcion, macronutriente) VALUES
('Arroz con atún', 'Arroz blanco con atún en agua', 'PROTEINA'),
('Ensalada de pollo', 'Ensalada fresca con pollo', 'PROTEINA'),
('Huevos revueltos', 'Huevos con tomate y cebolla', 'PROTEINA'),
('Tacos de carne', 'Tortillas con carne y vegetales', 'PROTEINA'),
('Avena con frutas', 'Avena con banano y manzana', 'CARBOHIDRATO'),
('Pasta con pollo', 'Pasta con pollo y cebolla', 'CARBOHIDRATO'),
('Sopa de avena', 'Avena caliente con leche', 'CARBOHIDRATO'),
('Ensalada de frutas', 'Varias frutas mezcladas', 'CARBOHIDRATO'),
('Aguacate relleno', 'Aguacate con pollo y queso', 'GRASA'),
('Huevos con queso y aguacate', 'Huevos, aguacate y queso', 'GRASA'),
('Ensalada keto', 'Lechuga, aguacate y queso', 'GRASA'),
('Ensalada cesar', 'Lechuga, aguacate y queso', 'GRASA');

-- ============================
-- INGREDIENTES
-- ============================
INSERT INTO ingrediente (nombre) VALUES
('Arroz'),
('Atún'),
('Pollo'),
('Lechuga'),
('Tomate'),
('Avena'),
('Banano'),
('Manzana'),
('Pasta'),
('Espinaca'),
('Cebolla'),
('Carne molida'),
('Tortilla'),
('Leche'),
('Fresa'),
('Piña'),
('Yogurt'),
('Aguacate'),
('Queso'),
('Aceite de oliva');

-- ============================
-- INGREDIENTE_RECETA  (✔ corregido)
-- ============================

-- Arroz con atún (1)
INSERT INTO ingrediente_receta (id_receta, id_ingrediente, cantidad) VALUES
(1, 1, 100),
(1, 2, 80);

-- Ensalada de pollo (2)
INSERT INTO ingrediente_receta (id_receta, id_ingrediente, cantidad) VALUES
(2, 3, 100),
(2, 4, 50),
(2, 5, 50);

-- Huevos revueltos (3)
INSERT INTO ingrediente_receta (id_receta, id_ingrediente, cantidad) VALUES
(3, 11, 30),
(3, 5, 40);

-- Tacos de carne (4)
INSERT INTO ingrediente_receta (id_receta, id_ingrediente, cantidad) VALUES
(4, 12, 120),
(4, 13, 2),
(4, 5, 30),
(4, 18, 40);

-- Avena con frutas (5)
INSERT INTO ingrediente_receta (id_receta, id_ingrediente, cantidad) VALUES
(5, 6, 50),
(5, 7, 1),
(5, 8, 1);

-- Pasta con pollo (6)
INSERT INTO ingrediente_receta (id_receta, id_ingrediente, cantidad) VALUES
(6, 9, 120),
(6, 3, 80),
(6, 11, 30);

-- Sopa de avena (7)
INSERT INTO ingrediente_receta (id_receta, id_ingrediente, cantidad) VALUES
(7, 6, 50),
(7, 14, 200);

-- Ensalada de frutas (8)
INSERT INTO ingrediente_receta (id_receta, id_ingrediente, cantidad) VALUES
(8, 15, 60),
(8, 16, 60),
(8, 7, 1),
(8, 8, 1);

-- Aguacate relleno (9)
INSERT INTO ingrediente_receta (id_receta, id_ingrediente, cantidad) VALUES
(9, 18, 100),
(9, 3, 80),
(9, 19, 50);

-- Huevos con queso y aguacate (10)
INSERT INTO ingrediente_receta (id_receta, id_ingrediente, cantidad) VALUES
(10, 18, 60),
(10, 19, 40),
(10, 11, 20);

-- Ensalada keto (11)
INSERT INTO ingrediente_receta (id_receta, id_ingrediente, cantidad) VALUES
(11, 4, 50),
(11, 18, 80),
(11, 19, 50),
(11, 20, 10);

-- DÍAS
INSERT INTO dia (fecha) VALUES
('2025-11-10'),
('2025-11-11'),
('2025-11-12');

-- COMIDAS
INSERT INTO comida (dia_id, receta_id, hora) VALUES
(1, 1, '08:00'),
(1, 2, '13:00'),
(2, 3, '07:30');
