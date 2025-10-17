# Planificador-Menu
pruebas unitarias y pruebas de integracion
mvn clean test

correr codigo
mvn spring-boot:run

desde POSTMAN

crear ingrediente
 {
    "nombre": "Chocolate"
 }

crear receta, se debe realizar con los id's de los ingredientes previamente creados.
{
  "nombre": "Pastel de vainilla",
  "descripcion": "Cuidado para los alérgicos a la lactosa.",
  "macronutriente": "CARBOHIDRATO",
  "ingredientesReceta": [
    {
      "ingrediente": { "id": 5 },
      "cantidad": 200.0
    },
    {
      "ingrediente": { "id": 6 },
      "cantidad": 100.0
    },
    {
      "ingrediente": { "id": 7 },
      "cantidad": 300.0
    }
  ]
}