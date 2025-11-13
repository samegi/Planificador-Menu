import { useEffect, useState } from "react";
import { Bar } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";

// Registrar componentes de ChartJS
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

export default function MacronutrientesCharts() {
  const [data, setData] = useState(null);

  useEffect(() => {
    fetch("http://localhost:8080/estadisticas/recetas/macronutrientes")
      .then((res) => res.json())
      .then((json) => setData(json))
      .catch((err) => console.error("Error cargando datos:", err));
  }, []);

  if (!data) return <p>Cargando gráfica...</p>;

  return (
    <Bar
      data={{
        labels: Object.keys(data),
        datasets: [
          {
            label: "Cantidad de recetas",
            data: Object.values(data),
            backgroundColor: ["#4e79a7", "#f28e2b", "#e15759"],
          },
        ],
      }}
      options={{
        responsive: true,
        plugins: {
          title: {
            display: true,
            text: "Distribución por macronutriente",
          },
          legend: {
            display: false,
          },
        },
      }}
    />
  );
}
