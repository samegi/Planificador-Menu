-- ===========================================
-- LIMPIAR TABLAS EN ORDEN (para evitar FK)
-- ===========================================
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE comida;
TRUNCATE TABLE ingrediente_receta;
TRUNCATE TABLE ingrediente;
TRUNCATE TABLE dia;
TRUNCATE TABLE receta;

SET FOREIGN_KEY_CHECKS = 1;

-- ===========================================
-- RECETAS (IDs auto-increment, NO ponemos id)
-- ===========================================
INSERT INTO receta (nombre, descripcion, macronutriente) VALUES
('Arroz con atún', 'Arroz blanco con atún en agua', 'PROTEINA'),
('Ensalada de pollo', 'Ensalada fresca con pollo', 'PROTEINA'),
('Avena con frutas', 'Avena con banano y manzana', 'CARBOHIDRATO');

-- Después de esto, los IDs quedan así:
-- receta.id = 1 -> Arroz con atún
-- receta.id = 2 -> Ensalada de pollo
-- receta.id = 3 -> Avena con frutas

-- ===========================================
-- INGREDIENTES
-- ===========================================
INSERT INTO ingrediente (nombre) VALUES
('Arroz'),       -- id = 1
('Atún'),        -- id = 2
('Pollo'),       -- id = 3
('Lechuga'),     -- id = 4
('Tomate'),      -- id = 5
('Avena'),       -- id = 6
('Banano'),      -- id = 7
('Manzana');     -- id = 8

-- ===========================================
-- INGREDIENTE_RECETA
-- OJO: cantidad solo numérica (coincide con tu columna)
-- ===========================================
INSERT INTO ingrediente_receta (id_receta, id_ingrediente, cantidad) VALUES
-- Arroz con atún
(1, 1, 100),   -- 100 g de arroz
(1, 2, 80),    -- 80 g de atún

-- Ensalada de pollo
(2, 3, 100),
(2, 4, 50),
(2, 5, 50),

-- Avena con frutas
(3, 6, 60),
(3, 7, 1),
(3, 8, 1);

-- ===========================================
-- DÍAS
-- ===========================================
INSERT INTO dia (fecha) VALUES
('2025-11-10'),
('2025-11-11'),
('2025-11-12');

-- IDs auto:
-- dia.id = 1 -> 2025-11-10
-- dia.id = 2 -> 2025-11-11
-- dia.id = 3 -> 2025-11-12

-- ===========================================
-- COMIDAS
-- ===========================================
INSERT INTO comida (dia_id, receta_id, hora) VALUES
(1, 1, '08:00'),
(1, 2, '13:00'),
(2, 3, '07:30');
