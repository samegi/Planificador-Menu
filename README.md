# Planificador de Menú

Este proyecto es una aplicación desarrollada con **Spring Boot 3** para gestionar menús diarios.  
Permite **crear, actualizar y eliminar ingredientes, recetas, comidas y días**, además de **relacionarlos entre sí** para generar menús completos.

---

## Herramientas necesarias

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- Java 17 o superior
- Maven 3.8+
- Spring Boot 3.x
- (Opcional) AlwaysData o H2 Database

Por defecto, el proyecto usa una base de datos embebida **H2** para las pruebas de integración.  
Si se desea persistencia real, se puede configurar una base de datos externa en `application.properties`.

---

## Instalación y ejecución

### 1. Clonar el repositorio
```bash
git clone https://github.com/usuario/planificador-menu.git
cd planificador-menu
```

### 2. Ejecutar pruebas (unitarias e integración)
```bash
mvn clean test
```

### 3. Iniciar la aplicación
```bash
mvn spring-boot:run
```

Por defecto, el servidor se ejecuta en:  
`http://localhost:8080`

---

## Endpoints principales

A continuación se muestran los endpoints disponibles y ejemplos para probarlos con Postman o cualquier cliente HTTP.

| Entidad | Método | Endpoint | Descripción |
|----------|---------|-----------|-------------|
| Ingredientes | GET | /api/ingredientes | Listar todos los ingredientes |
| | POST | /api/ingredientes | Crear un nuevo ingrediente |
| | PUT | /api/ingredientes/{id} | Actualizar un ingrediente existente |
| | DELETE | /api/ingredientes/{id} | Eliminar un ingrediente |
| Recetas | GET | /api/recetas | Listar todas las recetas |
| | GET | /api/recetas/{id} | Obtener una receta por ID |
| | POST | /api/recetas | Crear una receta (con ingredientes asociados) |
| | PUT | /api/recetas/{id} | Actualizar una receta |
| | DELETE | /api/recetas/{id} | Eliminar una receta |
| Ingredientes-Receta | GET | /api/ingredientes-receta | Listar combinaciones de ingredientes y recetas |
| Comidas | GET | /api/comidas | Listar todas las comidas |
| | POST | /api/comidas/{recetaId} | Crear una comida asociada a una receta existente |
| | PUT | /api/comidas/{id} | Actualizar hora o receta de una comida |
| | DELETE | /api/comidas/{id} | Eliminar una comida |
| Días | GET | /api/dias | Listar todos los días |
| | POST | /api/dias | Crear un nuevo día |
| | PUT | /api/dias/{id} | Actualizar la fecha de un día |
| | DELETE | /api/dias/{id} | Eliminar un día |
| | POST | /api/dias/{diaId}/comidas/{recetaId} | Agregar una comida a un día específico |

---

## Ejemplos de uso en Postman

### Crear un ingrediente
```http
POST http://localhost:8080/api/ingredientes
Content-Type: application/json
```
```json
{
  "nombre": "Chocolate"
}
```

### Consultar ingredientes existentes
```http
GET http://localhost:8080/api/ingredientes
```

---

### Crear una receta (con ingredientes existentes)
Usa los **IDs** de los ingredientes previamente creados.
```http
POST http://localhost:8080/api/recetas
Content-Type: application/json
```
```json
{
  "nombre": "Pastel de vainilla",
  "descripcion": "Cuidado para los alérgicos a la lactosa.",
  "macronutriente": "CARBOHIDRATO",
  "ingredientesReceta": [
    { "ingrediente": { "id": 5 }, "cantidad": 200.0 },
    { "ingrediente": { "id": 6 }, "cantidad": 100.0 },
    { "ingrediente": { "id": 7 }, "cantidad": 300.0 }
  ]
}
```

---

### Crear una comida a partir de una receta existente
```http
POST http://localhost:8080/api/comidas/{recetaId}
Content-Type: application/json
```
```json
{
  "hora": "12:30"
}
```

---

### Crear un día y asociarle una comida
1. Crear el día:
```http
POST http://localhost:8080/api/dias
Content-Type: application/json
```
```json
{
  "fecha": "2025-10-19"
}
```

2. Agregar una comida existente al día:
```http
POST http://localhost:8080/api/dias/{diaId}/comidas/{recetaId}
```

---

## Pruebas de integración

Ejecuta todas las pruebas con:
```bash
mvn test
```
Cada clase tiene su prueba unitaria correspondiente

Las pruebas de inetgracion verifican:
- Persistencia en base de datos  
- Actualización y eliminación de entidades  
- Relaciones entre recetas, comidas y días  

Cada test usa `@SpringBootTest` y `@Transactional`, lo que asegura que los datos se revierten automáticamente después de cada ejecución.

---
## Autores
Catalina Ospina, Federico Alarcón, Sara Mejia y Sara Muñoz

Desarrollado por OsMe  
Proyecto académico - Universidad Javeriana 
2025
